import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DiscussionBoardGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextArea messageArea;

    public DiscussionBoardGUI() {
        frame = new JFrame("Discussion Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Menu setup
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem registerUserMenu = new JMenuItem("Register User");
        JMenuItem createPostMenu = new JMenuItem("Create Post");
        JMenuItem searchPostsMenu = new JMenuItem("Search Posts");

        optionsMenu.add(registerUserMenu);
        optionsMenu.add(createPostMenu);
        optionsMenu.add(searchPostsMenu);
        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        // Main panel with CardLayout
        mainPanel = new JPanel(new CardLayout());
        JPanel registerUserPanel = createRegisterUserPanel();
        JPanel createPostPanel = createPostPanel();
        JPanel searchPostsPanel = createSearchPostsPanel();

        mainPanel.add(registerUserPanel, "Register User");
        mainPanel.add(createPostPanel, "Create Post");
        mainPanel.add(searchPostsPanel, "Search Posts");

        // Message area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setPreferredSize(new Dimension(300, 600));

        // Layout setup
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.EAST);

        // Menu actions
        registerUserMenu.addActionListener(e -> showPanel("Register User"));
        createPostMenu.addActionListener(e -> showPanel("Create Post"));
        searchPostsMenu.addActionListener(e -> showPanel("Search Posts"));

        frame.setVisible(true);
    }

    private JPanel createRegisterUserPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField fullNameField = new JTextField();
        JTextField usernameField = new JTextField();
        JButton registerButton = new JButton("Register");

        panel.add(new JLabel("Full Name:"));
        panel.add(fullNameField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(registerButton);

        registerButton.addActionListener(e -> {
            String fullName = fullNameField.getText().trim();
            String username = usernameField.getText().trim();
            if (fullName.isEmpty() || username.isEmpty()) {
                messageArea.append("Error: Full name or username cannot be blank.\n");
            } else {
                // Add user registration logic here
                messageArea.append("User registered successfully.\n");
            }
        });

        return panel;
    }

    private JPanel createPostPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField usernameField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField contentField = new JTextField();
        JButton createPostButton = new JButton("Create Post");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Content:"));
        panel.add(contentField);
        panel.add(createPostButton);

        createPostButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String title = titleField.getText().trim();
            String content = contentField.getText().trim();
            if (username.isEmpty() || title.isEmpty() || content.isEmpty()) {
                messageArea.append("Error: All fields must be filled.\n");
            } else {
                // Add post creation logic here
                messageArea.append("Post created successfully.\n");
            }
        });

        return panel;
    }

    private JPanel createSearchPostsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextField usernameField = new JTextField();
        JButton searchButton = new JButton("Search");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        panel.add(new JLabel("Enter Username:"), BorderLayout.NORTH);
        panel.add(usernameField, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.EAST);
        panel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                messageArea.append("Error: Username cannot be blank.\n");
            } else {
                // Add search logic here
                resultArea.setText("Displaying posts for username...\n");
            }
        });

        return panel;
    }

    private void showPanel(String panelName) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, panelName);
        messageArea.setText(""); // Clear messages
    }

    public static void main(String[] args) {
        new DiscussionBoardGUI();
    }
}
