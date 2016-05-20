package com.kurylets.mykola.bcmodule;

/**
 * Created by Mykola on 09.05.2016.
 * Клас що містить вхідні дані для розрахунків
 */
public class InputData
{
    public InputData()
    {
        m_Distance      = 0.0;
        m_Temperature   = 0.0;
        m_Pressure      = 0.0;
        m_WindSpeed     = 0.0;
        m_WindDirection = WindDirections.e0;
    }

    public void SetDistance(double dist)
    {
        m_Distance = dist;
    }

    public void SetTemperature(double temp)
    {
        m_Temperature = temp;
    }

    public void SetPressure(double press)
    {
        m_Pressure = press;
    }

    public void SetWindSpeed(double speed)
    {
        m_WindSpeed = speed;
    }

    public void SetWindDirection(WindDirections dir)
    {
        m_WindDirection = dir;
    }

    public double GetDistance()
    {
        return m_Distance;
    }

    public double GetTemperature()
    {
        return m_Temperature;
    }

    public double GetPressure()
    {
        return m_Pressure;
    }

    public double GetWindSpeed()
    {
        return m_WindSpeed;
    }

    public WindDirections GetWindDirection()
    {
        return m_WindDirection;
    }

    private double m_Distance;
    private double m_Temperature;
    private double m_Pressure;
    private double m_WindSpeed;
    private WindDirections m_WindDirection;
}
