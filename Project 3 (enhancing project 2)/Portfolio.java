package ePortfolio;

import java.io.*;
import java.util.*;

/**
 * The {@code Portfolio} class manages a collection of {@code Investment} objects, including
 * operations to load, save, buy, sell, update, search, and calculate the gain of investments.
 * It also maintains a keyword index for efficient search functionality based on investment names.
 * 
 * @since 1.0
 */
public class Portfolio {
    private ArrayList<Investment> investments;
    private HashMap<String, ArrayList<Integer>> keywordIndex;

    /**
     * Constructs an empty {@code Portfolio} and initializes the investment list and keyword index.
     */
    public Portfolio() {
        investments = new ArrayList<>();
        keywordIndex = new HashMap<>();
    }

    /**
     * Loads investments from the specified file. If the file doesn't exist or has a read error,
     * an error message is displayed. Investments are added to the list and the keyword index is updated.
     *
     * @param filename the file to load investments from
     */
    public void loadInvestments(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("type = ")) continue;

                String type = line.split(" = ")[1].trim();
                String symbol = reader.readLine().split(" = ")[1].trim();
                String name = reader.readLine().split(" = ")[1].trim();
                int quantity = Integer.parseInt(reader.readLine().split(" = ")[1].trim());
                double price = Double.parseDouble(reader.readLine().split(" = ")[1].trim());

                Investment investment = type.equalsIgnoreCase("stock") ?
                                        new Stock(symbol, name, quantity, price) :
                                        new MutualFund(symbol, name, quantity, price);

                investments.add(investment);

