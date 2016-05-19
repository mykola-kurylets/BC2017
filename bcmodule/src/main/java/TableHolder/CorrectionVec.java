package TableHolder;

import Utilities.BCVector;

/**
 * Created by Mykola on 11.05.2016.
 * вектор поправок на дистанціях
 */
public class CorrectionVec extends BCVector<MeteoBalisticCorrectionPair>
{
    public CorrectionDistTIt GetDistTIt()
    {
        return new CorrectionDistTIt(this);
    }

    public CorrectionDistPIt GetDistPIt()
    {
        return new CorrectionDistPIt(this);
    }

    public CorrectionDistWIt GetDistWit()
    {
        return new CorrectionDistWIt(this);
    }

    public CorrectionDirWIt GetDirWIt()
    {
        return new CorrectionDirWIt(this);
    }

    public CorrectionDerIt GetDerivationIt()
    {
        return new CorrectionDerIt(this);
    }

}
