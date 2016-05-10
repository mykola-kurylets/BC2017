package TableHolder;

import Utilities.BCPair;

/**
 * Created by Mykola on 11.05.2016.
 * пара дистанція - набір поправок на дистанції
 */
public class MeteoBalisticCorrectionPair extends BCPair<Integer, MeteoBalisticCorrection>
{

    public MeteoBalisticCorrectionPair(Integer f, MeteoBalisticCorrection s)
    {
        super(f, s);
    }
}
