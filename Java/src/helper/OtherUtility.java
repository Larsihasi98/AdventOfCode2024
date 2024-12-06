package helper;


import java.util.ArrayList;
import java.util.List;

public class OtherUtility 
{
    static final boolean DEBUG = false;
    public static <T> List<T[]> allCombinations(T[] input)
    {
        List<T[]> out = new ArrayList<>();
        allCombinations(input.length, input, out);

        return out;
    }

    private static <T> void allCombinations(int k, T[] in, List<T[]> out)
    {
        T[] input = in;

        if(DEBUG)
        {
            System.out.printf("\ninput to check: with k = %d: ", k);
            for(T i : input)
            {
                System.out.printf(" "+i+",");
            }
        }

        if(k == 1)
        {
            //No more alterations possible
            out.add(input.clone());
        }
        else
        {
            //Generate permutations with k-th unaltered
            allCombinations(k-1, input, out);

            //Generate permutations for k-th swapped with each k-1 initial
            if(DEBUG)
            {
                System.out.printf("\nGenerating permutations with k = %d\n", k);
            }
            for(int i = 0; i<k-1; i++)
            {
                if(DEBUG)
                {
                    System.out.printf("\nCode Preswap: ", k);
                    for(T t : input)
                    {
                        System.out.printf(" "+t+",");
                    }
                }
                if(k%2 == 0)
                {
                    if(DEBUG)
                        System.out.printf("\nSwapping "+ input[i] +" und "+ input[k-1]+"\n");
                    swap(input, i,k-1);
                }
                else
                {
                    if(DEBUG)
                        System.out.printf("Swapping "+ input[0] +" und "+ input[k-1]+"\n");
                    swap(input, 0,k-1);
                }
                allCombinations(k-1, input, out);
            }
        }

        if(DEBUG)
        {
            System.out.printf("\nCurrent Output: ");
            for(T[] line : out)
            {
                System.out.printf("\n- ");
                for(T i : line)
                {
                    System.out.printf(" "+i+", ");
                }
            }
        }
    }

    public static <T> List<T[]> allCombinationsNoRecursion(T[] input)
    {
        List<T[]> out = new ArrayList<>();
        allCombinationsNoRecursion(input.length, input, out);

        return out;
    }

    private static <T> void allCombinationsNoRecursion(int k, T[] in, List<T[]> out)
    {
        T[] input = in;
        int[] indexes = new int[k];

        for(int i = 0; i < k; i++)
        {
            indexes[i] = 0;
        }
        if(DEBUG)
        {
            System.out.printf("\ninput to check: with k = %d: ", k);
            for(T i : input)
            {
                System.out.printf(" "+i+",");
            }
        }
        out.add(input.clone());

        int i = 0;

        while(i<k)
        {
            if(indexes[i]<i)
            {
                if(DEBUG)
                {
                    System.out.printf("\nCode Preswap: ", k);
                    for(T t : input)
                    {
                        System.out.printf(" "+t+",");
                    }
                }
                if(i%2 == 0)
                {
                    if(DEBUG)
                        System.out.printf("\nSwapping "+ input[i] +" und "+ input[k-1]+"\n");
                    swap(input, i,k-1);
                }
                else
                {
                    if(DEBUG)
                        System.out.printf("Swapping "+ input[0] +" und "+ input[k-1]+"\n");
                    swap(input, 0,k-1);
                }
                
                out.add(input.clone());

                indexes[i]++;
                i = 0;
            }
            else
            {
                indexes[i] = 0;
                i++;
            }
        }
    }

    public static <T> void swap(T[] a, int i, int j)
    {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void swap(int[] a, int i, int j)
    {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static int integerToInt(Integer in)
    {
        return in;
    }

    public static int[] integerToInt(Integer[] in)
    {
        int[] out = new int[in.length];

        for(int i = 0; i < in.length; i++)
        {
            out[i] = in[i];
        }

        return out;
    }
    
    public static List<int[]> integerToInt(List<Integer[]> in)
    {
        ArrayList<int[]> out = new ArrayList<>();

        for(Integer[] line : in)
        {

            int[] temp = new int[line.length];
            
            for(int i = 0; i<line.length;i++)
            {
                temp[i] = line[i];
            }
            out.add(temp);
        }

        return out;
    }
}
