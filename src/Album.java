public class Album {
    private String name;
    private String condition;
    private Object manager; // Can be either PhotoManager or InvIndexPhotoManager
    private int nbComps;

    // Constructor for PhotoManager
    public Album(String name, String condition, PhotoManager manager) {
        this.name = name;
        this.condition = condition;
        this.manager = manager;
        this.nbComps = 0;
    }

    // Constructor for InvIndexPhotoManager
    public Album(String name, String condition, InvIndexPhotoManager manager) {
        this.name = name;
        this.condition = condition;
        this.manager = manager;
        this.nbComps = 0;
    }

    // Return the name of the album
    public String getName() {
        return name;
    }

    // Return the condition associated with the album
    public String getCondition() {
        return condition;
    }

    // Return the manager (you would need to cast this when using it)
    public Object getManager() {
        return manager;
    }

    // Return all photos that satisfy the album condition
    public LinkedList<Photo> getPhotos() {
        // Reset comparison counter
        nbComps = 0;

        if (manager instanceof PhotoManager) {
            return getPhotosStandard((PhotoManager) manager);
        } else if (manager instanceof InvIndexPhotoManager) {
            return getPhotosOptimized((InvIndexPhotoManager) manager);
        } else {
            throw new IllegalStateException("Unknown manager type");
        }
    }

    private LinkedList<Photo> getPhotosStandard(PhotoManager manager) {
        LinkedList<Photo> result = new LinkedList<>();
        LinkedList<Photo> allPhotos = manager.getPhotos();

        // Parse condition
        if (condition.trim().isEmpty()) {
            // Empty condition means all photos
            return allPhotos;
        }

        String[] conditions;
        if (condition.contains(" AND ")) {
            conditions = condition.split(" AND ");
        } else {
            conditions = new String[] { condition };
        }

        // Check each photo against conditions
        for (int i = 0; i < allPhotos.size(); i++) {
            Photo photo = allPhotos.get(i);
            boolean matches = true;

            for (String tag : conditions) {
                boolean hasTag = false;
                LinkedList<String> photoTags = photo.getTags();

                for (int j = 0; j < photoTags.size(); j++) {
                    nbComps++; // Count each tag comparison
                    if (photoTags.get(j).equals(tag)) {
                        hasTag = true;
                        break;
                    }
                }

                if (!hasTag) {
                    matches = false;
                    break;
                }
            }

            if (matches) {
                result.insert(photo);
            }
        }

        return result;
    }

    private LinkedList<Photo> getPhotosOptimized(InvIndexPhotoManager manager) {
        if (condition.trim().isEmpty()) {
            // Empty condition means all photos
            return manager.getPhotos();
        }

        // Parse condition
        String[] conditions;
        if (condition.contains(" AND ")) {
            conditions = condition.split(" AND ");
        } else {
            conditions = new String[] { condition };
        }

        // Strategy: Get photos for first tag, then filter by remaining tags
        LinkedList<Photo> result = null;

        for (String tag : conditions) {
            TagData tagData = new TagData(tag);
            TagData foundTagData = manager.getInvertedIndex().search(tagData);

            if (foundTagData == null) {
                // If any tag doesn't exist, there can't be any matching photos
                return new LinkedList<>();
            }

            LinkedList<Photo> photosWithTag = foundTagData.getPhotos();

            if (result == null) {
                // First condition
                result = copyLinkedList(photosWithTag);
            } else {
                // Filter existing result
                LinkedList<Photo> filteredResult = new LinkedList<>();

                for (int i = 0; i < result.size(); i++) {
                    Photo photo = result.get(i);
                    boolean found = false;

                    for (int j = 0; j < photosWithTag.size(); j++) {
                        Photo photoWithTag = photosWithTag.get(j);
                        nbComps++; // Count comparison

                        if (photo.getPath().equals(photoWithTag.getPath())) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        filteredResult.insert(photo);
                    }
                }

                result = filteredResult;
            }

            // If no photos match at this point, we can exit early
            if (result.isEmpty()) {
                break;
            }
        }

        return result != null ? result : new LinkedList<>();
    }

    // Return the number of tag comparisons used to find all photos of the album
    public int getNbComps() {
        return nbComps;
    }

    // Helper methods
    private LinkedList<Photo> copyLinkedList(LinkedList<Photo> original) {
        LinkedList<Photo> copy = new LinkedList<>();
        for (int i = 0; i < original.size(); i++) {
            copy.insert(original.get(i));
        }
        return copy;
    }
}