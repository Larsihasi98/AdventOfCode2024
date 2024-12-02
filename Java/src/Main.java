import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import helper.ImportUtility;

public class Main 
{
    static final String ROOT = System.getProperty("user.dir")+"\\java";
    static final String RessourcePath = ROOT+"\\resc\\";
    static final boolean DEBUG = false;
    public static void main(String[] args)
    {
        //Day1();

        Day2("input2.txt");
    }

    public static void Day1()
    {
        Boolean debug = false; //Set True to debug
        //Path to Input
        //String ROOT = System.getProperty("user.dir");
        //String PATH = "\\resc\\input1.txt";
        String input_path = RessourcePath + "input1.txt";
        //prepare two arrays of ints for the two sets of Numbers
        int[] num1 = null;
        int[] num2 = null;

        //getting Input from file and parsing it into two sets of ints
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input_path))))
        {
            //get lines from file and turn them into an array:
            String lines[] = reader.lines().toArray(String[]::new);
            num1 = new int[lines.length];
            num2 = new int[lines.length];
            for(int i = 0; i<lines.length; i++)
            {
                //split String along the regular expression \\s+, symbolising multiple spaces
                String[] strArr = lines[i].split("\\s+");

                //adding first String as a number to num1, and second String to num2
                num1[i] = Integer.parseInt(strArr[0]);
                num2[i] = Integer.parseInt(strArr[1]);
                
                //debug:
                if(debug)
                    System.out.printf("Line %d: %s%n - No.1: %d%n - No.2: %d%n%n", i, lines[i], num1[i], num2[i]);
            }
            
            
            //Sort Lists of Numbers from smallest to largest:
            Arrays.sort(num1);
            Arrays.sort(num2);
            if(debug)
                System.out.printf("Arrays Sorted");
        }
        catch(FileNotFoundException e)
        {
            System.out.printf("File couldn't be found at provided Sourcepath of %s%n", input_path);
        }
        catch(IOException e)
        {
            System.out.printf("The Reader encountered a Problem%n", input_path);
        }
        
        if (num1 != null && num2 != null)
        {
            int out = 0;
            for(int i=0; i<num1.length; i++)
            {
                //debug:
                if(debug)
                    System.out.printf("Comparing %d and %d. Difference is %d. Adding it to %d%n", num1[i], num2[i], Math.abs(num1[i]-num2[i]), out);
                out = out + Math.abs(num1[i]-num2[i]);
            }
            System.out.printf("%nThe total distance is: %d%n", out);

            //Part 2: Similarity Score:
            int out2 = 0;
            System.out.printf("Starting calculation of similarity score");
            for (int i : num1)
            {
                int count = 0;
                for(int j : num2)
                {
                    if(i == j)
                        count ++;
                }
                if(debug)
                    System.out.printf("Number %d occurs %d times on the right list. The similarity score increases by %d%n", i, count, i*count);
                out2 = out2 + (i*count);
            }

            System.out.printf("%nThe Similarity Score is %d%n", out2);
        }
        else
            System.out.printf("%narrays are empty%n");

        
    }

    public static void Day2(String fileName)
    {
        String input[] = ImportUtility.getLines(RessourcePath+fileName);
        if(input!=null)
        {
            String lines[][] = ImportUtility.splitColums(input);
            int numbers[][] = new int[lines.length][];

            for(int i = 0; i < lines.length; i++)
            {
                numbers[i] = ImportUtility.convertToInt(lines[i]);
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
