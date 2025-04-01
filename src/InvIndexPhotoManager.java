public class InvIndexPhotoManager {
    private LinkedList<Photo> photos;
    private BST<TagData> invertedIndex;

    // Constructor
    public InvIndexPhotoManager() {
        photos = new LinkedList<>();
        invertedIndex = new BST<>();
    }

    // Return all managed photos
    public LinkedList<Photo> getPhotos() {
        return photos;
    }

    // Return the inverted index of all managed photos
    public BST<TagData> getInvertedIndex() {
        return invertedIndex;
    }

    // Add a photo
    public void addPhoto(Photo p) {
        // Add to photos list
        photos.insert(p);

        // Update inverted index
        LinkedList<String> tags = p.getTags();
        for (int i = 0; i < tags.size(); i++) {
            String tag = tags.get(i);

            // Create a temporary TagData for searching
            TagData searchKey = new TagData(tag);
            TagData existingTagData = invertedIndex.search(searchKey);

            if (existingTagData == null) {
                // Tag doesn't exist yet, create a new entry
                TagData newTagData = new TagData(tag);
                newTagData.addPhoto(p);
                invertedIndex.insert(newTagData);
            } else {
                // Tag exists, add photo to its list
                existingTagData.addPhoto(p);
            }
        }
    }

    // Delete a photo
    public void deletePhoto(String path) {
        Photo toRemove = null;

        // Find the photo to remove
        for (int i = 0; i < photos.size(); i++) {
            Photo current = photos.get(i);
            if (current.getPath().equals(path)) {
                toRemove = current;
                break;
            }
        }

        if (toRemove != null) {
            // Remove from photos list
            photos.remove(toRemove);

            // Update inverted index
            LinkedList<String> tags = toRemove.getTags();
            for (int i = 0; i < tags.size(); i++) {
                String tag = tags.get(i);

                // Create a temporary TagData for searching
                TagData searchKey = new TagData(tag);
                TagData existingTagData = invertedIndex.search(searchKey);

                if (existingTagData != null) {
                    // Remove photo from tag's list
                    existingTagData.removePhoto(toRemove);

                    // If tag has no photos, remove it from the index
                    if (existingTagData.isEmpty()) {
                        invertedIndex.delete(existingTagData);
                    }
                }
            }
        }
    }
}