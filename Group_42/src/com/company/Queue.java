package com.company;

public class Queue {
   public Node head;

   public Queue(){
      this.head = null;
   }

   public void push(MyProcess prcs){
      Node node = new Node(prcs);
      if(head == null){
         head = node;
         return;
      }

      Node temp = head;
      while (temp.next != null){
         temp = temp.next;
      }

      temp.next = node;


   }

   public MyProcess pop(){
      Node popTemp = head;
      if(head.next!= null){
         head = head.next;
      } else {
         head = null;
      }

      return popTemp.myProcess;
   }




}
