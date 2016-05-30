package Utilities;

/**
 * Created by Mykola on 30.05.2016.
 */
public class FileUtilities
{
    public static String GetFolderPathFromFile(String filePath)
    {
        String startFolder;
        int lastSlesh = filePath.lastIndexOf('/');

        try {
            startFolder = filePath.substring(0, lastSlesh);
        }
        catch (IndexOutOfBoundsException e)
        {
            return "";
        }

        return startFolder;
    }
}
