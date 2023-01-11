package com.company;

    public class AVLNode<Item> {
        public int key;
        public Item data;
        public AVLNode left;
        public AVLNode right;
        public int height = 1;

        public AVLNode(Item data, int key){
            this.data= data;
            this.key = key;
            left = null;
            right = null;
            height = 1;
        }

        public Item getData() {
            return data;
        }

        public int getKey() {
            return key;
        }

        public AVLNode getLeft() {
            return left;
        }

        public AVLNode getRight() {
            return right;
        }

        @Override
        public String toString() {
            return ("Node(k= " + key + ", name= " + data.toString() + ")");
        }

    }




