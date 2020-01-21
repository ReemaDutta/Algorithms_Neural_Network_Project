package edu.neu.info6205.neuralnetworks.util;

import org.ejml.simple.SimpleMatrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {

    //generate the confusion matrix
    public static void CMGenerator(int[][] input, String fileName){
        StringBuilder builder = new StringBuilder();
        builder.append("Result,");
        for(int k = 0; k < 10; k++){
            builder.append("target"+k+",");
        }
        builder.append("\n");

        for(int i = 0; i < input.length; i++)//for each row
        {
            builder.append("actual"+i+",");
            for(int j = 0; j < input.length; j++)//for each column
            {
                builder.append(input[i][j]+"");//append to the output string
                if(j < input.length - 1)//if this is not the last row element
                    builder.append(",");//then add comma (if you don't like commas you can use spaces)
            }
            builder.append("\n");//append new line at the end of the row
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(builder.toString());//save the string representation of the input
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void saveNN(int inputNodes, int hiddenlayers, int hdNodes, int outputNodes, SimpleMatrix[] weights, SimpleMatrix[] bias){
        //save NN layer info
        StringBuilder builder = new StringBuilder();
        builder.append(inputNodes+",").append(hiddenlayers+",").append(hdNodes+",").append(outputNodes+",")
        .append("\n");

        //save weights
        for(SimpleMatrix sm:weights){
            double[][] wArray = MathUtil.toArray(sm);
            for(int i=0;i<wArray.length;i++){
                for(int j=0;j<wArray[0].length;j++){
                    builder.append(wArray[i][j]+"");
                    if(j < wArray[0].length - 1)//if this is not the last row element
                        builder.append(",");//then add comma (if you don't like commas you can use spaces)
                }
                builder.append("\n");
            }
        }
        bfWritter("weights.csv", builder);
        //save bias
        StringBuilder builder2 = new StringBuilder();
        for(SimpleMatrix sm:bias){
            double[][] bArray = MathUtil.toArray(sm);
            for(int i=0;i<bArray.length;i++){
                for(int j=0;j<bArray[0].length;j++){
                    builder2.append(bArray[i][j]+"");
                    if(j < bArray[0].length - 1)//if this is not the last row element
                        builder2.append(",");//then add comma (if you don't like commas you can use spaces)
                }
                builder2.append("\n");
            }
        }
        bfWritter("bias.csv", builder2);

    }

    private static void bfWritter(String name, StringBuilder builder){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(name));
            writer.write(builder.toString());//save the string representation of the input
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
