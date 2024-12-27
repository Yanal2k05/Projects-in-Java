import java.util.Scanner;
/**
 * The Main is the start of the portfolio.
 * Creates a Portfolio and starts the command loop.
 */
public class Main { 
    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio();
        portfolio.commandLoop(); // Start the command loop for user interaction
    }
}
