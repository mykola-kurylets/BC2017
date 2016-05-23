package GUIManager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;

import Application.Application;
import GUIManager.Fragment.CalculationFragment;
import GUIManager.Fragment.CalculationFragment.ICalculationFragmentListener;

/**
 * Created by Mykola on 17.05.2016.
 */
public class GUIManager
{
    public GUIManager(Application app)
    {
        m_App = app;
        m_CalcFragment = new CalculationFragment(new CalculationFragmentListener() );
    }


    class CalculationFragmentListener implements ICalculationFragmentListener
    {
         public boolean OnCalculate(InputData InD, OutputData OutD)
         {
            return m_App.Calculate(InD, OutD );
         }
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
