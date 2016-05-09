package Utilities;

import java.util.Vector;

/**
 * Created by Mykola on 09.05.2016.
 */
public class BCVector<T> extends Vector<T>
{
    public BCVectorIterator<T> Begin()
    {
        return new BCVectorIterator<T>(this);
    }

    public BCVectorIterator<T> End()
    {
        return new BCVectorIterator<T>(this, size());
    }

    public BCVectorIterator<T> IteratorOnPos(int pos)
    {
        return new BCVectorIterator<T>(this, pos);
    }
}
