package GUIManager;

import android.view.Menu;
import android.view.MenuItem;

import com.kurylets.mykola.bc2017.R;

/**
 * Created by samsung on 27.05.2016.
 */
public class CalculatorMenu
{
    public CalculatorMenu()
    {
        m_MenuLstnr = null;
    }

    public CalculatorMenu(IMenuListener mnListn)
    {
        m_MenuLstnr = mnListn;
    }

    public boolean CreateMenu(Menu menu)
    {
        menu.add(0, m_SelectModeId, m_SelectModeId, R.string.select_mode_text);
        menu.add(0, m_SelectSystemId, m_SelectSystemId, R.string.select_system_text);
        menu.add(0, m_SelectAboutId, m_SelectAboutId, R.string.select_about_text);
        return true;
    }

    public boolean OnSelectItem(MenuItem item)
    {
        if(m_MenuLstnr == null)
            return false;
        int id = item.getItemId();

        switch (id)
        {
            case m_SelectModeId:
                m_MenuLstnr.ExecudeModeItem();
                return true;

            case m_SelectSystemId:
                m_MenuLstnr.ExecudeSystemItem();
                return true;

            case m_SelectAboutId:
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

    private final int      m_SelectModeId = 0;
    private final int      m_SelectSystemId = 1;
    private final int      m_SelectAboutId = 2;

    private IMenuListener  m_MenuLstnr;
}
