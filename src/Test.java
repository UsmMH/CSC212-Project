public class Test {
    // Tag categories as constants for better readability
    private static final String[] ANIMALS = {"cat", "dog", "hedgehog", "bear", "fox", "deer", "rabbit"};
    private static final String[] INSECTS = {"butterfly", "bee", "ladybug", "ant"};
    private static final String[] OBJECTS = {"apple", "flower", "tree", "cab", "rock", "bench"};
    private static final String[] NATURE = {"grass", "wind", "sky", "river", "mountain", "forest"};
    private static final String[] COLORS = {"green", "red", "blue", "orange", "yellow", "purple"};
    private static final String[] WEATHER = {"sunny", "cloudy", "rainy", "snowy"};
    private static final String[] TIMES = {"morning", "noon", "evening", "night"};
    private static final String[] LOCATIONS = {"park", "beach", "city", "village"};
    private static final String[] ADJECTIVES = {"fluffy", "tiny", "bright", "calm", "wild"};
    private static final String[][] TAG_CATEGORIES = {ANIMALS, INSECTS, OBJECTS, NATURE, COLORS, WEATHER, TIMES, LOCATIONS, ADJECTIVES};

    // Album-relevant tag sets (each array represents tags needed for a specific album condition)
    private static final String[][] ALBUM_TAG_SETS = {
            {"bear"},                           // Album1: bear
            {"animal", "grass"},               // Album2: animal AND grass
            {"animal", "grass", "insect"},     // Album3: animal AND grass AND insect
            {"sunny"},                         // Album4: sunny
            {"fluffy", "forest", "blue"},      // Album5: fluffy AND forest AND blue
            {"insect", "morning"}              // Album6: insect AND morning
    };

    // Probability of generating a photo that matches an album condition
    private static final double MATCH_ALBUM_PROBABILITY = 0.8;

    // Toggle to show photo tags in output (set to false to avoid clutter)
    private static final boolean SHOW_PHOTO_TAGS = false;

    public static void main(String[] args) {
        // --- Basic PhotoManager Test ---
        PhotoManager manager = new PhotoManager();
        addTestPhotos(manager);

        // Create albums for PhotoManager
        Album[] albums = {
                new Album("Album1", "bear", manager),
                new Album("Album2", "animal AND grass", manager),
                new Album("Album3", "animal AND grass AND insect", manager),
                new Album("Album4", "sunny", manager),
                new Album("Album5", "fluffy AND forest AND blue", manager),
                new Album("Album6", "insect AND morning", manager)
        };

        System.out.println("===== Basic PhotoManager Test =====");
        System.out.println("Total photos in manager: " + manager.getPhotos().size());
        System.out.println("Sample photo path: " + manager.getPhotos().get(0).getPath());
        System.out.println("Sample album name: " + albums[1].getName());
        System.out.println("Sample album condition: " + albums[1].getCondition());
        System.out.println();
        printAllAlbums(albums, "Basic");

        // --- Inverted Index Test ---
        InvIndexPhotoManager indexManager = new InvIndexPhotoManager();
        addTestPhotos(indexManager);

        // Create albums for InvIndexPhotoManager
        Album[] optAlbums = {
                new Album("Album1", "bear", indexManager),
                new Album("Album2", "animal AND grass", indexManager),
                new Album("Album3", "animal AND grass AND insect", indexManager),
                new Album("Album4", "sunny", indexManager),
                new Album("Album5", "fluffy AND forest AND blue", indexManager),
                new Album("Album6", "insect AND morning", indexManager)
        };

        System.out.println("\n===== Inverted Index Test =====");
        System.out.println("Total photos in manager: " + indexManager.getPhotos().size());
        System.out.println();
        printAllAlbums(optAlbums, "Optimized");

        // --- After Deletion Test ---
        System.out.println("\n===== After Deletion Test =====");
        System.out.println("Deleting photo: bear.jpg");
        indexManager.deletePhoto("bear.jpg");
        System.out.println("Total photos after deletion: " + indexManager.getPhotos().size());
        System.out.println();

        // Skip optAlbum3 as in the original code
        Album[] optAlbumsAfterDeletion = {optAlbums[0], optAlbums[1], optAlbums[3], optAlbums[4], optAlbums[5]};
        printAllAlbums(optAlbumsAfterDeletion, "Optimized After Deletion");
    }

    // Helper to add original and generated photos to a PhotoManager
    private static void addTestPhotos(PhotoManager manager) {
        // Add original photos
        manager.addPhoto(new Photo("hedgehog.jpg", toTagsLinkedList("animal, hedgehog, apple, grass, green")));
        manager.addPhoto(new Photo("bear.jpg", toTagsLinkedList("animal, bear, cab, grass, wind, insect")));
        manager.addPhoto(new Photo("orange-butterfly.jpg", toTagsLinkedList("insect, butterfly, flower, color")));

        // Generate additional test photos
        generateTestPhotos(manager, 47); // Add 47 more photos for a total of 50
    }

    // Helper to add original and generated photos to an InvIndexPhotoManager
    private static void addTestPhotos(InvIndexPhotoManager manager) {
        // Add original photos
        manager.addPhoto(new Photo("hedgehog.jpg", toTagsLinkedList("animal, hedgehog, apple, grass, green")));
        manager.addPhoto(new Photo("bear.jpg", toTagsLinkedList("animal, bear, cab, grass, wind, insect")));
        manager.addPhoto(new Photo("orange-butterfly.jpg", toTagsLinkedList("insect, butterfly, flower, color")));

        // Generate additional test photos
        generateTestPhotos(manager, 47); // Add 47 more photos for a total of 50
    }

    // Helper to print all albums' photos and comparisons
    private static void printAllAlbums(Album[] albums, String testType) {
        for (Album album : albums) {
            LinkedList<Photo> photos = album.getPhotos();
            System.out.println("--- " + testType + " Album: " + album.getName() + " ---");
            System.out.println("Condition: " + album.getCondition());
            System.out.println("Matching photos: " + photos.size());
            System.out.println("Comparisons: " + album.getNbComps());
            System.out.println("Photos:");
            printPhotoList(photos);
            System.out.println();
        }
        System.out.println("----------------------------------------");
    }

    private static LinkedList<String> toTagsLinkedList(String tags) {
        LinkedList<String> result = new LinkedList<>();
        String[] tagsArray = tags.split("\\s*,\\s*");
        for (int i = 0; i < tagsArray.length; i++) {
            result.insert(tagsArray[i]);
        }
        return result;
    }

    // Prints the list of photos for an album (currently disabled)
    private static void printPhotoList(LinkedList<Photo> photos) {
        if (photos.isEmpty()) {
            System.out.println("  [None]");
            return;
        }

        // Commented out: printing of photo paths and tags
        /*
        for (int i = 0; i < photos.size(); i++) {
            Photo p = photos.get(i);
            System.out.print("  - " + p.getPath());
            if (SHOW_PHOTO_TAGS) {
                System.out.print(" (Tags: ");
                LinkedList<String> tags = p.getTags();
                for (int j = 0; j < tags.size(); j++) {
                    System.out.print(tags.get(j));
                    if (j < tags.size() - 1) System.out.print(", ");
                }
                System.out.print(")");
            }
            System.out.println();
        }
        */
    }

    // Helper method to generate test photos for PhotoManager
    private static void generateTestPhotos(PhotoManager manager, int numPhotos) {
        for (int i = 1; i <= numPhotos; i++) {
            String filename = generateFilename(i);
            String tags = generateTagsWithAlbumBias();
            Photo photo = new Photo(filename, toTagsLinkedList(tags));
            manager.addPhoto(photo);
        }
    }

    // Helper method to generate test photos for InvIndexPhotoManager
    private static void generateTestPhotos(InvIndexPhotoManager manager, int numPhotos) {
        for (int i = 1; i <= numPhotos; i++) {
            String filename = generateFilename(i);
            String tags = generateTagsWithAlbumBias();
            Photo photo = new Photo(filename, toTagsLinkedList(tags));
            manager.addPhoto(photo);
        }
    }

    // Generate a unique filename
    private static String generateFilename(int index) {
        String[] types = {ANIMALS[(int)(Math.random() * ANIMALS.length)],
                INSECTS[(int)(Math.random() * INSECTS.length)]};
        String type = types[(int)(Math.random() * types.length)];
        return type + "-" + index + ".jpg";
    }

    // Generate tags with a bias toward matching album conditions
    private static String generateTagsWithAlbumBias() {
        StringBuilder tags = new StringBuilder();
        int numTags = 3 + (int)(Math.random() * 4); // 3 to 6 tags

        // 80% chance to generate tags that match an album condition
        if (Math.random() < MATCH_ALBUM_PROBABILITY) {
            // Pick a random album condition to match
            String[] albumTags = ALBUM_TAG_SETS[(int)(Math.random() * ALBUM_TAG_SETS.length)];

            // Add all tags required for the album condition
            for (String tag : albumTags) {
                tags.append(tag);
                tags.append(", ");
            }

            // Add additional random tags to reach the desired number
            int remainingTags = numTags - albumTags.length;
            for (int i = 0; i < remainingTags; i++) {
                String[] category = TAG_CATEGORIES[(int)(Math.random() * TAG_CATEGORIES.length)];
                String tag = category[(int)(Math.random() * category.length)];
                tags.append(tag);
                if (i < remainingTags - 1 || albumTags.length > 0) tags.append(", ");
            }
        } else {
            // Generate completely random tags (as before)
            for (int i = 0; i < numTags; i++) {
                String[] category = TAG_CATEGORIES[(int)(Math.random() * TAG_CATEGORIES.length)];
                String tag = category[(int)(Math.random() * category.length)];
                tags.append(tag);
                if (i < numTags - 1) tags.append(", ");
            }
        }

        return tags.toString();
    }
}