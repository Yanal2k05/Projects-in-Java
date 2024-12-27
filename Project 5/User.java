import java.io.*;
import java.util.*;

class User {
    String fullName; // User's full name
    String username; // User's username

    public User(String fullName, String username) {
        // Validate full name
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be blank.");
        }
        this.fullName = fullName;

        // Generate default username if not provided
        this.username = (username == null || username.isBlank()) ? fullName.split(" ")[0].toLowerCase() : username.toLowerCase();
    }
}
