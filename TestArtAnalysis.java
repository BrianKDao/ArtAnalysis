package Projects.Project07;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestArtAnalysis {

    private static final double EQ_DELTA = .01;
    private final String newLine = System.lineSeparator();
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private ByteArrayOutputStream outStream;

    private void writeLinesToFile(String[] lines, File f) {
        try {
            PrintWriter pw = new PrintWriter(f);
            for (String line : lines) {
                pw.write(String.format("%s%s", line, newLine));
            }
            pw.close();
        } catch (FileNotFoundException e) {
            fail("Something weird happened, please reach out to a TA.");
        }
    }

    private void assertContains(String message, String expected, String actual) {
        assertTrue(
                String.format("%s: \n Expected: %s\n Actual: %s", message, expected, actual),
                actual.contains(expected)
        );
    }

    private String normalizeNewlines(String actual) {
        return actual.replace("\r\n", "\n").replace("\r", "\n");
    }

    @Before
    public void setUpStreams() {
        outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    // assert readFile(File) throws FileNotFoundException for no such file
    @Test(expected = FileNotFoundException.class)
    public void testReadFile_throwsFileNotFound_ifInvalidFile() throws FileNotFoundException {
        File f = new File("ThisIsInvalidFilePath");

        ArtAnalysis.readFile(f);
        fail("We expected readFile() to throw FileNotFoundException if passed an invalid " +
                "file!");
    }


    // assert readFile(File) returns list of lines in file
    @Test
    public void testReadFile_returnsListOfLinesInFile() throws IOException {
        String[] lines = {"This is line 1", "This is line 2"};
        File f = tempFolder.newFile();
        writeLinesToFile(lines, f);

        List<String> actual = ArtAnalysis.readFile(f);
        assertEquals(
                "When we write 2 lines to the file, we expect the number of lines returned: ",
                2,
                actual.size()
        );
        assertEquals(
                "When reading from the file we expect the first line to be ",
                lines[0],
                actual.get(0)
        );
        assertEquals(
                "When reading from the file we expect the second line to be ",
                lines[1],
                actual.get(1)
        );
    }


    // assert parseData(ArrayList<String>) replaces non-int & negative art value with 0
    @Test
    public void testParseData_replacesNonNumericArtValueWith0() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Starry Night\tVan Gogh\tOne Hundred Million\tNYC Modern Art Museum");
        ArrayList<Art> arts = ArtAnalysis.parseData(lines);

        assertEquals(
                "When we parse an art value of 'One Hundred Million' we expect ",
                0,
                arts.get(0).getValue(),
                EQ_DELTA
        );

    }

    @Test
    public void testParseData_replacesNegativeArtValueWith0() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Cloudy Night\tVan Nogh\t-318.98\tMy Living Room");
        ArrayList<Art> arts = ArtAnalysis.parseData(lines);
        assertEquals(
                "When we parse a art value of '-318.98' we expect ",
                0,
                arts.get(0).getValue(),
                .001
        );
    }


    // assert calcValueAvg(ArrayList<Art>) calculates average value for all arts
    @Test
    public void testCalcValueAvg_calculatesAvgValueForArts() {
        ArrayList<Art> arts = new ArrayList<>();
        arts.add(new Art());
        arts.add(new Art());
        arts.add(new Art());
        arts.get(0).setValue(103302.30);
        arts.get(1).setValue(2048672.50);
        arts.get(2).setValue(2094254.35);

        double actual = ArtAnalysis.calcValueAvg(arts);
        assertEquals("When we calculate the averageValue we expect ", 1415409.7166, actual, .01);
    }

    // assert calcPopulationAboveAverage(ArrayList<Art>, double) returns list of arts w/
    // population above average
    @Test
    public void testCalcFindHighestByValue_returnsArtsWithValueAboveAvg() {
        ArrayList<Art> arts = new ArrayList<>();
        Art art1 = new Art("ArtName_75", "Artist1", 75, "Location1");
        Art art2 = new Art("ArtName_100", "Artist2", 100, "Location2");
        Art art3 = new Art("ArtName_150", "Artist3", 150, "Location3");
        Art art4 = new Art("ArtName_175", "Artist4", 175, "Location4");
        Art art5 = new Art("ArtName_125", "Artist5", 125, "Location5");
        arts.add(art1);
        arts.add(art2);
        arts.add(art3);
        arts.add(art4);
        arts.add(art5);
        ArrayList<Art> actual = ArtAnalysis.findHighestArtByValue(arts, 125);
        assertEquals(
                "When we call calcPopulationAboveAverage with 2 arts above average we " + "expect"
                        + " only 2 arts returned but we got",
                2,
                actual.size()
        );
        assertTrue(String.format(
                "When we call calcPopulationAboveAverage we expect %s to be in " + "the returned "
                        + "list %s",
                "ArtName_150",
                actual.toString()
        ), actual.contains(art3));
        assertTrue(String.format(
                "When we call calcPopulationAboveAverage we expect %s to be in " + "the returned "
                        + "list %s",
                "ArtName_175",
                actual.toString()
        ), actual.contains(art4));
        assertFalse(String.format(
                "When we call calcPopulationAboveAverage we expect only arts " + "that are above "
                        + "average, not arts that are exactly average. %s is not " + "expected " + "to" + " " + "be returned in the list %s",
                "ArtName_125",
                actual.toString()
        ), actual.contains(art5));

    }

    // assert findLargestDistance(ArrayList<Art>) returns the name of the art with the largest
    // distance
    @Test
    public void testFindHighValue_returnsValueOfMostExpensiveArt() {
        ArrayList<Art> arts = new ArrayList<>();
        Art art1 = new Art("ArtName_75", "Artist1", 75, "Location1");
        Art art2 = new Art("ArtName_100", "Artist2", 100, "Location2");
        Art art3 = new Art("ArtName_150", "Artist3", 150, "Location3");
        Art art4 = new Art("ArtName_175", "Artist4", 175, "Location4");
        Art art5 = new Art("ArtName_125", "Artist5", 125, "Location5");
        arts.add(art1);
        arts.add(art2);
        arts.add(art3);
        arts.add(art4);
        arts.add(art5);

        double actual = ArtAnalysis.findHighValue(arts);
        double expected = 175;
        assertEquals(
                String.format("When we call findLargestDistance with %s we expect ", arts),
                expected,
                actual,
                EQ_DELTA
        );
    }

    // assert findArt(ArrayList<Art>,  Art) returns true if art is in list
    @Test
    public void testFindArt_returnsTrue_ifArtInList() {
        ArrayList<Art> arts = new ArrayList<>();
        Art art1 = new Art("ArtName_75", "Artist1", 75, "Location1");
        Art art2 = new Art("ArtName_100", "Artist2", 100, "Location2");
        Art art3 = new Art("ArtName_150", "Artist3", 150, "Location3");
        Art art4 = new Art("ArtName_175", "Artist4", 175, "Location4");
        Art art5 = new Art("ArtName_125", "Artist5", 125, "Location5");
        arts.add(art1);
        arts.add(art2);
        arts.add(art3);
        arts.add(art4);
        arts.add(art5);

        Art inList = new Art("ArtName_75", "Artist1", 75000, "Unknown");
        Art notInList = new Art("ArtName_0", "Artist1", 75, "Location1");
        Art notInList2 = new Art("ArtName_75", "Artist0", 75, "Location1");

        assertTrue(String.format(
                "When we pass an art into the findArt() method, we expect it to return " + "true "
                        + "if an art with that name is in the arts. We passed in [%s] and " +
                        "searched it for %s but got false!",
                arts.toString(),
                inList.toString()
        ), ArtAnalysis.findArt(arts, inList));

        assertFalse(String.format(
                "When we pass an art into the findArt() method, we expect it to return " + "false"
                        + " if an art with that name is not in the arts. We passed in [%s] and" + " " + "searched it for %s but got false!",
                arts.toString(),
                notInList.toString()
        ), ArtAnalysis.findArt(arts, notInList));

        assertFalse(String.format(
                "When we pass an art into the findArt() method, we expect it to return " + "false"
                        + " if an art with that name is not in the arts. We passed in [%s] and" + " " + "searched it for %s but got false!",
                arts.toString(),
                notInList2.toString()
        ), ArtAnalysis.findArt(arts, notInList2));
    }


    // assert writeOutData (String, ArrayList<String>, PrintWriter) correctly formatas and prints
    @Test
    public void testWriteOutData_withArtList_printsData() {
        OutputStream out = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(out);
        ArrayList<Art> values = new ArrayList<>();
        values.add(new Art("A Cup of Java", "Budwell", 250, "Richmond"));
        values.add(new Art("A Mug of Cocoa", "Whitten", 2500, "VCU"));
        try {
            ArtAnalysis.writeOutData("The art above the average value are: ", values, pw);
        } catch (FileNotFoundException ex) {
            fail("Something weird happened. Please contact a TA.");
        } finally {
            pw.close();
        }
        String actual = normalizeNewlines(out.toString());
        String expected = "The art above the average value are: A Cup of Java Budwell 250.00 " +
                "Richmond A Mug of Cocoa Whitten 2500.00 VCU\n\n";

        assertEquals(String.format(
                "When we call writeOutData with a list of art we expect %s but get %s",
                expected,
                actual
        ), expected.replace("\\s+", " ").trim(), actual.trim());

    }


    // assert writeOutData (String, double, PrintWriter) correctly formats and prints
    @Test
    public void testWriteOutData_withDouble_printsData() {
        OutputStream out = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(out);

        try {
            ArtAnalysis.writeOutData("The average value is: ", 129039.39, pw);
        } catch (FileNotFoundException ex) {
            fail("Something weird happened. Please contact a TA.");
        } finally {
            pw.close();
        }
        String actual = normalizeNewlines(out.toString());
        String expected = "The average value is: 129039.39\n\n";
        assertEquals(
                "When we call writeOutData with a double we expect ",
                expected.replace("\\s+", " ").trim(),
                actual.trim()
        );
        assertEquals(
                "Check your whitespace, your text looks correct but your spaces/newlines don't " + "match expected... ",
                expected,
                actual
        );
    }

    // assert writeOutData (String, String, PrintWriter) correctly formats and prints
    @Test
    public void testWriteOutData_withStringMessage_printsData() {
        OutputStream out = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(out);

        try {
            ArtAnalysis.writeOutData("The average value is: ", 12500.30, pw);
        } catch (FileNotFoundException ex) {
            fail("Something weird happened. Please contact a TA.");
        } finally {
            pw.close();
        }
        String actual = normalizeNewlines(out.toString());
        actual = normalizeNewlines(actual);
        String expected = "The average value is: 12500.30\n\n";
        assertEquals(
                "When we call writeOutData with a double we expect ",
                expected.replace("\\s+", " ").trim(),
                actual.trim()
        );
        assertEquals(
                "Check your whitespace, your text looks correct but your spaces/newlines " + "don"
                        + "'t match expected... ",
                expected,
                actual
        );
    }


    // assert writeOutData (String, boolean, PrintWriter) correctly formats and prints
    @Test
    public void testWriteOutData_withBooleanMessage_printsData() {
        OutputStream out = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(out);

        try {
            ArtAnalysis.writeOutData("Is Starry Night by Van Gogh in the data?: ", false, pw);
        } catch (FileNotFoundException ex) {
            fail("Something weird happened. Please contact a TA.");
        } finally {
            pw.close();
        }
        String actual = normalizeNewlines(out.toString());
        String expected = "Is Starry Night by Van Gogh in the data?: false\n\n";
        assertEquals(
                "When we call writeOutData with a string we expect ",
                expected.replace("\\s+", " ").trim(),
                actual.trim()
        );
        assertEquals(
                "Check your whitespace, your text looks correct but your spaces/newlines " + "don"
                        + "'t match expected... ",
                expected,
                actual
        );
    }
}
