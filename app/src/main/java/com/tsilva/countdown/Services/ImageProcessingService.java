package com.tsilva.countdown.Services;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import com.tsilva.countdown.Activities.MainActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

public final class ImageProcessingService
{
    private Context context = null;

    private ImageProcessingService() {}

    private ImageProcessingService(Context context)
    {
        this.context = context;
    }

    public static ImageProcessingService imageProcessingServiceInstance(Context context)
    {
        return new ImageProcessingService(context);
    }

    public List<Bitmap> constantFrameRateBuildList(Uri uri, long stepMillis)
    {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(context, uri);
        String METADATA_KEY_DURATION = mediaMetadataRetriever
                .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long millis = Long.valueOf(METADATA_KEY_DURATION);

        List<Bitmap> bitmapList = new LinkedList<>();
        for(long i = 0; i <= millis; i = i + stepMillis)
        {
            Bitmap bitmap = mediaMetadataRetriever
                    .getFrameAtTime(TimeUnit.MILLISECONDS.toMicros(i),
                                    MediaMetadataRetriever.OPTION_CLOSEST);
            if(bitmap != null)
            {
                if(bitmapList.isEmpty())
                {
                    bitmapList.add(bitmap);
                }
                else
                {
                    int lastIndex = bitmapList.size() - 1;
                    if(bitmapList.get(lastIndex).sameAs(bitmap))
                    {
                        continue;
                    }
                    else
                    {
                        bitmapList.add(bitmap);
                    }
                }
            }
        }

        return bitmapList;
    }
}