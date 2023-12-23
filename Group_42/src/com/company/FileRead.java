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


            nodeArr.add(new MyProcess(counterProcess++,arrivalTime,priority,burnTime));
        }
        return nodeArr;
    }



}