                reader.readLine();
            }
            updateKeywordIndex();
        } catch (IOException e) {
            System.out.println("No previous file found or unable to load data.");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error reading data format from file.");
        }
    }

    /**
     * Saves the investments in the portfolio to the specified file.
     *
     * @param filename the file to save investments to
     */
    public void saveInvestments(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Investment investment : investments) {
                String type;
                if (investment instanceof Stock) {
                    type = "stock";
                } else if (investment instanceof MutualFund) {
                    type = "mutualfund";
                } else {
                    System.out.println("Unknown investment type detected.");
                    continue;
                }
                writer.write("type = " + type + "\n" +
                             "symbol = " + investment.getSymbol() + "\n" +
                             "name = " + investment.getName() + "\n" +
                             "quantity = " + investment.getQuantity() + "\n" +
                             "price = " + investment.getPrice() + "\n" +
                             "book value = " + investment.getBookValue() + "\n");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data to file.");
        }
    }

    /**
     * Updates the keyword index based on the current list of investments, allowing for efficient
     * search by keywords in the name of each investment.
     */
    private void updateKeywordIndex() {
        keywordIndex.clear();
        for (int i = 0; i < investments.size(); i++) {
            String[] keywords = investments.get(i).getName().toLowerCase().split("\\s+");
            for (String keyword : keywords) {
                keywordIndex.computeIfAbsent(keyword, k -> new ArrayList<>()).add(i);
            }
        }
    }

    /**
     * Adds a new investment or updates an existing one in the portfolio. If both the name and symbol
     * match, the quantity of the existing investment is increased.
     *
     * @param type     the type of the investment (e.g., stock or mutual fund)
     * @param symbol   the symbol of the investment
     * @param name     the name of the investment
     * @param quantity the quantity to add
     * @param price    the price per unit
     */
    public void buyInvestment(String type, String symbol, String name, int quantity, double price) {
        Investment existingInvestment = null;
        boolean nameMatch = false, symbolMatch = false;

        for (Investment inv : investments) {
            if (inv.getSymbol().equalsIgnoreCase(symbol)) symbolMatch = true;
            if (inv.getName().equalsIgnoreCase(name)) nameMatch = true;
            if (symbolMatch && nameMatch) {
                existingInvestment = inv;
                break;
            }
        }

        if (existingInvestment != null) {
            existingInvestment.setQuantity(existingInvestment.getQuantity() + quantity);
            existingInvestment.updateBookValue(existingInvestment.getQuantity() * price);
            System.out.println("Investment updated with additional quantity.");
        } else if (symbolMatch || nameMatch) {
            System.out.println("Investment already exists with matching name or symbol. Returning to menu.");
        } else {
            Investment investment = type.equalsIgnoreCase("stock") ? 
                                    new Stock(symbol, name, quantity, price) : 
                                    new MutualFund(symbol, name, quantity, price);
            investments.add(investment);
            addKeywordsToIndex(investment, investments.size() - 1);
        }
    }

    /**
     * Sells a specified quantity of an investment. If the quantity sold reduces the investment to zero,
     * it is removed from the portfolio and the keyword index is updated.
     *
     * @param symbol   the symbol of the investment to sell
     * @param quantity the quantity to sell
     * @param price    the selling price per unit
     * @return 
     */
    public double sellInvestment(String symbol, int quantity, double price) {
        Investment investment = findInvestment(symbol);
        if (investment != null && investment.getQuantity() >= quantity) {
            double proceeds = investment.calculateSellProceeds(quantity, price);
            int newQuantity = investment.getQuantity() - quantity;
    
            // Update book value for the remaining quantity
            double newBookValue = (investment.getBookValue() * newQuantity) / investment.getQuantity();
            investment.updateBookValue(newBookValue);
    
            investment.setQuantity(newQuantity);
            if (newQuantity == 0) {
                int position = investments.indexOf(investment);
                investments.remove(investment);
                removeKeywordsFromIndex(position);
            }
    
            // Print to the console (optional)
            System.out.println("Proceeds from sale: " + proceeds);
            return proceeds; // Return the proceeds to the caller
        } else {
            System.out.println("Error: Investment not found or insufficient quantity.");
            return 0.0; // Return 0.0 if there's an error
        }
    }
    
    /**
     * Adds keywords from the investment name to the keyword index for search optimization.
     *
     * @param investment the investment whose keywords are to be indexed
     * @param position   the position of the investment in the list
     */
    private void addKeywordsToIndex(Investment investment, int position) {
        String[] keywords = investment.getName().toLowerCase().split("\\s+");
        for (String keyword : keywords) {
            keywordIndex.computeIfAbsent(keyword, k -> new ArrayList<>()).add(position);
        }
    }

    /**
     * Removes keywords from the index associated with the investment being removed
     * and adjusts indices of subsequent entries.
     *
     * @param position the position of the investment in the list to remove from the index
     */
    private void removeKeywordsFromIndex(int position) {
        for (Map.Entry<String, ArrayList<Integer>> entry : keywordIndex.entrySet()) {
            entry.getValue().removeIf(index -> index == position);
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (entry.getValue().get(i) > position) {
                    entry.getValue().set(i, entry.getValue().get(i) - 1);
                }
            }
        }
        keywordIndex.values().removeIf(List::isEmpty);
    }

    /**
     * Prompts the user to update the price of an investment of the specified type.
     * @return 
     */
    public String updatePrices(String symbol, double price) {
        Investment investment = findInvestment(symbol);  // Assuming findInvestment searches in your portfolio
        if (investment != null) {
            investment.setPrice(price);  // Set the new price for the found investment
            return "Price updated for " + investment.getName() + " (" + symbol + ").";  // Return a success message
        } else {
            return "Investment with symbol " + symbol + " not found.";  // Return an error message
        }
    }
    

    /**
     * Calculates and returns the total gain of all investments in the portfolio.
     *
     * @return the total gain of all investments
     */
    public double getTotalGains() {
        double totalGains = 0.0;
        for (Investment investment : investments) {
            totalGains += investment.calculateGain();  // Calculate gain for each investment
        }
        return totalGains;
    }

    public ArrayList<Investment> getInvestments() {
        return investments;
    }

    

    /**
     * Searches for investments in the portfolio based on symbol, keyword in the name, and price range.
     * If a keyword is provided, it will narrow down the search using the keyword index.
     *
     * @param symbol   the symbol to search for
     * @param keyword  the keyword in the name to search for
     * @param lowPrice the minimum price
     * @param highPrice the maximum price
     * @return 
     */
    public String search(String symbol, String keyword, double lowPrice, double highPrice) {
        Set<Integer> resultPositions = new HashSet<>();
        StringBuilder resultMessage = new StringBuilder();
    
        if (!keyword.isEmpty()) {
            String normalizedKeyword = keyword.toLowerCase();
            ArrayList<Integer> positions = keywordIndex.get(normalizedKeyword);
    
            if (positions != null) {
                resultPositions.addAll(positions);
            }
        }
    
        if (!symbol.isEmpty()) {
            for (int i = 0; i < investments.size(); i++) {
                Investment investment = investments.get(i);
                if (investment.getSymbol().equalsIgnoreCase(symbol)) {
                    resultPositions.add(i);
                }
            }
        }
    
        if (lowPrice >= 0 && highPrice > lowPrice) {
            for (int i = 0; i < investments.size(); i++) {
                Investment investment = investments.get(i);
                double price = investment.getPrice();
                if (price >= lowPrice && price <= highPrice) {
                    resultPositions.add(i);
                }
            }
        }
    
        // Format the results
        if (!resultPositions.isEmpty()) {
            for (int position : resultPositions) {
                Investment investment = investments.get(position);
                resultMessage.append(String.format("Name: %s\nSymbol: %s\nQuantity: %d\nPrice: %.2f\nBook Value: %.2f\n\n",
                        investment.getName(),
                        investment.getSymbol(),
                        investment.getQuantity(),
                        investment.getPrice(),
                        investment.getBookValue()));
            }
        } else {
            resultMessage.append("No investments found with the given criteria.");
        }
    
        return resultMessage.toString();
    }
    

    /**
     * Finds an investment in the portfolio by its symbol.
     *
     * @param symbol the symbol of the investment to find
     * @return the matching investment, or {@code null} if not found
     */
    private Investment findInvestment(String symbol) {
        for (Investment investment : investments) {
            if (investment.getSymbol().equalsIgnoreCase(symbol)) {
                return investment;
            }
        }
        return null;
    }

    
    /**
     * Provides a command loop for interacting with the portfolio, allowing users to execute
     * various commands such as buy, sell, update, getGain, search, and quit.
     */
    public void commandLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Buy");
            System.out.println("Sell");
            System.out.println("Update");
            System.out.println("getGain");
            System.out.println("Search");
            System.out.println("Quit");
            System.out.print("Enter command (buy, sell, update, getGain, search, quit): ");
            
            String command = scanner.nextLine().toLowerCase();

            if (command.equals("quit") || command.equals("q")) {
                System.out.println("Ending program, Goodbye!");
                break;
            }

            if (command.equalsIgnoreCase("buy") || command.equalsIgnoreCase("b")) {
                String type;
                do {
                    System.out.print("Enter investment type (stock/mutualfund): ");
                    type = scanner.nextLine().toLowerCase();
                    if (type.equals("s")) type = "stock";
                    if (type.equals("m")) type = "mutualfund";
                } while (!type.equals("stock") && !type.equals("mutualfund"));

                System.out.print("Enter symbol: ");
                String symbol = scanner.nextLine();
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                System.out.print("Enter price: ");
                double price = scanner.nextDouble();
                buyInvestment(type, symbol, name, quantity, price);
                scanner.nextLine();
            } else if (command.equalsIgnoreCase("sell")) {
                System.out.print("Enter symbol: ");
                String symbols = scanner.nextLine();
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                System.out.print("Enter price: ");
                double price = scanner.nextDouble();
                sellInvestment(symbols, quantity, price);
                scanner.nextLine();
            } else if (command.equalsIgnoreCase("update") || command.equalsIgnoreCase("u")) {
                System.out.print("Enter symbol: ");
                String symbols = scanner.nextLine();
                System.out.print("Enter price: ");
                double price = scanner.nextDouble();
                updatePrices(symbols, price);
            } else if (command.equalsIgnoreCase("getgain") || command.equalsIgnoreCase("g")) {
                System.out.println("Total Gain: " + getGain());
                            } else if (command.equalsIgnoreCase("search")) {
                                System.out.print("Enter symbol (or leave blank): ");
                                String symbol = scanner.nextLine();
                                System.out.print("Enter name keyword (or leave blank): ");
                                String keyword = scanner.nextLine();
                
                                double lowPrice = 0;
                                double highPrice = 0;
                                boolean input = false;
                
                                while (!input) {
                                    System.out.print("Enter low price (or leave blank): ");
                                    String lowPriceInput = scanner.nextLine();
                                    if (lowPriceInput.isEmpty()) {
                                        lowPrice = 0;
                                        input = true;
                                    } else {
                                        try {
                                            lowPrice = Double.parseDouble(lowPriceInput);
                                            input = true;
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid input. Please enter a valid number.");
                                        }
                                    }
                                }
                
                                input = false;
                                while (!input) {
                                    System.out.print("Enter high price (or leave blank): ");
                                    String highPriceInput = scanner.nextLine();
                                    if (highPriceInput.isEmpty()) {
                                        highPrice = 0;
                                        input = true;
                                    } else {
                                        try {
                                            highPrice = Double.parseDouble(highPriceInput);
                                            input = true;
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid input. Please enter a valid number.");
                                        }
                                    }
                                }
                
                                search(symbol, keyword, lowPrice, highPrice);
                            } else {
                                System.out.println("Invalid Option. Please try again.");
                            }
                        }
                        scanner.close();
                    }
                
                    private String getGain() {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException("Unimplemented method 'getGain'");
                    }
}