package Utilities;

/**
 * Created by Mykola on 09.05.2016.
 */
public abstract class VecSecondIt<T, D> extends BCVectorIterator<T> implements SecondValIt<D>
{
    public VecSecondIt(BCVector<T> vec)
    {
        super(vec);
    }

    abstract public D Get();
}
