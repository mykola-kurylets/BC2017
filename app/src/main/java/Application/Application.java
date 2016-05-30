package Application;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.kurylets.mykola.bc2017.MainActivity;
import com.kurylets.mykola.bc2017.R;
import com.kurylets.mykola.bcmodule.BCModule;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Configuration.ComfigurationManager;
import GUIManager.Dialog.GunSystemFileDlg;
import GUIManager.GUIManager;
import Utilities.FileUtilities;


public class Application
{
    public Application(MainActivity mA)
    {
        m_Owner         = mA;
        m_GUIManager    = new GUIManager(this);
        m_BCModule      = new BCModule();
        m_Config        = new ComfigurationManager(this);
        m_DlgExecuter   = new GunSystemFileDlgExecuter();
    }

    public boolean LoadGeneralConfigs()
    {
        return m_Config.LoadGeneralPreferences();
    }

    public boolean LoadGUIConfigs()
    {
        return m_Config.LoadGUIPreferences();
    }

    //  для зміни режиму необхідно перезапустити програму
    public void Reset()
    {
        m_Owner.finish();
        m_Owner.startActivity(new Intent(m_Owner, m_Owner.getClass()));
        m_Owner.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public int GetCurrentMode()
    {
        return m_Config.GetCurrentMode();
    }

    public void SetCurentMode(int mode)
    {
        m_Config.SetCurentMode(mode);
    }

    public String GetCurrentSystemFile()
    {
        return m_Config.GetGunSystemFileName();
    }

    public void SetCurrentSystemFile(String path)
    {
        m_Config.SetGunSystemFileName(path);
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

    public void SetListeners()
    {
        m_GUIManager.SetListeners();
    }

    public void LoadGunSystemFiles()
    {
        String folder = GetFolderPath();

        GunSystemLoadCheck(folder, m_Config.GetGunSystemFileName());
    }

    public void CopyFilesFromAssets(String destenitionDir, String assetsSubDir)
    {
        AssetManager assetManager = m_Owner.getAssets();
        String[] files = null;

        try {
            files = assetManager.list(assetsSubDir);
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
                in = assetManager.open(assetsSubDir + "/" + filename);
                File outFile = new File(destenitionDir, filename);
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

        String bcDirName = m_Config.GetTablesFolderName();
        String bcDirPath = exFolderPath + "/" + bcDirName;
        File bcTables = new File(bcDirPath);

       if(bcTables.exists())
            return bcTables.getAbsolutePath();

        bcTables.mkdir();

        CopyFilesFromAssets(bcDirPath, bcDirName);

        return bcDirPath;
    }

    public void GunSystemLoadCheck(String folderPath, String filePath)
    {
        String file = filePath;
        if(folderPath != null && file.equalsIgnoreCase(ComfigurationManager.m_UnDefined))
            file = folderPath + "/" + filePath;

        if(m_BCModule.GunSystemLoad(file)) {
            m_Config.SetGunSystemFileName(file);
            return;
        }

        String startFolder = FileUtilities.GetFolderPathFromFile(file);
        if(startFolder == null)
            return;

        m_GUIManager.ShowGunSystemFileDlg(m_DlgExecuter, startFolder);
    }

    public class GunSystemFileDlgExecuter implements GunSystemFileDlg.IGunSystemFileDlgListener
    {
        @Override
        public void SetFile(String path)
        {
            GunSystemLoadCheck(null, path);
        }
    }

    private MainActivity                m_Owner;

    private GUIManager                  m_GUIManager;
    private BCModule                    m_BCModule;
    private ComfigurationManager        m_Config;
    private GunSystemFileDlgExecuter    m_DlgExecuter;

}

