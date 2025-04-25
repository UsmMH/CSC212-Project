/**
 * A helper class for implementing a hash table to optimize set operations.
 * This is used to improve the performance of AND and OR operations in the Album class.
 */
public class PhotoSet {
    private static final int INITIAL_CAPACITY = 101; // A prime number for better hash distribution
    private Photo[] table;
    private int size;
    
    /**
     * Constructs a new PhotoSet with the default initial capacity.
     */
    public PhotoSet() {
        table = new Photo[INITIAL_CAPACITY];
        size = 0;
    }
    
    /**
     * Adds a photo to the set if it's not already present.
     * @param photo The photo to add
     * @return true if the photo was added, false if it was already in the set
     */
    public boolean add(Photo photo) {
        if (contains(photo)) {
            return false;
        }
        
        int index = getIndex(photo);
        
        // Linear probing to find an empty slot
        while (table[index] != null) {
            index = (index + 1) % table.length;
        }
        
        table[index] = photo;
        size++;
        
        // Resize if load factor exceeds 0.75
        if (size > table.length * 0.75) {
            resize();
        }
        
        return true;
    }
    
    /**
     * Checks if the set contains the specified photo.
     * @param photo The photo to check for
     * @return true if the set contains the photo, false otherwise
     */
    public boolean contains(Photo photo) {
        int index = getIndex(photo);
        
        // Linear probing to find the photo
        while (table[index] != null) {
            if (table[index].equals(photo)) {
                return true;
            }
            index = (index + 1) % table.length;
        }
        
        return false;
    }
    
    /**
     * Gets the number of photos in the set.
     * @return The number of photos
     */
    public int size() {
        return size;
    }
    
    /**
     * Converts the set to a LinkedList.
     * @return A LinkedList containing all photos in the set
     */
    public LinkedList<Photo> toLinkedList() {
        LinkedList<Photo> result = new LinkedList<>();
        
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                result.insert(table[i]);
            }
        }
        
        return result;
    }
    
    /**
     * Gets the index in the hash table for the specified photo.
     * @param photo The photo to get the index for
     * @return The index
     */
    private int getIndex(Photo photo) {
        // Use the photo's path hashcode for hashing
        int hashCode = photo.getPath().hashCode();
        return Math.abs(hashCode) % table.length;
    }
    
    /**
     * Resizes the hash table when it gets too full.
     */
    private void resize() {
        Photo[] oldTable = table;
        table = new Photo[oldTable.length * 2];
        size = 0;
        
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                add(oldTable[i]);
            }
        }
    }
}
