package TableHolder;

/**
 * Created by Mykola on 10.05.2016.
 * нормальні табличні умови
 */
public class Normal
{
    public Normal()
    {
        m_TargetAngle = 0.0;
        m_LateralTilt = 0.0;
        m_Pressure = 0.0;
        m_AirTemperature = 0.0;
        m_RelativeHumidity = 0.0;
    }

    public double m_TargetAngle;       // кут цілі
    public double m_LateralTilt;       // кут нахилу системи
    public double m_Pressure;          // атмосферний тиск мм.рт.ст
    public double m_AirTemperature;    // температура повітря
    public double m_RelativeHumidity;  // відносна вологість
}
