package com.kurylets.mykola.bcmodule;

import Logic.LogicModule;
import TableHolder.GunSystemHolder;
import Utilities.BCDouble;
import Utilities.BCInteger;

/**
 * Created by Mykola on 20.05.2016.
 */
public class BCModule
{
    public BCModule()
    {
        m_GsHolder  = new GunSystemHolder();
        m_LogMd     = new LogicModule(this);
    }

    public boolean GunSystemLoad(String filePath)
    {
        return m_GsHolder.Load(filePath);
    }

    public boolean Calculate(InputData inD, OutputData outD)
    {
        return m_LogMd.Calculate(inD, outD);
    }

    public double GetTemperatureCorrect(double dist)
    {
        return m_GsHolder.GetTemperatureCorrect(dist);
    }

    public double GetPressureCorrect(double dist)
    {
        return m_GsHolder.GetPressureCorrect(dist);
    }

    public double GetWindOnDistanceOrDirection(double distance, double windSpeed, BCInteger descretSpeed, boolean windOndistance)
    {
        return m_GsHolder.GetWindOnDistanceOrDirection(distance, windSpeed, descretSpeed, windOndistance);
    }

    public double GetDerivationCorrect(double dist)
    {
        return m_GsHolder.GetDerivation(dist);
    }

    public boolean GetVerticalSight(BCInteger sight, BCDouble displacement, double distCalc, double distTrue)
    {
        return m_GsHolder.GetVerticalSight(sight, displacement, distCalc, distTrue);
    }

    public double GetNormalAirTemperature()
    {
        return m_GsHolder.GetNormalAirTemperature();
    }

    public double GetNormalPressure()
    {
        return m_GsHolder.GetNormalAirPressure();
    }

    public double GetPriceDivision()
    {
        return m_GsHolder.GetPriceDivision();
    }

    public double GetPriceClick()
    {
        return m_GsHolder.GetPriceClick();
    }

    private GunSystemHolder m_GsHolder;
    private LogicModule     m_LogMd;
}
