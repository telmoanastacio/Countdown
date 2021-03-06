package com.tsilva.countdown.modules.editPost.viewModel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.ObservableField;

import com.tsilva.countdown.R;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.updateCountdownEvent.UpdateCountdownEventRequestBodyDto;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.StorageService;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

/**
 * Created by Telmo Silva on 27.01.2020.
 */

public final class EditPostViewModel
{
    public static final int IMAGE_PICK_REQUEST_CODE = 9020;

    public EditPostObservables editPostObservables = null;
    private boolean isEdit = false;
    private Context context = null;
    private StorageService storageService = null;
    private UserLoginCredentials userLoginCredentials = null;
    private View root = null;
    private String postId = null;
    private CountdownEventDto countdownEventDto = null;
    private Date dateTsi = null;
    private Date dateTsf = null;
    private List<String> emailList = new LinkedList<>();
    private EditText editTextTsi = null;
    private EditText editTextTsf = null;

    private EditPostViewModel() {}

    /**
     * If any of the parameters (<b>postId</b> or <b>countdownEventDto</b>)
     * is null a new event is to be created,
     * otherwise an event is to be patched.
     */
    EditPostViewModel(
            Context context,
            StorageService storageService,
            UserLoginCredentials userLoginCredentials,
            @NotNull View root,
            @Nullable String postId,
            @Nullable CountdownEventDto countdownEventDto)
    {
        this.context = context;
        this.storageService = storageService;
        this.userLoginCredentials = userLoginCredentials;
        this.root = root;
        this.postId = postId;
        this.countdownEventDto = countdownEventDto;

        this.isEdit = this.postId != null && this.countdownEventDto != null;

        setup();
    }

    private void setup()
    {
        this.editPostObservables = new EditPostObservables();

        editTextTsi = root.findViewById(R.id.editTextTsi);
        editTextTsf = root.findViewById(R.id.editTextTsf);

        if(isEdit)
        {
            setupEditPost();
        }
    }

