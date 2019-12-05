package com.tsilva.countdown.Services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

public final class PersistenceService
{
    private static final String TAG = "PersistenceService";

    private Context context = null;
    private String imageCacheDirPath = null;

    private PersistenceService() {}

    private PersistenceService(Context context)
    {
        this.context = context;
        this.imageCacheDirPath = context.getFilesDir().getPath()
                + File.separator + "imageCache";
    }

    public static PersistenceService persistenceServiceInstance(Context context)
    {
        return new PersistenceService(context);
    }

    // public methods
    // ============================

    /**
     *
     * @param dataObject object to be saved. Implements {@link Serializable}
     * @return true if successfully saved, false otherwise
     */
    public <T extends Serializable> boolean saveSerializableObject(Context context, T dataObject)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try
        {
            fileOutputStream = context
                    .openFileOutput(dataObject.getClass().getSimpleName(), Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(dataObject);

            return true;
        }
        catch(IOException e)
        {
            Log.e(TAG, "saveSerializableObject: couldn't save " + dataObject.getClass().getSimpleName(), e);

            return false;
        }
        finally
        {
            if(objectOutputStream != null)
            {
                try
                {
                    objectOutputStream.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param blankDataObject object to be populated. Implements {@link Serializable}
     * @return true if successfully populated blankDataObject, false otherwise
     */
    public <T extends Serializable> boolean loadSerializableObject(Context context, T blankDataObject)
    {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try
        {
            fileInputStream = context.openFileInput(blankDataObject.getClass().getSimpleName());
            objectInputStream = new ObjectInputStream(fileInputStream);

            Object o = objectInputStream.readObject();
            if(o.getClass().equals(blankDataObject))
            {
                blankDataObject = (T) objectInputStream.readObject();
                return true;
            }

            return false;
        }
        catch(IOException e)
        {
            Log.e(TAG, "loadSerializableObject: couldn't load " + blankDataObject.getClass().getSimpleName(), e);
            return false;
        }
        catch(ClassNotFoundException e)
        {
            Log.e(TAG, "loadSerializableObject: couldn't find " + blankDataObject.getClass().getSimpleName(), e);
            return false;
        }
        finally
        {
            if(objectInputStream != null)
            {
                try
                {
                    objectInputStream.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            if(fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public Drawable loadImage(File image)
    {
        if(image.exists() && image.isFile())
        {
            return Drawable.createFromPath(image.getPath());
        }

        return null;
    }

    public Drawable[] loadCachedImages()
    {
        File dir = new File(imageCacheDirPath);

        if(dir.exists())
        {
            File[] images = dir.listFiles();

            if(images != null)
            {
                int imageCount = images.length;

                if(imageCount > 0)
                {
                    Drawable[] drawables = new Drawable[imageCount];

                    for(int i = 0; i < imageCount; i++)
                    {
                        drawables[i] = loadImage(images[i]);
                    }

                    return drawables;
                }
            }
        }

        return null;
    }

    public void saveImage(Bitmap bitmap, String fileName)
    {
        File dir = new File(imageCacheDirPath);
        File imageFile = new File(imageCacheDirPath + File.separator + fileName + ".png");

        if(!dir.exists())
        {
            dir.mkdir();
        }

        if(!imageFile.exists())
        {
            try
            {
                imageFile.createNewFile();
            }
            catch(IOException e)
            {
                Log.e(TAG, "saveImage: couldn't create the file", e);
            }
        }

        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
        }
        catch(SecurityException e)
        {
            Log.e(TAG, "saveImage: security exception", e);
        }
        catch(FileNotFoundException e)
        {
            Log.e(TAG, "saveImage: couldn't save image", e);
        }
        finally
        {
            try
            {
                if(fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    // private methods
    // ============================
}