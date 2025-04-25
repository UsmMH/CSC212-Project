/**
 * Represents an album of photos that match a specific condition.
 */
public class Album {
    private String name;
    private String condition;
    private PhotoManager manager;
    private int nbComps;
    
    /**
     * Constructs a new Album with the specified name, condition, and photo manager.
     * @param name The name of the album
     * @param condition The condition that photos must satisfy to be included in the album
     * @param manager The photo manager that provides the photos
     */
    public Album(String name, String condition, PhotoManager manager) {
        this.name = name;
        this.condition = condition;
        this.manager = manager;
        this.nbComps = 0;
    }
    
    /**
     * Gets the name of the album.
     * @return The name of the album
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the condition that photos must satisfy to be included in the album.
     * @return The condition
     */
    public String getCondition() {
        return condition;
    }
    
    /**
     * Gets all photos that satisfy the album's condition.
     * @return A list of photos that satisfy the condition
     */
    public LinkedList<Photo> getPhotos() {
        // Reset comparison counter
        nbComps = 0;
        
        // Check if we can use the optimized inverted index approach
        if (manager instanceof InvIndexPhotoManager) {
            return getPhotosUsingInvertedIndex((InvIndexPhotoManager) manager);
        } else {
            return getPhotosUsingStandardApproach();
        }
    }
    
    /**
     * Gets photos using the standard approach (iterating through all photos).
     * @return A list of photos that satisfy the condition
     */
    private LinkedList<Photo> getPhotosUsingStandardApproach() {
        LinkedList<Photo> result = new LinkedList<>();
        LinkedList<Photo> allPhotos = manager.getPhotos();
        
        // Parse condition
        if (condition.contains(" AND ")) {
            String[] tags = condition.split(" AND ");
            String tag1 = tags[0].trim();
            String tag2 = tags[1].trim();
            
            // Find photos that contain both tags
            for (int i = 0; i < allPhotos.size(); i++) {
                Photo photo = allPhotos.get(i);
                LinkedList<String> photoTags = photo.getTags();
                
                boolean hasTag1 = false;
                boolean hasTag2 = false;
                
                for (int j = 0; j < photoTags.size(); j++) {
                    String tag = photoTags.get(j);
                    nbComps++;
                    
                    if (tag.equals(tag1)) {
                        hasTag1 = true;
                    }
                    
                    if (tag.equals(tag2)) {
                        hasTag2 = true;
                    }
                    
                    if (hasTag1 && hasTag2) {
                        break;
                    }
                }
                
                if (hasTag1 && hasTag2) {
                    result.insert(photo);
                }
            }
        } else if (condition.contains(" OR ")) {
            String[] tags = condition.split(" OR ");
            String tag1 = tags[0].trim();
            String tag2 = tags[1].trim();
            
            // Find photos that contain either tag
            for (int i = 0; i < allPhotos.size(); i++) {
                Photo photo = allPhotos.get(i);
                LinkedList<String> photoTags = photo.getTags();
                
                boolean hasTag = false;
                
                for (int j = 0; j < photoTags.size(); j++) {
                    String tag = photoTags.get(j);
                    nbComps++;
                    
                    if (tag.equals(tag1) || tag.equals(tag2)) {
                        hasTag = true;
                        break;
                    }
                }
                
                if (hasTag) {
                    result.insert(photo);
                }
            }
        } else {
            // Simple tag condition
            String tag = condition.trim();
            
            // Find photos that contain the tag
            for (int i = 0; i < allPhotos.size(); i++) {
                Photo photo = allPhotos.get(i);
                LinkedList<String> photoTags = photo.getTags();
                
                for (int j = 0; j < photoTags.size(); j++) {
                    nbComps++;
                    
                    if (photoTags.get(j).equals(tag)) {
                        result.insert(photo);
                        break;
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Gets photos using the optimized inverted index approach.
     * @param invManager The InvIndexPhotoManager to use
     * @return A list of photos that satisfy the condition
     */
    private LinkedList<Photo> getPhotosUsingInvertedIndex(InvIndexPhotoManager invManager) {
        LinkedList<Photo> result = new LinkedList<>();
        
        // Parse condition
        if (condition.contains(" AND ")) {
            String[] tags = condition.split(" AND ");
            String tag1 = tags[0].trim();
            String tag2 = tags[1].trim();
            
            // Get photos for each tag using the inverted index
            LinkedList<Photo> photos1 = invManager.getPhotosByTag(tag1);
            // Count one comparison for the BST lookup
            nbComps++;
            
            LinkedList<Photo> photos2 = invManager.getPhotosByTag(tag2);
            // Count one more comparison for the second BST lookup
            nbComps++;
            
            // Use PhotoSet for efficient intersection
            // Create a set from the smaller list for faster lookups
            PhotoSet photoSet = new PhotoSet();
            
            // Determine which list is smaller to minimize iterations
            LinkedList<Photo> smallerList = photos1.size() <= photos2.size() ? photos1 : photos2;
            LinkedList<Photo> largerList = photos1.size() > photos2.size() ? photos1 : photos2;
            
            // Add all photos from the smaller list to the set
            for (int i = 0; i < smallerList.size(); i++) {
                photoSet.add(smallerList.get(i));
            }
            
            // For each photo in the larger list, check if it exists in the set
            for (int i = 0; i < largerList.size(); i++) {
                Photo photo = largerList.get(i);
                // Count one comparison for each lookup in the set
                nbComps++;
                
                if (photoSet.contains(photo)) {
                    result.insert(photo);
                }
            }
        } else if (condition.contains(" OR ")) {
            String[] tags = condition.split(" OR ");
            String tag1 = tags[0].trim();
            String tag2 = tags[1].trim();
            
            // Get photos for each tag using the inverted index
            LinkedList<Photo> photos1 = invManager.getPhotosByTag(tag1);
            // Count one comparison for the BST lookup
            nbComps++;
            
            LinkedList<Photo> photos2 = invManager.getPhotosByTag(tag2);
            // Count one more comparison for the second BST lookup
            nbComps++;
            
            // Use PhotoSet for efficient union
            PhotoSet photoSet = new PhotoSet();
            
            // Add all photos from both lists to the set (duplicates will be automatically handled)
            for (int i = 0; i < photos1.size(); i++) {
                photoSet.add(photos1.get(i));
            }
            
            for (int i = 0; i < photos2.size(); i++) {
                // Count one comparison for each add operation
                nbComps++;
                photoSet.add(photos2.get(i));
            }
            
            // Convert the set back to a LinkedList
            result = photoSet.toLinkedList();
        } else {
            // Simple tag condition
            String tag = condition.trim();
            
            // Get photos directly using the inverted index
            result = invManager.getPhotosByTag(tag);
            // Count one comparison for the BST lookup
            nbComps++;
        }
        
        return result;
    }
    
    /**
     * Gets the number of tag comparisons made during the last call to getPhotos().
     * @return The number of tag comparisons
     */
    public int getNbComps() {
        return nbComps;
    }
    
    /**
     * Returns a string representation of the album.
     * @return A string representation of the album
     */
    @Override
    public String toString() {
        return "Album{name='" + name + "', condition='" + condition + "'}";
    }
}
