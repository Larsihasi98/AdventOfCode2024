import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main 
{
    public static void main(String[] args) throws Exception 
    {
        Boolean debug = false; //Set True to debug
        //Path to Input
        String ROOT = System.getProperty("user.dir");
        String PATH = "\\resc\\input.txt\\";
        String input_path = ROOT+PATH;
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
}
