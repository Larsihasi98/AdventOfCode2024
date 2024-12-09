import helper.ImportUtility;

import java.util.ArrayList;
import java.util.regex.*;

public class Day7
{
    private static final boolean DEBUG = false;
    private final int part;
    final String[] content;
    private final ArrayList<Calculation> calculations;

    public static void run(String filename, int part)
    {
        String input[] = ImportUtility.getLines(Main.RESSOURCEPATH+filename);
        if(input!=null)
        {
            Day7 day = new Day7(input, part);
            day.start();
        }
    }

    public Day7(String[] input, int part)
    {
        this.part = part;
        this.content = input;
        this.calculations = new ArrayList<>();
    }

    private void start()
    {
        if(part == 1)
        {
            Pattern calculation = Pattern.compile("^(?<Result>\\d+):(?<Numbers>( \\d+)+)$");
            for(String line : content)
            {
                Matcher matcher = calculation.matcher(line);
                if(matcher.find())
                {
                    long result = Long.parseLong(matcher.group("Result"));
                    String[] temp = matcher.group("Numbers").split(" ");
                    int[] numbers = new int[temp.length-1];
                    for(int i = 1; i<temp.length;i++)
                    {
                        numbers[i-1] = Integer.parseInt(temp[i]);
                    }
                    // int[] numbers = StringUtility.convertToInt(matcher.group("Numbers").split(" "));
                    calculations.add(new Calculation(result, numbers));
                    if(DEBUG)
                    {
                        String strOut = "Creating a calculation by adding the numbers of";
                        for(int i : numbers)
                        {
                            strOut += " " + i;
                        }
                        strOut+= " resulting in " + result + " using unknown means\n";
                        System.out.println(strOut);
                    }
                }
                else
                {
                    System.out.printf("Unable to create a calculation based on the input: %s\n", line);
                }
            }

            long endresult = 0;

            for(Calculation c : calculations)
            {
                if(DEBUG)
                {
                    System.out.println(c.toString());
                }
                endresult = Math.addExact(endresult, c.calculate());
            }

            System.out.printf("The Endresult of all possible equations is %d", endresult);

            for(Calculation c : calculations)
            {
                c.check();
            }
        }

        if(part == 2)
        {
            Pattern calculation = Pattern.compile("^(?<Result>\\d+):(?<Numbers>( \\d+)+)$");
            for(String line : content)
            {
                Matcher matcher = calculation.matcher(line);
                if(matcher.find())
                {
                    long result = Long.parseLong(matcher.group("Result"));
                    String[] temp = matcher.group("Numbers").split(" ");
                    int[] numbers = new int[temp.length-1];
                    for(int i = 1; i<temp.length;i++)
                    {
                        numbers[i-1] = Integer.parseInt(temp[i]);
                    }
                    // int[] numbers = StringUtility.convertToInt(matcher.group("Numbers").split(" "));
                    calculations.add(new Calculation(result, numbers));
                    if(DEBUG)
                    {
                        String strOut = "Creating a calculation by adding the numbers of";
                        for(int i : numbers)
                        {
                            strOut += " " + i;
                        }
                        strOut+= " resulting in " + result + " using unknown means\n";
                        System.out.println(strOut);
                    }
                }
                else
                {
                    System.out.printf("Unable to create a calculation based on the input: %s\n", line);
                }
            }

            long endresult = 0;

            for(Calculation c : calculations)
            {
                if(DEBUG)
                {
                    System.out.println(c.toString());
                }
                endresult = Math.addExact(endresult, c.calculate());
            }

            System.out.printf("The Endresult of all possible equations is %d", endresult);
        }
    }

    

    private class Calculation
    {
        final long result;

        final int[] numbers;

        Operator[] operators;

        boolean solveable = false;

        public Calculation(long resultIn, int[] numbersIn)
        {
            this.result = resultIn;
            this.numbers = numbersIn;
            this.operators = new Operator[this.numbers.length-1];


            if(DEBUG)
            {
                System.out.printf("Created a new Calculation resulting in %d usign unknown means\n", this.result);
            }

            this.calculateOperators();
        }

