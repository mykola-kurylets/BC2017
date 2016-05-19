package Utilities;

/**
 * Created by Mykola on 16.05.2016.
 */
public class BCPairsVecUtilities
{
    public static <F extends Number, S, T extends BCPair<F, S>, D extends Number> double InterpolateInNeighborhood(VecSecondIt<T, D> it, double x)
    {
        BCVector<T> vec = it.GetVector();
        if(vec == null)
            return Double.MAX_VALUE;

        int []out = {-1, -1, -1};
        if(!FindNeighborhoodOfPoint(vec, out, x))
            return Double.MAX_VALUE;

        int startIndex = out[0];
        int endIndex = out[1];
        final int nearest = out[2];

        if(startIndex == endIndex){
            it.SetPos(startIndex);
            D val = it.Get();
            if(val == null)
                return Double.MAX_VALUE;
            return val.doubleValue();
        }

        if(!Find4Niarest(vec.size(), startIndex, endIndex, out))
            return Double.MAX_VALUE;

        startIndex = out[0];
        endIndex = out[1];

        InterpolationNodesVec interpolationNodes = new InterpolationNodesVec();
        for(it.SetPos(startIndex); startIndex <= endIndex; ++startIndex, it.StepForward()){
            T pairVal = it.GetCurrentItem();
            D secondVal = it.Get();
            if(secondVal == null)
                return Double.MAX_VALUE;

            interpolationNodes.add(new InterpolationNode(pairVal.first.doubleValue(), secondVal.doubleValue()));
        }


        double res = Double.MAX_VALUE;
        try {
            res = InterpolationPolynomialNewton.Calculate(x, interpolationNodes);
            interpolationNodes.clear();
        } catch (Exception e){
            interpolationNodes.clear();
            return Double.MAX_VALUE;
        }

        return res;
    }

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

        if (size == 1)
            return CheckPoint(vec.elementAt(0), out, x, 0);

        boolean outOfRange = true;
        for(int i = 0, next = 1, stape = size - 1; next <= stape; ++i, ++next){
            T pairF = vec.elementAt(i);
            T pairS = vec.elementAt(next);
            // точка у табличному значенні
            if(CheckPoint(pairF, out, x, i))
                return true;

            if(CheckPoint(pairS, out, x, next))
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

    private static <F extends Number, S, T extends BCPair<F, S>> boolean CheckPoint(T pair, int []out, Double x, int pos)
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
