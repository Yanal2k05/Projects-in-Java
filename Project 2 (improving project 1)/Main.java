/**
 * The {@code Main} class serves as the entry point for the investment portfolio application.
 * It initializes a {@code Portfolio} object, loads investments from a specified file,
 * processes commands in a loop, and saves updated investments back to the file.
 * 
 * <p>Usage:</p>
 * <pre>
 * java Main &lt;filename&gt;
 * </pre>
 * <p>The filename is required as a command-line argument to load and save investment data.</p>
 * 
 * @since 1.0
 */
package ePortfolio;

public class Main {
    /**
     * The main method of the program. It checks if a filename is provided as a command-line argument,
     * initializes a {@code Portfolio} object, loads investments from the file, processes user commands,
     * and saves the investments back to the file upon exiting.
     *
     * @param args the command-line arguments, where the first argument is the filename for investment data
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <filename>");
            return;
        }

        String filename = args[0];
        Portfolio portfolio = new Portfolio();
        portfolio.loadInvestments(filename);
        portfolio.commandLoop();
        portfolio.saveInvestments(filename);
    }
}
