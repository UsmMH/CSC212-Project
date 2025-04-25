/**
 * Binary Search Tree implementation.
 * @param <T> The type of elements stored in the tree, must implement Comparable
 */
public class BST<T extends Comparable<T>> {
    private class Node {
        T data;
        Node left;
        Node right;
        
        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    
    private Node root;
    private int size;
    
    /**
     * Constructs an empty BST.
     */
    public BST() {
        root = null;
        size = 0;
    }
    
    /**
     * Inserts an element into the BST.
     * @param element The element to insert
     */
    public void insert(T element) {
        root = insertRec(root, element);
        size++;
    }
    
    private Node insertRec(Node root, T element) {
        if (root == null) {
            return new Node(element);
        }
        
        int compareResult = element.compareTo(root.data);
        
        if (compareResult < 0) {
            root.left = insertRec(root.left, element);
        } else if (compareResult > 0) {
            root.right = insertRec(root.right, element);
        }
        
        return root;
    }
    
    /**
     * Checks if the BST contains the specified element.
     * @param element The element to check for
     * @return true if the BST contains the element, false otherwise
     */
    public boolean contains(T element) {
        return containsRec(root, element);
    }
    
    private boolean containsRec(Node root, T element) {
        if (root == null) {
            return false;
        }
        
        int compareResult = element.compareTo(root.data);
        
        if (compareResult == 0) {
            return true;
        } else if (compareResult < 0) {
            return containsRec(root.left, element);
        } else {
            return containsRec(root.right, element);
        }
    }
    
    /**
     * Gets the element from the BST that is equal to the specified element.
     * @param element The element to get
     * @return The element if found, null otherwise
     */
    public T get(T element) {
        return getRec(root, element);
    }
    
    private T getRec(Node root, T element) {
        if (root == null) {
            return null;
        }
        
        int compareResult = element.compareTo(root.data);
        
        if (compareResult == 0) {
            return root.data;
        } else if (compareResult < 0) {
            return getRec(root.left, element);
        } else {
            return getRec(root.right, element);
        }
    }
    
    /**
     * Deletes the specified element from the BST.
     * @param element The element to delete
     */
    public void delete(T element) {
        root = deleteRec(root, element);
        size--;
    }
    
    private Node deleteRec(Node root, T element) {
        if (root == null) {
            return null;
        }
        
        int compareResult = element.compareTo(root.data);
        
        if (compareResult < 0) {
            root.left = deleteRec(root.left, element);
        } else if (compareResult > 0) {
            root.right = deleteRec(root.right, element);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            
            // Node with two children
            root.data = minValue(root.right);
            root.right = deleteRec(root.right, root.data);
        }
        
        return root;
    }
    
    private T minValue(Node root) {
        T minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }
    
    /**
     * Gets the size of the BST.
     * @return The number of elements in the BST
     */
    public int size() {
        return size;
    }
    
    /**
     * Checks if the BST is empty.
     * @return true if the BST is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Performs an in-order traversal of the BST and returns the elements in a LinkedList.
     * @return A LinkedList containing the elements in in-order traversal order
     */
    public LinkedList<T> inOrderTraversal() {
        LinkedList<T> result = new LinkedList<>();
        inOrderTraversalRec(root, result);
        return result;
    }
    
    private void inOrderTraversalRec(Node root, LinkedList<T> result) {
        if (root != null) {
            inOrderTraversalRec(root.left, result);
            result.insert(root.data);
            inOrderTraversalRec(root.right, result);
        }
    }
    
    /**
     * Returns a string representation of the BST.
     * @return A string representation of the BST
     */
    @Override
    public String toString() {
        LinkedList<T> elements = inOrderTraversal();
        return elements.toString();
    }
}
