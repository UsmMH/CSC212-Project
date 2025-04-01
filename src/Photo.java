public class Photo {
    private String path;
    private LinkedList<String> tags;

    // Constructor
    public Photo(String path, LinkedList<String> tags) {
        this.path = path;
        this.tags = tags;
    }

    // Return the full file name (the path) of the photo
    public String getPath() {
        return path;
    }

    // Return all tags associated with the photo
    public LinkedList<String> getTags() {
        return tags;
    }
}