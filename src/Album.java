public class Album {
    private String name;
    private String condition;
    private PhotoManager manager;
    private int nbComps;

    // Constructor
    public Album(String name, String condition, PhotoManager manager) {
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

    // Return the manager
    public PhotoManager getManager() {
        return manager;
    }

    // Return all photos that satisfy the album condition
    public LinkedList<Photo> getPhotos() {
        LinkedList<Photo> result = new LinkedList<>();
        LinkedList<Photo> allPhotos = manager.getPhotos();

        // Reset comparison counter
        nbComps = 0;

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

    // Return the number of tag comparisons used to find all photos of the album
    public int getNbComps() {
        return nbComps;
    }
}