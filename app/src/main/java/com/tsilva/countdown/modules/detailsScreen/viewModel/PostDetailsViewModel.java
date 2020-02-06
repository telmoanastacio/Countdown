package com.tsilva.countdown.modules.detailsScreen.viewModel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Base64;
import android.view.View;

import androidx.databinding.ObservableField;

import com.tsilva.countdown.R;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.services.StorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

/**
 * Created by Telmo Silva on 04.02.2020.
 */

public final class PostDetailsViewModel
{
    private static String blankContent = "&#160&#160&#160&#160&#160&#160-";

    private Context context = null;
    private StorageService storageService = null;
    private CountdownEventDto countdownEventDto = null;
    private Date now = new Date();

    public PostDetailsObservables postDetailsObservables = null;

    private PostDetailsViewModel() {}

    PostDetailsViewModel(
            Context context,
            StorageService storageService,
            CountdownEventDto countdownEventDto)
    {
        this.context = context;
        this.storageService = storageService;
        this.countdownEventDto = countdownEventDto;

        setup();
    }

    private void setup()
    {
        this.postDetailsObservables = new PostDetailsObservables();

        if(countdownEventDto != null)
        {
            {
                String title = countdownEventDto.title;
                String label = context.getResources().getString(R.string.title);
                if(title == null || title.isEmpty())
                {
                    title = blankContent;
                }
                Spanned spanned = generateLabelContentText(label, title);
                postDetailsObservables.titleTextContent.set(spanned);
            }

            {
                String details = countdownEventDto.details;
                String label = context.getResources().getString(R.string.details);
                if(details == null || details.isEmpty())
                {
                    details = blankContent;
                }
                Spanned spanned = generateLabelContentText(label, details);
                postDetailsObservables.detailsTextContent.set(spanned);
            }

            {
                Long tsi = countdownEventDto.tsi;
                String label = context.getResources().getString(R.string.details_tsi);
                String content = blankContent;
                if(tsi != null)
                {
                    Date tsiDate = new Date(tsi);
                    content = storageService.getUtilsManager().dateToString(tsiDate);
                }
                Spanned spanned = generateLabelContentText(label, content);
                postDetailsObservables.tsiTextContent.set(spanned);
            }

            Long tsf = countdownEventDto.tsf;
            {
                String label = context.getResources().getString(R.string.details_tsf);
                String content = blankContent;
                if(tsf != null)
                {
                    Date tsfDate = new Date(tsf);
                    content = storageService.getUtilsManager().dateToString(tsfDate);
                }
                Spanned spanned = generateLabelContentText(label, content);
                postDetailsObservables.tsfTextContent.set(spanned);
            }

            {
                String label = context.getResources().getString(R.string.remaining);
                String content = remainingTime(tsf);
                Spanned spanned = generateLabelContentText(label, content);
                postDetailsObservables.remainingTextContent.set(spanned);
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
                    postDetailsObservables.imageViewDrawable.set(drawable);
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
        else
        {
            onDismissClick(null);
        }
    }

    public void onBackPressed()
    {
        onDismissClick(null);
    }

    public void onDismissClick(View view)
    {
        storageService.getActivityManager().getCurrentActivity().finishAffinity();
    }

    /**
     *
     * @return text with a label in bold if label is not null.
     * null if content is null
     */
    private Spanned generateLabelContentText(@Nullable String label, String content)
    {
        if(content == null)
        {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("");
        if(label != null && !label.isEmpty())
        {
            sb.append("<b>");
            sb.append(label);
            sb.append(":");
            sb.append("</b> ");
        }
        sb.append(content);

        return Html.fromHtml(sb.toString());
    }

    private String remainingTime(Long tsf)
    {
        if(tsf != null)
        {
            long diff = tsf - now.getTime();

            if(diff <= 0L)
            {
                return blankContent;
            }
            else
            {
                long days = 0L;
                long hours = 0L;
                long minutes = 0L;
                StringBuilder sb = new StringBuilder();
                sb.append("");

                days = TimeUnit.MILLISECONDS.toDays(diff);
                diff = diff - TimeUnit.DAYS.toMillis(days);

                hours = TimeUnit.MILLISECONDS.toHours(diff);
                diff = diff - TimeUnit.HOURS.toMillis(hours);

                minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

                Resources resources = context.getResources();
                if(days != 0L)
                {
                    sb.append(days);
                    sb.append(resources.getString(R.string.day));
                }

                if(days > 0L)
                {
                    sb.append(" ");
                }

                if(hours / 10L < 1L)
                {
                    sb.append("0");
                }
                sb.append(hours);
                sb.append(resources.getString(R.string.hour));
                if(minutes / 10L < 1L)
                {
                    sb.append("0");
                }
                sb.append(minutes);

                return sb.toString();
            }
        }

        return blankContent;
    }

    public final class PostDetailsObservables
    {
        public ObservableField<Spanned> titleTextContent = new ObservableField<>();
        public ObservableField<Spanned> detailsTextContent = new ObservableField<>();
        public ObservableField<Spanned> tsiTextContent = new ObservableField<>();
        public ObservableField<Spanned> tsfTextContent = new ObservableField<>();
        public ObservableField<Spanned> remainingTextContent = new ObservableField<>();

        public ObservableField<Drawable> imageViewDrawable = new ObservableField<>();

        private PostDetailsObservables()
        {
            titleTextContent.set(new SpannedString(""));
            detailsTextContent.set(new SpannedString(""));
            tsiTextContent.set(new SpannedString(""));
            tsfTextContent.set(new SpannedString(""));
            remainingTextContent.set(new SpannedString(""));

            imageViewDrawable.set(null);
        }
    }
}