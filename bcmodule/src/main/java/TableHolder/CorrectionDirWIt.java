package TableHolder;

import Utilities.BCVector;

/**
 * Created by Mykola on 11.05.2016.
 */
public class CorrectionDirWIt extends WindsIterator
{

    public CorrectionDirWIt(BCVector<MeteoBalisticCorrectionPair> vec)
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

        DirrectionCorrection corr = mBcorr.GetDirectionCorrection();

        if(corr == null)
            return null;

        return corr.GetWinds();
    }
}