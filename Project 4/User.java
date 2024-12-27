import java.io.*;
import java.util.*;

class User {
    String fullName;
    String username;

    public User(String fullName, String username) {
        this.fullName = fullName;
        this.username = (username == null || username.isBlank()) ? fullName.split(" ")[0].toLowerCase() : username.toLowerCase();
    }
}
