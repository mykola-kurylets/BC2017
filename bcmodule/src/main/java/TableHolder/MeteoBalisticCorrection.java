package TableHolder;

/**
 * Created by Mykola on 11.05.2016.
 * набір метеорологічних і балістичних поправок на дистанцію, висоту, напрямок
 */
public class MeteoBalisticCorrection
{
    public MeteoBalisticCorrection()
    {
        m_DistanceCorrection = new Correction();
        m_HeightCorrrection = new Correction();
        m_DirCorrection = new DirrectionCorrection();
    }

    public Correction GetDistanceCorrection()
    {
        return m_DistanceCorrection;
    }

    public Correction GetHeightCorrection()
    {
        return  m_HeightCorrrection;
    }

    public DirrectionCorrection GetDirectionCorrection()
    {
        return  m_DirCorrection;
    }

    private Correction m_DistanceCorrection;        // поправки на дистанцію
    private Correction m_HeightCorrrection;         // поправки на висоту
    private DirrectionCorrection m_DirCorrection;   // поправки на напрямок
}