    private void setupEditPost()
    {
        if(countdownEventDto != null)
        {
            String title = countdownEventDto.title;
            if(title != null)
            {
                editPostObservables.titleTextContent.set(title);
            }

            String details = countdownEventDto.details;
            if(details != null)
            {
                editPostObservables.detailsTextContent.set(details);
            }

            Long tsi = countdownEventDto.tsi;
            if(tsi != null)
            {
                Date tsiDate = new Date(tsi);
                String str = storageService.getUtilsManager().dateToString(tsiDate);
                if(str != null && !str.isEmpty())
                {
                    editPostObservables.tsiTextContent.set(str);
                }
            }

            Long tsf = countdownEventDto.tsf;
            if(tsf != null)
            {
                Date tsfDate = new Date(tsf);
                String str = storageService.getUtilsManager().dateToString(tsfDate);
                if(str != null && !str.isEmpty())
                {
                    editPostObservables.tsfTextContent.set(str);
                }
            }

            List<String> shareList = countdownEventDto.shareWith;
            if(shareList != null)
            {
                String str = storageService.getUtilsManager().buildShareListString(shareList);
                editPostObservables.shareTextContent.set(str);
            }

            String base64Img = countdownEventDto.img;
            if(base64Img != null)
            {
                ByteArrayInputStream inputStream = null;
                try
                {
                    byte[] imageBytes = Base64.decode(base64Img, Base64.DEFAULT);
                    inputStream = new ByteArrayInputStream(imageBytes);
                    Drawable drawable = Drawable.createFromStream(inputStream, null);
                    editPostObservables.imageViewDrawable.set(drawable);
                }
                catch(IllegalArgumentException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if(inputStream != null)
                    {
                        try
                        {
                            inputStream.close();
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void onBackPressed()
    {
        onCancelClick(null);
    }

    public void onImageClick(View view)
    {
        Intent pickPhoto = new Intent();
        pickPhoto.setType("image/*");
        pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
        pickPhoto.putExtra("return-data", true);
        storageService.getActivityManager().getCurrentActivity()
                .startActivityForResult(pickPhoto, IMAGE_PICK_REQUEST_CODE);
    }

    public void onConfirmClick(View view)
    {
        if(validate(new Date()))
        {
            String img = (countdownEventDto == null || countdownEventDto.img == null)
                    ? "" : countdownEventDto.img;
            if(!isEdit)
            {
                PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto =
                        new PostCountdownEventRequestBodyDto(
                                userLoginCredentials.getEmail(),
                                editPostObservables.titleTextContent.get(),
                                editPostObservables.detailsTextContent.get(),
                                img,
                                emailList,
                                dateTsi.getTime(),
                                dateTsf.getTime());

                try
                {
                    storageService.getSharedViewModelManager()
                            .getPostListViewModel()
                            .fetchPostCountdownEvent(postCountdownEventRequestBodyDto);
                }
                catch(Throwable throwable)
                {
                    throwable.printStackTrace();
                }
            }
            else
            {
                UpdateCountdownEventRequestBodyDto updateCountdownEventRequestBodyDto =
                        new UpdateCountdownEventRequestBodyDto(
                                userLoginCredentials.getEmail(),
                                editPostObservables.titleTextContent.get(),
                                editPostObservables.detailsTextContent.get(),
                                img,
                                emailList,
                                dateTsi.getTime(),
                                dateTsf.getTime());

                try
                {
                    storageService.getSharedViewModelManager()
                            .getPostListViewModel()
                            .fetchPatchCountdownEvent(postId, updateCountdownEventRequestBodyDto);
                }
                catch(Throwable throwable)
                {
                    throwable.printStackTrace();
                }
            }

            onCancelClick(null);
        }
    }

    public void onCancelClick(View view)
    {
        storageService.getActivityManager().getCurrentActivity().finishAffinity();
    }

    public TextWatcher setStartDateTimeTextWatcher()
    {
        return dateTimeTextWatcher(true);
    }

    public TextWatcher setEndDateTimeTextWatcher()
    {
        return dateTimeTextWatcher(false);
    }

    private TextWatcher dateTimeTextWatcher(final boolean isStart)
    {
        return new TextWatcher()
        {
            int beginningLength = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                if(s != null)
                {
                    String str = s.toString();
                    beginningLength = str.length();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(s != null)
                {
                    String currentStr = s.toString();
                    int currentLength = currentStr.length();

                    if(currentLength > beginningLength)
                    {
                        validateDateInput(currentStr, isStart);
                    }

                    if(isStart)
                    {
                        editTextTsi.setSelection(currentLength);
                    }
                    else
                    {
                        editTextTsf.setSelection(currentLength);
                    }
                }
            }
        };
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode == IMAGE_PICK_REQUEST_CODE && data != null)
        {
            Uri uri = data.getData();

            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        context.getContentResolver(),
                        uri);

                bitmap = rotateImageIfRequired(context, bitmap, uri);

                if(bitmap != null)
                {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    if(countdownEventDto == null)
                    {
                        countdownEventDto = new CountdownEventDto();
                    }
                    countdownEventDto.img = Base64.encodeToString(bytes, Base64.DEFAULT);
                    if(countdownEventDto.img == null)
                    {
                        countdownEventDto.img = "";
                    }

                    editPostObservables.imageViewDrawable.set(new BitmapDrawable(
                            context.getResources(),
                            bitmap));
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage)
    {
        if(context == null || img == null || selectedImage == null)
        {
            return null;
        }

        try
        {
            InputStream input = context.getContentResolver().openInputStream(selectedImage);
            ExifInterface ei;
            if(Build.VERSION.SDK_INT > 23)
            {
                ei = new ExifInterface(input);
            }
            else
            {
                ei = new ExifInterface(selectedImage.getPath());
            }

            int orientation = ei.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch(orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                {
                    return rotateImage(img, 90);
                }
                case ExifInterface.ORIENTATION_ROTATE_180:
                {
                    return rotateImage(img, 180);
                }
                case ExifInterface.ORIENTATION_ROTATE_270:
                {
                    return rotateImage(img, 270);
                }
                default:
                {
                    return img;
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap rotateImage(Bitmap img, int degree)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(
                img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    private boolean validate(Date now)
    {
        boolean isValidTitle = validateTitle();
        boolean isValidDatesSet = validateDates(now);
        boolean isValidEmailList = validateEmails();

        if(!isValidTitle)
        {
            storageService.getUtilsManager().makeToast("Title not valid");
        }
        else if(!isValidDatesSet)
        {
            storageService.getUtilsManager().makeToast("Inserted dates are not valid");
        }
        else if(!isValidEmailList)
        {
            storageService.getUtilsManager().makeToast("Inserted emails are not valid");
        }

        return isValidTitle && isValidDatesSet && isValidEmailList;
    }

    private boolean validateTitle()
    {
        String titleStr = editPostObservables.titleTextContent.get();

        if(titleStr == null)
        {
            return false;
        }

        titleStr = titleStr.replace(" ", "");

        return !titleStr.isEmpty();
    }

    private boolean validateDates(Date now)
    {
        DateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm", Locale.UK);
        format.setLenient(false);
        try
        {
            dateTsi = format.parse(editPostObservables.tsiTextContent.get());
            dateTsf = format.parse(editPostObservables.tsfTextContent.get());

            return dateTsi.before(dateTsf) && dateTsf.after(now);
        }
        catch(ParseException e)
        {
            dateTsi = null;
            dateTsf = null;
            return false;
        }
    }

    private boolean validateEmails()
    {
        emailList.clear();
        emailList.add(userLoginCredentials.getEmail());
        String str = this.editPostObservables.shareTextContent.get();

        if(str != null)
        {
            str = str.replaceAll("\\s", "");
            if(!str.isEmpty())
            {
                String[] emails = str.split(",");
                int emailsLength = emails.length;
                if(emailsLength > 0)
                {
                    for(String email : emails)
                    {
                        if(storageService.getUtilsManager().validateEmail(email))
                        {
                            emailList.add(email);
                        }
                    }

                    return (emailList.size() - 1) == emailsLength;
                }
            }
        }

        return true;
    }

    private void validateDateInput(String dateStr, boolean isStart)
    {
        if(dateStr != null)
        {
            if(!dateStr.isEmpty())
            {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                char[] dateCharArray = dateStr.toCharArray();

                for(int i = 0; i < dateCharArray.length; i++)
                {
                    char dateChar = dateCharArray[i];
                    // year
                    if(i < 3)
                    {
                        if(validateNumber(dateChar, null))
                        {
                            sb.append(dateChar);
                        }
                        else
                        {
                            break;
                        }
                    }
                    else if(i == 3)
                    {
                        if(validateNumber(dateChar, null))
                        {
                            sb.append(dateChar);
                            sb.append('-');
                        }
                        else
                        {
                            break;
                        }
                    }
                    // month
                    else if(i == 5)
                    {
                        if(validateNumber(dateChar, 1))
                        {
                            sb.append(dateChar);
                        }
                        else
                        {
                            break;
                        }
                    }
                    else if(i == 6)
                    {
                        if(validateNumber(dateChar, null))
                        {
                            String monthStr = "" + dateCharArray[5] + dateCharArray[6];
                            try
                            {
                                Integer monthInt = Integer.valueOf(monthStr);
                                if(monthInt <= 12)
                                {
                                    sb.append(dateChar);
                                    sb.append('-');
                                }
                            }
                            catch(NumberFormatException e)
                            {
                                break;
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                    // day
                    else if(i == 8)
                    {
                        if(validateNumber(dateChar, 3))
                        {
                            sb.append(dateChar);
                        }
                        else
                        {
                            break;
                        }
                    }
                    else if(i == 9)
                    {
                        if(validateNumber(dateChar, null))
                        {
                            DateFormat format = new SimpleDateFormat(
                                    "yyyy-MM-dd", Locale.UK);
                            format.setLenient(false);
                            try
                            {
                                format.parse(sb.toString() + dateCharArray[9]);
                                sb.append(dateChar);
                                sb.append(' ');
                            }
                            catch(ParseException e)
                            {
                                break;
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                    // hours
                    else if(i == 11)
                    {
                        if(validateNumber(dateChar, 2))
                        {
                            sb.append(dateChar);
                        }
                        else
                        {
                            break;
                        }
                    }
                    else if(i == 12)
                    {
                        if(validateNumber(dateChar, null))
                        {
                            String monthStr = "" + dateCharArray[11] + dateCharArray[12];
                            try
                            {
                                Integer monthInt = Integer.valueOf(monthStr);
                                if(monthInt < 24)
                                {
                                    sb.append(dateChar);
                                    sb.append(':');
                                }
                            }
                            catch(NumberFormatException e)
                            {
                                break;
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                    // minutes
                    else if(i == 14)
                    {
                        if(validateNumber(dateChar, 5))
                        {
                            sb.append(dateChar);
                        }
                        else
                        {
                            break;
                        }
                    }
                    else if(i == 15)
                    {
                        if(validateNumber(dateChar, null))
                        {
                            String monthStr = "" + dateCharArray[14] + dateCharArray[15];
                            try
                            {
                                Integer monthInt = Integer.valueOf(monthStr);
                                if(monthInt < 60)
                                {
                                    sb.append(dateChar);
                                }
                            }
                            catch(NumberFormatException e)
                            {
                                break;
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                }

                if(isStart)
                {
                    editPostObservables.tsiTextContent.set(sb.toString());
                }
                else
                {
                    editPostObservables.tsfTextContent.set(sb.toString());
                }
            }
        }
    }

    /**
     * @param numberCeiling if null number will be evaluated from [0-9]
     */
    private boolean validateNumber(char numberChar, @Nullable Integer numberCeiling)
    {
        try
        {
            Integer currentNumber = Integer.valueOf("" + numberChar);
            if(numberCeiling != null)
            {
                return currentNumber <= numberCeiling;
            }
            else
            {
                return true;
            }
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    public final class EditPostObservables
    {
        public ObservableField<String> titleTextContent = new ObservableField<>();
        public ObservableField<String> detailsTextContent = new ObservableField<>();
        public ObservableField<String> tsiTextContent = new ObservableField<>();
        public ObservableField<String> tsfTextContent = new ObservableField<>();
        public ObservableField<String> shareTextContent = new ObservableField<>();

        public ObservableField<Drawable> imageViewDrawable = new ObservableField<>();

        private EditPostObservables()
        {
            titleTextContent.set("");
            detailsTextContent.set("");
            tsiTextContent.set("");
            tsfTextContent.set("");
            shareTextContent.set("");

            imageViewDrawable.set(null);
        }
    }
}