package TableHolder;

/**
 * Created by Mykola on 10.05.2016.
 * властивості прицілу
 */
public class Sight
{
    public Sight()
    {
        m_Name = null;
        m_PriceDivision = 0.0;
        m_PriceClick = 0.0;
    }

    public String m_Name;
    public double m_PriceDivision;  // ціна поділки
    public double m_PriceClick;     // ціна кліку
}
