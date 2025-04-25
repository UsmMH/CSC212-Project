/**
 * A class that represents an item in the inverted index.
 * Each item contains a tag and a list of photos that have that tag.
 * @param <T> The type of the tag, must implement Comparable
 */
public class TaggedItem<T extends Comparable<T>> implements Comparable<TaggedItem<T>> {
    private T tag;
    private LinkedList<Photo> photos;
    
    /**
     * Constructs a new TaggedItem with the specified tag.
     * @param tag The tag
     */
    public TaggedItem(T tag) {
        this.tag = tag;
        this.photos = new LinkedList<>();
    }
    
    /**
     * Gets the tag.
     * @return The tag
     */
    public T getTag() {
        return tag;
    }
    
    /**
     * Gets the list of photos associated with this tag.
     * @return The list of photos
     */
    public LinkedList<Photo> getPhotos() {
        return photos;
    }
    
    /**
     * Adds a photo to the list of photos associated with this tag.
     * @param photo The photo to add
     */
    public void addPhoto(Photo photo) {
        photos.insert(photo);
    }
    
    /**
     * Removes a photo from the list of photos associated with this tag.
     * @param photo The photo to remove
     * @return true if the photo was found and removed, false otherwise
     */
    public boolean removePhoto(Photo photo) {
        return photos.remove(photo);
    }
    
    /**
     * Checks if the list of photos is empty.
     * @return true if the list of photos is empty, false otherwise
     */
    public boolean isEmpty() {
        return photos.isEmpty();
    }
    
    /**
     * Compares this TaggedItem with another TaggedItem.
     * @param other The TaggedItem to compare with
     * @return A negative integer, zero, or a positive integer as this TaggedItem
     *         is less than, equal to, or greater than the specified TaggedItem
     */
    @Override
    public int compareTo(TaggedItem<T> other) {
        return this.tag.compareTo(other.tag);
    }
    
    /**
     * Checks if this TaggedItem is equal to another object.
     * Two TaggedItems are considered equal if they have the same tag.
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        TaggedItem<?> that = (TaggedItem<?>) obj;
        return tag.equals(that.tag);
    }
    
    /**
     * Returns a string representation of the TaggedItem.
     * @return A string representation of the TaggedItem
     */
    @Override
    public String toString() {
        return "TaggedItem{tag=" + tag + ", photos=" + photos + "}";
    }
}
