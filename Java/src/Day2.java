import helper.ImportUtility;
import helper.StringUtility;

public class Day2 
{
    static final boolean DEBUG = false;

    public static void run(String fileName)
    {
        String input[] = ImportUtility.getLines(Main.RessourcePath+fileName);
        if(input!=null)
        {
            String lines[][] = ImportUtility.splitColums(input);
            int numbers[][] = new int[lines.length][];

            for(int i = 0; i < lines.length; i++)
            {
                numbers[i] = StringUtility.convertToInt(lines[i]);
            }

            int count = 0;
            for(int[] row : numbers)
            {
                boolean safe = isSafe(row);

                if(safe)
                    count++;
                else
                {
                    safe = isSafeWithDampener(row);
                    if(safe)
                    {
                        count++;
                    }
                }
                
                if(DEBUG)
                    System.out.printf("The line is " + (safe?"safe":"unsafe") + "\n");
            }
            System.out.printf("%n there were %d safe lines.%n", count);
        }
    }
    

    private static boolean isSafe(int[] arrayIn)
    {
        boolean increases = true;
        boolean safe = true;
        if(arrayIn.length >1)
        {
            if(DEBUG)
            {
                System.out.printf("Checking if the series of");
                for(int i : arrayIn)
                    System.out.printf(" %d,", i);
                System.out.printf(" is Safe.%n");
            }
            //check if array increases or decreases. In case of no difference, it will be declared unsafe anyways
            if(arrayIn[0] > arrayIn[1])
                increases = false;
            if(DEBUG)
                System.out.printf("The line " + (increases?"increases%n":"decreases%n"));

            for(int i = 1; i < arrayIn.length; i++)
            {
                if(increases)
                {
                    if(arrayIn[i] <= arrayIn[i-1])
                    {
                        safe = false;
                        if(DEBUG)
                            System.out.printf("unsafe because %d is not higher than %d%n", arrayIn[i], arrayIn[i-1]);
                    }
                    if(arrayIn[i] > arrayIn[i-1]+3)
                    {
                        safe = false;
                        if(DEBUG)
                            System.out.printf("unsafe because %d is more than 3 higher than %d%n", arrayIn[i], arrayIn[i-1]);
                    }
                }
                else if(!increases)
                {
                    if(arrayIn[i] >= arrayIn[i-1])
                    {
                        safe = false;
                        System.out.printf("unsafe because %d is not lower than %d%n", arrayIn[i], arrayIn[i-1]);
                    }
                    if(arrayIn[i] < arrayIn[i-1]-3)
                    {
                        safe = false;
                        System.out.printf("unsafe because %d is more than 3 lower than %d%n", arrayIn[i], arrayIn[i-1]);
                    }
                }
            }
        }
        return safe;
    }

    private static boolean isSafeWithDampener(int[] arrayIn)
    {
        if(DEBUG)
        {
            System.out.printf("Check Problem Dampener for");
            for(int part : arrayIn)
                System.out.printf(" %d,",part);
            System.out.printf("%n");
        }
        boolean safe = false;
        for(int i=0; i<arrayIn.length; i++)
        {
            if(DEBUG)
                System.out.printf("Check if line is safe without %d%n", arrayIn[i]);
            int[] arrayNew = new int[arrayIn.length-1];
            
            for(int j=0; j<arrayIn.length; j++)
            {
                if(i!=j)
                {
                    if(j<i)
                        arrayNew[j]=arrayIn[j];
                    else if(j>i)
                        arrayNew[j-1]=arrayIn[j];
                }
                else if(DEBUG) System.out.printf("Skipping the %d" + (j==0 ? "st" : j==1 ? "nd" : j==2 ? "rd" : "th") + " element of %d%n", j+1, arrayIn[j]);
            }

            if(isSafe(arrayNew))
            {
                safe = true;
                if(DEBUG)
                {
                    System.out.printf("The Line of");
                    for(int part : arrayIn)
                        System.out.printf(" %d,", part);
                    System.out.printf(" without %d is safe!%n", arrayIn[i]);
                }
            }
        }
        return safe;
    }
}
