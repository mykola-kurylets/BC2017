package Utilities;

/**
 * Created by Mykola on 15.05.2016.
 */
public class InterpolationPolynomialNewton
{
    public static double Calculate(double x, InterpolationNodesVec nodes) throws Exception
    {
        double res = 0.0;

        InterpolationNode node = nodes.elementAt(0);
        res = node.GetY();
        for(int i = 1, start = 0, end = nodes.size() - 1; i <= end; ++i){
            try {
                res += (DevidedDifference(nodes, start, i) * Polynomial(nodes, start, i - 1, x));
            }
            catch (Exception e) {
                throw e;
            }
        }

        return res;
    }

    private static double DevidedDifference(InterpolationNodesVec nodes, int start, int end)throws ArrayIndexOutOfBoundsException, ArithmeticException
    {
        InterpolationNode startNode = null;
        InterpolationNode endNode = null;
        double denominator = 0.0;
        double firstDifference = 0.0;
        double secondDifference = 0.0;
        double res = 0.0;

        try {
            if(start == end){
                InterpolationNode node = nodes.elementAt(start);
                return node.GetY();
            }

            startNode = nodes.elementAt(start);
            endNode = nodes.elementAt(end);
            denominator = endNode.GetX() - startNode.GetX();

            if(Math.abs(denominator) < 1e-6)
                throw new ArithmeticException("denominator = 0.0");

            firstDifference = DevidedDifference(nodes, start + 1, end);
            secondDifference = DevidedDifference(nodes, start, end - 1);

            res = (firstDifference - secondDifference) / denominator;
        }
        catch (ArrayIndexOutOfBoundsException aoe) {
            throw aoe;
        }

        return res;
    }

    private static double Polynomial(InterpolationNodesVec nodes, int start, int end, double x)throws ArrayIndexOutOfBoundsException
    {
        double res = 1.0;
        for(; start <= end; ++start){
            InterpolationNode node = nodes.elementAt(start);
            res *= (x - node.GetX());
        }

        return res;
    }

}
