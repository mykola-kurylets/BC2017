package com.kurylets.mykola.bcmodule;

/**
 * Created by Mykola on 20.05.2016.
 */
public enum WindDirections
{
    e0(0), 
    e45(45),
    e90(90),
    e135(135),
    e180(180),
    e225(225),
    e270(270),
    e315(315);

    WindDirections(int i){
        m_Dir = i;
    }

    int GetDir()
    {
        return m_Dir;
    }

    private int m_Dir;
}
