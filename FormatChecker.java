
import java.io.File;
import java.util.Scanner;
/**
 * The FormatChecker Class validates the format of one or more files.
 * Each file is checked for the specific formatting and the program will state
 * whether the file is valid or invalid based on predetermined format of matching the number of rows and columns provided 
 * in the first two lines of the file.
 * 
 * @author Hunter McCallister
 */
public class FormatChecker {

    /**
     * This is the main method that reads the command-line arguments and processes each file.
     * It prints whether the file is VALID or INVALID based on its format.
     * If no arguments are given it will print hte usage message for direction.
     * @param args the name of the files to be checked 
     */
    public static void main(String[] args) {

        String usageMsg = "Usage: $ java FormatChecker file1 [file2 ... fileN]";

        if (args.length == 0) {
            System.out.println(usageMsg);
            return;
        }

        for (String filename : args) {
            System.out.println(filename);

            try {
                openFile(filename);
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("INVALID");
            }
            System.out.println();
        }

    }

    /**
     * This method opens the file and checks if the file format is valid.
     * The file must begin with two integers representing the expected number of rows and columns that are in the following lines
     * Followed by rows of double values seperated by white space.
     * @param filename the name of the file to be checked
     * @throws Exception If the file format is invalid or if the file can't be found.
     */
    public static void openFile(String filename) throws Exception {
        Scanner fileScan = new Scanner(new File(filename));
        int expectedRows = 0;
        int expectedCols = 0;
        int actualRows = 0;
    
        if (fileScan.hasNextLine()) {
            String firstLine = fileScan.nextLine();
            int[] dimensions = readDimensions(firstLine);
            expectedRows = dimensions[0];
            expectedCols = dimensions[1];
        } else {
            fileScan.close();
            throw new Exception("File is empty");
        }
        //trouble shoot this part causing valid3 to fail
        while (fileScan.hasNextLine()) {
            String dataLine = fileScan.nextLine().trim();
            if (dataLine.isEmpty()) {
                continue;
            }
            actualRows++;
            Scanner lineScan = new Scanner(dataLine);
            int colCount = 0;
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                try {
                    Double.parseDouble(token);
                    colCount++;
                } catch (NumberFormatException e) {
                    lineScan.close();
                    fileScan.close();
                    throw new Exception("Invalid number format");
                }
            }
            if (colCount != expectedCols) {
                lineScan.close();
                fileScan.close();
                throw new Exception("Column count does not match expected");
            }
            lineScan.close();
        }
        if (actualRows != expectedRows) {
            fileScan.close();
            throw new Exception("Rows do not match expected");
        }
        fileScan.close();
        System.out.println("VALID");
    }
   
    /**
     * This method is set up to read the first line of the file and get the number of rows and columns from it.
     * The first line must contain EXACTLY two positive integers and no extra chars
     * @param firstLine the first line of the file containing the dimensions
     * @return an array list with [0] containing rows and [1] columns
     * @throws Exception if the first line does not contain exactly two valid integers
     */
    private static int[] readDimensions(String firstLine) throws Exception {
        Scanner lineScan = new Scanner(firstLine);

        int expectedRows = 0;
        int expectedCols = 0;

        if (lineScan.hasNextInt()) {
            expectedRows = lineScan.nextInt();
        } else {
            lineScan.close();
            throw new Exception("First line must contain exactly two integers.");
        }
        if (lineScan.hasNextInt()) {
            expectedCols = lineScan.nextInt();
        } else {
            lineScan.close();
            throw new Exception("First line must contain exactly two integers.");
        }
        if (lineScan.hasNext()) {
            lineScan.close();
            throw new Exception("First line must contain exactly two integers.");
        }
        if (expectedRows <= 0 || expectedCols <= 0) {
            lineScan.close();
            throw new Exception("Row and column counts must be positive integers. Found: rows = " + expectedRows + ", columns = " + expectedCols);
        }
        lineScan.close();
        int[] dimensions = {expectedRows, expectedCols};

        return dimensions;

    }
}
