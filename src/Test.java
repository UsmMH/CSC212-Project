/**
 * Test class for the Photo Management System.
 * This class demonstrates the usage of the implemented classes and tests their functionality.
 */
public class Test {
    public static void main(String[] args) {
        // Test basic functionality
        testBasicFunctionality();

        // Test inverted index functionality
        testInvertedIndexFunctionality();

        // Compare performance
        comparePerformance();
    }

    /**
     * Tests the basic functionality of the Photo Management System.
     */
    private static void testBasicFunctionality() {
        System.out.println("=============================================");
        System.out.println("  BASIC FUNCTIONALITY TEST (Standard Implementation)");
        System.out.println("=============================================");

        // Create a photo manager
        PhotoManager manager = new PhotoManager();

        // Create and add photos
        Photo photo1 = new Photo("hedgehog.jpg", toTagsLinkedList("animal, hedgehog, apple, grass, green"));
        manager.addPhoto(photo1);

        Photo photo2 = new Photo("bear.jpg", toTagsLinkedList("animal, bear, cab, grass, wind"));
        manager.addPhoto(photo2);

        Photo photo3 = new Photo("orange-butterfly.jpg", toTagsLinkedList("insect, butterfly, flower, color"));
        manager.addPhoto(photo3);

        // Create albums
        Album album1 = new Album("Album1", "bear", manager);
        Album album2 = new Album("Album2", "animal AND grass", manager);

        // Test photo retrieval
        System.out.println("\nPhoto Details:");
        System.out.println("  Path: " + photo1.getPath());
        System.out.println("  Tags: " + tagsToString(photo1.getTags()));

        System.out.println("\nAlbum Details:");
        System.out.println("  Name: " + album2.getName());
        System.out.println("  Condition: " + album2.getCondition());
        System.out.println("  Photos: " + photosToString(album2.getPhotos()));
        System.out.println("  Tag Comparisons: " + album2.getNbComps());

        // Test photo deletion
        System.out.println("\nDeleting photo 'bear.jpg'");
        manager.deletePhoto("bear.jpg");
        System.out.println("  Album1 photos after deletion: " + photosToString(album1.getPhotos()));
        System.out.println("  Album2 photos after deletion: " + photosToString(album2.getPhotos()));

        System.out.println();
    }

    /**
     * Tests the inverted index functionality of the Photo Management System.
     */
    private static void testInvertedIndexFunctionality() {
        System.out.println("=============================================");
        System.out.println("  INVERTED INDEX FUNCTIONALITY TEST");
        System.out.println("=============================================");

        // Create an inverted index photo manager
        InvIndexPhotoManager manager = new InvIndexPhotoManager();

        // Create and add photos
        Photo photo1 = new Photo("hedgehog.jpg", toTagsLinkedList("animal, hedgehog, apple, grass, green"));
        manager.addPhoto(photo1);

        Photo photo2 = new Photo("bear.jpg", toTagsLinkedList("animal, bear, cab, grass, wind"));
        manager.addPhoto(photo2);

        Photo photo3 = new Photo("orange-butterfly.jpg", toTagsLinkedList("insect, butterfly, flower, color"));
        manager.addPhoto(photo3);

        // Create albums
        Album album1 = new Album("Album1", "bear", manager);
        Album album2 = new Album("Album2", "animal AND grass", manager);

        // Test photo retrieval
        System.out.println("\nAlbum Retrieval:");
        System.out.println("  Album1 (condition: 'bear'):");
        System.out.println("    Photos: " + photosToString(album1.getPhotos()));
        System.out.println("    Tag Comparisons: " + album1.getNbComps());

        System.out.println("  Album2 (condition: 'animal AND grass'):");
        System.out.println("    Photos: " + photosToString(album2.getPhotos()));
        System.out.println("    Tag Comparisons: " + album2.getNbComps());

        // Test direct tag-based retrieval
        System.out.println("\nDirect Tag Retrieval:");
        System.out.println("  Photos with tag 'animal': " + photosToString(manager.getPhotosByTag("animal")));

        // Test photo deletion
        System.out.println("\nDeleting photo 'bear.jpg'");
        manager.deletePhoto("bear.jpg");
        System.out.println("  Album1 photos after deletion: " + photosToString(album1.getPhotos()));
        System.out.println("  Album2 photos after deletion: " + photosToString(album2.getPhotos()));
        System.out.println("  Photos with tag 'animal' after deletion: " + photosToString(manager.getPhotosByTag("animal")));

        System.out.println();
    }

