package TableHolder;

/**
 * Created by Mykola on 11.05.2016.
 * набір балістичних і метеорологічних поправок
 */
public class Correction
{
    public Correction()
    {
        m_Temperature = 0.0;
        m_Pressure = 0.0;
        m_Speed = 0.0;
        m_Winds = new WindsVec();
    }

    public double GetTemperature()
    {
        return m_Temperature;
    }

    public void SetTemperature(double t)
    {
        m_Temperature = t;
    }

    public double GetPressure()
    {
        return m_Pressure;
    }

    public void SetPressure(double p)
    {
        m_Pressure = p;
    }

    public double GetSpeed()
    {
        return m_Speed;
    }

    public void SetSpeed(double s)
    {
        m_Speed = s;
    }

    public WindsVec GetWindsVec()
    {
        return (WindsVec)m_Winds.clone();
    }

    public void SetWinds(WindsVec w)
    {
        m_Winds.clear();
        m_Winds = null;
        m_Winds = w;
    }

    public void AddWind(WindPair wp)
    {
        m_Winds.add(wp);
    }

    private double m_Temperature;   // поправка на температуру
    private double m_Pressure;      // поправка на тиск
    private double m_Speed;         // поправка на початкову швидкість кулі
    private WindsVec m_Winds;       // поправка на вітер
}
