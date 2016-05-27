package GUIManager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.view.Menu;

import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;

import Application.Application;
import GUIManager.Dialog.SelectModeDialog;
import GUIManager.Fragment.CalculationFragment;
import GUIManager.Fragment.CalculationFragment.ICalculationFragmentListener;
import GUIManager.Fragment.WindDirectionAdapter;

/**
 * Created by Mykola on 17.05.2016.
 */
public class GUIManager
{
    public GUIManager(Application app)
    {
        m_App = app;
        m_CalcMenu = new CalculatorMenu(this, new MenuListener());
        m_CalcFragment = new CalculationFragment(new CalculationFragmentListener() );

    }

    public CalculatorMenu GetMenu()
    {
        return m_CalcMenu;
    }

    class CalculationFragmentListener implements ICalculationFragmentListener
    {

        public boolean OnCalculate(InputData InD, OutputData OutD)
         {
            return m_App.Calculate(InD, OutD );
         }
    }

    class MenuListener implements CalculatorMenu.IMenuListener
    {
        @Override
        public void ExecudeModeItem()
        {

        }

        @Override
        public void ExecudeSystemItem()
        {

        }

        @Override
        public void ExecudeAboutItem()
        {

        }

    }



    public boolean CreateMenu(Menu menu)
    {

        return true;
    }

//     установка фрагменту
    public void SetFragment(FragmentManager fragM, int resId)
    {
        FragmentTransaction fragT = fragM.beginTransaction();
        fragT.add(resId, m_CalcFragment);
        fragT.commit();
    }

    private Application m_App;
    private CalculationFragment m_CalcFragment;
    private CalculatorMenu m_CalcMenu;
}
