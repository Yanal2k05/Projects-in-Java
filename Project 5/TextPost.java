class TextPost extends Post {
    private String content;

    public TextPost(User createdBy, String title, String content) {
        super(createdBy, title);
        if (content == null || content.isBlank()) throw new IllegalArgumentException("Content cannot be blank."); // checks if the content is null or empty
        this.content = content; // sets the content of the post
    }

    @Override
    public String getContent() { // returns the content of the post
        return content;
    }

    @Override
    public String display() {
        return super.display() + "\nContent: " + content; // calls the parent class's display method
    }
}
