package edu.neu.info6205.neuralnetworks.util;

public class ActivationFunction {

    public static MathFunction sigmoidFunction = (x ->1 / (1 + Math.exp(-x)));
    public static MathFunction dSigmoidFunction = (x -> x*(1-x));
    public static MathFunction tanh = (x-> Math.tanh(x));
    public static MathFunction dTanh = (x-> 1-(x*x));
    public static MathFunction relu = (x-> x>0?x:0);
    public static MathFunction dRelu = (x-> x>0?1:0);
}
