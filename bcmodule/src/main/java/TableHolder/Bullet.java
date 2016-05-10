package TableHolder;

/**
 * Created by Mykola on 10.05.2016.
 * властивості потрону
 */
public class Bullet
{
    public Bullet()
    {
        m_Name = new String();
        m_Caliber = 0.0;
        m_Speed = 0.0;
        m_Weight = 0.0;
        m_Description = new String();
    }

    public String m_Name;
    public double m_Caliber;
    public double m_Speed;
    public double m_Weight;
    public String m_Description;
}
