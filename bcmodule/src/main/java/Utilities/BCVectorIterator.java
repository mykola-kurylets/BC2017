package Utilities;

/**
 * Created by Mykola on 09.05.2016.
 */
public class BCVectorIterator<T>
{
    public BCVectorIterator(BCVector<T> vec)
    {
        m_BCVector = vec;
        m_Pos = 0;
    }

    public BCVectorIterator(BCVector<T> vec, int pos)
    {
        this(vec);
        m_Pos = pos;
    }

    public void StepForward()
    {
        ++m_Pos;
    }

    public void StepBack()
    {
        --m_Pos;
    }

    public boolean Equal(BCVectorIterator<T> it)
    {
        return m_Pos == it.GetPos();
    }

    public T GetCurrentItem()
    {
        if(!IsInBound())
            return null;

        return m_BCVector.elementAt(m_Pos);
    }

    public boolean SetCurrentItem(T obj)
    {
        if(!IsInBound())
            return false;

        m_BCVector.setElementAt(obj, m_Pos);

        return true;
    }

    public int GetPos()
    {
        return m_Pos;
    }

    public void SetPos(int pos)
    {
        m_Pos = pos;
    }

    public BCVector<T> GetVector()
    {
        return m_BCVector;
    }

    protected boolean IsInBound()
    {
        return (m_Pos >= 0 && m_Pos < m_BCVector.size());
    }

    protected BCVector<T> m_BCVector;
    protected int m_Pos;
}
