package GUIManager;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.kurylets.mykola.bc2017.R;

import GUIManager.Dialog.SelectModeDialog;

/**
 * Created by samsung on 27.05.2016.
 */
public class CalculatorMenu extends FragmentActivity {
    public CalculatorMenu()
    {
        m_SelectModeId = 0;
        m_SelectSystemId = 1;
        m_SelectAboutId = 2;

        m_Gui = null;
        m_MenuLstnr = null;
    }

    public CalculatorMenu(GUIManager guiMng, IMenuListener mnListn)
    {
        this();
        m_Gui = guiMng;
        m_MenuLstnr = mnListn;
    }



    public boolean CreateMenu(Menu menu)
    {
        menu.add(0, m_SelectModeId, 0, R.string.select_mode_text);
        menu.add(0,m_SelectSystemId, 1, R.string.select_system_text);
        menu.add(0,m_SelectAboutId, 2, R.string.select_about_text);
        return true;
    }

    public boolean OnSelectItem(MenuItem item)
    {
        if(m_MenuLstnr == null)
            return false;
        int id = item.getItemId();

        if (id == m_SelectModeId) {
            m_MenuLstnr.ExecudeModeItem();

            return true;
        }

        if (id == m_SelectSystemId) {
            m_MenuLstnr.ExecudeSystemItem();
            return true;
        }

        if (id == m_SelectAboutId) {
            m_MenuLstnr.ExecudeAboutItem();
            return true;
        }

        return  false;
    }



    public interface IMenuListener
    {
        void ExecudeModeItem();
        void ExecudeSystemItem();
        void ExecudeAboutItem();
    }

    private int m_SelectModeId;
    private int m_SelectSystemId;
    private int m_SelectAboutId;

    private GUIManager m_Gui;
    private  IMenuListener m_MenuLstnr;
}
