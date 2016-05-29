package Application;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;

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
        m_DlgExecuter   = new GunSystemFileDlgExecuter();
    }

    public boolean Load()
    {
        if(!m_Config.Load())
            return false;

        SetFragment(m_Owner.getFragmentManager(), R.id.main_container);

        StartApp();

        return true;
    }


//    для зміни режиму необхідно перезапустити програму
   public void ChangeMode(int id)
   {
       m_CurrentTheme = id;
       m_Owner.finish();
       m_Owner.startActivity(new Intent(m_Owner, m_Owner.getClass()));
       m_Owner.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
   }

//    загрузка вибраної теми
    public static void SelectTheme(MainActivity activity ){
        int theme = m_CurrentTheme;
        switch (theme)
        {
            case DAY_MODE_THEME :
                activity.setTheme(R.style.Theme_Day_Mode);
                break;
            case NIGHT_MODE_THEME :
                activity.setTheme(R.style.Theme_Night_Mode);
                break;
            default:
                activity.setTheme(R.style.Theme_Day_Mode);
                break;
        }
    }

    public static int GetTheme()
    {
        return Application.m_CurrentTheme;
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
            file = folderPath + "/" + "SVD-1974-PSO1.xml";

        if(m_BCModule.GunSystemLoad(file))
            return;

        String startFolder = null;
       // m_GUIManager.ShowGunSystemFileDlg(m_DlgExecuter, startFolder);
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

    private static int                  m_CurrentTheme ;
    private final static int            DAY_MODE_THEME = 0;
    private final static int            NIGHT_MODE_THEME = 1;


}

