package com.kurylets.mykola.bc2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kurylets.mykola.bcmodule.BCModule;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;
import com.kurylets.mykola.bcmodule.WindDirections;

import Application.Application;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_App = new Application(this);

        // задання фрагрменту у головну активність
        // main_container - ідентифікатор activity_main.xml
        // android:id="@+id/main_container">
        //m_App.SetFragment(getFragmentManager(), R.id.main_container);

//        //тестування обрахунку вертикального прицілу і поправок НЕ СТИРАТИ!!!!!!!!!!!
//        BCModule bcModule = new BCModule();
//        boolean res = bcModule.GunSystemLoad("/data/data/com.kurylets.mykola.bc2017/bctables/SVD-PSO1.xml");
//
//        InputData inD = new InputData();
//        OutputData outD = new OutputData();
//
//        inD.SetDistance(449.0);
//        inD.SetTemperature(15.0);
//        inD.SetPressure(750.0);
//        inD.SetWindSpeed(0.0);
//        inD.SetWindDirection(WindDirections.e90);
//
//        res = bcModule.Calculate(inD, outD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private Application m_App;
}
