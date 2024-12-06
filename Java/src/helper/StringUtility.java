package helper;
import java.util.List;
import java.util.ArrayList;

public class StringUtility 
{
    public static String sanitiseString(String StringInput)
    {
        StringInput = StringInput.replaceAll("%","%%");
        StringInput = StringInput.replaceAll("\\\\","\\\\\\\\");
        return StringInput;
    }


    public static int convertToInt(String strIn)
    {
        return(Integer.parseInt(strIn));
    }

    public static int[] convertToInt(String[] arrayIn)
    {
        int arrayOut[] = new int[arrayIn.length];
        
        for(int i = 0; i < arrayIn.length; i++)
        {
            arrayOut[i] = Integer.parseInt(arrayIn[i]);
        }

        return arrayOut;
    }

    public static List<Integer> convertToInt(List<String> listIn)
    {
        List<Integer> out = new ArrayList<>();
        
        for(int i = 0; i < listIn.size(); i++)
        {
            out.add(Integer.valueOf(listIn.get(i)));
        }

        return out;
    }

    public static String linesToString(String[] in)
    {
        String out = in[0];
        for(int i = 1; i<in.length; i++)
            out += ("\n"+in[i]); 
        return out;
    }
}
