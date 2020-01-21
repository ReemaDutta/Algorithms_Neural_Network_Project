package edu.neu.info6205.neuralnetworks;

import edu.neu.info6205.neuralnetworks.util.CSVReader;
import edu.neu.info6205.neuralnetworks.util.CSVWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Driver {

    public static void main(String[] args){

        mnist();

    }

    public static void mnist(){
        String fileName = "train.csv";
        System.out.println("Loading data...");
        List<double[]> inputList = CSVReader.readInput(fileName);
        List<double[]> targetList = CSVReader.readTarget(fileName);


        NeuralNetwork neuralNetwork = new NeuralNetwork(784,2,196,10);
        System.out.println("Start training...");
        for(int k=0;k<10;k++){
            System.out.println("***Epoch: "+k+"***");
            for(int i=0;i<inputList.size();i++)
                neuralNetwork.train(inputList.get(i),targetList.get(i));
        }
        //save NN
        System.out.println("Saving Neural network..");
        neuralNetwork.saveNN();

        String file2 = "validate.csv";
        System.out.println("Loading testData...");
        List<double[]> inputList2 = CSVReader.readInput(file2);
        List<double[]> targetList2 = CSVReader.readTarget(file2);
        System.out.println("Start predicting...");
        List<double[]> res = new ArrayList<>();
        for(double[] d:inputList2){
            double[] tmp = neuralNetwork.predict(d);
            res.add(tmp);
        }

        int count=0;
        int[][] confusionMatrix = new int[10][10];
        for(int i=0;i<res.size();i++){
            int max = 0;
            for(int j=0;j<res.get(0).length;j++){
                if(res.get(i)[j]>res.get(i)[max]) max = j;
            }


            int target = 0;
            for(int k=0;k<targetList2.get(0).length;k++){
                if(targetList2.get(i)[k]==1.0) {
                    target = k;
                    break;
                }
            }
            confusionMatrix[max][target]++;
            System.out.println("Prediction: "+max+"|"+"Target: "+target);
            if(max==target) count++;
        }

        System.out.println("Total Accuracy: "+100*count/res.size()+"%");
        String outputName = "confusion_matrix.csv";
        System.out.println("Saving confusion Matrix to "+outputName+"...");
        CSVWriter.CMGenerator(confusionMatrix,outputName);

    }

}
