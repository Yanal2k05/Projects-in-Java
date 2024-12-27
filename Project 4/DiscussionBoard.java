/*
How to compile: javac DiscussionBoard.java
How to execute: java DiscussionBoard dboard
*/

import java.util.*;
import java.io.*; // need this for it to work, what it does basically is to import all the classes in the java.io package
// import java.util.*; need this for it to work, what it does basically is to import all the classes in the java.util package

public class DiscussionBoard {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Post> posts = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filename = (args.length > 0) ? "./boards/" + args[0] + ".dboard" : null;

        if (filename != null && new File(filename).exists()) {
            loadDiscussionBoard(filename);
        } else {
            System.out.println("No valid file specified. Starting with a blank discussion board.");
        }

        boolean running = true;
        while (running) {
            displayMenu();
            int option = getValidIntegerInput(scanner, 1, 8);

            // Always call nextLine() after nextInt() to consume the newline character
            scanner.nextLine(); 

            switch (option) {
                case 1 -> createNewUser(scanner);
                case 2 -> createNewPost(scanner);
                case 3 -> viewAllPosts();
                case 4 -> voteInPoll(scanner);
                case 5 -> viewPostsByUsername(scanner);
                case 6 -> viewPostsByKeyword(scanner);
                case 7 -> saveDiscussionBoard(scanner);
                case 8 -> {
                    System.out.println("Program ended.");
                    running = false;
                }
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n(1) Create new user");
        System.out.println("(2) Create new post");
        System.out.println("(3) View all posts");
        System.out.println("(4) Vote in poll");
        System.out.println("(5) View all posts with a given username");
        System.out.println("(6) View all posts with a given keyword");
        System.out.println("(7) Save Discussion Board");
        System.out.println("(8) End Program");
        System.out.print("Choose option: ");
    }

    private static void createNewUser(Scanner scanner) {
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter username (optional): ");
        String username = scanner.nextLine();

        if (users.stream().anyMatch(user -> user.username.equals(username.toLowerCase()))) {
            System.out.println("Error: Username already exists.");
            return;
        }

        User newUser = new User(fullName, username);
        users.add(newUser);
        System.out.println("User registered successfully.");
    }

    private static void createNewPost(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().toLowerCase();

        User user = users.stream().filter(u -> u.username.equals(username)).findFirst().orElse(null);
        if (user == null) {
            System.out.println("Error: User not registered.");
            return;
        }

        String postType;
        do {
            System.out.print("Enter the post type ('text', 'poll'): ");
            postType = scanner.nextLine().toLowerCase();
            if (!postType.equals("text") && !postType.equals("poll")) {
                System.out.println("Invalid post type. Please enter 'text' or 'poll'.");
            }
        } while (!postType.equals("text") && !postType.equals("poll"));

        if (postType.equals("text")) {
            System.out.print("Enter post title: ");
            String title = scanner.nextLine();
            System.out.print("Enter post content: ");
            String content = scanner.nextLine();
            TextPost newPost = new TextPost(user, title, content);
            posts.add(newPost);
            System.out.println("Text post added successfully.");
        } else if (postType.equals("poll")) {
            System.out.print("Enter poll title: ");
            String title = scanner.nextLine();
            System.out.print("Enter poll options (separated by semicolons): ");
            String options = scanner.nextLine();
            PollPost newPoll = new PollPost(user, title, options);
            posts.add(newPoll);
            System.out.println("Poll post added successfully.");
        }
    }

    private static void viewAllPosts() {
        if (posts.isEmpty()) {
            System.out.println("No posts.");
        } else {
            posts.forEach(post -> System.out.println(post.display()));
        }
    }

    // New function: View all posts by a given username
    private static void viewPostsByUsername(Scanner scanner) {
        System.out.print("Enter the username: ");
        String username = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Post post : posts) {
            if (post.createdBy.username.equals(username)) {
                System.out.println(post.display());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No posts found for the username: " + username);
        }
    }

    // New function: View all posts containing a given keyword
// New function: View all posts containing a given keyword
private static void viewPostsByKeyword(Scanner scanner) {
    System.out.print("Enter the keyword: ");
    String keyword = scanner.nextLine().toLowerCase();

    boolean found = false;
    for (Post post : posts) {
        // Split title into words and check if any word matches the keyword
        String[] titleWords = post.title.toLowerCase().split("\\W+");  // Splitting by non-word characters
        if (Arrays.asList(titleWords).contains(keyword)) {
            System.out.println(post.display());
            found = true;
            continue;  // Move to the next post if title matches
        }

        // If it's a TextPost, split content into words and check for keyword match
        if (post instanceof TextPost) {
            String[] contentWords = ((TextPost) post).content.toLowerCase().split("\\W+");
            if (Arrays.asList(contentWords).contains(keyword)) {
                System.out.println(post.display());
                found = true;
            }
        }
    }
    if (!found) {
        System.out.println("No posts found containing the keyword: " + keyword);
    }
}


    private static void voteInPoll(Scanner scanner) {
        System.out.print("Enter post ID to vote: ");
        int postId = getValidIntegerInput(scanner);

        Post post = posts.stream().filter(p -> p.id == postId).findFirst().orElse(null);
        if (post instanceof PollPost pollPost) {
            System.out.println(pollPost.display());
            System.out.print("Enter the option number to vote for: ");
            int option = getValidIntegerInput(scanner, 1, pollPost.options.size()) - 1;
            pollPost.vote(option);
            System.out.println("Vote recorded.");
        } else {
            System.out.println("Error: Post is not a poll.");
        }
    }

    private static void saveDiscussionBoard(Scanner scanner) {
        System.out.print("Enter filename to save (or append) to: ");
        String filename = "./boards/" + scanner.nextLine() + ".dboard";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            for (Post post : posts) {
                if (post instanceof TextPost textPost) {
                    writer.println("TEXTPOST;" + post.id + ";" + textPost.createdBy.username + ";" + textPost.title + ";" + textPost.content);
                } else if (post instanceof PollPost pollPost) {
                    writer.print("POLLPOST;" + post.id + ";" + pollPost.createdBy.username + ";" + pollPost.title + ";");
                    pollPost.options.forEach((option, count) -> writer.print(option + ":" + count + ";"));
                    writer.println();
                }
            }
            System.out.println("Discussion board saved (appended).");
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    private static void loadDiscussionBoard(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found. Starting with a blank discussion board.");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue; // Skip empty lines

                String[] data = line.split(";");
                if (data.length < 4) {
                    System.out.println("Error: Invalid line format - " + line);
                    continue; // Skip the line if it doesn't have enough data
                }

                String postType = data[0];
                int postId = Integer.parseInt(data[1]);
                String username = data[2];
                String title = data[3];

                User user = users.stream().filter(u -> u.username.equals(username)).findFirst().orElse(null);
                if (user == null) {
                    user = new User(username, username);
                    users.add(user);
                }

                if (postType.equals("TEXTPOST")) {
                    if (data.length < 5) {
                        System.out.println("Error: Text post missing content - " + line);
                        continue;
                    }
                    String content = data[4];
                    TextPost textPost = new TextPost(user, title, content);
                    textPost.id = postId;
                    posts.add(textPost);
                } else if (postType.equals("POLLPOST")) {
                    if (data.length < 5) {
                        System.out.println("Error: Poll post missing options - " + line);
                        continue;
                    }
                    String optionsString = data[4];
                    PollPost pollPost = new PollPost(user, title, optionsString);
                    pollPost.id = postId;
                    posts.add(pollPost);
                } else {
                    System.out.println("Error: Unknown post type - " + line);
                }
            }
            System.out.println("Discussion board loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
    }

    private static int getValidIntegerInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static int getValidIntegerInput(Scanner scanner, int min, int max) {
        int input;
        do {
            input = getValidIntegerInput(scanner);
            if (input < min || input > max) {
                System.out.printf("Invalid input. Please enter a number between %d and %d: ", min, max);
            }
        } while (input < min || input > max);
        return input;
    }
}
