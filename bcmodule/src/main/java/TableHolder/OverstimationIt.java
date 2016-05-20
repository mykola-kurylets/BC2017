package TableHolder;

import Utilities.BCPairsVecUtilities;
import Utilities.BCVector;
import Utilities.VecSecondIt;

/**
 * Created by Mykola on 19.05.2016.
 */
public class OverstimationIt extends VecSecondIt<SightParams, Double>
{
    public OverstimationIt(BCVector<SightParams> vec, double dist) {
        super(vec);
        m_Distance = dist;
    }

    @Override
    public Double Get() {

        SightParams sp = GetCurrentItem();
        if(sp == null)
            return null;

        DistOverVecPair oversVec = sp.second;
        if(oversVec == null)
            return null;

        DistOverVecPairIt oversVecIt = oversVec.GetDistOverIt();
        if(oversVecIt == null)
            return null;

        return BCPairsVecUtilities.InterpolateInNeighborhood(oversVecIt, m_Distance);
    }

    private double m_Distance;
}
