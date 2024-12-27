import java.util.Scanner;

public class DiscussionBoard {
    private static final int MAX_POSTS = 10; // Maximum number of posts allowed
    private static final String[] posts = new String[MAX_POSTS]; // Array to store posts
    private static int pCount = 0; // Counter for the number of posts

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true; // Control flag for running the program
            
            // Main loop for the menu
            while (running) {
                displayMenu(); // Show options to the user
                int option = scanner.nextInt(); // Get user's choice
                scanner.nextLine(); // Consume newline character
                
                // Option 9 ends the program
                if (option == 9) {
                    System.out.println("Program ended.");
                    running = false;
                } else {
                    handleOption(scanner, option); // Handle the chosen option
                }
            }
        }
    }

    // Display the menu options
    private static void displayMenu() {
        System.out.println("(1) Post new message");
        System.out.println("(2) Print all posts");
        System.out.println("(3) Print all posts in reverse order");
        System.out.println("(4) Print number of posts entered so far");
        System.out.println("(5) Print all posts from a user");
        System.out.println("(6) Print the number of vowels across all posts");
        System.out.println("(7) Perform a search of posts containing a given word (case sensitive)");
        System.out.println("(8) Perform a search of posts containing a given word (case insensitive)");
        System.out.println("(9) End Program");
        System.out.print("Choose option: "); 
    }

    // Handle the option chosen by the user
    private static void handleOption(Scanner scanner, int option) {
        switch (option) {
            case 1 -> postNewMessage(scanner); // Add a new post
            case 2 -> printAllPosts(false); // Print all posts in normal order
            case 3 -> printAllPostsInReverseOrder(); // Print posts in reverse order
            case 4 -> printNumberOfPostsEnteredSoFar(); // Print the number of posts
            case 5 -> printAllPostsFromUser(scanner); // Print all posts from a specific user
            case 6 -> printNumberOfVowels(); // Count and print the total number of vowels
            case 7 -> performCaseSensitiveWordSearch(scanner); // Case-sensitive word search
            case 8 -> performCaseInsensitiveWordSearch(scanner); // Case-insensitive word search
            default -> System.out.println("Invalid option."); // Handle invalid options
        }
    }

    // Post a new message to the board
    private static void postNewMessage(Scanner scanner) {
        if (pCount >= MAX_POSTS) { // Check if the board is full
            System.out.println("Error. Board is full.");
            return;
        }
        System.out.print("Enter name: ");
        String name = scanner.nextLine().toLowerCase();  // Convert name to lowercase
        System.out.print("Enter post: ");
        String message = scanner.nextLine(); // Get the post message
        posts[pCount] = formatPost(name, message); // Format and store the post
        pCount++; // Increment the post count
        System.out.println("Post added.");
    }

    // Format the post in the form "name says: message"
    private static String formatPost(String name, String message) {
        return name + " says: " + message;
    }

    // Print all posts, normal or in reverse order based on the 'reverse' flag
    private static void printAllPosts(boolean reverse) {
        if (pCount == 0) { // Check if there are no posts
            System.out.println("No posts.");
            return;
        }

        if (reverse) { // Print in reverse order
            for (int i = pCount - 1; i >= 0; i--) {
                System.out.println(posts[i]);
            }
        } else { // Print in normal order
            for (int i = 0; i < pCount; i++) {
                System.out.println(posts[i]);
            }
        }
    }

    // Print posts in reverse order
    private static void printAllPostsInReverseOrder() {
        printAllPosts(true);
    }

    // Print the number of posts entered so far
    private static void printNumberOfPostsEnteredSoFar() {
        System.out.println("Total posts: " + pCount);
    }

    // Print all posts from a specific user
    private static void printAllPostsFromUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String user = scanner.nextLine().toLowerCase(); // Convert username to lowercase
        for (String post : posts) {
            if (post != null && post.toLowerCase().startsWith(user + " says:")) {
                System.out.println(post.split(" says: ", 2)[1]); // Print the message part of the post
            }
        }
    }

    // Print the number of vowels in all posts
    private static void printNumberOfVowels() {
        int vowelCount = 0;
        for (String post : posts) {
            if (post != null) {
                vowelCount += countVowelsInPost(post); // Count vowels in each post
            }
        }
        System.out.println("Total vowels in post content: " + vowelCount);
    }

    // Count the number of vowels in a given post
    private static int countVowelsInPost(String post) {
        String messageContent = post.split(" says: ", 2)[1].toLowerCase(); // Get the message part
        int count = 0;
        for (char c : messageContent.toCharArray()) {
            if ("aeiou".indexOf(c) != -1) { // Check if the character is a vowel
                count++;
            }
        }
        return count;
    }

    // Perform a case-sensitive word search in posts
    private static void performCaseSensitiveWordSearch(Scanner scanner) {
        System.out.print("Enter word to search: ");
        String word = scanner.nextLine();
        boolean found = false;
        
        for (String post : posts) {
            if (post != null) {
                String messageContent = post.split(" says: ", 2)[1]; // Get the message part
                if (searchWordInMessage(messageContent, word, false)) { // Case-sensitive search
                    System.out.println(post);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No posts.");
        }
    }

    // Perform a case-insensitive word search in posts
    private static void performCaseInsensitiveWordSearch(Scanner scanner) {
        System.out.print("Enter word to search: ");
        String word = scanner.nextLine();
        boolean found = false;

        for (String post : posts) {
            if (post != null) {
                String messageContent = post.split(" says: ", 2)[1]; // Get the message part
                if (searchWordInMessage(messageContent, word, true)) { // Case-insensitive search
                    System.out.println(post);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No posts.");
        }
    }

    // Search for a word in the message content, with case sensitivity controlled by a flag
    private static boolean searchWordInMessage(String message, String word, boolean caseInsensitive) {
        if (caseInsensitive) {
            return message.toLowerCase().contains(word.toLowerCase()); // Case-insensitive search
        } else {
            return message.contains(word); // Case-sensitive search
        }
    }
}
