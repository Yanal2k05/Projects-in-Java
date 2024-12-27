abstract class Post {
    static int idCounter = 1; // Static counter for post IDs
    int id;                   // Unique ID for each post
    String title;             // Title of the post
    User createdBy;           // User who created the post

    public Post(User createdBy, String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be blank.");
        this.id = idCounter++; // Assign unique ID and increment counter
        this.title = title;
        this.createdBy = createdBy;
    }

    public int getId() { // Getter for ID 
        return id; 
    }

    public User getUser() { // Getter for username
        return createdBy;
    }

    public String getTitle() { // Getter for title
        return title;
    }

    // Abstract method for getting content to be implemented by subclasses
    public abstract String getContent();

    public String display() {
        return "Post #" + id + "\nCreated By: " + createdBy.fullName + " (@" + createdBy.username + ")\nTitle: " + title;
    }
}
