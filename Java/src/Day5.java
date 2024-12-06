import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.util.Arrays;
import helper.ImportUtility;
import helper.StringUtility;
import helper.OtherUtility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Day5
{
    static final boolean DEBUG = false;
    final int part;
    final String[] content;


    private static class Ordering
    {
        private class Node implements Comparable<Node>
        {
            private ArrayList<Node> precursers;
            private final int value;
            
            public Node(int number)
            {
                value = number;
            }

            public int getValue()
            {
                return this.value;
            }

            public boolean isFirst()
            {
                return precursers == null;
            }


            //Add a precurser and return true, if successfull
            public void addPrecurser(Node precurser)
            {
                if(this.isFirst())
                {
                    precursers = new ArrayList<>();
                    precursers.add(precurser);
                }
                else
                {
                    precursers.add(precurser);
                }
            }

            public ArrayList<Node> getPrecursers()
            {
                if(this.isFirst())
                {
                    return new ArrayList<>();
                }
                else
                    return precursers;
            }

            @Override
            public int compareTo(Node other)
            {
                int valueThis = this.getValue();
                int valueOther = other.getValue();

                return valueThis - valueOther;
            } 

            @Override
            public String toString()
            {
                String out = "Number " + this.getValue();
                if(this.isFirst())
                    out += " has no precurser";
                else
                {
                    out += "has the precursers of:";
                    for(Node node : this.getPrecursers())
                    {
                        out += "\n- "+ node.toString();
                    }
                }


                return out;
            }
        }

        private final ArrayList<Node> entries;

        public Ordering()
        {
            entries = new ArrayList<>();
        }

        private void newEntry(int value)
        {
            entries.add(new Node(value));
        }

        private Node findEntry(int value)
        {
            Node out = null;

            for(Node entry : entries)
            {
                if(entry.getValue() == value)
                    out = entry;
            }

            return out;
        }

        private boolean containsEntry(int value)
        {
            boolean out = false;
            for(Node entry : entries)
            {
                if(entry.getValue() == value)
                    out = true;
            }

            return out;
        }

        public void newRule(int value1, int value2)
        {
            if(!this.containsEntry(value1))
            {
                this.newEntry(value1);
            }

            if(!this.containsEntry(value2))
            {
                this.newEntry(value2);
            }

            this.findEntry(value2).addPrecurser(this.findEntry(value1));
        }

        public ArrayList<Integer> getPrecursers(int value)
        {
            ArrayList<Integer> precursers = new ArrayList<>();
            if(this.containsEntry(value))
            {
                Node entry = this.findEntry(value);
                for(Node node : entry.getPrecursers())
                {
                    precursers.add(node.getValue());
                }
            }
            return precursers;
        }

        public ArrayList<Integer> getAllPrecursers(int value)
        {            
            ArrayList<Integer> precursers = new ArrayList<>();
            if(this.containsEntry(value))
            {
                Node entry = this.findEntry(value);
                precursers.add(value);
                if(DEBUG)
                {
                    System.out.printf("Adding %d as a Precurser of itself to prevent looping\n", value);
                }
                if(!entry.isFirst())
                {
                    if(DEBUG)
                    {
                        System.out.printf("%d has the following precursers:", value);
                        for(Node node: entry.getPrecursers())
                            System.out.printf("\n- %d", node.getValue());
                        System.out.printf("\n");
                    }
                    for(Node node : entry.getPrecursers())
                    {
                        precursers = this.getAllPrecursers(node.getValue(), precursers);
                    }
                }
                else
                {
                    if(DEBUG)
                    {
                        System.out.printf("%d has no precursers\n", value);
                    }
                }

                precursers.remove(Integer.valueOf(value));
                if(DEBUG)
                {
                    System.out.printf("Removing %d as a precurser of itself \n", value);
                }
            }


            return precursers;
        }

        private ArrayList<Integer> getAllPrecursers(int value, ArrayList<Integer> foundPrecursers)
        {
            Node entry = this.findEntry(value);
            //ArrayList<Integer> precursers = new ArrayList<>();

            if(foundPrecursers.contains(entry.getValue()))
            {
                System.out.printf("Skipping the node of %d cause it already is a known Precurser\n", value);
            }
            else
            {
                foundPrecursers.add(value);
                if(DEBUG)
                {
                    System.out.printf("\nAdding the node of %d to our list of known Precursers", entry.getValue());
                    System.out.printf("\nFound Precursers:\n");
                    for(int i : foundPrecursers)
                    {
                        System.out.printf("- %s\n",i);
                    }
                }

                if(!entry.isFirst())
                {
                    if(DEBUG)
                    {
                        System.out.printf("\n%d has the following precursers:", value);
                        for(Node node: entry.getPrecursers())
                            System.out.printf("\n- %d", node.getValue());
                        System.out.printf("\n");
                    }

                    for(Node node : entry.getPrecursers())
                    {
                        foundPrecursers = this.getAllPrecursers(node.getValue(), foundPrecursers);
                    }
                }
                else
                {
                    System.out.printf("%d has no precursers\n", value);
                }
            }

            return foundPrecursers;
        }
    }


    public static void run(String filename, int part)
    {
        String input[] = ImportUtility.getLines(Main.RESSOURCEPATH+filename);
        if(input!=null)
        {
            Day5 day = new Day5(input, part);
            day.start();
        }
    }

    private Day5(String[] input, int part)
    {
        this.part = part;
        this.content = input;
    }

    public void start()
    {
        if(part == 1)
        {
            String code = StringUtility.linesToString(content);
            Pattern rulePattern = Pattern.compile("(?<=(^|\\n))(?<Number1>\\d+)\\|(?<Number2>\\d+)(?=(\\n|$))");
            Pattern instructionPattern = Pattern.compile("(?<=(^|\\n))(?<Instruction>(\\d+,?)+)(?=(\\n|$))");
            ArrayList<int[]> rules = new ArrayList<>();
            ArrayList<int[]> instructions = new ArrayList<>();
            Matcher rulesMatcher = rulePattern.matcher(code);
            Matcher instructionMatcher = instructionPattern.matcher(code);

            System.out.printf("Parsing input.\n");
            
            int count = 0;
            while(rulesMatcher.find())
            {
                count++;
                if(DEBUG)
                {
                    System.out.printf("Rule No.%d: Page %s before %s%n", count, rulesMatcher.group("Number1"), rulesMatcher.group("Number2"));
                }
                int[] rule = {Integer.parseInt(rulesMatcher.group("Number1")),Integer.parseInt(rulesMatcher.group("Number2"))};
                rules.add(rule);
            }
            
            count = 0;
            while(instructionMatcher.find())
            {
                count++;
                if(DEBUG)
                {
                    System.out.printf("Instruction No.%d: %s\n", count, instructionMatcher.group("Instruction"));
                }
                String[] instructionString = instructionMatcher.group("Instruction").split(",");
                int[] instruction = new int[instructionString.length];
                for(int j = 0; j < instructionString.length; j++)
                {
                    instruction[j] = Integer.parseInt(instructionString[j]);
                }
                instructions.add(instruction);
            }

            int[][] restrictions = this.parseAllRules(rules);

            System.out.printf("\n\nLists of Restrictions:");
            for(int i = 0; i < restrictions.length; i++)
            {
                if(restrictions[i] != null && restrictions[i].length>0)
                {
                    System.out.printf("\n- Before Page %d, print:", i);
                    for(int j = 0; j < restrictions[i].length; j++)
                    {
                        System.out.printf(" %d,",restrictions[i][j]);
                    }                                
                }
                else
                {
                    System.out.printf("\n- No rules for Page %d",i);
                }
            }

            this.rulesToFile("Page_Restrictions.txt", restrictions);

            ArrayList<Integer> middleNumbers = new ArrayList<>();
            for(int[] instruction : instructions)
            {
                System.out.print("\nChecking the update for Pages: ");
                for(int i = 0; i<instruction.length; i++)
                {
                    System.out.printf((i==instruction.length-1 ? "and ": "") +"%d" + (i>instruction.length-1 ? ", ": ", "),instruction[i] );
                }

                int[] check = checkRestrictions(instruction, restrictions);
                System.out.printf("\n%d,%d",check[0],check[1]);
                if(check[0]<0)
                {
                    System.out.print("\n- The update is valid\n");
                    middleNumbers.add(instruction[((instruction.length-1)/2)]);
                }
                else
                {
                    System.out.printf("\n- The update is invalid because %d needs to be printed before %d\n", check[0], check[1]);
                }
                    
            }

            int middleAdd = 0;

            for(Integer i : middleNumbers)
            {
                middleAdd+=i;
            }

            System.out.printf("%nThe Sum of all valid middle numbers is: %d%n", middleAdd);
        }


        if(part == 2)
        {
            String code = StringUtility.linesToString(content);
            Pattern rulePattern = Pattern.compile("(?<=(^|\\n))(?<Number1>\\d+)\\|(?<Number2>\\d+)(?=(\\n|$))");
            Pattern instructionPattern = Pattern.compile("(?<=(^|\\n))(?<Instruction>(\\d+,?)+)(?=(\\n|$))");
            ArrayList<int[]> rules = new ArrayList<>();
            ArrayList<int[]> instructions = new ArrayList<>();
            Matcher rulesMatcher = rulePattern.matcher(code);
            Matcher instructionMatcher = instructionPattern.matcher(code);

            System.out.printf("Parsing input.\n");
            
            int count = 0;
            while(rulesMatcher.find())
            {
                count++;
                if(DEBUG)
                {
                    System.out.printf("Rule No.%d: Page %s before %s%n", count, rulesMatcher.group("Number1"), rulesMatcher.group("Number2"));
                }
                int[] rule = {Integer.parseInt(rulesMatcher.group("Number1")),Integer.parseInt(rulesMatcher.group("Number2"))};
                rules.add(rule);
            }
            
            count = 0;
            while(instructionMatcher.find())
            {
                count++;
                if(DEBUG)
                {
                    System.out.printf("Instruction No.%d: %s\n", count, instructionMatcher.group("Instruction"));
                }
                String[] instructionString = instructionMatcher.group("Instruction").split(",");
                int[] instruction = new int[instructionString.length];
                for(int j = 0; j < instructionString.length; j++)
                {
                    instruction[j] = Integer.parseInt(instructionString[j]);
                }
                instructions.add(instruction);
            }

            int[][] restrictions = this.parseAllRules(rules);

            System.out.printf("\n\nLists of Restrictions:");
            for(int i = 0; i < restrictions.length; i++)
            {
                if(restrictions[i] != null && restrictions[i].length>0)
                {
                    System.out.printf("\n- Before Page %d, print:", i);
                    for(int j = 0; j < restrictions[i].length; j++)
                    {
                        System.out.printf(" %d,",restrictions[i][j]);
                    }                                
                }
                else
                {
                    System.out.printf("\n- No rules for Page %d",i);
                }
            }

            ArrayList<int[]> wrongUpdates = new ArrayList<>();

            for(int[] instruction : instructions)
            {
                System.out.print("\nChecking the update for Pages: ");
                for(int i = 0; i<instruction.length; i++)
                {
                    System.out.printf((i==instruction.length-1 ? "and ": "") +"%d" + (i>instruction.length-1 ? ", ": ", "),instruction[i] );
                }

                if(checkRestrictions(instruction, restrictions)[0]<0)
                    System.out.print("\n- The update is valid\n");
                else
                {
                    System.out.print("\n- The update is valid\n");
                    wrongUpdates.add(instruction);


                    int[] update = orderUpdate(instruction, restrictions);

                    if(update == null)
                    {
                        System.out.print("\n- No update possible\n");
                    }
                    else
                    {
                        System.out.print("\n- A valid update is: \n");
                        for(int i = 0; i<update.length; i++)
                        {
                            System.out.printf((i==update.length-1 ? "and ": "") +"%d" + (i>update.length-1 ? ", ": ", "),update[i] );
                        }
                    }
                }
                    
            }


            int wrongNumb = 0;
            for(int[] instruction : wrongUpdates)
            {
                System.out.print("\nTrying to find a solution for Pages: ");
                for(int i = 0; i<instruction.length; i++)
                {
                    System.out.printf((i==instruction.length-1 ? "and ": "") +"%d" + (i>instruction.length-1 ? ", ": ", "),instruction[i] );
                }

                int[] update = orderUpdate(instruction, restrictions);

                if(update == null)
                {
                    System.out.print("\n- No update possible\n");
                }
                else
                {
                    wrongNumb+=instruction[((instruction.length-1)/2)];
                    System.out.print("\n- A valid update is: \n");
                    for(int i = 0; i<update.length; i++)
                    {
                        System.out.printf((i==update.length-1 ? "and ": "") +"%d" + (i>update.length-1 ? ", ": ", "),update[i] );
                    }
                }
            }

            System.out.printf("%nAdding the middle numbers of the fixed updates together we get %d%n", wrongNumb);

        }
    }

    private int[] orderUpdate(int[] instruction, int[][] restrictions)
    {
        Integer[] integer = new Integer[instruction.length];
        int[] out = null;
        
        for (int i = 0; i < instruction.length; i++)
        {
            integer[i] = instruction[i];
        }
        List<Integer[]> possibilities = OtherUtility.allCombinationsNoRecursion(integer);
        
        for(int[] line : OtherUtility.integerToInt(possibilities))
        {
            if(checkRestrictions(line, restrictions)[0]<0)
            {
                out = line;
            }
        }

        return out;
    }


    //returns the problematic pair
    private static int[] checkRestrictions(int[] instruction, int[][] restrictions)
    {
        int[] out = {-1,-1};
        if(DEBUG)
        {
            System.out.print("\nChecking restrictiobns for Update:");
            for(int i = 0; i<instruction.length; i++)
            {
                System.out.printf((i==instruction.length ? "and ": "") +"%d" + (i>instruction.length-1 ? ", ": ", "),instruction[i] );
            }
            System.out.print("\n");
        }


        for (int i = 0; i < instruction.length; i++)
        {
            for (int j = instruction.length-1; j > i; j--)
            {
                if(restrictions[instruction[i]] != null)
                {
                    for(int k : restrictions[instruction[i]])
                    {
                        if (k == instruction[j])
                        {
                            out[0] = instruction[j];
                            out[1] = instruction[i];

                            if(DEBUG)
                            {
                                System.out.printf("Update wrong, because Page %d needs to be printed before Page %d.\n", instruction[j], instruction[i]);
                            }
                        }
                        else
                        {
                            if(DEBUG)
                            {
                                System.out.printf("Ain't no rule saying Page %d needs to be printed before Page %d.\n", instruction[j], instruction[i]);
                            }
                        }
                    }
                }
                else
                    System.out.printf("There are no restrictions placed pm %d%n", i);
            }
        }

        return out;
    }

    private int[][] parseAllRules(ArrayList<int[]> rules)
    {
        int size = 0;
        for(int i = 0; i<rules.size(); i++)
        {
            if(rules.get(i)[0]>size)
                size = rules.get(i)[0];
            if(rules.get(i)[1]>size)
                size = rules.get(i)[1];
        }

        int[][] restrictions = new int[size+1][];
        Ordering order = new Ordering();


        for(int[] rule : rules)
        {
            order.newRule(rule[0], rule[1]);
            if(DEBUG)
                System.out.printf("Adding the rule that Page %d must come before Page %d.%n", rule[0],rule[1]);
        }

        for(int i = 1; i <= size; i++)
        {
            ArrayList<Integer> precursers = order.getPrecursers(i);
            if(DEBUG)
            {
                System.out.printf("Need to print:");
                for(int page : precursers)
                {
                    System.out.printf("\n- Page %d", page);
                }
                if(precursers.isEmpty())
                    System.out.printf("\n- Nothing");
                System.out.printf("\nBefore page %d.\n", i);
            }

            int[] restriction = new int[precursers.size()];
            for(int j = 0; j< restriction.length; j++)
            {
                restriction[j] = precursers.get(j);
            }

            restrictions[i] = restriction;
        }

        for(int[] line : restrictions)
        {
            if (line != null)
                Arrays.sort(line);
        }

        return restrictions;
    }

    private void rulesToFile(String filename, int[][] restrictions)
    {
        try
        {
            File file = new File(Main.RESSOURCEPATH+filename);
            if(file.createNewFile())
                System.out.printf("Created new file \"%s\"\n", filename);
        }
        catch(IOException e)
        {
            System.out.printf("An Error occured while trying to safe the file");
        }

        try
        {
            FileWriter writer = new FileWriter(Main.RESSOURCEPATH+filename);
        
            writer.write("\n\nLists of Restrictions:");
            for(int i = 0; i < restrictions.length; i++)
            {
                if(restrictions[i] != null && restrictions[i].length>0)
                {
                    writer.write("\n- Before Page" + i + ", print: ");
                    for(int j = 0; j < restrictions[i].length; j++)
                    {
                        writer.write(restrictions[i][j]+", ");
                    }                                
                }
                else
                {
                    writer.write("\n- No rules for Page " + i);
                }
            }

            writer.close();
        }
        catch(IOException e)
        {
            System.out.printf("An Error occured while trying to safe the file");
        }
    }
}