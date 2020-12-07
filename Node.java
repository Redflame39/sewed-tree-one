package com.company;

//Класс-узел
class Node {
    public int data;
    public boolean lIsNotThread;
    public boolean rIsNotThread;
    public Node left;
    public Node right;

    public Node(int data){
        this.data = data;
        lIsNotThread = true;
        rIsNotThread = true;
        left  = null;
        right = null;
    }

    public void show() { System.out.print(data + " "); }
}
