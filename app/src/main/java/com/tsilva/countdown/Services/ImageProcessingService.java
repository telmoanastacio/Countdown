package com.tsilva.countdown.Services;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

public final class ImageProcessingService
{
    private Context context = null;
    private PersistenceService persistenceService = null;

    private ImageProcessingService() {}

    private ImageProcessingService(Context context, PersistenceService persistenceService)
    {
        this.context = context;
        this.persistenceService = persistenceService;
    }

    public static ImageProcessingService imageProcessingServiceInstance
            (Context context,
             PersistenceService persistenceService)
    {
        return new ImageProcessingService(context, persistenceService);
    }

    public void constantFrameRateBuildCache(Uri uri, long stepMillis)
    {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(context, uri);
        String METADATA_KEY_DURATION = mediaMetadataRetriever
                .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long millis = Long.valueOf(METADATA_KEY_DURATION);

        int position = 0;
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
                    persistenceService.saveImage(bitmap, "" + position);
                    position++;
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
                        persistenceService.saveImage(bitmap, "" + position);
                        position++;
                    }
                }
            }
        }
    }
}