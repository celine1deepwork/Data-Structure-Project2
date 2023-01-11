package com.company;

import java.util.ArrayList;

import static java.util.Objects.hash;

public class AVLTree<Item> {

    AVLNode<Item> root;
    int middle;
    AVLNode<Item> temp = root;
    ArrayList<Item> foundIds = new ArrayList();


    public AVLTree() {
        root = null;
    }

    String line;

    public int height(AVLNode<Item> d) { // d => null dönüyor height 1 node classta atanmasına rağmen ??? Ağaç oluşmuyor
        if (d == null) {
            return 0;
        } else {
            return d.height;
        }
    }

    //rotate focus.right; replace it with left child
    public AVLNode<Item> rotateMyLeft(AVLNode<Item> focus) {
        AVLNode<Item> k = focus.left;
        focus.left = k.right;
        k.right = focus;
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        k.height = Math.max(height(k.left), height(k.right)) + 1;
        return k;
    }

    //rotate focus.left; replace it with right child
    public AVLNode<Item> rotateMyRight(AVLNode<Item> focus) {
        AVLNode<Item> k = focus.right;
        focus.right = k.left;
        k.left = focus;
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        k.height = Math.max(height(k.left), height(k.right)) + 1;
        return k;
    }

    public AVLNode<Item> doubleRotateLeftSide(AVLNode focus) {
        focus.left = rotateMyRight(focus.left);
        return rotateMyLeft(focus);
    }

    public AVLNode<Item> doubleRotateRightSide(AVLNode focus) {
        focus.right = rotateMyLeft(focus.right);
        return rotateMyRight(focus);
    }

    // Get Balance factor of node focus
    int getBalance(AVLNode<Item> focus) {
        if (focus == null) {
            return 0;
        }
        return Math.abs(height(focus.left) - height(focus.right));
    }

    public AVLNode<Item> insert(AVLNode focus, Item data, int key) {
        if (focus == null) {
            focus = new AVLNode(data, key);

        } else if (key < focus.key) { // string kıyaslaması
            focus.left = insert(focus.left, data, key);
            if (getBalance(focus) == 2) { //rotattion
                if (key <= focus.left.key) {
                    focus = rotateMyLeft(focus);
                } else {
                    focus = doubleRotateLeftSide(focus);
                }
            }
        } else if (key > focus.key) {
            focus.right = insert(focus.right, data, key);
            if (getBalance(focus) == 2) {
                if (key > focus.right.key) {
                    focus = rotateMyRight(focus);
                } else {
                    focus = doubleRotateRightSide(focus);
                }
            }
        } else {
            // key is equal to focus.key, update data
            focus.data = data;
        }

        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        return focus;
    }

    public AVLNode searcById(int value) {
        AVLNode d;
        d = root;
        while (d != null) {
            if ((int)d.data == value) {
                System.out.println("Song found :)");
                return d;
            } else if ((int)d.data > value) {
                d = d.left;
            } else {
                d = d.right;
            }
        }
        return null;
    }

    public AVLNode searchString (String value) { // string değeri kıyaslamak için , Farklı yönle de yazılırdı Hash kullanmadan ama göremedik
        AVLNode d;
        d = root;
        while (d != null) {
            if (d.key == hash(value)) {
                System.out.println("Song found :)");
                return d;
            } else if (d.key > hash(value)) {
                d = d.left;
            } else {
                d = d.right;
            }
        }
        return null;
    }


    public void SearchInaRange(AVLNode node, int keyLower, int keyUpper) {
        if (node == null) {
            return;
        }
        if (keyLower < (int)node.data) {
            SearchInaRange(node.left, keyLower, keyUpper);
        }
        if (keyLower <= (int)node.data && keyUpper >= (int)node.data) {
            System.out.print(node.data + " ");
        }
        SearchInaRange(node.right, keyLower, keyUpper);
    }


