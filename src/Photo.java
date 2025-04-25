/**
 * Represents a photo with a path and associated tags.
 */
public class Photo {
    private String path;
    private LinkedList<String> tags;
    
    /**
     * Constructs a new Photo with the specified path and tags.
     * @param path The file path of the photo
     * @param tags The list of tags associated with the photo
     */
    public Photo(String path, LinkedList<String> tags) {
        this.path = path;
        this.tags = tags;
    }
    
    /**
     * Gets the file path of the photo.
     * @return The file path
     */
    public String getPath() {
        return path;
    }
    
    /**
     * Gets the list of tags associated with the photo.
     * @return The list of tags
     */
    public LinkedList<String> getTags() {
        return tags;
    }
    
    /**
     * Checks if this photo is equal to another object.
     * Two photos are considered equal if they have the same path.
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Photo photo = (Photo) obj;
        return path.equals(photo.path);
    }
    
    /**
     * Returns a string representation of the photo.
     * @return A string representation of the photo
     */
    @Override
    public String toString() {
        return "Photo{path='" + path + "'}";
    }
}
