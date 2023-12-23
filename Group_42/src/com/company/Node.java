package com.company;

public class Node {

    public MyProcess myProcess;
    public  Node next;


    public Node(MyProcess myProcess) {
        this.myProcess = myProcess;
        this.next = null;
    }
}
