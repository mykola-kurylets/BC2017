package Logic;

import com.kurylets.mykola.bcmodule.BCModule;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;
import com.kurylets.mykola.bcmodule.WindDirections;

import Utilities.BCDouble;
import Utilities.BCInteger;
import Utilities.BCString;
import Utilities.MathConstants;

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

        final double trueDist = inD.GetDistance();
        if(trueDist < MathConstants.gePointDelta)
            return false;

        double calcDist = CalcDistanceCorrect(inD);

        BCInteger sightNum = new BCInteger();
        BCDouble displac = new BCDouble();

        if(!CalcVerticalSight(sightNum, displac, calcDist, trueDist))
            return false;

        outD.SetVerticalSight(sightNum.val);
        outD.SetVerticalDeviation(displac.val);

        double calcDir = CalcDirectionCorrect(inD);

        BCString horSight = new BCString();
        if(!CalcHorizontalSight(horSight, displac, (calcDir * 1000.0) / trueDist))
            return false;

        outD.SetHorizontalSight(horSight.val);
        outD.SetHorizontalDeviation(displac.val);

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

    private double GetWindCorrection(double dist, double windSpeed, double multipel, boolean distace)
    {
        BCInteger denominator = new BCInteger(1);
        double windRes = m_BCModule.GetWindOnDistanceOrDirection(dist, windSpeed, denominator, distace);
        if (windRes == Double.MAX_VALUE)
            windRes = 0.0;

        return ((windRes * multipel)* windSpeed) / denominator.val;
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

        return GetWindCorrection(dist, windSpeed, multipel, true);
    }

    private double GetWindOnDirection(double dist, double windSpeed, WindDirections windDir)
    {
        double multipel = 1.0;
        switch (windDir)
        {
            case e45:
            case e135:
                multipel *= 0.5;
                break;
            case e90:
                multipel *= -1.0;
                break;
            case e315:
            case e225:
                multipel *= -0.5;
                break;
            case e0:
            case e180:
                return 0.0;
        }

        return GetWindCorrection(dist, windSpeed, multipel, false);
    }

    private double CalcDirectionCorrect(InputData inD)
    {
        if(inD == null)
            return 0.0;

        final double dist = inD.GetDistance();

        double windCorrect = GetWindOnDirection(dist, inD.GetWindSpeed(), inD.GetWindDirection());

        double derivation = m_BCModule.GetDerivationCorrect(dist);
        if(derivation == Double.MAX_VALUE)
            derivation = 0.0;

        return windCorrect - derivation;
    }

    private boolean CalcVerticalSight(BCInteger sightNum, BCDouble displacement, double distanceCalc, double trueDistance)
    {
        if(!m_BCModule.GetVerticalSight(sightNum, displacement, distanceCalc, trueDistance))
            return false;

        displacement.val *= -1.0;

        return true;
    }

    private boolean CalcHorizontalSight(BCString sight, BCDouble displacement, double displacementOndist)
    {
        final double priceClik = m_BCModule.GetPriceClick();
        final double priceDivision = m_BCModule.GetPriceDivision();
        final int signum = (Math.abs(displacementOndist) - displacementOndist <= MathConstants.gePointDelta)?(1):(-1);

        if(priceClik < MathConstants.gePointDelta || priceDivision < MathConstants.gePointDelta)
            return false;

        // кількість кліків в подіці
        final double clicksCountInDivision = priceDivision / priceClik;

        // 1 кількість кліків для зміщення
        Double clicksCount = Math.abs(displacementOndist) / priceClik + MathConstants.gePointDelta;
        int decimalClick = clicksCount.intValue();

        // 2 вираховуємо номер поділки
        Double division = decimalClick / clicksCountInDivision + MathConstants.gePointDelta;
        int decimalDivision = division.intValue();

        // 3 вираховуємо зміщення Т. П.
        displacement.val = (clicksCount - decimalClick ) * priceClik * signum;

        // 4 вираховуємо додаткові кліки
        Double addClick = ((division - decimalDivision)/ priceClik) + MathConstants.gePointDelta;
        int additionalClicks = addClick.intValue();

        sight.val = String.valueOf(decimalDivision * signum) + "/" + String.valueOf(additionalClicks);

        return true;
    }


    private BCModule m_BCModule;
}
