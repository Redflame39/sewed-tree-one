package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Random rand = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        BinTree tree = new BinTree();

        for (int i = 0; i < 10; i++)
            list.add(rand.nextInt(100));
        System.out.println("Порядок вставки элементов в двоичное дерево поиска: ");
        for (Integer it : list){
            System.out.print(it + " ");
            tree.insert(it);
        }
        System.out.println();

        tree.getInOrder();

        tree.makeSymmetricallyThreaded();

        tree.threadedInOrderTraverse();
        tree.threadedInOrderTraverseReverse();

        tree.insertToSymmetricTree(-1);
        tree.insertToSymmetricTree(-52);
        tree.insertToSymmetricTree(-250);
        tree.insertToSymmetricTree(120);
        tree.insertToSymmetricTree(170);
        tree.insertToSymmetricTree(130);
        tree.insertToSymmetricTree(150);
        tree.insertToSymmetricTree(190);
        tree.insertToSymmetricTree(410);
        tree.insertToSymmetricTree(-967);

        System.out.println("После вставки новых ключей:");
        tree.threadedInOrderTraverse();

        tree.deleteFromSymmetricTree(99999);
        tree.deleteFromSymmetricTree(-99999);
        tree.deleteFromSymmetricTree(-99990);

        tree.deleteFromSymmetricTree(170);
        tree.deleteFromSymmetricTree(-967);

        System.out.println("После удаления:");
        tree.threadedInOrderTraverse();
    }
}