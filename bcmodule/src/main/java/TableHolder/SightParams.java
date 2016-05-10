package TableHolder;

import Utilities.BCPair;

/**
 * Created by Mykola on 11.05.2016.
 * Пара - номер прицілу, вектор пар - дистанція, завищення
 */
public class SightParams extends BCPair<Integer, DistOverVecPair>
{
    public SightParams(Integer f, DistOverVecPair s)
    {
        super(f, s);
    }
}
