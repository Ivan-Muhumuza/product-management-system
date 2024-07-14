package com.ecommerce.project.tree;

import com.ecommerce.project.entity.Product;

public class BinaryTree {

    // Node class representing each node in the binary tree
    private class Node {
        Product product; // Product stored in the node
        Node left, right; // Left and right child nodes

        Node(Product product) {
            this.product = product; // Initialize node with a product
            left = right = null; // Initialize child nodes as null
        }
    }

    private Node root; // Root node of the binary tree

    // Insert a new product into the binary tree
    public void insert(Product product) {
        root = insertRec(root, product); // Start the recursive insertion
    }

    // Recursive method to insert a product
    private Node insertRec(Node root, Product product) {
        // If the current node is null, create a new node
        if (root == null) {
            root = new Node(product);
            return root;
        }
        // Compare product IDs to determine the position
        if (product.getId() < root.product.getId()) {
            root.left = insertRec(root.left, product); // Insert in the left subtree
        } else if (product.getId() > root.product.getId()) {
            root.right = insertRec(root.right, product); // Insert in the right subtree
        }
        return root; // Return the unchanged node pointer
    }

    // Search for a product by its ID
    public Product search(Long id) {
        return searchRec(root, id); // Start the recursive search
    }

    // Recursive method to search for a product
    private Product searchRec(Node root, Long id) {
        // Base cases: root is null or product ID matches
        if (root == null || root.product.getId().equals(id)) {
            return root != null ? root.product : null; // Return found product or null
        }
        // Recur down the tree based on the product ID comparison
        return id < root.product.getId() ? searchRec(root.left, id) : searchRec(root.right, id);
    }

    // Delete a product by its ID
    public void delete(Long id) {
        root = deleteRec(root, id); // Start the recursive deletion
    }

    // Recursive method to delete a product
    private Node deleteRec(Node root, Long id) {
        // Base case: if the root is null, return null
        if (root == null) return root;

        // Navigate to the node to be deleted
        if (id < root.product.getId()) {
            root.left = deleteRec(root.left, id); // Go left
        } else if (id > root.product.getId()) {
            root.right = deleteRec(root.right, id); // Go right
        } else {
            // Node with only one child or no child
            if (root.left == null) return root.right; // Replace with right child
            else if (root.right == null) return root.left; // Replace with left child

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            root.product = minValue(root.right); // Replace product with inorder successor
            root.right = deleteRec(root.right, root.product.getId()); // Delete the inorder successor
        }
        return root; // Return the unchanged node pointer
    }

    // Find the node with the minimum value in a subtree
    private Product minValue(Node root) {
        Product minProduct = root.product; // Start with the root product
        while (root.left != null) { // Navigate to the leftmost node
            minProduct = root.left.product; // Update the minimum product
            root = root.left; // Move to the left child
        }
        return minProduct; // Return the minimum product found
    }
}

