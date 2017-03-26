package com.kurylets.mykola.bc2017;

import com.kurylets.mykola.bcmodule.BCModule;
import com.kurylets.mykola.bcmodule.ErrorState;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;
import com.kurylets.mykola.bcmodule.WindDirections;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    public  ExampleUnitTest()
    {
        m_BCmodule = new BCModule();
    }

    @Before
    public void LoadGunSustem()throws Exception
    {
        assertTrue( m_BCmodule.GunSystemLoad("D:\\SVD-1974-PSO1.xml"));
    }

    @Test
    public void TestBCmodule() throws  Exception
    {
        InputData inData = new InputData();
        OutputData outData = new OutputData();

        inData.SetDistance(856.3);
        inData.SetPressure(851.0);
        inData.SetTemperature(23);
        inData.SetWindSpeed(6.0);
        inData.SetWindDirection(WindDirections.e45);

        ErrorState es = m_BCmodule.Calculate(inData, outData);
        assertEquals(0, es.GetState());

        int vS = outData.GetVerticalSight();
        double vD = outData.GetVerticalDeviation();
        String hS = outData.GetHorizontalSight();
        double hD = outData.GetHorizontalDeviation();

        assertEquals( 9, vS);
        assertEquals( -0.26, vD, 0.01);
        assertEquals( "2/0", hS);
        assertEquals( 0.08, hD, 0.01);
    }

    private BCModule m_BCmodule;
}