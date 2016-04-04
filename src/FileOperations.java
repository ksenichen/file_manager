import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileOperations {

    public static final String SEARCH = "search";
    public static final String REPLACE = "replace";
    public static final Scanner SCANNER = new Scanner(System.in);
    public static final String TEMP = "temp";

    enum FilePurpose {
        SOURCE("source"),
        DESTINATION("destination"),
        DEFAULT("target");

        private String value;

        FilePurpose(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static String readFileName(FilePurpose filePurpose){
        System.out.println("Input " + filePurpose.getValue() + " file name: ");
        return readNextLine();
    }

    private static String readWord(String purpose){
        System.out.println("Input word to " + purpose + " in a file: ");
        return readNextLine();
    }

    private static String readNextLine() {
        return SCANNER.nextLine();
    }

    public static void create() throws IOException {
        File file = new File(readFileName(FilePurpose.DEFAULT));
        if (file.exists()){
            System.out.println("File already exists");
        } else {
            file.createNewFile();
        }
    }

    public static void delete(){
        String name = readFileName(FilePurpose.DEFAULT);
        File file = new File(name);
        if (file.exists()){
            file.delete();
        } else {
            System.out.println("No file " + name);
        }
    }

    public static void rename(){
        String srcName = readFileName(FilePurpose.SOURCE);
        String destName = readFileName(FilePurpose.DESTINATION);
        File srcFile = new File(srcName);
        File destFile = new File(destName);
        if (!srcFile.exists()) {
            System.out.println("No file " + srcName);
        } else if (destFile.exists()){
            System.out.println("File " + destName + " already exists");
        } else {
            srcFile.renameTo(destFile);
        }
    }

    public static void copy() throws IOException {
        String srcName = readFileName(FilePurpose.SOURCE);
        String destName = readFileName(FilePurpose.DESTINATION);
        File srcFile = new File(srcName);
        File destFile = new File(destName);
        Path src = srcFile.toPath();
        Path dest = destFile.toPath();
        if (srcFile.exists()) {
            Files.copy(src, dest, REPLACE_EXISTING);
        } else {
            System.out.println("No file " + srcName);
        }
    }

    public static void searchWord() throws FileNotFoundException {
        String name = readFileName(FilePurpose.DEFAULT);
        File file = new File(name);
        if (file.exists()) {
            String word = readWord(SEARCH);
            Scanner sc = new Scanner(file);
            int lineCount = 0;
            boolean found = false;
            while (sc.hasNext()) {
                lineCount ++;
                String buf = sc.nextLine();
                String tempStr = buf.toLowerCase();
                if (tempStr.contains(word.toLowerCase())) {
                    System.out.println("The word '" + word + "' was found in line " + lineCount);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("The word '" + word + "' wasn't found");
            }
        } else {
            System.out.println("No file " + name);
        }
    }

    public static void replaceWord() throws IOException {
        String name = readFileName(FilePurpose.DEFAULT);
        File file = new File(name);
        File temp = new File(TEMP);
        if (file.exists()) {
            String word = readWord(SEARCH);
            String newWord = readWord(REPLACE);
            Scanner sc = new Scanner(file);
            PrintWriter printWriter = new PrintWriter(temp);
            String buf;
            String result;
            int lineCount = 0;
            while (sc.hasNext()) {
                lineCount++;
                buf = sc.nextLine();
                String tempStr = buf.toLowerCase();
                if (tempStr.contains(word.toLowerCase())){
                    result = buf.replace(word, newWord);
                    System.out.println("Word was replaced in line " + lineCount + " : " + result);
                    printWriter.println(result);
                }
                else {
                    printWriter.println(buf);
                }
            }
            printWriter.close();
            sc.close();
            Files.copy(temp.toPath(), file.toPath(), REPLACE_EXISTING);
            temp.deleteOnExit();
        } else {
            System.out.println("No file " + name);
        }
    }
}