    /**
     * Compares the performance of the regular PhotoManager and the InvIndexPhotoManager.
     */
    private static void comparePerformance() {
        System.out.println("=============================================");
        System.out.println("  PERFORMANCE COMPARISON");
        System.out.println("=============================================");

        // Create a regular photo manager
        PhotoManager regularManager = new PhotoManager();

        // Create an inverted index photo manager
        InvIndexPhotoManager invIndexManager = new InvIndexPhotoManager();

        // Create a larger set of photos
        int numPhotos = 100;
        String[] tags = {"animal", "nature", "landscape", "portrait", "city", "beach", "mountain", "forest", "water", "sky"};

        System.out.println("\nGenerating test data with " + numPhotos + " photos...");

        for (int i = 0; i < numPhotos; i++) {
            // Create a random set of tags for each photo
            LinkedList<String> photoTags = new LinkedList<>();
            int numTags = 3 + (int)(Math.random() * 4); // 3 to 6 tags per photo

            for (int j = 0; j < numTags; j++) {
                int tagIndex = (int)(Math.random() * tags.length);
                String tag = tags[tagIndex];
                if (!photoTags.contains(tag)) {
                    photoTags.insert(tag);
                }
            }

            // Create and add the photo to both managers
            Photo photo = new Photo("photo" + i + ".jpg", photoTags);
            regularManager.addPhoto(photo);
            invIndexManager.addPhoto(photo);
        }

        // Test simple tag condition
        String simpleCondition = "animal";
        Album regularAlbum1 = new Album("RegularAlbum1", simpleCondition, regularManager);
        Album invIndexAlbum1 = new Album("InvIndexAlbum1", simpleCondition, invIndexManager);

        // Measure performance for simple condition
        LinkedList<Photo> regularResults1 = regularAlbum1.getPhotos();
        int regularComps1 = regularAlbum1.getNbComps();

        LinkedList<Photo> invIndexResults1 = invIndexAlbum1.getPhotos();
        int invIndexComps1 = invIndexAlbum1.getNbComps();

        // Test AND condition
        String andCondition = "animal AND water";
        Album regularAlbum2 = new Album("RegularAlbum2", andCondition, regularManager);
        Album invIndexAlbum2 = new Album("InvIndexAlbum2", andCondition, invIndexManager);

        // Measure performance for AND condition
        LinkedList<Photo> regularResults2 = regularAlbum2.getPhotos();
        int regularComps2 = regularAlbum2.getNbComps();

        LinkedList<Photo> invIndexResults2 = invIndexAlbum2.getPhotos();
        int invIndexComps2 = invIndexAlbum2.getNbComps();

        // Print results
        System.out.println("\nSimple Condition Test: '" + simpleCondition + "'");
        System.out.println("  Standard Implementation:");
        System.out.println("    Photos Found: " + regularResults1.size());
        System.out.println("    Tag Comparisons: " + regularComps1);

        System.out.println("  Inverted Index Implementation:");
        System.out.println("    Photos Found: " + invIndexResults1.size());
        System.out.println("    Tag Comparisons: " + invIndexComps1);
        System.out.println("    Improvement: " + calculateImprovement(regularComps1, invIndexComps1) + "% fewer comparisons");

        System.out.println("\nAND Condition Test: '" + andCondition + "'");
        System.out.println("  Standard Implementation:");
        System.out.println("    Photos Found: " + regularResults2.size());
        System.out.println("    Tag Comparisons: " + regularComps2);

        System.out.println("  Inverted Index Implementation:");
        System.out.println("    Photos Found: " + invIndexResults2.size());
        System.out.println("    Tag Comparisons: " + invIndexComps2);
        System.out.println("    Improvement: " + calculateImprovement(regularComps2, invIndexComps2) + "% fewer comparisons");
    }

    /**
     * Calculates the percentage improvement between two values.
     * @param original The original value
     * @param improved The improved value
     * @return The percentage improvement
     */
    private static double calculateImprovement(int original, int improved) {
        return Math.round(((double)(original - improved) / original) * 100.0 * 10.0) / 10.0;
    }

    /**
     * Converts a comma-separated string of tags to a LinkedList of tags.
     * @param tags The comma-separated string of tags
     * @return A LinkedList containing the tags
     */
    private static LinkedList<String> toTagsLinkedList(String tags) {
        LinkedList<String> result = new LinkedList<>();
        String[] tagsArray = tags.split("\\s*,\\s*");
        for (int i = 0; i < tagsArray.length; i++) {
            result.insert(tagsArray[i]);
        }
        return result;
    }

    /**
     * Converts a LinkedList of tags to a string representation.
     * @param tags The LinkedList of tags
     * @return A string representation of the tags
     */
    private static String tagsToString(LinkedList<String> tags) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tags.size(); i++) {
            sb.append(tags.get(i));
            if (i < tags.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Converts a LinkedList of photos to a string representation.
     * @param photos The LinkedList of photos
     * @return A string representation of the photos
     */
    private static String photosToString(LinkedList<Photo> photos) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < photos.size(); i++) {
            sb.append(photos.get(i).getPath());
            if (i < photos.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