        public long calculate()
        {
            if(solveable == true)
                return this.result;
            else
                return 0;
        }

        public boolean check()
        {
            long allegedResult = this.result;
            long tempResult = this.numbers[0];
            for(int i = 1; i<this.numbers.length; i++)
            {
                switch(this.operators[i-1])
                {
                    case MULTIPLICATION:
                        tempResult = Math.multiplyExact(tempResult, this.numbers[i]);
                        break;
                    case ADDITION:
                        tempResult = Math.addExact(tempResult, this.numbers[i]);
                    case CONCATENATION:
                    tempResult = Long.parseLong((""+tempResult)+this.numbers[i]);
                    default:
                        assert false;
                }

            }

            if(tempResult!=allegedResult && solveable == true)
            {
                System.out.printf("False Positive on result %d!\n", this.result);
                return false;
            }
            else if(tempResult==allegedResult && !solveable)
            {
                System.out.printf("False Negative on result %d\n", result);
                return false;
            }
            else
                return true;
        }

        private void calculateOperators()
        {
            int possibleEnums = Operator.values().length;
            boolean foundSolution = false;

            // System.out.printf("Going for %d attempts (%d^%d)\n",(int)Math.pow(possibleEnums, this.operators.length), possibleEnums, this.operators.length);
            int i = 0;
            while((!foundSolution) && i < Math.pow(possibleEnums, this.operators.length))
            {
                for(int j = 0; j < operators.length; j++)
                {
                    this.operators[j] = Operator.valueOf(Math.floorDiv(i,(int)Math.pow(possibleEnums, j))%possibleEnums);
                    if(DEBUG)
                        System.out.printf("Setting the %dth operator to %d\n", j, this.operators[j].operatorValue);
                }

                long testResult = numbers[0];
                for(int j = 0; j < operators.length; j++)
                {
                    switch(operators[j])
                    {
                        case MULTIPLICATION:
                            if(DEBUG)
                            {
                                System.out.printf("Multiplying %d with % d\n", testResult, numbers[j+1]);
                            }
                            testResult = Math.multiplyExact(testResult, numbers[j+1]);
                            break;
                        
                        case ADDITION:
                            if(DEBUG)
                            {
                                System.out.printf("Adding %d and % d together\n", testResult, numbers[j+1]);
                            }
                            testResult = Math.addExact(testResult, numbers[j+1]);
                            break;

                        case CONCATENATION:
                            if(DEBUG)
                            {
                                System.out.printf("Concatenating %d and % d together\n", testResult, numbers[j+1]);
                            }
                            testResult = Long.parseLong((""+testResult)+numbers[j+1]);
                            break;
                        default:
                            System.out.printf("We need more operators apparently");
                    }
                }
                if(testResult==this.result)
                {
                    foundSolution = true;
                }
                i++;
            }

            if(foundSolution)
            {
                this.solveable = true;
            }
        }

        @Override
        public String toString()
        {
            if(this.solveable)
            {
                String toString = "";
                toString += numbers[0];

                for(int i = 1; i < numbers.length; i++)
                {
                    switch(operators[i-1])
                    {
                        case MULTIPLICATION:
                            toString += "*";
                            break;
                        case ADDITION:
                            toString += "+";
                            break;
                        case CONCATENATION:
                            toString += "||";
                        default:
                            toString += "###";
                    }

                    toString += numbers[i];
                }
                toString += "=" + result;

                return toString;
            }
            else
                return "Not Possible";
        }
    }

    private enum Operator
    {
        MULTIPLICATION(0), ADDITION(1), CONCATENATION(2);
        private final int operatorValue;

        Operator(int operatorValue)
        {
            this.operatorValue = operatorValue;
        }

        public static Operator valueOf(int value) 
        {
            for (Operator o : Operator.values())
            {
                if (o.operatorValue == value) return o;
            }
            throw new IllegalArgumentException("Operator not found");
        }
    }
}
