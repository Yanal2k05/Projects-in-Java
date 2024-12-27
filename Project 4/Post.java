import java.util.*;

class Post {
    static int idCounter = 1;
    int id;
    String title;
    User createdBy;

    public Post(User createdBy, String title) {
        this.id = idCounter++;
        this.title = title;
        this.createdBy = createdBy;
    }

    public String display() {
        return "Post #" + id + "\nCreated By: " + createdBy.fullName + " (@" + createdBy.username + ")\nTitle: " + title;
    }
}

class TextPost extends Post {
    String content;

    public TextPost(User createdBy, String title, String content) {
        super(createdBy, title);
        this.content = content;
    }

    @Override
    public String display() {
        return super.display() + "\n" + content;
    }
}

class PollPost extends Post {
    Map<String, Integer> options = new LinkedHashMap<>();

    public PollPost(User createdBy, String title, String optionsString) {
        super(createdBy, title);
        String[] optionsArray = optionsString.split(";");
        for (String option : optionsArray) {
            String trimmedOption = option.trim();
            if (!trimmedOption.isEmpty()) {
                options.put(trimmedOption, 0);
            }
        }
    }

    public void vote(int optionIndex) {
        int count = 0;
        for (String option : options.keySet()) {
            if (count == optionIndex) {
                options.put(option, options.get(option) + 1);
                return;
            }
            count++;
        }
    }

    @Override
    public String display() {
        StringBuilder result = new StringBuilder(super.display() + "\n");
        for (Map.Entry<String, Integer> entry : options.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return result.toString();
    }
}
