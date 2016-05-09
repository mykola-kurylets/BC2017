package com.kurylets.mykola.bcmodule;

/**
 * Created by Mykola on 09.05.2016.
 * Клас що містить вихидні дані (результат роботи)
 */
public class OutputData
{
    public OutputData()
    {
        m_VSight = 0;
        m_VOut = 0.0;
        m_HSight = new String();
        m_HOut = 0.0;
    }

    public void SetVerticalSight(int sight)
    {
        m_VSight = sight;
    }

    // встановити вертикальне зміщення Т. П.
    public void SetVerticalDeviation(double dev)
    {
        m_VOut = dev;
    }

    public void SetHorizontalSight(String sight)
    {
        m_HSight = null;
        m_HSight = new String(sight);
    }

    // встановити горизонталье зміщення Т. П.
    public void SetHorizontalDeviation(double dev)
    {
        m_HOut = dev;
    }

    public int GetVerticalSight()
    {
        return m_VSight;
    }

    // отримати вертикальне зміщення Т. П.
    public double GetVerticalDeviation()
    {
        return m_VOut;
    }

    public String GetHorizontalSight()
    {
        return new String(m_HSight);
    }

    // отримати горизонталье зміщення Т. П.
    public double GetHorizontalDeviation()
    {
        return m_HOut;
    }


    private int m_VSight;       // номер вертикального прицілу
    private double m_VOut;      // зміщення Т.П. (точки прицілювання)
    private String m_HSight;    // номер і кількість кліків для горизонтального прицілу
    private double m_HOut;      // зміщення Т.П.
}
