package com.tsilva.countdown.storage.utils;

import android.widget.Toast;

import com.tsilva.countdown.services.StorageService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Telmo Silva on 28.01.2020.
 */

public final class UtilsManager
{
    private static UtilsManager utilsManagerInstance = null;
    private static boolean isInitialized = false;

    private static final String VALID_EMAIL_ADDRESS_REGEX =
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    private StorageService storageService = null;

    private UtilsManager() {}

    public static UtilsManager utilsManagerInstance()
    {
        if(utilsManagerInstance == null)
        {
            utilsManagerInstance = new UtilsManager();
        }
        return utilsManagerInstance;
    }

    public void init(
            StorageService storageService)
    {
        if(!isInitialized)
        {
            this.storageService = storageService;
        }

        isInitialized = true;
    }

    public boolean validateEmail(String emailTextContent)
    {
        if(emailTextContent != null)
        {
            emailTextContent = emailTextContent.trim();

            Pattern pattern = Pattern.compile(VALID_EMAIL_ADDRESS_REGEX, Pattern.CASE_INSENSITIVE);

            Matcher matcher = pattern.matcher(emailTextContent);

            return matcher.find();
        }

        return false;
    }

    public String dateToString(Date date)
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK);

        return format.format(date);
    }

    public String buildShareListString(List<String> shareList)
    {
        if(shareList != null && !shareList.isEmpty())
        {
            int shareListSize = shareList.size();
            if(shareListSize > 1)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("");

                for(int i = 1; i < shareListSize; i++)
                {
                    sb.append(shareList.get(i));

                    if(i != shareListSize - 1)
                    {
                        sb.append(", ");
                    }
                }

                return sb.toString();
            }
        }

        return "";
    }

    public void makeToast(String str)
    {
        Toast.makeText(storageService.getContext(), str, Toast.LENGTH_SHORT).show();
    }
}