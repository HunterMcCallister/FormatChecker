
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
        boolean isValid = true;

        if (fileScan.hasNextLine()) {
            String firstLine = fileScan.nextLine();
            int[] dimensions = readDimensions(firstLine);
            expectedRows = dimensions[0];
            expectedCols = dimensions[1];
        } else {
            throw new Exception("File is empty");
        }

    }

    private static int[] readDimensions(String firstLine) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
