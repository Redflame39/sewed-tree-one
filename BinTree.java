package com.company;

class BinTree {
    private Node root;
    private Node head;
    private final boolean symThreadingDone;


    public BinTree(){
        root = null;
        symThreadingDone = false;
        head = null;
    }


    public void insert(int data){
        if (symThreadingDone){
            System.out.println("Эта версия вставки более не поддерживается");
        } else {
            Node newNode = new Node(data);

            if (root == null) {
                root = newNode;
            } else {
                Node curr = root, prev;

                while (true) {
                    prev = curr;
                    if (data < curr.data) {
                        curr = curr.left;
                        if (curr == null) {
                            prev.left = newNode;
                            return;
                        }
                    } else {
                        curr = curr.right;
                        if (curr == null) {
                            prev.right = newNode;
                            return;
                        }
                    }
                }
            }
        }
    }


    public void deleteFromSymmetricTree(int data){
        Node prev = null;
        Node curr  = root;
        boolean found = false;

        while (curr != null) {
            if (data == curr.data) {
                found = true;
                break;
            }
            prev = curr;
            if (data < curr.data) {
                if (curr.lIsNotThread)
                    curr = curr.left;
                else
                    break;
            }
            else {
                if (curr.rIsNotThread)
                    curr = curr.right;
                else
                    break;
            }
        }

        if (!found)
            System.out.println("Такого ключа нет в дереве!");
        else if (curr.lIsNotThread && curr.rIsNotThread)
            deleteNodeWithTwoChildren(prev, curr);
        else if (curr.lIsNotThread)
            deleteNodeWithOneChild(prev, curr);
        else if (curr.rIsNotThread)
            deleteNodeWithOneChild(prev, curr);
        else
            deleteLeaf(prev, curr);
    }

    private void deleteLeaf(Node prev, Node curr){
        if (prev == null)
            root = null;
        else if (curr == prev.left) {
            prev.lIsNotThread = false;
            prev.left = curr.left;
        } else {
            prev.rIsNotThread = false;
            prev.right = curr.right;
        }
    }

    private void deleteNodeWithOneChild(Node prev, Node curr){
        Node child;

        if (curr.lIsNotThread)
            child = curr.left;
        else
            child = curr.right;

        if (prev == null)
            root = child;
        else if (curr == prev.left)
            prev.left = child;
        else
            prev.right = child;

        Node s = inSuccessor(curr);
        Node p = inPredecessor(curr);

        if (curr.lIsNotThread)
            p.right = s;
        else {
            if (curr.rIsNotThread)
                s.left = p;
        }
    }

    private void deleteNodeWithTwoChildren(Node prev, Node curr){
        Node prevSucc = curr;
        Node succ = curr.right;

        while (succ.lIsNotThread) {
            prevSucc = succ;
            succ = succ.left;
        }
        curr.data = succ.data;
        if (!succ.lIsNotThread && !succ.rIsNotThread)
            deleteLeaf(prevSucc, succ);
        else
            deleteNodeWithOneChild(prevSucc, succ);
    }

    private Node inSuccessor(Node ptr) {
        if (!ptr.rIsNotThread)
            return ptr.right;

        ptr = ptr.right;
        while (ptr.lIsNotThread)
            ptr = ptr.left;
        return ptr;
    }

    private Node inPredecessor(Node ptr) {
        if (!ptr.lIsNotThread)
            return ptr.left;

        ptr = ptr.left;
        while (ptr.rIsNotThread)
            ptr = ptr.right;
        return ptr;
    }

    public void insertToSymmetricTree(int data) {
        Node curr = root;
        Node prev = null;
        while (curr != null) {
            prev = curr;
            if (data < curr.data) {
                if (curr.lIsNotThread)
                    curr = curr.left;
                else
                    break;
            } else {
                if (curr.rIsNotThread)
                    curr = curr.right;
                else
                    break;
            }
        }

        Node newNode = new Node(data);
        newNode.lIsNotThread = false;
        newNode.rIsNotThread = false;

        if (prev == null) {
            root = newNode;
            newNode.left = null;
            newNode.right = null;
        } else if (data < (prev.data)) {
            newNode.left = prev.left;
            newNode.right = prev;
            prev.lIsNotThread = true;
            prev.left = newNode;
        } else {
            newNode.left = prev;
            newNode.right = prev.right;
            prev.rIsNotThread = true;
            prev.right = newNode;
        }
    }


