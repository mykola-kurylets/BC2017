package TableHolder;

/**
 * Created by Mykola on 11.05.2016.
 * набір поправок на напрямок
 */
public class DirrectionCorrection
{
    public DirrectionCorrection()
    {
        m_Winds = new WindsVec();
        m_Derivation = 0.0;
    }

    public double GetDerivation()
    {
        return m_Derivation;
    }

    public void SetDerivation(double d)
    {
        m_Derivation = d;
    }

    public WindsVec GetWinds()
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


    private WindsVec m_Winds;    // поправки на боковий вітер
    private double m_Derivation; // поправки деривації
}
