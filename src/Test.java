public class Test {
    public static void main(String[] args) {
        // Test basic functionality
        PhotoManager manager = new PhotoManager();

        Photo photo1 = new Photo("hedgehog.jpg", toTagsLinkedList("animal, hedgehog, apple, grass, green"));
        manager.addPhoto(photo1);

        Photo photo2 = new Photo("bear.jpg", toTagsLinkedList("animal, bear, cab, grass, wind"));
        manager.addPhoto(photo2);

        Photo photo3 = new Photo("orange-butterfly.jpg", toTagsLinkedList("insect, butterfly, flower, color"));
        manager.addPhoto(photo3);

        Album album1 = new Album("Album1", "bear", manager);
        Album album2 = new Album("Album2", "animal AND grass", manager);

        System.out.println("===== Basic PhotoManager Test =====");
        System.out.println("Photo1 path: " + photo1.getPath());
        System.out.println("Album2 name: " + album2.getName());
        System.out.println("Album2 condition: " + album2.getCondition());

        System.out.println("Photos in Album1:");
        printPhotoList(album1.getPhotos());
        System.out.println("Comparisons: " + album1.getNbComps());

        System.out.println("Photos in Album2:");
        printPhotoList(album2.getPhotos());
        System.out.println("Comparisons: " + album2.getNbComps());

        // Test with inverted index
        System.out.println("\n===== Inverted Index Test =====");
        InvIndexPhotoManager indexManager = new InvIndexPhotoManager();

        Photo p1 = new Photo("hedgehog.jpg", toTagsLinkedList("animal, hedgehog, apple, grass, green"));
        indexManager.addPhoto(p1);

        Photo p2 = new Photo("bear.jpg", toTagsLinkedList("animal, bear, cab, grass, wind"));
        indexManager.addPhoto(p2);

        Photo p3 = new Photo("orange-butterfly.jpg", toTagsLinkedList("insect, butterfly, flower, color"));
        indexManager.addPhoto(p3);

        OptimizedAlbum opt1 = new OptimizedAlbum("Album1", "bear", indexManager);
        OptimizedAlbum opt2 = new OptimizedAlbum("Album2", "animal AND grass", indexManager);

        System.out.println("Photos in Optimized Album1:");
        printPhotoList(opt1.getPhotos());
        System.out.println("Comparisons: " + opt1.getNbComps());

        System.out.println("Photos in Optimized Album2:");
        printPhotoList(opt2.getPhotos());
        System.out.println("Comparisons: " + opt2.getNbComps());

        // Delete a photo and test again
        System.out.println("\n===== After Deletion Test =====");
        indexManager.deletePhoto("bear.jpg");

        System.out.println("Photos in Optimized Album1 after deletion:");
        printPhotoList(opt1.getPhotos());
        System.out.println("Comparisons: " + opt1.getNbComps());

        System.out.println("Photos in Optimized Album2 after deletion:");
        printPhotoList(opt2.getPhotos());
        System.out.println("Comparisons: " + opt2.getNbComps());
    }

    private static LinkedList<String> toTagsLinkedList(String tags) {
        LinkedList<String> result = new LinkedList<>();
        String[] tagsArray = tags.split("\\s*,\\s*");
        for (int i = 0; i < tagsArray.length; i++) {
            result.insert(tagsArray[i]);
        }
        return result;
    }

    private static void printPhotoList(LinkedList<Photo> photos) {
        if (photos.isEmpty()) {
            System.out.println("  No photos");
            return;
        }

        for (int i = 0; i < photos.size(); i++) {
            Photo p = photos.get(i);
            System.out.println("  " + p.getPath());
        }
    }
}