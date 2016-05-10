package TableHolder;

import Utilities.BCVector;

/**
 * Created by Mykola on 10.05.2016.
 */
public class DistOverVecPair extends BCVector<DistOverPair>
{
    public DistOverVecPairIt GetDistOverIt()
    {
        return new DistOverVecPairIt(this);
    }
}
