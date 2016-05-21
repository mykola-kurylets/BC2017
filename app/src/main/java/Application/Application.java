package Application;

import android.app.FragmentManager;
import android.content.SharedPreferences;

import com.kurylets.mykola.bc2017.MainActivity;
import com.kurylets.mykola.bcmodule.BCModule;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;

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

    private MainActivity            m_Owner;

    private GUIManager              m_GUIManager;
    private BCModule                m_BCModule;
    private ComfigurationManager    m_Config;
}
