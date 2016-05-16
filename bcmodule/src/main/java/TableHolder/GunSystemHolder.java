package TableHolder;

/**
 * Created by Mykola on 14.05.2016.
 */
public class GunSystemHolder
{
    public GunSystemHolder()
    {
        m_Guns = new GunsVec();
        m_BulletProp = new Bullet();
        m_SightProp = new Sight();
        m_NormalConditions = new Normal();
        m_OverestimationTbl = new OverestimationVec();
        m_CorrectionsTbl = new CorrectionVec();
    }

    public boolean Load(String filePath)
    {
        GunSystemLoader gsLoader = new GunSystemLoader(this);

        return gsLoader.Load(filePath);
    }

    void SetName(String name)
    {
        m_Name = name;
    }

    public String GetName()
    {
        return m_Name;
    }

    GunsVec GetGunsVec()
    {
        return m_Guns;
    }

    Bullet GetBullet()
    {
        return m_BulletProp;
    }

    Sight GetSight()
    {
        return m_SightProp;
    }

    Normal GetNormal()
    {
        return m_NormalConditions;
    }

    OverestimationVec GetOverestimationTbl()
    {
        return m_OverestimationTbl;
    }

    CorrectionVec GetCorrectionTbl()
    {
        return m_CorrectionsTbl;
    }


    private String              m_Name;
    private GunsVec             m_Guns;
    private Bullet              m_BulletProp;
    private Sight               m_SightProp;
    private Normal              m_NormalConditions;

    private OverestimationVec   m_OverestimationTbl;
    private CorrectionVec       m_CorrectionsTbl;
}
