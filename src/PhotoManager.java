/**
 * Manages a collection of photos.
 */
public class PhotoManager {
    private LinkedList<Photo> photos;
    
    /**
     * Constructs a new PhotoManager with an empty photo collection.
     */
    public PhotoManager() {
        photos = new LinkedList<>();
    }
    
    /**
     * Adds a photo to the manager.
     * @param p The photo to add
     */
    public void addPhoto(Photo p) {
        photos.insert(p);
    }
    
    /**
     * Deletes a photo with the specified path from the manager.
     * @param path The path of the photo to delete
     */
    public void deletePhoto(String path) {
        for (int i = 0; i < photos.size(); i++) {
            Photo photo = photos.get(i);
            if (photo.getPath().equals(path)) {
                photos.remove(i);
                break;
            }
        }
    }
    
    /**
     * Gets all photos managed by this PhotoManager.
     * @return A list of all photos
     */
    public LinkedList<Photo> getPhotos() {
        return photos;
    }
    
    /**
     * Finds a photo with the specified path.
     * @param path The path of the photo to find
     * @return The photo if found, null otherwise
     */
    public Photo findPhoto(String path) {
        for (int i = 0; i < photos.size(); i++) {
            Photo photo = photos.get(i);
            if (photo.getPath().equals(path)) {
                return photo;
            }
        }
        return null;
    }
    
    /**
     * Returns the number of photos managed by this PhotoManager.
     * @return The number of photos
     */
    public int getPhotoCount() {
        return photos.size();
    }
    
    /**
     * Checks if a photo with the specified path exists in the manager.
     * @param path The path to check
     * @return true if a photo with the specified path exists, false otherwise
     */
    public boolean containsPhoto(String path) {
        return findPhoto(path) != null;
    }
    
    /**
     * Returns a string representation of the PhotoManager.
     * @return A string representation of the PhotoManager
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PhotoManager with " + photos.size() + " photos:\n");
        for (int i = 0; i < photos.size(); i++) {
            sb.append("- ").append(photos.get(i)).append("\n");
        }
        return sb.toString();
    }
}
