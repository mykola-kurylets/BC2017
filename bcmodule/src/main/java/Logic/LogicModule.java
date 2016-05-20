package Logic;

import com.kurylets.mykola.bcmodule.BCModule;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;
import com.kurylets.mykola.bcmodule.WindDirections;

import Utilities.BCDouble;
import Utilities.BCInteger;

/**
 * Created by Mykola on 20.05.2016.
 */
public class LogicModule
{
    public LogicModule(BCModule bcm)
    {
        m_BCModule = bcm;
    }

    public boolean Calculate(InputData inD, OutputData outD)
    {
        if(inD == null || outD == null)
            return false;

        double calcDist = CalcDistanceCorrect(inD);

        BCInteger sightNum = new BCInteger();
        BCDouble displac = new BCDouble();

        if(!CalcVerticalSight(sightNum, displac, calcDist, inD.GetDistance()))
            return false;

        outD.SetVerticalSight(sightNum.val);
        outD.SetVerticalDeviation(displac.val);

        return true;
    }

    private double CalcDistanceCorrect(InputData inD)
    {
        if(inD == null)
            return 0.0;

        final double dist = inD.GetDistance();

        double tempRes = GetTempDistCorr(dist, inD.GetTemperature());

        double pressureRes = GetPressureCorr(dist, inD.GetPressure());

        double windRes = GetWindOnDistance(dist, inD.GetWindSpeed(), inD.GetWindDirection());

        return dist + tempRes + pressureRes + windRes;
    }

    private double GetTempDistCorr(double dist, double userTemp)
    {
        double tempRes = m_BCModule.GetTemperatureCorrect(dist);
        if(tempRes == Double.MAX_VALUE)
            return 0.0;

        return tempRes * ((userTemp - m_BCModule.GetNormalAirTemperature()) * -1.0) / 10.0;
    }

    private double GetPressureCorr(double dist, double userPressure)
    {
        double pressureRes = m_BCModule.GetPressureCorrect(dist);
        if(pressureRes == Double.MAX_VALUE)
            return 0.0;

        return pressureRes * (userPressure - m_BCModule.GetNormalPressure()) / 10.0;
    }

    private double GetWindOnDistance(double dist, double windSpeed, WindDirections windDir)
    {
        double multipel = 1.0;
        switch (windDir)
        {
            case e0:
                multipel *= -1.0;
                break;
            case e45:
            case e315:
                multipel *= -0.5;
                break;
            case e135:
            case e225:
                multipel *= 0.5;
                break;
            case e90:
            case e270:
                return 0.0;
        }

        BCInteger denominator = new BCInteger(1);
        double windRes = m_BCModule.GetWindOnDistanceOrDirection(dist, windSpeed, denominator, true);
        if (windRes == Double.MAX_VALUE)
            windRes = 0.0;

        return ((windRes * multipel)* windSpeed) / denominator.val;
    }

    //private double CalcDirectionCorrect(InputData):

    private boolean CalcVerticalSight(BCInteger sightNum, BCDouble displacement, double distanceCalc, double trueDistance)
    {
        if(!m_BCModule.GetVerticalSight(sightNum, displacement, distanceCalc, trueDistance))
            return false;

        displacement.val *= -1.0;

        return true;
    }

    //private boolean CalcHorizontalSight(Double, Double, double):


    private BCModule m_BCModule;
}
