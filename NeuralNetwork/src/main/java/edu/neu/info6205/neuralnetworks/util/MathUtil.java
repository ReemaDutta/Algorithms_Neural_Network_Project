package edu.neu.info6205.neuralnetworks.util;

import org.ejml.simple.SimpleMatrix;

import java.util.Random;

public class MathUtil {
    public static Random random = new Random();

    // Converts an array to a Matrix
    public static SimpleMatrix fromArray(double[] arr) {
        double[][] arr2 = {arr};
        return new SimpleMatrix(arr2).transpose();
    }


    //mapping the matrix through MathFunction
    public static SimpleMatrix map(SimpleMatrix matrix, MathFunction func){
        SimpleMatrix output = new SimpleMatrix(matrix.numRows(), matrix.numCols());

        for (int i = 0; i < matrix.numRows(); i++) {
            for(int j=0;j<matrix.numCols();j++)
            output.set(i, j, func.function(matrix.get(i, j)));
        }
        return output;

    }
    //SoftMax
    public static SimpleMatrix softMaxOutput(SimpleMatrix matrix){
        SimpleMatrix output = matrix;
        double max = 0.0;
        for(int i=0;i<output.numRows();i++){
            max=Math.max(max,output.get(i,0));
        }
        output=output.minus(max).elementExp();
        output = output.divide(output.elementSum());
        return  output;
    }

    public static double[] getColumn(SimpleMatrix matrix, int col) {
        double[] result = new double[matrix.numRows()];
        for (int i = 0; i < result.length; i++) {
            result[i] = matrix.get(i, col);
        }

        return result;
    }

    public static double[][] toArray(SimpleMatrix matrix) {
        double[][] result = new double[matrix.numRows()][matrix.numCols()];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = matrix.get(i, j);
            }
        }
        return result;
    }
}
