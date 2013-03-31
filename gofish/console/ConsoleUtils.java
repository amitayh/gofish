package gofish.console;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUtils {
    
    public static int nextInt(Scanner input, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException();
        }
        
        boolean isValid = false;
        int result = 0;
        do {            
            try {
                result = checkBounds(input.nextInt(), min, max);
                input.nextLine();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input (must be a number), try again: ");
                input.nextLine();
            } catch (IndexOutOfBoundsException e) {
                System.out.print("Invalid number (must be between " + min +
                                 " and " + max + "), try again: ");
            }
        } while (!isValid);
        
        return result;
    }
    
    public static boolean nextBoolean(Scanner input) {
        String line = input.nextLine();
        while (!line.equals("y") && !line.equals("n")) {
            System.out.print("Invalid input, try again: ");
            line = input.nextLine();
        }
        return line.equals("y");
    }
    
    public static int checkBounds(int index, int min, int max) {
        if (index < min || index > max) {
            throw new IndexOutOfBoundsException();
        }
        return index;
    }

}
