public class PhotoManager {
    private LinkedList<Photo> photos;

    // Constructor
    public PhotoManager() {
        photos = new LinkedList<>();
    }

    // Return all managed photos
    public LinkedList<Photo> getPhotos() {
        return photos;
    }

    // Add a photo
    public void addPhoto(Photo p) {
        photos.insert(p);
    }

    // Delete a photo
    public void deletePhoto(String path) {
        LinkedList<Photo> result = new LinkedList<>();

        for (int i = 0; i < photos.size(); i++) {
            Photo current = photos.get(i); // Use the get method we added
            if (!current.getPath().equals(path)) {
                result.insert(current);
            }
        }
        photos = result;
    }
}