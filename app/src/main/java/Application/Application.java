package Application;

import android.app.FragmentManager;

import com.kurylets.mykola.bc2017.MainActivity;
import GUIManager.GUIManager;

/**
 * Created by Mykola on 17.05.2016.
 */
public class Application
{
    public Application(MainActivity mA)
    {
        m_Owner = mA;
        m_GUIManager = new GUIManager(this);
    }

    public void SetFragment(FragmentManager fragM, int resId)
    {
        m_GUIManager.SetFragment(fragM, resId);
    }

    private MainActivity    m_Owner;

    private GUIManager      m_GUIManager;
}
