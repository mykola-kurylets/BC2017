package GUIManager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import Application.Application;
import GUIManager.Fragment.CalculationFragment;

/**
 * Created by Mykola on 17.05.2016.
 */
public class GUIManager
{
    public GUIManager(Application app)
    {
        m_App = app;
        m_CalcFragment = new CalculationFragment();

    }

    // установка фрагменту
    public void SetFragment(FragmentManager fragM, int resId)
    {
        FragmentTransaction fragT = fragM.beginTransaction();
        fragT.add(resId, m_CalcFragment);
        fragT.commit();
    }

    private Application m_App;
    private CalculationFragment m_CalcFragment;
}
