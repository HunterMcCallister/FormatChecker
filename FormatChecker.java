
import java.io.File;
import java.util.Scanner;

public class FormatChecker {

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
