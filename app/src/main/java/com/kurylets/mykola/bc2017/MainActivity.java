package com.kurylets.mykola.bc2017;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import Application.Application;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        m_App = new Application(this);
        m_App.LoadGeneralConfigs();

        SelectTheme(m_App.GetCurrentMode());
        setContentView(R.layout.activity_main);

        m_App.LoadGunSystemFiles();
        m_App.SetFragment(getFragmentManager(), R.id.main_container);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        m_App.SetListeners();
        m_App.LoadGUIConfigs();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        m_App.Save();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return m_App.GetGUIManager().CreateMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        m_App.GetGUIManager().GetMenu().OnSelectItem(item);
        return super.onOptionsItemSelected(item);
    }


    public  void SelectTheme(int theme ){
        switch (theme)
        {
            case m_DayModeTheme:
                setTheme(R.style.Theme_Day_Mode);
                break;
            case m_NightMode:
                setTheme(R.style.Theme_Night_Mode);
                break;
            default:
                setTheme(R.style.Theme_Day_Mode);
        }
    }

    private Application m_App;
    public final static int    m_DayModeTheme = 0;
    public final static int    m_NightMode = 1;

}
