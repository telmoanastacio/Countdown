package com.tsilva.countdown.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.tsilva.countdown.persistence.sharedPreferences.SharedPreferencesOperations;

import java.io.EOFException;
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

    private SharedPreferencesOperations sharedPreferencesOperations = null;
    private String imageCacheDirPath = null;
    private String keyDir = null;

    private PersistenceService() {}

    private PersistenceService(Context context)
    {
        this.sharedPreferencesOperations = new SharedPreferencesOperations(context);
        this.imageCacheDirPath = context.getFilesDir().getPath()
                + File.separator + "imageCache";
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if(dir != null)
        {
            String basePackageName = context.getPackageName();
            this.keyDir = dir.getPath()
                    .replace("Android/data/" + basePackageName + "/files/", "");
        }
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
     * @param name name of the destination file.
     * @return true if successfully saved, false otherwise
     */
    public <T extends Serializable> boolean saveSerializableObject(
            Context context,
            T dataObject,
            String name)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try
        {
            fileOutputStream = context
                    .openFileOutput(name, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(dataObject);

            return true;
        }
        catch(IOException e)
        {
            Log.e(TAG, "saveSerializableObject: couldn't save " + name, e);

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
     * Same as saveSerializableObject(Context context, T dataObject, String name)
     * but no file name provided
     */
    public <T extends Serializable> boolean saveSerializableObject(
            Context context,
            T dataObject)
    {
        return saveSerializableObject(context, dataObject,
                                      dataObject.getClass().getSimpleName() + ".dat");
    }

    /**
     *
     * @param expectedReturnClass is the class object of {@link T} and {@link T}
     *                            implements {@link Serializable}
     * @param name name of the object file source.
     * @return {@link T} object if the file contains the same type of object
     */
    public <T extends Serializable> T loadSerializableObject(
            Context context,
            Class<T> expectedReturnClass,
            String name)
    {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try
        {
            fileInputStream = context.openFileInput(name);
            objectInputStream = new ObjectInputStream(fileInputStream);

            Object o = objectInputStream.readObject();
            if(o.getClass().equals(expectedReturnClass))
            {
                return (T) o;
            }

            return null;
        }
        catch(EOFException e)
        {
            Log.e(TAG, "loadSerializableObject: couldn't load " + name, e);
            return null;
        }
        catch(IOException e)
        {
            Log.e(TAG, "loadSerializableObject: couldn't load " + name, e);
            return null;
        }
        catch(ClassNotFoundException e)
        {
            Log.e(TAG, "loadSerializableObject: couldn't find " + name, e);
            return null;
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

    /**
     *
     * Same as loadSerializableObject(Context context, Class<T> expectedReturnClass, String name)
     * but no file name provided
     */
    public <T extends Serializable> T loadSerializableObject(
            Context context,
            Class<T> expectedReturnClass)
    {
        return loadSerializableObject(
                context, expectedReturnClass,
                expectedReturnClass.getSimpleName() + ".dat");
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

    public void saveKey(Context context, String key)
    {
        File dir = new File(keyDir);
        File keyFile = new File(dir.getPath() + File.separator + "key.dat");

        if(!dir.exists())
        {
            dir.mkdirs();
        }

        if(!keyFile.exists())
        {
            try
            {
                keyFile.createNewFile();
            }
            catch(IOException e)
            {
                Log.e(TAG, "saveKey: couldn't create the file", e);
            }
        }

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(keyFile);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            key = encodeKey(key);
            objectOutputStream.writeObject(key);
        }
        catch(SecurityException e)
        {
            Log.e(TAG, "saveKey: security exception", e);
        }
        catch(FileNotFoundException e)
        {
            Log.e(TAG, "saveKey: couldn't save image", e);
        }
        catch(IOException e)
        {
            Log.e(TAG, "saveKey: couldn't save image", e);
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

    public String loadKey()
    {
        File dir = new File(keyDir);
        File keyFile = new File(keyDir + File.separator + "key.dat");

        if(dir.exists() && keyFile.exists())
        {
            FileInputStream fileInputStream = null;
            ObjectInputStream objectInputStream = null;

            try
            {
                fileInputStream = new FileInputStream(keyFile);
                objectInputStream = new ObjectInputStream(fileInputStream);

                String key = (String) objectInputStream.readObject();
                key = decodeKey(key);

                return key;
            }
            catch(SecurityException e)
            {
                Log.e(TAG, "loadKey: lack permissions", e);
                return null;
            }
            catch(EOFException e)
            {
                Log.e(TAG, "loadKey: couldn't load key", e);
                return null;
            }
            catch(IOException e)
            {
                Log.e(TAG, "loadKey: couldn't load key", e);
                return null;
            }
            catch(ClassNotFoundException e)
            {
                Log.e(TAG, "loadKey: couldn't find key", e);
                return null;
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

        return null;
    }

    public SharedPreferencesOperations getSharedPreferencesOperations()
    {
        return sharedPreferencesOperations;
    }

    // private methods
    // ============================

    /**
     *
     * @param key must be split by ;
     */
    private String encodeKey(String key)
    {
        if(key != null && !key.isEmpty())
        {
            byte[] bytes = key.getBytes();
            StringBuilder sb = new StringBuilder();
            sb.append("");

            int character = -1;
            for(int i = 0; i < bytes.length; i++)
            {
                character = bytes[i];
                sb.append(character);
                if(i != bytes.length - 1)
                {
                    sb.append('.');
                }
            }

            return sb.toString();
        }

        return "";
    }

    private String decodeKey(String encodedKey)
    {
        if(encodedKey != null && !encodedKey.isEmpty())
        {
            StringBuilder decodedKeySB = new StringBuilder();
            decodedKeySB.append("");

            String[] chars = encodedKey.split("\\.");

            for(String intChar : chars)
            {
                int character = Integer.valueOf(intChar);
                decodedKeySB.append((char) character);
            }

            return decodedKeySB.toString();
        }

        return "";
    }
}