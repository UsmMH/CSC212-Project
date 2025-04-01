public class TagData implements Comparable<TagData> {
    private String tag;
    private LinkedList<Photo> photos;

    public TagData(String tag) {
        this.tag = tag;
        this.photos = new LinkedList<>();
    }

    public String getTag() {
        return tag;
    }

    public LinkedList<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        photos.insert(photo);
    }

    public boolean removePhoto(Photo photo) {
        return photos.remove(photo);
    }

    public boolean isEmpty() {
        return photos.isEmpty();
    }

    @Override
    public int compareTo(TagData other) {
        return this.tag.compareTo(other.tag);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TagData tagData = (TagData) obj;
        return tag.equals(tagData.tag);
    }
}