    Node y;
    public void makeSymmetricallyThreaded(){
        if (symThreadingDone){
            System.out.println("Невозможно создать дерево с симметричным прошиванием! Оно уже имеет симметричное прошивание!");
        } else {
            head = new Node(-1);
            head.left  = root;
            head.right = head;

            y = head;

            recursiveSymmetricalThreadingR(root);
            y.right = head;
            y.rIsNotThread = false;

            y = head;

            recursiveSymmetricalThreadingL(root);
            y.left = head;
            y.lIsNotThread = false;
        }
    }

    private void recursiveSymmetricalThreadingR(Node x){
        if (x != null){
            recursiveSymmetricalThreadingR(x.left);
            doSymmetricalThreadingR(x);
            recursiveSymmetricalThreadingR(x.right);
        }
    }

    private void doSymmetricalThreadingR(Node p){
        if (y != null){
            if (y.right == null){
                y.rIsNotThread = false;
                y.right = p;
            } else {
                y.rIsNotThread = true;
            }
        }
        y = p;
    }

    private void recursiveSymmetricalThreadingL(Node x){
        if (x != null){
            if (x.rIsNotThread) recursiveSymmetricalThreadingL(x.right);
            doSymmetricalThreadingL(x);
            recursiveSymmetricalThreadingL(x.left);
        }
    }

    private void doSymmetricalThreadingL(Node p){
        if (y != null){
            if (y.left == null){
                y.lIsNotThread = false;
                y.left = p;
            } else {
                y.lIsNotThread = true;
            }
        }
        y = p;
    }

    public void threadedInOrderTraverse(){
        Node curr = root;
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Симметричный обход дерева при помощи метода прошивки:");
        while (curr != head){
            while (curr.left != null && curr.lIsNotThread) curr = curr.left;
            curr.show();
            while (!curr.rIsNotThread && curr.right != null) {
                curr = curr.right;
                if (curr == head) {
                    System.out.println();
                    return;
                }
                curr.show();
            }
            curr = curr.right;
        }
        System.out.println();
    }

    public void threadedInOrderTraverseReverse(){
        Node curr = root;
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Симметричный обход дерева при помощи метода прошивки (по убыванию ключа):");
        while (curr != head){
            while (curr.right != null && curr.rIsNotThread) curr = curr.right;
            curr.show();
            while (!curr.lIsNotThread) {
                curr = curr.left;
                if (curr == head) {
                    System.out.println();
                    return;
                }
                curr.show();
            }
            curr = curr.left;
        }
        System.out.println();
    }

    public Node find(int x){
        Node curr = root;
        while (curr.data != x){
            if (x < curr.data)
                curr = curr.left;
            else
                curr = curr.right;
            if (curr == null) return null;
        }
        return curr;
    }

    public void getInOrder(){
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Симметричный обход дерева:");
        inOrder(root);
        System.out.println();
    }

    public void getPreOrder(){
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Прямой обход дерева:");
        preOrder(root);
        System.out.println();
    }

    public void getPostOrder(){
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Обратный обход дерева:");
        postOrder(root);
        System.out.println();
    }

    private void inOrder(Node root){
        if (root != null){
            inOrder(root.left);
            root.show();
            inOrder(root.right);
        }
    }

    private void preOrder(Node root){
        if (root != null){
            root.show();
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    private void postOrder(Node root){
        if (root != null){
            postOrder(root.left);
            postOrder(root.right);
            root.show();
        }
    }

    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.right;

        while(current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }

        if(successor != delNode.right) {
            successorParent.left = successor.right;
            successor.right = delNode.right;
        }
        return successor;
    }

    public boolean delete(int key) {

        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;
        while (current.data != key) {
            parent = current;
            if (key < current.data) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null)
                return false;
        }

        if (current.left == null &&
                current.right == null) {
            if (current == root)
                root = null;
            else if (isLeftChild)
                parent.left = null;
            else
                parent.right = null;
        }
        else
            if (current.right == null) {
                if (current == root)
                    root = current.left;
                else
                if (isLeftChild)
                    parent.left = current.left;
                else
                    parent.right = current.left;
            }
            else
                if (current.left == null) {
                    if (current == root)
                        root = current.right;
                    else
                    if (isLeftChild)
                        parent.left = current.right;
                    else
                        parent.right = current.right;
                }
                else

                {

                    Node successor = getSuccessor(current);
                    if (current == root)
                        root = successor;
                    else if (isLeftChild)
                        parent.left = successor;
                    else
                        parent.right = successor;

                    successor.left = current.left;
                }
        return true;
    }
}