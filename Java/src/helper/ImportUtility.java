package helper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ImportUtility 
{
    private static final boolean DEBUG = false;

    //Returns and Array containing all the lines of the Input File
    public static String[] getLines(String inputPath)
    {
        if(DEBUG)
            System.out.printf("Trying to open File %s.%n", inputPath);
        String lines[] = null;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath))))
        {
            //get lines from file and turn them into an array:
            lines = reader.lines().toArray(String[]::new);
            for(int i = 0; i<lines.length; i++)
            {
                //debug:
                if(DEBUG)
                    System.out.printf("Line %d: %s%n", i, lines[i]);
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.printf("File couldn't be found at provided Sourcepath of %s%n", inputPath);
        }
        catch(IOException e)
        {
            System.out.printf("The Reader encountered a Problem%n", inputPath);
        }

        return lines;
    }

    //Takes the input Array and splits the lines alongside the regular expression "\s+"
    //In: The array to be split and the amount of colums to split into
    //Out: An Array containing the columns of the input as subarrays
    public static String[][] splitLines(String[] arrayIn, int columns)
    {
        String arrayOut[][] = new String[columns][arrayIn.length]; 
        for(int i=0; i < arrayIn.length; i++)
        {
            String[] strArr = arrayIn[i].split("\\s+"); //splitting line
            
            if(DEBUG)
                System.out.printf("Split line %s into %s%n", arrayIn[i], 
                strArr[0] +
                ( 
                    (strArr.length==2) ? " and " + strArr[1] :
                    (strArr.length==3) ? ", "+ strArr[1] + " and " + strArr[2] :
                    (strArr.length==4) ? ", "+ strArr[1] + " ," + strArr[2] + " and " + strArr[3]:
                    (strArr.length==5) ? ", "+ strArr[1] + " ," + strArr[2] + " ," + strArr[3] + " and " + strArr[4] :
                    "more than 5 subsets")
                );


            for(int j=0; j < strArr.length;j++)
            {
                arrayOut[j][i]=strArr[j]; //adding split lines to output
            }
        }

        return arrayOut;
    }

    //Takes the Input Array and returns an array with each line properly split into it's colums.
    public static String[][] splitColums(String[] arrayIn)
    {
        String arrayOut[][] = new String[arrayIn.length][];

        for(int i=0; i < arrayIn.length; i++)
        {
            String[] strArr = arrayIn[i].split("\\s+");
            arrayOut[i] = strArr;
            if(DEBUG)
                System.out.printf("Split line %s into %s%n", arrayIn[i], 
                strArr[0] +
                ( 
                    (strArr.length==2) ? " and " + strArr[1] :
                    (strArr.length==3) ? ", "+ strArr[1] + " and " + strArr[2] :
                    (strArr.length==4) ? ", "+ strArr[1] + " ," + strArr[2] + " and " + strArr[3]:
                    (strArr.length==5) ? ", "+ strArr[1] + " ," + strArr[2] + " ," + strArr[3] + " and " + strArr[4] :
                    "more than 5 subsets")
                );
        }

        return arrayOut; 
    }
}