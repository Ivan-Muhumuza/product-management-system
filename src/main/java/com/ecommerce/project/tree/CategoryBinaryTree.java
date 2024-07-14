package com.ecommerce.project.tree;

import com.ecommerce.project.entity.Category;

public class CategoryBinaryTree {

    // Node class representing each node in the binary tree
    private class Node {
        Category category; // Category stored in the node
        Node left, right; // Left and right child nodes

        Node(Category category) {
            this.category = category; // Initialize node with a category
            left = right = null; // Initialize child nodes as null
        }
    }

    private Node root; // Root node of the binary tree

    // Insert a new category into the binary tree
    public void insert(Category category) {
        root = insertRec(root, category); // Start the recursive insertion
    }

    // Recursive method to insert a category
    private Node insertRec(Node root, Category category) {
        // If the current node is null, create a new node
        if (root == null) {
            root = new Node(category);
            return root;
        }
        // Compare category IDs to determine the position
        if (category.getId() < root.category.getId()) {
            root.left = insertRec(root.left, category); // Insert in the left subtree
        } else if (category.getId() > root.category.getId()) {
            root.right = insertRec(root.right, category); // Insert in the right subtree
        }
        return root; // Return the unchanged node pointer
    }

    // Search for a category by its ID
    public Category search(Long id) {
        return searchRec(root, id); // Start the recursive search
    }

    // Recursive method to search for a category
    private Category searchRec(Node root, Long id) {
        // Base cases: root is null or category ID matches
        if (root == null || root.category.getId().equals(id)) {
            return root != null ? root.category : null; // Return found category or null
        }
        // Recur down the tree based on the category ID comparison
        return id < root.category.getId() ? searchRec(root.left, id) : searchRec(root.right, id);
    }

    // Delete a category by its ID
    public void delete(Long id) {
        root = deleteRec(root, id); // Start the recursive deletion
    }

    // Recursive method to delete a category
    private Node deleteRec(Node root, Long id) {
        // Base case: if the root is null, return null
        if (root == null) return root;

        // Navigate to the node to be deleted
        if (id < root.category.getId()) {
            root.left = deleteRec(root.left, id); // Go left
        } else if (id > root.category.getId()) {
            root.right = deleteRec(root.right, id); // Go right
        } else {
            // Node with only one child or no child
            if (root.left == null) return root.right; // Replace with right child
            else if (root.right == null) return root.left; // Replace with left child

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            root.category = minValue(root.right); // Replace category with inorder successor
            root.right = deleteRec(root.right, root.category.getId()); // Delete the inorder successor
        }
        return root; // Return the unchanged node pointer
    }

    // Find the node with the minimum value in a subtree
    private Category minValue(Node root) {
        Category minCategory = root.category; // Start with the root category
        while (root.left != null) { // Navigate to the leftmost node
            minCategory = root.left.category; // Update the minimum category
            root = root.left; // Move to the left child
        }
        return minCategory; // Return the minimum category found
    }
}
