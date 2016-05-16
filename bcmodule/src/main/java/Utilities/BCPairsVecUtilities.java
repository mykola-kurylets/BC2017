package Utilities;

/**
 * Created by Mykola on 16.05.2016.
 */
public class BCPairsVecUtilities
{
    public static boolean Find4Niarest(int lastElemIndex, int first, int second, int []out )
    {
        if(out.length < 2)
            return false;

        if(first == second){
            out[0] = first;
            out[1] = second;
            return true;
        }

        int f1 = first - 2;
        int s1 = second + 2;

        if(f1 < 0)
            f1 = 0;

        if(s1 > lastElemIndex)
            s1 = lastElemIndex;

        if((s1 - f1) <= 3 ){
            out[0] = f1;
            out[1] = s1;
            return true;
        }

        out[0] = first - 1;
        out[1] = second + 1;

        return true;
    }

    public static <F extends Number, S, T extends BCPair<F, S>> boolean FindNeighborhoodOfPoint(BCVector<T> vec, int []out, Double x)
    {
        if(out.length < 3)
            return false;

        final int size = vec.size();

        if(size == 1)
            return ChechPoint(vec.elementAt(0), out, x, 0);

        boolean outOfRange = true;
        for(int i = 0, next = 1, stape = size - 1; next <= stape; ++i, ++next){
            T pairF = vec.elementAt(i);
            T pairS = vec.elementAt(next);
            // точка у табличному значенні
            if(ChechPoint(pairF, out, x, i))
                return true;

            if(ChechPoint(pairS, out, x, next))
                return true;
            //
            if(pairF.first.doubleValue() < x && x < pairS.first.doubleValue() ){
                out[0] = i;
                out[1] = next;
                outOfRange = false;
                break;
            }
        }

        if(outOfRange)
            return false;

        T pairF = vec.elementAt(out[0]);
        T pairS = vec.elementAt(out[1]);

        if(Math.abs(x - pairF.first.doubleValue()) < Math.abs(x - pairS.first.doubleValue()))
            out[2] = out[0];
        else
            out[2] = out[1];

        return true;
    }

    private static <F extends Number, S, T extends BCPair<F, S>> boolean ChechPoint(T pair, int []out, Double x, int pos)
    {
        if(out.length < 3)
            return false;

        if(Math.abs(pair.first.doubleValue() - x) > MathConstants.gePointDelta)
            return false;

        out[0] = pos;
        out[1] = pos;
        out[2] = pos;
        return true;
    }
}
