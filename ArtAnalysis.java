/****************************************************************************
 * ArtAnalysis
 ****************************************************************************
 * Analyzes art
 *_____________________________________________________
 * Brian Dao
 * 5/7/2021
 * CMSC-255-003-SP2021
 ****************************************************************************/

package Projects.Project07;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ArtAnalysis
{
    public static void main(String[] args)
    {
        /**
         * Try catch block to catch a FileNotFoundException
         */
        try{
            /**
             * Setting up the input and output files
             */

            File input = new File(args[0]);
            File output = new File(args[1]);
            ArrayList<Art> theGoodStuff = parseData(readFile(input));

            PrintWriter out = new PrintWriter(output);

            /**
             * Writing to the output file and closing the PrintWriter
             */
            writeOutData("The average value is: ", calcValueAvg(theGoodStuff), out);
            writeOutData("The highest value is: ", findHighValue(theGoodStuff), out);
            writeOutData("The art above the average value are:", findHighestArtByValue(theGoodStuff, calcValueAvg(theGoodStuff)), out);
            writeOutData("Is David by Donatello in the data? ", findArt(theGoodStuff, new Art("David", "Donatello", 0.0, null)), out);
            out.close();
        } catch(FileNotFoundException ex){
            System.out.println("Incorrect input filename");
            System.out.println("Incorrect output filename");
        }

    }

    public static ArrayList<String> readFile(File inputFile) throws FileNotFoundException {
        /**
         * Setting up the scanner and making an ArrayList
         */

        Scanner input = new Scanner(inputFile);
        ArrayList<String> artWorks = new ArrayList<String>();

        /**
         * Reads the input file and adds each line to a string, and adds that string to an ArrayList
         */
        while(input.hasNextLine())
        {
            String line = input.nextLine();
            artWorks.add(line);
        }
        input.close();
        return artWorks;
    }

    public static ArrayList<Art> parseData(ArrayList<String> lines)
    {
        /**
         * Setting up ArrayList
         */

        ArrayList<Art> arts = new ArrayList<Art>();

        /**
         * Using for-loop to run though the String ArrayList and adding them to an ArrayList with Art Objects
         */
        for(String line : lines)
        {
            String[] tokens = line.split("\t");
            double value;

            /**
             * Catches a NumberFormatException and makes it a default of 0.0
             */
            try{
                if(Double.parseDouble(tokens[3]) >= 0.0)
                {
                    value = Double.parseDouble(tokens[3]);
                }
                else
                {
                    value = 0.0;
                }
            }catch (NumberFormatException e){
                value = 0.0;
            }

            /**
             * Adds the tokens to the ArrayList
             */
            Art art = new Art(tokens[0], tokens[1], value, tokens[3]);
            arts.add(art);
        }
        return arts;
    }

    public static double calcValueAvg(ArrayList<Art> artworks)
    {
        /**
         * Initializes the variables and uses a for-loop to add all the art's values and increment count. Then returns value / count
         */
        double value = 0;
        int count = 0;
        double average;
        for(Art art : artworks)
        {
            value += art.getValue();
            count++;
        }
        average = value / count;

        return average;
    }

    public static double findHighValue(ArrayList<Art> artworks)
    {
        /**
         * Scans through the ArrayList using for-loop, and replaces highest everytime it finds a value higher than it
         */
        double highest = 0;
        for(Art art : artworks)
        {
            if(art.getValue() > highest)
            {
                highest = art.getValue();
            }
        }

        return highest;
    }

    public static ArrayList<Art> findHighestArtByValue(ArrayList<Art> artworks, double avg)
    {
        /**
         * Creates an ArrayList and uses a for-loop to add Art Objects whose value is higher than the average given
         */

        ArrayList<Art> aboveAverage = new ArrayList<Art>();

        for(Art art : artworks)
        {
            if (art.getValue() > avg)
            {
                aboveAverage.add(art);
            }
        }

        return aboveAverage;
    }

    public static boolean findArt(ArrayList<Art> artworks, Art art)
    {
        /**
         * Scans through the ArrayList and finds if the given Art is in the ArrayList
         */

        int count = 0;
        for(Art arts : artworks)
        {
            if(arts.equals(art))
            {
                count++;
            }
        }

        if(count != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void writeOutData (String outputMessage, ArrayList<Art> artworks, PrintWriter out) throws FileNotFoundException
    {
        /**
         * Scans through ArrayList and prints out the Art's
         */

        out.print(outputMessage);
        for(Art art : artworks)
        {
            out.print(art + " ");
        }
        out.println();
        out.println();
    }

    public static void writeOutData (String outputMessage, double value, PrintWriter out) throws FileNotFoundException
    {
        /**
         * Prints the outputMessage and the formatted value
         */

        out.print(outputMessage);
        out.printf("%.2f", value);
        out.println();
        out.println();
    }

    public static void writeOutData (String outputMessage, boolean value, PrintWriter out) throws FileNotFoundException
    {
        /**
         * Prints out the output message and a boolean
         */

        out.println(outputMessage + value);
        out.println();
    }

}
