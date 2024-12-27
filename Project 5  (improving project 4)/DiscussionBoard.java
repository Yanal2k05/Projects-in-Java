/*
Name: Yanal Abu Rahmmeh
User: yaburahm
student ID: 1284819
How to compile: javac DiscussionBoard.java
How to execute: java DiscussionBoard cis2430
*/
import java.util.*;
import java.io.*;

public class DiscussionBoard {
    private static ArrayList<User> users = new ArrayList<>();          // List of users
    private static ArrayList<Post> posts = new ArrayList<>();           // List of posts
    private static HashMap<String, ArrayList<Post>> userPostIndexMap = new HashMap<>(); // Map posts by username

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filename = (args.length > 0) ? "./boards/" + args[0] + ".dboard" : null; // Assign filename if provided

        if (filename != null && new File(filename).exists()) {
            loadDiscussionBoard(filename); // Load existing board from file if available
        } else {
            System.out.println("No valid file specified. Starting with a blank discussion board.");
        }

        boolean running = true;
        while (running) {
            displayMenu(); // Display options for user
            int option = getValidIntegerInput(scanner, 1, 5); // Get user option and validate

            // Execute selected option using switch statement
            switch (option) {
                case 1 -> createNewUser(scanner);
                case 2 -> createNewPost(scanner);
                case 3 -> viewAllPosts();
                case 4 -> viewPostsByUsername(scanner);
                case 5 -> {
                    if (filename != null) saveDiscussionBoard(filename); // Save board if filename provided
                    System.out.println("Program ended.");
                    running = false;
                }
            }
        }
    }

    // Load discussion board from specified file
    private static void loadDiscussionBoard(String filename) {
    File file = new File(filename);
    if (!file.exists()) { // Check if file exists
        System.out.println("File not found.");
        return;
    }

    try (Scanner fileScanner = new Scanner(file)) { // Open file scanner
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim(); // Read each line
            if (line.isEmpty()) continue; // Skip empty lines

            String[] data = line.split(";");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].trim(); // Trim each element
            }

            if (data.length < 4) {
                System.out.println("Error: Invalid line format - " + line);
                continue; // Skip invalid format lines
            }

            String postType = data[0];
            int postId;
            try {
                postId = Integer.parseInt(data[1]); // Parse post ID and validate
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid post ID - " + data[1]);
                continue;
            }
            String username = data[2];
            String title = data[3];

            // Check if user exists; create if not
            User user = users.stream().filter(u -> u.username.equals(username)).findFirst().orElse(null);
            if (user == null) {
                user = new User(username, username); // Use username as full name if missing
                users.add(user);
            }

            // Process text post
            if (postType.equals("TEXTPOST")) {
                if (data.length < 5) {
                    System.out.println("Error: Text post missing content - " + line);
                    continue;
                }
                String content = data[4];
                TextPost textPost = new TextPost(user, title, content); // Create TextPost object
                textPost.id = postId;
                posts.add(textPost);
                userPostIndexMap.putIfAbsent(username, new ArrayList<>()); // Add post to user index map
                userPostIndexMap.get(username).add(textPost);
            } else {
                System.out.println("Error: Unknown post type - " + line);
            }
        }
        System.out.println("Discussion board loaded from file.");
    } catch (FileNotFoundException e) {
        System.out.println("Error: File not found.");
    }
}

    // Display menu options
    private static void displayMenu() {
        System.out.println("\n(1) Create new user");
        System.out.println("(2) Create new post");
        System.out.println("(3) View all posts");
        System.out.println("(4) View all posts with a given username");
        System.out.println("(5) End Program");
        System.out.print("Choose option: ");
    }

    // Create a new user
    private static void createNewUser(Scanner scanner) {
        String fullName = getNonBlankInput(scanner, "Enter full name: "); // Get user's full name
        String username;

        while (true) {
            username = getNonBlankInput(scanner, "Enter username: "); // Get and validate username
            String usernameToCheck = username.toLowerCase();

            boolean usernameExists = !username.isBlank() && users.stream().anyMatch(user -> user.username.equals(usernameToCheck));

            if (usernameExists) { // Check if username is unique
                System.out.println("Error: Username already exists. Please choose another one.");
            } else {
                break;
            }
        }

        // Add new user to list
        try {
            User newUser = new User(fullName, username);
            users.add(newUser);
            System.out.println("User registered successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    // Create a new post by an existing user with partial match
    private static void createNewPost(Scanner scanner) {
        String username = getNonBlankInput(scanner, "Enter your username: ").toLowerCase(); // Get username input

        User user = users.stream()
            .filter(u -> u.username.toLowerCase().contains(username))
            .findFirst()
            .orElse(null); // Find user with partial match

        if (user == null) { // Check if user exists
            System.out.println("Error: User not registered.");
            return;
        }

        // Get post title and content
        String title = getNonBlankInput(scanner, "Enter post title: ");
        String content = getNonBlankInput(scanner, "Enter post content: ");

        try {
            TextPost newPost = new TextPost(user, title, content); // Create TextPost object
            posts.add(newPost);

            // Update user posts in HashMap
            userPostIndexMap.putIfAbsent(user.username.toLowerCase(), new ArrayList<>());
            userPostIndexMap.get(user.username.toLowerCase()).add(newPost);

            System.out.println("Text post added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating post: " + e.getMessage());
        }
    }

    // Display all posts
    private static void viewAllPosts() {
        if (posts.isEmpty()) {
            System.out.println("No posts.");
        } else {
            posts.forEach(post -> System.out.println(post.display())); // Display each post
        }
    }

    // View posts by a specific username with partial match
    private static void viewPostsByUsername(Scanner scanner) {
        System.out.print("Enter the username: ");
        String username = scanner.nextLine().toLowerCase();

        ArrayList<Post> userPosts = new ArrayList<>();

        // Check all usernames in the map for partial match
        for (String key : userPostIndexMap.keySet()) {
            if (key.contains(username)) {
                userPosts.addAll(userPostIndexMap.get(key));
            }
        }

        if (userPosts.isEmpty()) {
            System.out.println("No posts found for the username: " + username);
        } else {
            userPosts.forEach(post -> System.out.println(post.display())); // Display posts by user
        }
    }

    // Get a valid integer input within a specified range
    private static int getValidIntegerInput(Scanner scanner, int min, int max) {
        int input = -1;
        while (input < min || input > max) {
            try {
                input = Integer.parseInt(scanner.nextLine()); // Parse integer input
                if (input < min || input > max) {
                    System.out.printf("Please enter a number between %d and %d: ", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
        return input;
    }

    // Get a non-blank input from the user with a prompt
    private static String getNonBlankInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Input cannot be blank. Please enter a valid value.");
            }
        } while (input.isBlank());
        return input;
    }

    // Save discussion board to a specified file
    private static void saveDiscussionBoard(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Post post : posts) {
                String postType = post instanceof TextPost ? "TEXTPOST" : "UNKNOWN"; // Check post type
                // Format and write post details to file
                String line = String.format("Type: %s; Post ID: %d; Username: %s; Title: %s; Content %s", 
                postType, post.getId(), post.getUser().username, post.getTitle(), post.getContent());
                writer.println(line);
            }
            System.out.println("Discussion board saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving discussion board: " + e.getMessage());
        }
    }
}
