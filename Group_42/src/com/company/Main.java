package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.nio.file.Paths;
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        FileRead fileRead = new FileRead();
        Queue realTime = new Queue();
        Queue priorty1 = new Queue();
        Queue priorty2 = new Queue();
        Queue priorty3 = new Queue();

        //Askıdaki process oluşturulurken varış zamanı -1 olarak tanımlanıyor bu sayede askıdaki process olduğu anlaşılıyor
        MyProcess askıdakiProcess = new MyProcess(-1,0,0,0,0,0,0,0,0);


        // Deneme.txt dosyasından veriler okunuyor
        String qurrent_path = Paths.get(".").toAbsolutePath().normalize().toString() + ("\\src.\\Deneme.txt");
        ArrayList<MyProcess> nodeArr = fileRead.readFile(qurrent_path);
        int totalProcess = nodeArr.size();
        //processlerin varış zamanlarına göre sıralanması
        nodeArr.sort((node1, node2) -> node1.arrivalTime - node2.arrivalTime);

        //varış zamanlarına göre processler realTime priority1, priority2, priority3 olmak üzere queuelara atılıyor.
        int counter = 0;
        while (true) 
        {
            for (int i = 0; i < nodeArr.size() && nodeArr.get(i).arrivalTime <= counter; i++) 
            {
                if (nodeArr.get(i).arrivalTime == counter) 
                {
                    switch (nodeArr.get(i).priority) 
                    {
                        case 0:
                            realTime.push(nodeArr.get(i));
                            break;
                        case 1:
                            priorty1.push(nodeArr.get(i));
                            break;
                        case 2:
                            priorty2.push(nodeArr.get(i));
                            break;
                        case 3:
                            priorty3.push(nodeArr.get(i));
                            break;
                        default:
                            break;
                    }
                }
               
            }
            //1 saniyelik gecikme yapılıyor
            Thread.sleep(1000);
            
            System.out.println("ZAMAN: "+counter);
            //zamanasimi kontrolü
            boolean zamanasimi=false;
            if(realTime.head != null && realTime.head.myProcess.timeout<counter)
            {
                System.out.println("Prosess" + realTime.head.myProcess.processId + " Zaman aşımına uğradı  Önceliği:" + realTime.head.myProcess.priority);
                realTime.pop();
                totalProcess--;
                zamanasimi=true;
            }
            if(priorty1.head != null &&  priorty1.head.myProcess.timeout<counter)
            {
                System.out.println("Prosess" + priorty1.head.myProcess.processId + " Zaman aşımına uğradı  Önceliği:" + priorty1.head.myProcess.priority);
                priorty1.pop();
                totalProcess--;
                zamanasimi=true;
            }
            if(priorty2.head != null && priorty2.head.myProcess.timeout<counter)
            {
                System.out.println("Prosess" + priorty2.head.myProcess.processId + " Zaman aşımına uğradı  Önceliği:" + priorty2.head.myProcess.priority);
                priorty2.pop();
                totalProcess--;
                zamanasimi=true;
            }
            if(priorty3.head != null &&priorty3.head.myProcess.timeout<counter)
            {
                System.out.println("Prosess" + priorty3.head.myProcess.processId + " Zaman aşımına uğradı  Önceliği:" + priorty3.head.myProcess.priority);
                priorty3.pop();
                totalProcess--;
                zamanasimi=true;
            }
            if(zamanasimi==true)
                continue;
            
            if (realTime.head != null) {
                Node temp = realTime.head;
                    if (askıdakiProcess.processId != temp.myProcess.processId && askıdakiProcess.processId != -1) {
                        System.out.println("Process" + askıdakiProcess.processId + " askıya alındı");
                    }

                    temp.myProcess.run();
                    if (temp.myProcess.burnTime == 0) {
                        realTime.pop();
                        totalProcess--;
                        System.out.println("Process" + temp.myProcess.processId + " sonlandı");
                        askıdakiProcess.processId = -1;
                    } else {
                        askıdakiProcess = new MyProcess(temp.myProcess.processId, temp.myProcess.arrivalTime, temp.myProcess.priority, temp.myProcess.burnTime,
                        		temp.myProcess.mbyte,temp.myProcess.yazicilar,temp.myProcess.tarayicilar,temp.myProcess.modemler,temp.myProcess.cd);
                    }


            } else if (priorty1.head != null) {
                Node temp = priorty1.head;
                    if (askıdakiProcess.processId != temp.myProcess.processId && askıdakiProcess.processId != -1) {
                        System.out.println("Process" + askıdakiProcess.processId + " askıya alındı");
                    }
                    temp.myProcess.run();


                    if (temp.myProcess.burnTime == 0) {
                        priorty1.pop();
                        totalProcess--;
                        System.out.println("Process" + temp.myProcess.processId + " sonlandı");
                        askıdakiProcess.processId = -1;
                    } else {
                    	askıdakiProcess = new MyProcess(temp.myProcess.processId, temp.myProcess.arrivalTime, temp.myProcess.priority, temp.myProcess.burnTime,
                        		temp.myProcess.mbyte,temp.myProcess.yazicilar,temp.myProcess.tarayicilar,temp.myProcess.modemler,temp.myProcess.cd);
                    }

            } else if (priorty2.head != null) {

                Node temp = priorty2.head;
                    if (askıdakiProcess.processId != temp.myProcess.processId && askıdakiProcess.processId != -1) {
                        System.out.println("Process" + askıdakiProcess.processId + " askıya alındı");
                    }
                    temp.myProcess.run();


                    if (temp.myProcess.burnTime == 0) {
                        priorty2.pop();
                        totalProcess--;
                        System.out.println("Process" + temp.myProcess.processId + " sonlandı");
                        askıdakiProcess.processId = -1;
                    } else {
                    	askıdakiProcess = new MyProcess(temp.myProcess.processId, temp.myProcess.arrivalTime, temp.myProcess.priority, temp.myProcess.burnTime,
                        		temp.myProcess.mbyte,temp.myProcess.yazicilar,temp.myProcess.tarayicilar,temp.myProcess.modemler,temp.myProcess.cd);                    }

            } else if (priorty3.head != null) {
                Node temp = priorty3.head;
                    if (askıdakiProcess.processId != temp.myProcess.processId && askıdakiProcess.processId != -1) {
                        System.out.println("Process" + askıdakiProcess.processId + " askıya alındı");
                    }

                    temp.myProcess.run();

                    if (temp.myProcess.burnTime == 0) {
                        priorty3.pop();
                        totalProcess--;
                        System.out.println("Process" + temp.myProcess.processId + " sonlandı");
                        askıdakiProcess.processId = -1;
                    } else {
                    	askıdakiProcess = new MyProcess(temp.myProcess.processId, temp.myProcess.arrivalTime, temp.myProcess.priority, temp.myProcess.burnTime,
                        		temp.myProcess.mbyte,temp.myProcess.yazicilar,temp.myProcess.tarayicilar,temp.myProcess.modemler,temp.myProcess.cd);                    }
                }


            if(totalProcess == 0){
                return;
            }
            counter++;
        }

    }
}



















