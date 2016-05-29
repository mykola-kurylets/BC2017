package GUIManager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.view.Menu;

import com.kurylets.mykola.bc2017.MainActivity;
import com.kurylets.mykola.bc2017.R;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;

import java.util.Map;
import java.util.Set;

import Application.Application;
import GUIManager.Dialog.GunSystemFileDlg;
import GUIManager.Dialog.SelectModeDialog;
import GUIManager.Fragment.CalculationFragment;
import GUIManager.Fragment.CalculationFragment.ICalculationFragmentListener;
import Utilities.StringsMap;


public class GUIManager
{
    public GUIManager(Application app)
    {
        m_App = app;
        m_CalcMenu = new CalculatorMenu(new MenuListener());
        m_CalcFragment = new CalculationFragment(new CalculationFragmentListener());
        m_ModeDialog = new SelectModeDialog();
        m_ModeDialog.SetListener(new SelectModeListener());
        m_ChoseGunSystemDlg = new GunSystemFileDlg();
        m_CurrentMode = MainActivity.m_DayModeTheme;
    }

    public CalculatorMenu GetMenu()
    {
        return m_CalcMenu;
    }

    public int GetCurrentMode(){ return m_CurrentMode; }
    public void SetCurrentMode(int mode){m_CurrentMode = mode; }

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
            ShowSelectModeDialog();
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

    class SelectModeListener implements SelectModeDialog.ISelectModeListener
    {
        @Override
       public void OnPossitive()
        {
            if (m_CurrentMode == m_ModeDialog.GetMode())
                return;
            m_CurrentMode = m_ModeDialog.GetMode();
            m_App.ChangeMode();

        }
    }




    public void ShowSelectModeDialog()
    {
        m_ModeDialog.SetMode(m_CurrentMode);
        m_ModeDialog.show(m_CalcFragment.getFragmentManager(), "ModeDialog");
    }

    public void ShowGunSystemFileDlg(GunSystemFileDlg.IGunSystemFileDlgListener dlgExecuter, String startFolder)
    {
        m_ChoseGunSystemDlg.SetListener(dlgExecuter);
        m_ChoseGunSystemDlg.SetCurrent(startFolder);

        m_ChoseGunSystemDlg.show(m_CalcFragment.getFragmentManager(), "GunSystemDlg");
    }

    public boolean CreateMenu(Menu menu)
    {
        return  m_CalcMenu.CreateMenu(menu);
    }

//     установка фрагменту
    public void SetFragment(FragmentManager fragM, int resId)
    {
        FragmentTransaction fragT = fragM.beginTransaction();
        fragT.add(resId, m_CalcFragment);
        fragT.commit();
    }


    public boolean SetPreferences(StringsMap preference)
    {
        if (preference == null)
            return false;
        String mode = preference.get(m_ModeName);
         if ( mode != null && !mode.isEmpty())
             m_CurrentMode = Integer.parseInt(mode);
        m_CalcFragment.SetPreference(preference);

        return true;
    }


    public boolean GetPreferences(StringsMap preference )
    {

        if(preference == null)
            return false;

        preference.clear();
        preference.put(m_ModeName, String.valueOf(m_CurrentMode));
        m_CalcFragment.GetPreferences(preference);

        return true;
    }



    public String GetPreferenceName()
    {
        return "bc-2017-gui-pref";
    }

    private Application         m_App;
    private CalculationFragment m_CalcFragment;
    private CalculatorMenu      m_CalcMenu;
    private SelectModeDialog    m_ModeDialog;
    private GunSystemFileDlg    m_ChoseGunSystemDlg;
    private int                 m_CurrentMode;

    private static final String  m_ModeName = "cur-mode-name";


}
