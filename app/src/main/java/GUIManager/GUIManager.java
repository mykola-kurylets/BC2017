package GUIManager;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.DialogInterface;
import android.view.Menu;

import com.kurylets.mykola.bc2017.MainActivity;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;

import Application.Application;
import GUIManager.Dialog.GunSystemFileDlg;
import GUIManager.Dialog.SelectModeDialog;
import GUIManager.Fragment.CalculationFragment;
import GUIManager.Fragment.CalculationFragment.ICalculationFragmentListener;
import Utilities.FileUtilities;
import Utilities.StringsMap;


public class GUIManager
{
    public GUIManager(Application app)
    {
        m_App                       = app;
        m_CalcMenu                  = new CalculatorMenu();
        m_CalcFragment              = new CalculationFragment();
        m_ModeDialog                = new SelectModeDialog();
        m_ChoseGunSystemDlg         = new GunSystemFileDlg();
        m_ExecudeGunSystemFileDlg   = new GunSystemFileDlgExecuter();
        m_MenuListener              = new MenuListener();
        m_ClacFrgListener           = new CalculationFragmentListener();
        m_SelectModeListener        = new SelectModeListener();
    }

    public void SetListeners()
    {
        m_CalcMenu.SetListener(m_MenuListener);
        m_CalcFragment.SetListener(m_ClacFrgListener);
        m_ModeDialog.SetListener(m_SelectModeListener);
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
            ShowSelectModeDialog();
        }

        @Override
        public void ExecudeSystemItem()
        {
            ShowGunSystemFileDlg(m_ExecudeGunSystemFileDlg, FileUtilities.GetFolderPathFromFile(m_App.GetCurrentSystemFile()));
        }

        @Override
        public void ExecudeAboutItem()
        {

        }
    }

    public class GunSystemFileDlgExecuter implements GunSystemFileDlg.IGunSystemFileDlgListener
    {
        @Override
        public void SetFile(String path)
        {
            if(!m_App.GunSystemLoad(path))
                return;

            m_App.SetCurrentSystemFile(path);
        }
    }

    class SelectModeListener implements SelectModeDialog.ISelectModeListener
    {
        @Override
       public void OnPossitive(int res)
        {
            if (m_App.GetCurrentMode() == res)
                return;

            m_App.SetCurentMode(res);
            m_App.Reset();
        }
    }

    public void ShowSelectModeDialog()
    {
        m_ModeDialog.SetMode(m_App.GetCurrentMode());
        m_ModeDialog.show(m_App.GetActivity().getFragmentManager(), "ModeDialog");
    }

    public void ShowGunSystemFileDlg(GunSystemFileDlg.IGunSystemFileDlgListener dlgExecuter, String startFolder)
    {
        m_ChoseGunSystemDlg.SetListener(dlgExecuter);
        m_ChoseGunSystemDlg.SetCurrent(startFolder);

        m_ChoseGunSystemDlg.show(m_App.GetActivity().getFragmentManager(), "GunSystemDlg");
    }

    public boolean CreateMenu(Menu menu)
    {
        return  m_CalcMenu.CreateMenu(menu);
    }

    // установка фрагменту
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

        m_CalcFragment.SetPreference(preference);

        return true;
    }

    public boolean GetPreferences(StringsMap preference )
    {
        if(preference == null)
            return false;
        preference.clear();

        m_CalcFragment.GetPreferences(preference);

        return true;
    }

    public void ShowAlertDialog(String massage)
    {
        AlertDialog.Builder delmessagebuilder = new AlertDialog.Builder(m_App.GetActivity());

        delmessagebuilder.setCancelable(false);

        delmessagebuilder.setMessage(massage);

        delmessagebuilder.setNeutralButton("Oк", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        delmessagebuilder.create().show();
    }

    public String GetPreferencesName()
    {
        return "bc-2017-gui-pref";
    }

    private Application                 m_App;
    private CalculationFragment         m_CalcFragment;
    private CalculatorMenu              m_CalcMenu;
    private SelectModeDialog            m_ModeDialog;
    private GunSystemFileDlg            m_ChoseGunSystemDlg;
    private GunSystemFileDlgExecuter    m_ExecudeGunSystemFileDlg;
    private MenuListener                m_MenuListener;
    private CalculationFragmentListener m_ClacFrgListener;
    private SelectModeListener          m_SelectModeListener;
}
