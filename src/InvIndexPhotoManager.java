/**
 * A PhotoManager implementation that uses an inverted index to optimize photo retrieval.
 */
public class InvIndexPhotoManager extends PhotoManager {
    private BST<TaggedItem<String>> invertedIndex;
    private LinkedList<Photo> photos;
    
    /**
     * Constructs a new InvIndexPhotoManager with an empty photo collection.
     */
    public InvIndexPhotoManager() {
        super();
        invertedIndex = new BST<>();
        photos = new LinkedList<>();
    }
    
    /**
     * Adds a photo to the manager and updates the inverted index.
     * @param p The photo to add
     */
    @Override
    public void addPhoto(Photo p) {
        // Add to regular photo list
        super.addPhoto(p);
        photos.insert(p);
        
        // Update inverted index
        LinkedList<String> tags = p.getTags();
        for (int i = 0; i < tags.size(); i++) {
            String tag = tags.get(i);
            
            // Create a TaggedItem for searching
            TaggedItem<String> searchItem = new TaggedItem<>(tag);
            
            // Check if tag exists in the index
            if (invertedIndex.contains(searchItem)) {
                // Get the existing TaggedItem
                TaggedItem<String> existingItem = invertedIndex.get(searchItem);
                // Add photo to the existing tag's photo list
                existingItem.addPhoto(p);
            } else {
                // Create a new TaggedItem
                TaggedItem<String> newItem = new TaggedItem<>(tag);
                // Add photo to the new tag's photo list
                newItem.addPhoto(p);
                // Add the new TaggedItem to the inverted index
                invertedIndex.insert(newItem);
            }
        }
    }
    
    /**
     * Deletes a photo with the specified path from the manager and updates the inverted index.
     * @param path The path of the photo to delete
     */
    @Override
    public void deletePhoto(String path) {
        // Find the photo to delete
        Photo photoToDelete = null;
        for (int i = 0; i < photos.size(); i++) {
            Photo photo = photos.get(i);
            if (photo.getPath().equals(path)) {
                photoToDelete = photo;
                photos.remove(i);
                break;
            }
        }
        
        // Call the parent method to remove from the regular list
        super.deletePhoto(path);
        
        if (photoToDelete != null) {
            // Update inverted index
            LinkedList<String> tags = photoToDelete.getTags();
            for (int i = 0; i < tags.size(); i++) {
                String tag = tags.get(i);
                
                // Create a TaggedItem for searching
                TaggedItem<String> searchItem = new TaggedItem<>(tag);
                
                // Get the existing TaggedItem
                TaggedItem<String> existingItem = invertedIndex.get(searchItem);
                
                if (existingItem != null) {
                    // Remove photo from the tag's photo list
                    existingItem.removePhoto(photoToDelete);
                    
                    // If the tag has no more photos, remove it from the index
                    if (existingItem.isEmpty()) {
                        invertedIndex.delete(existingItem);
                    }
                }
            }
        }
    }
    
    /**
     * Gets the inverted index of all managed photos.
     * This method is renamed to avoid conflict with the parent class method.
     * @return The inverted index as a BST of TaggedItem<String>
     */
    public BST<TaggedItem<String>> getInvertedIndex() {
        return invertedIndex;
    }
    
    /**
     * Gets all photos that have the specified tag.
     * @param tag The tag to search for
     * @return A list of photos that have the specified tag
     */
    public LinkedList<Photo> getPhotosByTag(String tag) {
        TaggedItem<String> searchItem = new TaggedItem<>(tag);
        TaggedItem<String> foundItem = invertedIndex.get(searchItem);
        
        if (foundItem != null) {
            return foundItem.getPhotos();
        } else {
            return new LinkedList<>();
        }
    }
    
    /**
     * Returns a string representation of the InvIndexPhotoManager.
     * @return A string representation of the InvIndexPhotoManager
     */
    @Override
    public String toString() {
        return "InvIndexPhotoManager with " + photos.size() + " photos and " + 
               invertedIndex.size() + " tags in the inverted index";
    }
}
