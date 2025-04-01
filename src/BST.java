public class BST<T extends Comparable<T>> {
    private BSTNode<T> root;

    private static class BSTNode<T> {
        T data;
        BSTNode<T> left;
        BSTNode<T> right;

        BSTNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    // Constructor
    public BST() {
        root = null;
    }

    // Insert a value into the BST
    public void insert(T data) {
        root = insertRec(root, data);
    }

    private BSTNode<T> insertRec(BSTNode<T> root, T data) {
        if (root == null) {
            root = new BSTNode<>(data);
            return root;
        }

        int comparison = data.compareTo(root.data);
        if (comparison < 0) {
            root.left = insertRec(root.left, data);
        } else if (comparison > 0) {
            root.right = insertRec(root.right, data);
        }

        return root;
    }

    // Search for a value in the BST
    public T search(T data) {
        return searchRec(root, data);
    }

    private T searchRec(BSTNode<T> root, T data) {
        if (root == null || root.data.equals(data)) {
            return root != null ? root.data : null;
        }

        int comparison = data.compareTo(root.data);
        if (comparison < 0) {
            return searchRec(root.left, data);
        } else {
            return searchRec(root.right, data);
        }
    }

    // Delete a value from the BST
    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private BSTNode<T> deleteRec(BSTNode<T> root, T data) {
        if (root == null) {
            return null;
        }

        int comparison = data.compareTo(root.data);
        if (comparison < 0) {
            root.left = deleteRec(root.left, data);
        } else if (comparison > 0) {
            root.right = deleteRec(root.right, data);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children
            root.data = minValue(root.right);

            // Delete the successor
            root.right = deleteRec(root.right, root.data);
        }

        return root;
    }

    private T minValue(BSTNode<T> root) {
        T minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }

    // Get all values in the BST (inorder traversal)
    public LinkedList<T> getAll() {
        LinkedList<T> result = new LinkedList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(BSTNode<T> node, LinkedList<T> list) {
        if (node != null) {
            inorderTraversal(node.left, list);
            list.insert(node.data);
            inorderTraversal(node.right, list);
        }
    }
}
