
import java.util.ArrayList;
import java.util.regex.*;

import helper.ImportUtility;
import helper.StringUtility;

public class Day3
{
    static final boolean DEBUG = false;


    public static void run(String filename, int part)
    {
        String input[] = ImportUtility.getLines(Main.RessourcePath+filename);
        if(input!=null)
        {
            if(DEBUG)
            {
                for(String line : input)
                {
                    System.out.printf(StringUtility.sanitiseString(line)+"\n");
                }
            }

            if(part == 1)
            {
                Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
                ArrayList<String> instructions = new ArrayList<String>();

                for(String line : input)
                {
                    Matcher matcher = pattern.matcher(line);

                    while(matcher.find())
                    {
                        instructions.add(matcher.group());
                    }
                }

                if(DEBUG)
                {
                    for(String line : instructions)
                    {
                        System.out.printf(StringUtility.sanitiseString(line)+"\n");
                    }
                }

                int[][] numbers = new int[2][instructions.size()];

                Pattern factor = Pattern.compile("\\d{1,3}");
                for(int i = 0; i<instructions.size(); i++)
                {
                    Matcher matcher = factor.matcher(instructions.get(i));
                    if(matcher.find())
                        numbers[0][i] = StringUtility.convertToInt(matcher.group());
                    if(matcher.find())
                        numbers[1][i] = StringUtility.convertToInt(matcher.group());
                }
                int result = 0;

                for (int i = 0; i < numbers[0].length; i++)
                {
                    if(DEBUG)
                    {
                        System.out.printf("Instruction %d: Multiply %d and %d.%n", i, numbers[0][1], numbers[1][i]);
                    }

                    result += numbers[0][i]*numbers[1][i];
                }

                System.out.printf("The result is: %d", result);
            }
            else if (part == 2)
            {
                Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)");
                ArrayList<String> instructions = new ArrayList<String>();

                for(String line : input)
                {
                    Matcher matcher = pattern.matcher(line);

                    while(matcher.find())
                    {
                        instructions.add(matcher.group());
                    }
                }

                if(DEBUG)
                {
                    for(String line : instructions)
                    {
                        System.out.printf(StringUtility.sanitiseString(line)+"\n");
                    }
                }

                int[][] numbers = new int[2][instructions.size()];

                Pattern factor = Pattern.compile("\\d{1,3}");
                boolean dobidibido = true;

                for(int i = 0; i<instructions.size(); i++)
                {
                    String instruction = instructions.get(i);

                    if(DEBUG)
                    {
                        System.out.printf("Instruction: %s%n", instruction);
                    }

                    Matcher matcher = factor.matcher(instruction);
                    Matcher matcherMul = pattern.matcher(instruction);
                    if(instruction.equals("do()"))
                    {
                        dobidibido = true;
                        if(DEBUG)
                        {
                            System.out.printf("allowing mul instructions%n");
                        }
                    }
                    else if(instruction.equals("don't()"))
                    {
                        dobidibido = false;
                        if(DEBUG)
                        {
                            System.out.printf("preventing mul instructions%n");
                        }
                    }
                    else if (dobidibido && matcherMul.matches())
                    {
                        if(DEBUG)
                        {
                            System.out.printf("Adding Factors to numbers%n");
                        }
                        if(matcher.find())
                            numbers[0][i] = StringUtility.convertToInt(matcher.group());
                        if(matcher.find())
                            numbers[1][i] = StringUtility.convertToInt(matcher.group());
                    }
                    else if (!dobidibido && matcherMul.matches())
                    {
                        if(DEBUG)
                            System.out.printf("Skipping instruction");
                    }
                    else System.out.printf("How did we get here?%n");
                }
                int result = 0;

                for (int i = 0; i < numbers[0].length; i++)
                {
                    if(DEBUG)
                    {
                        System.out.printf("Instruction %d: Multiply %d and %d.%n", i, numbers[0][1], numbers[1][i]);
                    }

                    result += numbers[0][i]*numbers[1][i];
                }

                System.out.printf("The result is: %d", result);
            }
        }
    }
}
