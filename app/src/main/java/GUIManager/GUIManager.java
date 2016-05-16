package GUIManager;

import android.app.FragmentManager;

import Application.Application;

/**
 * Created by Mykola on 17.05.2016.
 */
public class GUIManager
{
    public GUIManager(Application app)
    {
        m_App = app;
    }

    // установка фрагменту
    public void SetFragment(FragmentManager fragM, int resId)
    {

    }

    private Application m_App;
}
