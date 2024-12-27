package ePortfolio;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * This class represents the main graphical user interface (GUI) for the ePortfolio application.
 * It includes a menu for executing various commands, such as buying, selling, updating, and searching investments.
 * It also handles displaying a welcome message to the user and setting up the layout of the application.
 */
public class PortfolioGUI extends JFrame {
    private Portfolio portfolio;
    private JPanel topPanel;
    private JPanel contentPanel;

    // Declare text fields for user input
    private JTextField typeField;
    private JTextField symbolField;
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField priceField;

    // Declare search-related text fields
    private JTextField keywordField;
    private JTextField lowPriceField;
    private JTextField highPriceField;

    /**
     * Constructor that sets up the initial GUI for the ePortfolio.
     */
    public PortfolioGUI() {
        super();  // Call the parent constructor for JFrame
        portfolio = new Portfolio();  // Initialize the portfolio

        // Initialize the text fields for user input
        typeField = new JTextField();
        symbolField = new JTextField();
        nameField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();
        keywordField = new JTextField();
        lowPriceField = new JTextField();
        highPriceField = new JTextField();

        // Set up the frame size and properties
        setSize(800, 700);
        setTitle("ePortfolio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color border = new Color(100, 120, 150);  // Set border color for panels
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        // Set up the title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("ePortfolio");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.GRAY);
        Border titleBorder = BorderFactory.createLineBorder(border, 2);  // Set a border for the title panel
        titlePanel.add(titleLabel);
        titlePanel.setBorder(titleBorder);
        topPanel.add(titlePanel);

        // Set up the "Commands" button
        JButton commandsButton = new JButton("Commands");
        commandsButton.setFont(new Font("Arial", Font.BOLD, 16));
        commandsButton.setForeground(Color.GRAY);
        commandsButton.setBackground(Color.WHITE);  
        commandsButton.setBorderPainted(false);    
        commandsButton.setFocusPainted(false);     

        // Create a context menu for the "Commands" button
        JPopupMenu commandsMenu = new JPopupMenu();

        // Define menu items for different commands
        JMenuItem buyItem = new JMenuItem("Buy");
        JMenuItem sellItem = new JMenuItem("Sell");
        JMenuItem updateItem = new JMenuItem("Update");
        JMenuItem getGainsItem = new JMenuItem("Get Gains");
        JMenuItem searchItem = new JMenuItem("Search");
        JMenuItem quitItem = new JMenuItem("Quit");

        // Add action listeners for each menu item
        buyItem.addActionListener(new BuyWindow());
        sellItem.addActionListener(new SellWindow());
        updateItem.addActionListener(new UpdateWindow());
        getGainsItem.addActionListener(new GetGainsWindow());
        searchItem.addActionListener(new SearchWindow());
        quitItem.addActionListener(new QuitWindow());

        // Add menu items to the context menu
        commandsMenu.add(buyItem);
        commandsMenu.add(sellItem);
        commandsMenu.add(updateItem);
        commandsMenu.add(getGainsItem);
        commandsMenu.add(searchItem);
        commandsMenu.add(quitItem);

        // Add action listener to the "Commands" button to display the menu
        commandsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandsMenu.show(commandsButton, 0, commandsButton.getHeight());
            }
        });

        // Set up the commands panel to hold the "Commands" button
        JPanel commandsPanel = new JPanel();
        commandsPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align button to the left
        commandsPanel.add(commandsButton);

        topPanel.add(commandsPanel);

        // Set up the content panel where the main content will be displayed
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Add the top panel and content panel to the frame
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // Display the welcome message to the user
        displayWelcomeMessage();

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Displays a welcome message in the content panel when the application starts.
     */
    private void displayWelcomeMessage() {
        // Create a text area to display the welcome message
        JTextArea welcomeMessage = new JTextArea();
        welcomeMessage.setEditable(false);  // Make the message non-editable
        welcomeMessage.setText("""
                               
                               
                                           Welcome to ePortfolio!
                               
                               
                               
                                                      Choose a command from the \u201cCommands\u201d menu to buy or sell
                                                      an investment, update prices for all investments, get gain for the
                                                      portfolio, search for relevant investments, or quit the program""");
        welcomeMessage.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeMessage.setForeground(Color.GRAY);

        // Clear the content panel and add the welcome message
        contentPanel.removeAll();
        contentPanel.add(welcomeMessage, BorderLayout.CENTER);
        revalidate();  // Revalidate the layout to reflect changes
        repaint();  // Repaint the panel to show the updated content
    }

    private class BuyWindow implements ActionListener {

        /**
         * This method is triggered when the "Buy" button is clicked.
         * It displays a form for purchasing an investment, validates user input,
         * and attempts to purchase the investment by adding it to the portfolio.
         */
        public void actionPerformed(ActionEvent e) {
            // Create the main panel for the "Buy Investment" form
            JPanel buyPanel = new JPanel(new BorderLayout());
    
            // Create and configure the header for the panel
            JLabel headerLabel = new JLabel("Buying an Investment");
            headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            headerPanel.add(headerLabel);
    
            // Create the input panel with fields for type, symbol, name, quantity, and price
            JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
    
            JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"stock", "mutual fund"});
            JTextField symbolField = new JTextField(10);
            JTextField nameField = new JTextField(10);
            JTextField quantityField = new JTextField(10);
            JTextField priceField = new JTextField(10);
    
            // Add labels and fields to the input panel
            inputPanel.add(new JLabel("Type:"));
            inputPanel.add(typeComboBox);
            inputPanel.add(new JLabel("Symbol:"));
            inputPanel.add(symbolField);
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Quantity:"));
            inputPanel.add(quantityField);
            inputPanel.add(new JLabel("Price:"));
            inputPanel.add(priceField);
    
            // Create a message area to display status messages (e.g., success or error messages)
            JTextArea messageArea = new JTextArea(5, 20);
            messageArea.setWrapStyleWord(true);
            messageArea.setLineWrap(true);
            messageArea.setEditable(false);
    
            // Add the message area to a scroll pane for better readability
            JScrollPane messageScrollPane = new JScrollPane(messageArea);
            messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
            // Create a panel for displaying messages
            JLabel messageLabel = new JLabel("Messages");
    
            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
    
            // Add message label and scroll pane to the message panel
            messagePanel.add(messageLabel, gbc);
    
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            messagePanel.add(messageScrollPane, gbc);
    
            // Create a button panel with "Buy" and "Reset" buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            JButton buyButton = new JButton("Buy");
            JButton resetButton = new JButton("Reset");
    
            // Set fixed sizes for the buttons
            Dimension buttonSize = new Dimension(80, 25);
            buyButton.setPreferredSize(buttonSize);
            buyButton.setMaximumSize(buttonSize);
            buyButton.setMinimumSize(buttonSize);
    
            resetButton.setPreferredSize(buttonSize);
            resetButton.setMaximumSize(buttonSize);
            resetButton.setMinimumSize(buttonSize);
    
            // Add buttons to the button panel
            buttonPanel.add(Box.createVerticalGlue());
            buttonPanel.add(resetButton);
            buttonPanel.add(Box.createVerticalStrut(10));
            buttonPanel.add(buyButton);
            buttonPanel.add(Box.createVerticalGlue());
    
            // Create the main panel and arrange all components
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(headerPanel, BorderLayout.NORTH);
            mainPanel.add(inputPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.EAST);
            mainPanel.add(messagePanel, BorderLayout.SOUTH);
    
            // Update the content panel to display the "Buy Investment" form
            contentPanel.removeAll();
            contentPanel.add(mainPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
    
            /**
             * Action listener for the "Buy" button.
             * It validates the input fields, creates an investment object,
             * and adds it to the portfolio if valid.
             */
            buyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Retrieve the values entered in the form
                    String type = typeComboBox.getSelectedItem().toString();
                    String symbol = symbolField.getText();
                    String name = nameField.getText();
                    int quantity = 0;
    
                    // Validate the quantity field
                    try {
                        quantity = Integer.parseInt(quantityField.getText());
                    } catch (NumberFormatException ex) {
                        messageArea.append("Invalid quantity entered. Please enter a valid number. \n");
                        return;
                    }
    
                    double price = 0;
                    // Validate the price field
                    try {
                        price = Double.parseDouble(priceField.getText());
                    } catch (NumberFormatException ex) {
                        messageArea.append("Invalid price entered. Please enter a valid number. \n");
                        return;
                    }
    
                    // Create an investment object based on the selected type (stock or mutual fund)
                    Investment investment = null;
                    if (type.equalsIgnoreCase("stock")) {
                        investment = new Stock(symbol, name, quantity, price);
                    } else if (type.equalsIgnoreCase("mutual fund")) {
                        investment = new MutualFund(symbol, name, quantity, price);
                    }
    
                    // Add the investment to the portfolio
                    portfolio.buyInvestment(type, symbol, name, quantity, price);
                    messageArea.append("Investment bought successfully. \n");
                }
            });
    
            /**
             * Action listener for the "Reset" button.
             * It clears all the fields and resets the form.
             */
            resetButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Reset all input fields and selection
                    typeComboBox.setSelectedIndex(0);
                    symbolField.setText("");
                    nameField.setText("");
                    quantityField.setText("");
                    priceField.setText("");
                    messageArea.append("Fields reset. \n");
                }
            });
        }
    }
    

    private class SellWindow implements ActionListener {

        /**
         * This method is triggered when the "Sell" button is clicked.
         * It displays a form for selling an investment, validates user input,
         * and attempts to sell the investment by removing it from the portfolio.
         */
        public void actionPerformed(ActionEvent e) {
            // Create the main panel for the "Sell Investment" form
            JPanel sellPanel = new JPanel(new BorderLayout());
    
            // Create and configure the header for the panel
            JLabel headerLabel = new JLabel("Selling an Investment");
            headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            headerPanel.add(headerLabel);
    
            // Create the input panel with fields for symbol, quantity, and price
            JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            JTextField symbolField = new JTextField(10);
            JTextField quantityField = new JTextField(10);
            JTextField priceField = new JTextField(10);
    
            // Add labels and fields to the input panel
            inputPanel.add(new JLabel("Symbol:"));
            inputPanel.add(symbolField);
            inputPanel.add(new JLabel("Quantity:"));
            inputPanel.add(quantityField);
            inputPanel.add(new JLabel("Price:"));
            inputPanel.add(priceField);
    
            // Create a message area to display status messages (e.g., success or error messages)
            JTextArea messageArea = new JTextArea(5, 20);
            messageArea.setWrapStyleWord(true);
            messageArea.setLineWrap(true);
            messageArea.setEditable(false);
    
            // Add the message area to a scroll pane for better readability
            JScrollPane messageScrollPane = new JScrollPane(messageArea);
            messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
            // Create a panel for displaying messages
            JLabel messageLabel = new JLabel("Messages");
    
            JPanel messagePanel = new JPanel(new BorderLayout());
            messagePanel.add(messageLabel, BorderLayout.NORTH);
            messagePanel.add(messageScrollPane, BorderLayout.CENTER);
    
            // Create a button panel with "Sell" and "Reset" buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            JButton sellButton = new JButton("Sell");
            JButton resetButton = new JButton("Reset");
    
            // Set fixed sizes for the buttons
            Dimension buttonSize = new Dimension(80, 25);
            sellButton.setPreferredSize(buttonSize);
            sellButton.setMaximumSize(buttonSize);
            sellButton.setMinimumSize(buttonSize);
    
            resetButton.setPreferredSize(buttonSize);
            resetButton.setMaximumSize(buttonSize);
            resetButton.setMinimumSize(buttonSize);
    
            // Add buttons to the button panel
            buttonPanel.add(Box.createVerticalGlue());
            buttonPanel.add(resetButton);
            buttonPanel.add(Box.createVerticalStrut(10));
            buttonPanel.add(sellButton);
            buttonPanel.add(Box.createVerticalGlue());
    
            // Create the main panel and arrange all components
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(headerPanel, BorderLayout.NORTH);
            mainPanel.add(inputPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.EAST);
            mainPanel.add(messagePanel, BorderLayout.SOUTH);
    
            // Update the content panel to display the "Sell Investment" form
            contentPanel.removeAll();
            contentPanel.add(mainPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
    
            /**
             * Action listener for the "Sell" button.
             * It validates the input fields, performs the sale,
             * and updates the message area with the result.
             */
            sellButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Retrieve the values entered in the form
                        String symbol = symbolField.getText();
                        int quantity = Integer.parseInt(quantityField.getText());
                        double price = Double.parseDouble(priceField.getText());
    
                        // Attempt to sell the investment and get the proceeds
                        double proceeds = portfolio.sellInvestment(symbol, quantity, price);
    
                        // Display appropriate messages based on the sale result
                        if (proceeds > 0) {
                            messageArea.append("Investment sold successfully!\n");
                            messageArea.append("Proceeds from sale: " + proceeds + "\n");
                        } else {
                            messageArea.append("Error: Investment not found or insufficient quantity.\n");
                        }
                    } catch (NumberFormatException ex) {
                        messageArea.append("Please enter valid numbers for quantity and price.\n");
                    } catch (Exception ex) {
                        messageArea.append("Error: " + ex.getMessage() + "\n");
                    }
                }
            });
    
            /**
             * Action listener for the "Reset" button.
             * It clears all the input fields and resets the form.
             */
            resetButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Reset all input fields
                    symbolField.setText("");
                    quantityField.setText("");
                    priceField.setText("");
                    messageArea.append("Fields reset.\n");
                }
            });
        }
    }
    
    
    private class UpdateWindow implements ActionListener {

        /**
         * This method is triggered when the "Update Investment" button is clicked.
         * It creates a form where users can update the price of an investment.
         * It allows navigation through a list of investments and handles validation
         * of the new price before updating it in the portfolio.
         */
        public void actionPerformed(ActionEvent e) {
            // Create the main panel for the "Update Investment" form
            JPanel updatePanel = new JPanel(new BorderLayout());
    
            // Create and configure the header for the panel
            JLabel headerLabel = new JLabel("Updating Investment");
            headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            headerPanel.add(headerLabel);
    
            // Create the input panel with fields for symbol, name, and new price
            JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            JTextField symbolField = new JTextField(10);
            JTextField nameField = new JTextField(10);
            JTextField priceField = new JTextField(10);
    
            // Add labels and fields to the input panel
            inputPanel.add(new JLabel("Symbol:"));
            inputPanel.add(symbolField);
            symbolField.setEditable(false);
            inputPanel.add(new JLabel("Name:"));
            nameField.setEditable(false);
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("New Price:"));
            inputPanel.add(priceField);
    
            // Create a message area to display status messages (e.g., success or error messages)
            JTextArea messageArea = new JTextArea(5, 20);
            messageArea.setWrapStyleWord(true);
            messageArea.setLineWrap(true);
            messageArea.setEditable(false);
    
            // Add the message area to a scroll pane for better readability
            JScrollPane messageScrollPane = new JScrollPane(messageArea);
            messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
            // Create a panel for the buttons: "Save", "Prev", "Next"
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    
            // Set fixed sizes for the buttons
            Dimension buttonSize = new Dimension(100, 40);
            JButton updateButton = new JButton("Save");
            JButton prevButton = new JButton("Prev");
            JButton nextButton = new JButton("Next");
    
            updateButton.setPreferredSize(buttonSize);
            prevButton.setPreferredSize(buttonSize);
            nextButton.setPreferredSize(buttonSize);
    
            // Add buttons to the button panel
            buttonPanel.add(Box.createVerticalGlue());
            buttonPanel.add(prevButton);
            buttonPanel.add(Box.createVerticalStrut(10));
            buttonPanel.add(nextButton);
            buttonPanel.add(Box.createVerticalStrut(10));
            buttonPanel.add(updateButton);
            buttonPanel.add(Box.createVerticalGlue());
    
            // Create a right-aligned panel to hold the buttons
            JPanel rightAlignPanel = new JPanel(new BorderLayout());
            rightAlignPanel.add(buttonPanel, BorderLayout.EAST);
    
            // Create the main panel and arrange all components
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(headerPanel, BorderLayout.NORTH);
            mainPanel.add(inputPanel, BorderLayout.CENTER);
            mainPanel.add(rightAlignPanel, BorderLayout.EAST);
            mainPanel.add(messageScrollPane, BorderLayout.SOUTH);
    
            // Update the content panel to display the "Update Investment" form
            contentPanel.removeAll();
            contentPanel.add(mainPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
    
            // Retrieve the list of investments from the portfolio
            List<Investment> investments = portfolio.getInvestments();
            final int[] currentIndex = {0};
    
            // If investments are available, populate the fields with the current investment details
            if (!investments.isEmpty()) {
                Investment currentInvestment = investments.get(currentIndex[0]);
                symbolField.setText(currentInvestment.getSymbol());
                nameField.setText(currentInvestment.getName());
                priceField.setText(String.valueOf(currentInvestment.getPrice()));
                updateNavigationButtons(prevButton, nextButton, currentIndex[0], investments.size());
            } else {
                messageArea.append("No investments available.\n");
                prevButton.setEnabled(false);
                nextButton.setEnabled(false);
            }
    
            /**
             * Action listener for the "Prev" button.
             * It navigates to the previous investment in the list.
             */
            prevButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (currentIndex[0] > 0) {
                        currentIndex[0]--;
                        Investment currentInvestment = investments.get(currentIndex[0]);
                        symbolField.setText(currentInvestment.getSymbol());
                        nameField.setText(currentInvestment.getName());
                        priceField.setText(String.valueOf(currentInvestment.getPrice()));
                        messageArea.setText("Moved to previous investment.");
                        updateNavigationButtons(prevButton, nextButton, currentIndex[0], investments.size());
                    } else {
                        messageArea.setText("Already at the first investment.");
                    }
                }
            });
    
            /**
             * Action listener for the "Next" button.
             * It navigates to the next investment in the list.
             */
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (currentIndex[0] < investments.size() - 1) {
                        currentIndex[0]++;
                        Investment currentInvestment = investments.get(currentIndex[0]);
                        symbolField.setText(currentInvestment.getSymbol());
                        nameField.setText(currentInvestment.getName());
                        priceField.setText(String.valueOf(currentInvestment.getPrice()));
                        messageArea.setText("Moved to next investment.");
                        updateNavigationButtons(prevButton, nextButton, currentIndex[0], investments.size());
                    } else {
                        messageArea.setText("Already at the last investment.");
                    }
                }
            });
    
            /**
             * Action listener for the "Save" button.
             * It validates the price input, updates the price of the current investment,
             * and displays a success or error message.
             */
            updateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Retrieve the price entered by the user and validate it
                        String symbol = symbolField.getText().trim();
                        double price = Double.parseDouble(priceField.getText().trim());
    
                        if (price <= 0) {
                            throw new NumberFormatException();
                        }
    
                        // Update the price of the current investment
                        Investment currentInvestment = investments.get(currentIndex[0]);
                        currentInvestment.setPrice(price);
    
                        // Display success message
                        messageArea.append("Price updated successfully for " + currentInvestment.getSymbol() + "!\n");
                    } catch (NumberFormatException ex) {
                        messageArea.append("Please enter a valid price.\n");
                    } catch (Exception ex) {
                        messageArea.append("Error: " + ex.getMessage() + "\n");
                    }
                }
            });
        }
    
        /**
         * Helper method to enable or disable navigation buttons
         * based on the current index and total number of investments.
         */
        private void updateNavigationButtons(JButton prevButton, JButton nextButton, int currentIndex, int totalSize) {
            prevButton.setEnabled(currentIndex > 0);
            nextButton.setEnabled(currentIndex < totalSize - 1);
        }
    }
    
    
    private class GetGainsWindow implements ActionListener {

        /**
         * This method is triggered when the "Get Gains" action is performed.
         * It displays the total gains from the portfolio as well as the individual gains for each investment.
         * The information is displayed in a user-friendly format in the UI.
         */
        public void actionPerformed(ActionEvent e) {
            // Create the main panel for displaying total and individual gains
            JPanel gainsPanel = new JPanel(new BorderLayout());
    
            // Create the label for the header of the window
            JLabel messageLabel = new JLabel("Getting total gain");
            messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
            JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            messagePanel.add(messageLabel);
            gainsPanel.add(messagePanel, BorderLayout.NORTH);
    
            // Panel to display the total gains
            JPanel totalGainsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            totalGainsPanel.add(Box.createHorizontalStrut(25));
            JLabel totalLabel = new JLabel("Total Gains:");
            
            // Non-editable text field to display the total gains
            JTextField totalGainsField = new JTextField(10);
            totalGainsField.setEditable(false);
            
            // Retrieve total gains from the portfolio
            double totalGains = portfolio.getTotalGains();
            totalGainsField.setText(String.format("%.2f", totalGains));
            
            // Add total gains label and field to the panel
            totalGainsPanel.add(totalLabel);
            totalGainsPanel.add(totalGainsField);
            gainsPanel.add(totalGainsPanel, BorderLayout.CENTER);
    
            // Create a text area to display the individual gains of each investment
            JTextArea individualGainsArea = new JTextArea(5, 20);
            individualGainsArea.setEditable(false);
            individualGainsArea.setWrapStyleWord(true);
            individualGainsArea.setLineWrap(true);
    
            // StringBuilder to accumulate individual gain information
            StringBuilder individualGains = new StringBuilder();
            for (Investment investment : portfolio.getInvestments()) {
                // Calculate gain for each investment and append it to the string
                double gain = investment.calculateGain();
                individualGains.append(investment.getName() + ": " + String.format("%.2f", gain) + "\n");
            }
    
            // Set the text area with the individual gains information
            individualGainsArea.setText(individualGains.toString());
    
            // Label and panel for individual gains section
            JLabel messagesLabel = new JLabel("Individual Gains");
            JPanel messagesPanel = new JPanel(new BorderLayout());
            messagesPanel.add(messagesLabel, BorderLayout.NORTH);
    
            // Scroll pane to allow scrolling of the individual gains area
            JScrollPane scrollPane = new JScrollPane(individualGainsArea, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            messagesPanel.add(scrollPane, BorderLayout.CENTER);
    
            // Add the messages panel containing individual gains to the main gains panel
            gainsPanel.add(messagesPanel, BorderLayout.SOUTH);
    
            // Replace the content panel with the new gains panel
            contentPanel.removeAll();
            contentPanel.add(gainsPanel, BorderLayout.CENTER);
    
            // Revalidate and repaint the content panel to apply the changes
            revalidate();
            repaint();
        }
    }
    

/**
 * This class defines the SearchWindow where users can search investments in the portfolio.
 * Users can search by symbol, name keyword, and specify a price range (low and high).
 */
private class SearchWindow implements ActionListener {

    private JTextField symbolField = new JTextField(20);
    private JTextField keywordField = new JTextField(20);
    private JTextField lowPriceField = new JTextField(10);
    private JTextField highPriceField = new JTextField(10);
    private JButton searchButton = new JButton("Search");
    private JButton resetButton = new JButton("Reset");
    private JTextArea messageArea = new JTextArea(5, 20);

    /**
     * This method is triggered when the "Search" or "Reset" button is pressed.
     * It displays the search form and handles the search functionality.
     *
     * @param e the ActionEvent triggered by pressing a button
     */
    public void actionPerformed(ActionEvent e) {
        // Create the main panel for the search window
        JPanel searchPanel = new JPanel(new BorderLayout());

        // Create a label for the title of the window
        JLabel titleLabel = new JLabel("Searching Investments");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel for user input fields (symbol, keyword, price range)
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Symbol:"));
        inputPanel.add(symbolField);
        inputPanel.add(new JLabel("Name Keyword:"));
        inputPanel.add(keywordField);
        inputPanel.add(new JLabel("Low Price:"));
        inputPanel.add(lowPriceField);
        inputPanel.add(new JLabel("High Price:"));
        inputPanel.add(highPriceField);

        // Set up the message area that will display search results
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);

        // Add the message area inside a scroll pane for scrollable results
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel messagesPanel = new JPanel(new BorderLayout());
        messagesPanel.add(new JLabel("Search Results"), BorderLayout.NORTH);
        messagesPanel.add(messageScrollPane, BorderLayout.CENTER);

        // Panel for the buttons (search and reset)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Set consistent button size for reset and search buttons
        Dimension buttonSize = new Dimension(80, 25);
        resetButton.setPreferredSize(buttonSize);
        resetButton.setMaximumSize(buttonSize);
        resetButton.setMinimumSize(buttonSize);

        searchButton.setPreferredSize(buttonSize);
        searchButton.setMaximumSize(buttonSize);
        searchButton.setMinimumSize(buttonSize);

        // Add buttons to the panel
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(searchButton);
        buttonPanel.add(Box.createVerticalGlue());

        // Main panel to organize the layout of the window
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(messagesPanel, BorderLayout.SOUTH);

        // Clear existing content and add the new search panel
        contentPanel.removeAll();
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        
        // Revalidate and repaint to update the UI with new components
        revalidate();
        repaint();

        // Search button action listener to handle search queries
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the values entered by the user in the search form
                String symbol = symbolField.getText();
                String keyword = keywordField.getText();
                double lowPrice = -1, highPrice = -1;

                String lowPriceText = lowPriceField.getText().trim();
                String highPriceText = highPriceField.getText().trim();

                // Handle parsing of price fields, default to -1 if empty or invalid
                if (!lowPriceText.isEmpty()) {
                    try {
                        lowPrice = Double.parseDouble(lowPriceText);
                    } catch (NumberFormatException ex) {
                        messageArea.setText("Invalid low price input.");
                    }
                }

                if (!highPriceText.isEmpty()) {
                    try {
                        highPrice = Double.parseDouble(highPriceText);
                    } catch (NumberFormatException ex) {
                        messageArea.setText("Invalid high price input.");
                    }
                }

                // Perform the search and display the results in the message area
                String searchResults = portfolio.search(symbol, keyword, lowPrice, highPrice);
                messageArea.setText(searchResults);
            }
        });

        // Reset button action listener to clear the input fields and messages
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear all the input fields and message area
                symbolField.setText("");
                keywordField.setText("");
                lowPriceField.setText("");
                highPriceField.setText("");
                messageArea.setText("");
            }
        });
    }
}

/**
 * This class defines a QuitWindow where the user can exit the application.
 */
private class QuitWindow implements ActionListener {

    /**
     * This method is triggered when the "Quit" action is performed.
     * It closes the application.
     *
     * @param e the ActionEvent triggered by quitting the application
     */
    public void actionPerformed(ActionEvent e) {
        // Exit the application
        System.exit(0);
    }
}


 /**
 * The main entry point for the Portfolio GUI application.
 * This method initializes the GUI by creating an instance of the PortfolioGUI class.
 */
public static void main(String[] args) {

    new PortfolioGUI();
}
}