    // _______________________ DELETE ____________________________
    AVLNode deleteNode(AVLNode focus, Item key) { // delete için de hem string hem int için ayrı ayrı method yazmamak için hash kullandık.
        // STEP 1: PERFORM STANDARD BST DELETE
        if (focus == null) {
            return focus;
        }
        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (hash(key) < focus.key) {
            focus.left = deleteNode(focus.left, key);
        } // If the key to be deleted is greater than the
        // root's key, then it lies in right subtree
        else if (hash(key) > focus.key) {
            focus.right = deleteNode(focus.right, key);
        } // if key is same as root's key, then this is the node
        // to be deleted
        else {

            // node with only one child or no child
            if ((focus.left == null) || (focus.right == null)) {
                AVLNode temp = null;
                if (null == focus.left) {
                    temp = focus.right;
                } else { // null == focus.right
                    temp = focus.left;
                }
                // No child case
                if (temp == null) {
                    temp = focus;
                    focus = null;
                } else // One child case
                {
                    focus = temp; // Copy the contents of
                }                                // the non-empty child
            } else {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                Item temp = null;
                temp.equals(minValueNode(focus.right).getData());

                // Copy the inorder successor's data to this node
                focus.key = hash(temp);

                // Delete the inorder successor
                focus.right = deleteNode(focus.right, temp);
            }
        }

        // If the tree had only one node then return
        if (focus == null) {
            return focus;
        }

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(focus);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(focus.left) >= 0) {
            return rotateMyLeft(focus);
        }

        // Left Right Case
        if (balance > 1 && getBalance(focus.left) < 0) {
            focus.left = rotateMyRight(focus.left);
            return rotateMyLeft(focus);
        }

        // Right Right Case
        if (balance < -1 && getBalance(focus.right) <= 0) {
            return rotateMyRight(focus);
        }

        // Right Left Case
        if (balance < -1 && getBalance(focus.right) > 0) {
            focus.right = rotateMyLeft(focus.right);
            return rotateMyRight(focus);
        }

        return focus;
    }


    // _______________________* DELETE *____________________________

    AVLNode deleteNodeID(AVLNode focus, int key) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (focus == null) {
            return focus;
        }

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key < (int)focus.data) {
            focus.left = deleteNodeID(focus.left, key);
        } // If the key to be deleted is greater than the
        // root's key, then it lies in right subtree
        else if (key > (int)focus.data) {
            focus.right = deleteNodeID(focus.right, key);
        } // if key is same as root's key, then this is the node
        // to be deleted
        else {

            // node with only one child or no child
            if ((focus.left == null) || (focus.right == null)) {
                AVLNode temp = null;
                if (null == focus.left) {
                    temp = focus.right;
                } else { // null == focus.right
                    temp = focus.left;
                }
                // No child case
                if (temp == null) {
                    temp = focus;
                    focus = null;
                } else // One child case
                {
                    focus = temp; // Copy the contents of
                }                                // the non-empty child
            } else {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                AVLNode temp = minValueNode(focus.right);

                // Copy the inorder successor's data to this node
                focus.key = temp.key;

                // Delete the inorder successor
                focus.right = deleteNodeID(focus.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (focus == null) {
            return focus;
        }

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(focus);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
    //    if (balance > 1 && getBalance(focus.left) >= 0) {
      //      return rotateMyLeft(focus);
       // }

        // Left Right Case
        if (balance > 1 && getBalance(focus.left) < 0) {
            focus.left = rotateMyRight(focus.left);
            return rotateMyLeft(focus);
        }

        // Right Right Case
        if (balance < -1 && getBalance(focus.right) <= 0) {
            return rotateMyRight(focus);
        }

        // Right Left Case
        if (balance < -1 && getBalance(focus.right) > 0) {
            focus.right = rotateMyLeft(focus.right);
            return rotateMyRight(focus);
        }

        return focus;
    }




    AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    public void traverseLevelOrder(AVLNode focus) {
        if (focus == null) {
            focus = root; // if null is passed. print whole tree
        }
        java.util.LinkedList<AVLNode> que = new java.util.LinkedList<AVLNode>();
        que.add(focus);
        while (!que.isEmpty()) {
            AVLNode d = que.removeFirst();
            if (d.left != null) {
                que.addLast(d.left);
            }
            if (d.right != null) {
                que.addLast(d.right);
            }
            System.out.println(d);
        }
    }
}