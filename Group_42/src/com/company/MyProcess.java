package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyProcess   {
    public int arrivalTime;
    public int priority;
    public int burnTime;
    public int processId;
    public int timeout;
    public int mbyte;
    public int yazicilar;
    public int tarayicilar;
    public int modemler;
    public int cd;


    public MyProcess(int processId,int arrivalTime, int priority, int burnTime,int mbyte,int yazicilar, int tarayicilar,int modemler,int cd){
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.burnTime = burnTime;
        this.processId = processId;
        this.timeout=arrivalTime+20;//20 saniyede bitmeyen process sonlandırılır.
        this.mbyte=mbyte;
        this.yazicilar=yazicilar;
        this.modemler=modemler;
        this.cd=cd;
    } // run metodu: Process'i çalıştırır

    public void run() throws IOException {

            ProcessBuilder pb = new ProcessBuilder("java","-version");
             // Process'i başlat
            Process p = pb.start();
             // Process'i sonlandır
        
        p.destroy();
                // Burn time azalt

            burnTime--;
        // Burn time hala varsa ekrana bilgi yazdır
            if(burnTime >= 1){
                System.out.println("Process" + processId + " 1 saniye çalıştı "+ "Önceligi" +priority );
            }



    }


}
