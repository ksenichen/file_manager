import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        while (true) {
            try {
                System.out.println("0. Quit");
                System.out.println("1. Create file");
                System.out.println("2. Delete file");
                System.out.println("3. Rename file");
                System.out.println("4. Copy file");
                System.out.println("5. Search word in file");
                System.out.println("6. Replace word in file");
                System.out.println("Make your choice:");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        FileOperations.create();
                        break;
                    case 2:
                        FileOperations.delete();
                        break;
                    case 3:
                        FileOperations.rename();
                        break;
                    case 4:
                        FileOperations.copy();
                        break;
                    case 5:
                        FileOperations.searchWord();
                        break;
                    case 6:
                        FileOperations.replaceWord();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Enter menu item from 0 to 6");
                }

            } catch (NumberFormatException nfe) {
                System.out.println("Enter menu item from 0 to 6");
            } catch (IOException ioe) {
                System.out.println("Error " + ioe.getMessage());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
