package Utilities;

/**
 * Created by Mykola on 09.05.2016.
 * Пара значень як правило first - ключ, second - значення
 */
public class BCPair<firstT, secondT>
{
    public BCPair(firstT f, secondT s)
    {
        first = (firstT)f.getClass();
        second = (secondT)s.getClass();
    }

    public firstT  first;
    public secondT second;
}
