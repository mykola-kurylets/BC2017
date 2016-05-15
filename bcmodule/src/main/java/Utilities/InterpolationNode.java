package Utilities;

/**
 * Created by Mykola on 15.05.2016.
 */
public class InterpolationNode
{
    public InterpolationNode(double x, double y)
    {
        m_X = x;
        m_Y = y;
    }
    public double GetX()
    {
        return m_X;
    }

    public double GetY()
    {
        return m_Y;
    }

    private double m_X;
    private double m_Y;
}
