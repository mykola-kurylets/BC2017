package TableHolder;

import Utilities.BCDouble;
import Utilities.BCInteger;
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

    public double GetNormalAirTemperature()
    {
        return m_NormalConditions.m_AirTemperature;
    }

    public double GetNormalAirPressure()
    {
        return m_NormalConditions.m_Pressure;
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

    public double GetWindOnDistanceOrDirection(double dist, double windSpeed, BCInteger out, boolean distance)
    {
        WindsIterator wIt = (distance)?(m_CorrectionsTbl.GetDistWit()):(m_CorrectionsTbl.GetDirWIt());

        if(wIt == null)
            return Double.MAX_VALUE;

        if(!wIt.SetWindIndex(windSpeed))
            return Double.MAX_VALUE;

        out.val = wIt.GetDescretWind();

        return BCPairsVecUtilities.InterpolateInNeighborhood(wIt, dist);
    }

    public double GetDerivation(double x)
    {
        CorrectionDerIt it = m_CorrectionsTbl.GetDerivationIt();
        if(it == null)
            return Double.MAX_VALUE;

        return BCPairsVecUtilities.InterpolateInNeighborhood(it, x);
    }

    public boolean GetVerticalSight(BCInteger sight, BCDouble disp, double distCalc, double distTrue)
    {
        if(distTrue < 1e-6 || distCalc < 1e-6)
            return false;

        OverstimationIt overIt = m_OverestimationTbl.GetOverestimationIt(distCalc);
        if(overIt.GetCurrentItem() == null)
            return false;

        Double over = overIt.Get();
        for(double tmp = Double.MAX_VALUE; overIt.GetCurrentItem() != null; overIt.StepForward(), over = overIt.Get()){

            if(over == null || over == Double.MAX_VALUE)
                continue;

            if(Math.abs(over) > Math.abs(tmp)) {
                overIt.StepBack();
                over = tmp;
                break;
            }
            tmp = over;
        }

        SightParams sp = overIt.GetCurrentItem();
        if(sp == null)
            return false;
        sight.val = sp.first;
        disp.val = (over * 1000.0)/distTrue;

        return true;
    }

    private String              m_Name;
    private GunsVec             m_Guns;
    private Bullet              m_BulletProp;
    private Sight               m_SightProp;
    private Normal              m_NormalConditions;

    private OverestimationVec   m_OverestimationTbl;
    private CorrectionVec       m_CorrectionsTbl;
}
