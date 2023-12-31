package com.company;
import java.io.*;
import java.util.ArrayList;

public class FileRead {
    public ArrayList<MyProcess> readFile (String fileName) throws IOException{
        File file = new File(fileName);
        FileReader fReader = new FileReader(file);
        String line;
        BufferedReader bReader = new BufferedReader(fReader);
        ArrayList<MyProcess> nodeArr = new ArrayList<MyProcess>();
        int counterProcess = 0;
        while ((line=bReader.readLine()) != null){
            String[] arr = line.split(", ");
            int arrivalTime = Integer.parseInt(arr[0]);
            int priority = Integer.parseInt(arr[1]);
            int burnTime = Integer.parseInt(arr[2]);
            int mbyte = Integer.parseInt(arr[3]);
            int yazicilar = Integer.parseInt(arr[4]);
            int tarayicilar = Integer.parseInt(arr[5]);
            int modemler = Integer.parseInt(arr[6]);
            int cd = Integer.parseInt(arr[7]);


            nodeArr.add(new MyProcess(counterProcess++,arrivalTime,priority,burnTime,mbyte,yazicilar,tarayicilar,modemler,cd));
        }
        return nodeArr;
    }



}
