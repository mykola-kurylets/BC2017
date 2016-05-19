package TableHolder;

import Utilities.BCPairsVecUtilities;
import Utilities.BCVector;

/**
 * Created by Mykola on 11.05.2016.
 */
public class CorrectionDistWIt extends WindsIterator
{

    public CorrectionDistWIt(BCVector<MeteoBalisticCorrectionPair> vec)
    {
        super(vec);
    }


    protected WindsVec GetWinds()
    {
        MeteoBalisticCorrectionPair pair = GetCurrentItem();
        if(pair == null)
            return null;

        MeteoBalisticCorrection mBcorr = pair.second;
        if(mBcorr == null)
            return null;

        Correction corr = mBcorr.GetDistanceCorrection();

        if(corr == null)
            return null;

        return corr.GetWindsVec();
    }
}
