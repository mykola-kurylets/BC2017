package TableHolder;

import Utilities.VecSecondIt;

/**
 * Created by Mykola on 10.05.2016.
 */
public class DistOverVecPairIt extends VecSecondIt<DistOverPair, Double>
{
    public DistOverVecPairIt(DistOverVecPair vec)
    {
        super(vec);
    }
    @Override
    public Double Get()
    {
        DistOverPair pair = GetCurrentItem();
        if(pair == null)
            return null;

        return pair.second;
    }
}
