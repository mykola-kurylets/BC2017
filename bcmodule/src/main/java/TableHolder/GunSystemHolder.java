package TableHolder;

import Utilities.BCPairsVecUtilities;

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

    public void SetName(String name)
    {
        m_Name = name;
    }

    public String GetName()
    {
        return m_Name;
    }

    public GunsVec GetGunsVec()
    {
        return m_Guns;
    }

    public Bullet GetBullet()
    {
        return m_BulletProp;
    }

    public Sight GetSight()
    {
        return m_SightProp;
    }

    public Normal GetNormal()
    {
        return m_NormalConditions;
    }

    public OverestimationVec GetOverestimationTbl()
    {
        return m_OverestimationTbl;
    }

    public CorrectionVec GetCorrectionTbl()
    {
        return m_CorrectionsTbl;
    }

    public double GetTemperatureCorrect(double x)
    {
        CorrectionDistTIt it = m_CorrectionsTbl.GetDistTIt();
        if(it == null)
            return Double.MAX_VALUE;

        return BCPairsVecUtilities.InterpolateInNeighborhood(it, x);
    }

    public double GetPressureCorrect(double x)
    {
        CorrectionDistPIt it = m_CorrectionsTbl.GetDistPIt();
        if(it == null)
            return Double.MAX_VALUE;

        return BCPairsVecUtilities.InterpolateInNeighborhood(it, x);
    }

    public double GetWindOnDistance(double dist, double windSpeed, int []out)
    {
        if(out.length < 1)
            return Double.MAX_VALUE;

        CorrectionDistWIt wIt = m_CorrectionsTbl.GetDistWit();

        if(wIt == null)
            return Double.MAX_VALUE;

        if(!wIt.SetWindIndex(windSpeed))
            return Double.MAX_VALUE;

        out[0] = wIt.GetWindIndex();

        return BCPairsVecUtilities.InterpolateInNeighborhood(wIt, dist);
    }

    public double GetWindOnDirection(double dist, double windSpeed, int []out)
    {
        if(out.length < 1)
            return Double.MAX_VALUE;

        CorrectionDirWIt wIt = m_CorrectionsTbl.GetDirWIt();

        if(wIt == null)
            return Double.MAX_VALUE;

        if(!wIt.SetWindIndex(windSpeed))
            return Double.MAX_VALUE;

        out[0] = wIt.GetWindIndex();

        return BCPairsVecUtilities.InterpolateInNeighborhood(wIt, dist);
    }

    public double GetDerivation(double x)
    {
        CorrectionDerIt it = m_CorrectionsTbl.GetDerivationIt();
        if(it == null)
            return Double.MAX_VALUE;

        return BCPairsVecUtilities.InterpolateInNeighborhood(it, x);
    }

    private String              m_Name;
    private GunsVec             m_Guns;
    private Bullet              m_BulletProp;
    private Sight               m_SightProp;
    private Normal              m_NormalConditions;

    private OverestimationVec   m_OverestimationTbl;
    private CorrectionVec       m_CorrectionsTbl;
}
