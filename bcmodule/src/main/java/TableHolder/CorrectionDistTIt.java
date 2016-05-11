package TableHolder;

import Utilities.BCVector;
import Utilities.VecSecondIt;

/**
 * Created by Mykola on 11.05.2016.
 */
public class CorrectionDistTIt extends VecSecondIt<MeteoBalisticCorrectionPair, Double>
{

    public CorrectionDistTIt(BCVector<MeteoBalisticCorrectionPair> vec)
    {
        super(vec);
    }

    @Override
    public Double Get() {

        MeteoBalisticCorrectionPair pair = GetCurrentItem();
        if(pair == null)
            return null;

        MeteoBalisticCorrection mBcorr = pair.second;
        if(mBcorr == null)
            return null;

        Correction corr = mBcorr.GetDistanceCorrection();

        if(corr == null)
            return null;

        return new Double(corr.GetTemperature());
    }
}
