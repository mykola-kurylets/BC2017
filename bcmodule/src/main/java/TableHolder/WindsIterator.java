package TableHolder;

import Utilities.BCPairsVecUtilities;
import Utilities.BCVector;
import Utilities.VecSecondIt;

/**
 * Created by Mykola on 19.05.2016.
 */
public abstract class WindsIterator extends VecSecondIt<MeteoBalisticCorrectionPair, Double>
{
    public WindsIterator(BCVector<MeteoBalisticCorrectionPair> vec) {
        super(vec);
        m_WindIndex = -1;
    }

    @Override
    public Double Get() {

        WindsVec winds = GetWinds();
        if(winds == null)
            return Double.MAX_VALUE;

        WindPair wp = null;
        try {
            wp = winds.elementAt(m_WindIndex);
        }
        catch (ArrayIndexOutOfBoundsException e){
            winds.clear();
            return Double.MAX_VALUE;
        }

        Double res = new Double(wp.second);

        winds.clear();

        return res;
    }

    public boolean SetWindIndex(double windSpeed)
    {
        WindsVec winds = GetWinds();
        if(winds == null)
            return false;

        int windPoints[] = {-1, -1, -1};
        if(!BCPairsVecUtilities.FindNeighborhoodOfPoint(winds, windPoints, windSpeed))
            return false;

        m_WindIndex = windPoints[2];

        return true;
    }

    public int GetWindIndex()
    {
        return m_WindIndex;
    }

    protected abstract WindsVec GetWinds();

    protected int m_WindIndex;
}
