import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Custom implementation of a LinkedList data structure.
 * @param <T> The type of elements stored in the list
 */
public class LinkedList<T> implements Iterable<T> {
    private class Node {
        T data;
        Node next;
        
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node head;
    private int size;
    
    /**
     * Constructs an empty LinkedList.
     */
    public LinkedList() {
        head = null;
        size = 0;
    }
    
    /**
     * Inserts an element at the end of the list.
     * @param element The element to insert
     */
    public void insert(T element) {
        Node newNode = new Node(element);
        
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }
    
    /**
     * Gets the element at the specified index.
     * @param index The index of the element to get
     * @return The element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
    
    /**
     * Removes the element at the specified index.
     * @param index The index of the element to remove
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (index == 0) {
            head = head.next;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }
    
    /**
     * Removes the first occurrence of the specified element from the list.
     * @param element The element to remove
     * @return true if the element was found and removed, false otherwise
     */
    public boolean remove(T element) {
        if (head == null) {
            return false;
        }
        
        if (head.data.equals(element)) {
            head = head.next;
            size--;
            return true;
        }
        
        Node current = head;
        while (current.next != null && !current.next.data.equals(element)) {
            current = current.next;
        }
        
        if (current.next != null) {
            current.next = current.next.next;
            size--;
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks if the list contains the specified element.
     * @param element The element to check for
     * @return true if the list contains the element, false otherwise
     */
    public boolean contains(T element) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    /**
     * Gets the size of the list.
     * @return The number of elements in the list
     */
    public int size() {
        return size;
    }
    
    /**
     * Checks if the list is empty.
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Returns an iterator over the elements in the list.
     * @return An iterator over the elements in the list
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }
    
    private class LinkedListIterator implements Iterator<T> {
        private Node current = head;
        
        @Override
        public boolean hasNext() {
            return current != null;
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
    
    /**
     * Returns a string representation of the list.
     * @return A string representation of the list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
