package edu.neu.info6205.neuralnetworks.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {



    public static List<double[]> readInput(String fileName){
        List<double[]> res = new ArrayList<>();
        try {
            Scanner scanner = new Scanner( new BufferedReader(new FileReader(fileName)));
            String labels = scanner.nextLine();
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] array = line.split(",");
                double[] myArray = new double[array.length-1];
                for(int i=1;i<array.length;i++){
                    myArray[i-1] = Double.parseDouble(array[i]);
                }
                res.add(myArray);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return res;

    }

    public static List<double[]> readTarget(String fileName){
        List<double[]> res = new ArrayList<>();
        try {
            Scanner scanner = new Scanner( new BufferedReader(new FileReader(fileName)));
            String labels = scanner.nextLine();
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] array = line.split(",");
                double[] myArray = new double[10];
                int index = Integer.parseInt(array[0]);
                myArray[index] = 1.0;
                res.add(myArray);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return res;

    }


}
