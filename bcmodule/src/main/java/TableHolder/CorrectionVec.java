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
        return new CorrectionDistTIt();
    }

    public CorrectionDistPIt GetDistPIt()
    {
        return new CorrectionDistPIt();
    }

    public CorrectionDistWIt GetDistWit()
    {
        return new CorrectionDistWIt();
    }

    public CorrectionDirWIt GetDirWIt()
    {
        return new CorrectionDirWIt();
    }

    public CorrectionDerIt GetDerivationIt()
    {
        return new CorrectionDerIt();
    }

}
