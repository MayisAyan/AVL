import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static java.lang.StrictMath.max;

class Node{
    int data;
    Node left;
    Node right;
    Node(int key){
        data = key;
        left = null;
        right = null;
    }
}
class AVL{
    private Node root = null;
    AVL(int val){
        root = new Node(val);
    }

    public int getHeight(Node node){
        if(node == null){
            return 0;
        }
        return max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    public int getBalance(Node node){
        if(node == null){
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    public Node rightRotation(Node node){
        Node x = node.left;
        Node tmp = x.right;

        x.right = node;
        node.left = tmp;
        return x;
    }
    public Node leftRotation(Node node){
        Node x = node.right;
        Node tmp = x.left;

        x.left = node;
        node.right = tmp;
        return x;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public void insert(int data){
        if(isEmpty()){
            root = new Node(data);
        }
        root = insertHelper(root, data);
    }

    private Node insertHelper(Node node, int data){
        if(node == null){
            return new Node(data);
        }
        if(data < node.data){
            node.left = insertHelper(node.left, data);
        }
        else if(data > node.data){
            node.right = insertHelper(node.right, data);
        }
        else{
            return node;
        }
        int balance = getBalance(node);
        if(balance > 1 && data < node.left.data) {
            return rightRotation(node);
        }
        if(balance < -1 && data > node.right.data) {
            return leftRotation(node);
        }
        if(balance > 1 && data > node.left.data) {
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }
        if(balance < -1 && data < node.right.data) {
            node.right = rightRotation(node.right);
            return leftRotation(node);
        }
        return node;
    }

    public void remove(int data){
        if(root == null) {
            return;
        }
        root = removeHelper(root, data);
    }


    private Node removeHelper(Node node, int data) {
        if(node == null){
            return null;
        }
        if(node.data > data) {
            node.left = removeHelper(node.left, data);
        }
        else if(node.data < data) {
            node.right = removeHelper(node.right, data);
        }
        else {
            if(node.left == null) {
                return node.right;
            }
            else if(node.right == null) {
                return node.left;
            }
            else{
                int min = minNode(node.right);
                node.data = min;
                node.right = removeHelper(node.right, min);
            }
        }

        if(node == null) {
            return null;
        }

        int balance = getBalance(node);

        if(balance > 1 && getBalance(node.left) >= 0) {
            return rightRotation(node);
        }
        if(balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }
        if(balance < -1 && getBalance(node.right) < 0)
        {
            return leftRotation(node);
        }
        if(balance < -1 && getBalance(node.right) >= 0)
        {
            node.right = rightRotation(node.right);
            return leftRotation(node);
        }

        return node;
    }

    public Node find(int data){
        return findHelper(root, data);
    }

    public Node findIterative(int data){
        if(root == null || root.data == data){
            return root;
        }
        Node temp = root;
        while(temp != null){
            if(temp.data > data){
                if(temp.left != null) {
                    temp = temp.left;
                    if (temp.data == data) {
                        return temp;
                    }
                }
            }else{
                if(temp.right != null) {
                    temp = temp.right;
                    if (temp.data == data) {
                        return temp;
                    }
                }
            }
        }
        return temp;
    }


    public int minNode(Node node) {
        if (node.left != null)
            return minNode(node.left);
        else
            return node.data;
    }


    public void printInorderRecursive(Node node){
        if(node == null){
            return;
        }
        printInorderRecursive(node.left);
        System.out.print(node.data + " ");
        printInorderRecursive(node.right);
    }

    public void printPreorderRecursive(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        printPreorderRecursive(node.left);
        printPreorderRecursive(node.right);
    }

    public void printPostorderRecursive(Node node) {
        if (node == null){
            return;
        }
        printPostorderRecursive(node.left);
        printPostorderRecursive(node.right);
        System.out.print(node.data + " ");
    }
    public void printPostOrderIterative(Node node){
        if(root == null){
            return;
        }
        Stack<Node> st1 = new Stack<>();
        Stack<Node> st2 = new Stack<>();

        st1.push(node);
        while(!st1.isEmpty()){
            Node tmp = st1.pop();
            st2.push(tmp);
            if(tmp.left != null){
                st1.push(tmp.left);
            }
            if(tmp.right != null){
                st1.push(tmp.right);
            }
        }
        while(!st2.isEmpty()){
            Node tmp = st2.pop();
            System.out.print(tmp.data + " ");
        }
    }

    public void printPreOrderIterative(Node node){
        if(node == null){
            return;
        }
        Stack<Node> st = new Stack<>();
        st.push(root);
        while(!st.isEmpty()){
            Node tmp = st.peek();
            System.out.print(tmp.data + " ");
            st.pop();

            if(tmp.right != null){
                st.push(tmp.right);
            }
            if(tmp.left != null){
                st.push(tmp.left);
            }
        }
    }
    public void printInOrderIterative(){
        if(root == null){
            return;
        }
        Node tmp = root;
        Stack<Node> st = new Stack<>();
        while(tmp != null || st.size() > 0){
            while(tmp != null){
                st.push(tmp);
                tmp = tmp.left;
            }
            tmp = st.pop();
            System.out.print(tmp.data + " ");
            tmp = tmp.right;
        }
    }
    public void levelOrder(Node node){
        if(node == null){
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while(!queue.isEmpty()){
            Node tmp = queue.peek();
            queue.remove();
            System.out.println(tmp);
            if(tmp.left != null){
                queue.add(tmp.left);
            }
            if(tmp.right != null){
                queue.add(tmp.right);
            }
        }
    }


    private Node findHelper(Node node, int data){
        if(node == null || node.data == data){
            return node;
        }
        if(node.data > data){
            return findHelper(node.left, data);
        }else{
            return findHelper(node.right, data);
        }
    }

}