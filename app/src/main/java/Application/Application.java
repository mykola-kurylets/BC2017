package Application;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import com.kurylets.mykola.bc2017.MainActivity;
import com.kurylets.mykola.bcmodule.BCModule;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Configuration.ComfigurationManager;
import GUIManager.GUIManager;

/**
 * Created by Mykola on 17.05.2016.
 */
public class Application
{
    public Application(MainActivity mA)
    {
        m_Owner         = mA;
        m_GUIManager    = new GUIManager(this);
        m_BCModule      = new BCModule();
        m_Config        = new ComfigurationManager(this);
    }

    public boolean Load()
    {
        if(!m_Config.Load())
            return false;

        return true;
    }

    public boolean Save()
    {
        return m_Config.Save();
    }

    public boolean GunSystemLoad(String filePath)
    {
        return m_BCModule.GunSystemLoad(filePath);
    }

    public GUIManager GetGUIManager()
    {
        return m_GUIManager;
    }

    public SharedPreferences GetPreferances(String preferencesName, int mode)
    {
        return m_Owner.getSharedPreferences(preferencesName, mode);
    }

    public MainActivity GetActivity()
    {
        return m_Owner;
    }

    public boolean Calculate(InputData inD, OutputData outD)
    {
        return m_BCModule.Calculate(inD, outD);
    }

    public void SetFragment(FragmentManager fragM, int resId)
    {
        m_GUIManager.SetFragment(fragM, resId);
    }

    public void StartApp()
    {
        String folder = GetFolderPath();

        GunSystemLoadCheck(folder, m_Config.GetGunSystemFileName());
    }

    public void CopyFiles(String path)
    {
        AssetManager assetManager = m_Owner.getAssets();
        String[] files = null;

        try {
            files = assetManager.list(m_Config.GetTablesFolderName());
        }
        catch (IOException e)
        {
        }
        if (files == null)
            return;

        for (String filename : files) {

            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(path, filename);
                out = new FileOutputStream(outFile);
                CopyFile(in, out);
            }
            catch(IOException e)
            {
            }
            finally
            {
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException e)
                    {
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    }
                    catch (IOException e)
                    {
                    }
                }
            }
        }
    }

    private void CopyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public String GetFolderPath()
    {
        File externalFolder = m_Owner.getExternalFilesDir(null);
        if(externalFolder == null)
            return null;

        String exFolderPath = externalFolder.getAbsolutePath();
        if(exFolderPath == null)
            return null;

        String bcDirPath = exFolderPath + "/" + m_Config.GetTablesFolderName();
        File bcTables = new File(bcDirPath);

        if(bcTables.exists())
            return bcTables.getAbsolutePath();

        bcTables.mkdir();

        CopyFiles(bcDirPath);

        return bcDirPath;
    }

    public boolean GunSystemLoadCheck(String folderPath, String filePath)
    {
        return m_BCModule.GunSystemLoad(folderPath + "/" + "SVD-1974-PSO1.xml");
    }


    private MainActivity            m_Owner;

    private GUIManager              m_GUIManager;
    private BCModule                m_BCModule;
    private ComfigurationManager    m_Config;
}
