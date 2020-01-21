package edu.neu.info6205.neuralnetworks;


import java.util.Random;

import edu.neu.info6205.neuralnetworks.util.ActivationFunction;
import edu.neu.info6205.neuralnetworks.util.CSVWriter;
import edu.neu.info6205.neuralnetworks.util.MathFunction;
import edu.neu.info6205.neuralnetworks.util.MathUtil;
import org.ejml.simple.SimpleMatrix;

public class NeuralNetwork {

    private Random random = new Random();

    private Layer inputLayer;
    private HiddenLayers hiddenLayers;
    private Layer outputLayer;
    //learningRate
    private double rt=0.001;
    //Activation Function
    public static MathFunction actFunc=ActivationFunction.tanh;
    public static MathFunction deFunc = ActivationFunction.dTanh;
    private SimpleMatrix[] weights;
    private SimpleMatrix[] biases;

    public NeuralNetwork(){

    }

    public NeuralNetwork(int inputNodes, int totalHiddenLayer, int hiddenNodes, int outputNodes) {
        this.inputLayer = new Layer(inputNodes);
        this.hiddenLayers = new HiddenLayers(totalHiddenLayer,hiddenNodes);
        this.outputLayer = new Layer(outputNodes);
        this.weights = new SimpleMatrix[hiddenLayers.getSize() +1];
        this.biases = new SimpleMatrix[hiddenLayers.getSize() +1];
        randomizeWeight();
        randomizeBias();
    }
    public NeuralNetwork(int inputNodes, int totalHiddenLayer, int hiddenNodes, int outputNodes, int rt) {
        this.inputLayer = new Layer(inputNodes);
        this.hiddenLayers = new HiddenLayers(totalHiddenLayer,hiddenNodes);
        this.outputLayer = new Layer(outputNodes);
        this.rt = rt;
        this.weights = new SimpleMatrix[hiddenLayers.getSize() +1];
        this.biases = new SimpleMatrix[hiddenLayers.getSize() +1];
        randomizeWeight();
        randomizeBias();
    }

    public double[] predict(double[] inputArray) {
        SimpleMatrix output = MathUtil.fromArray(inputArray).divide(255);
//        SimpleMatrix output = MathUtil.fromArray(inputArray);
        for (int i = 0; i < hiddenLayers.getSize(); i++) {
            output = calculateOutput(output, weights[i], biases[i]);
        }
        output = calculateSoftMaxOutput(output, weights[hiddenLayers.getSize()], biases[hiddenLayers.getSize()]);
        return MathUtil.getColumn(output, 0);

    }

    public void train(double[] inputArray, double[] targetArray) {

        // Feed forward
        SimpleMatrix input = MathUtil.fromArray(inputArray).divide(255);
//        SimpleMatrix input = MathUtil.fromArray(inputArray);
        SimpleMatrix layers[] = new SimpleMatrix[hiddenLayers.getSize()+2];
        layers[0] = input;

        //hiddenLayer output
        for (int i = 1; i < hiddenLayers.getSize()+1; i++) {
            layers[i] = calculateOutput(input,weights[i-1], biases[i-1]);
            input = layers[i];
        }
        //SoftMaxLayer output

        layers[hiddenLayers.getSize()+1] = calculateSoftMaxOutput(input, weights[hiddenLayers.getSize()], biases[hiddenLayers.getSize()]);

        //Back propagation
        SimpleMatrix target = MathUtil.fromArray(targetArray);



        for (int j = hiddenLayers.getSize()+1; j > 0; j--) {
            SimpleMatrix error = target.minus(layers[j]);
            SimpleMatrix gradient = (j==hiddenLayers.getSize()+1) ? calculateSoftMaxGradient(layers[j], error) : calculateGradient(layers[j], error);
            biases[j-1] = biases[j-1].plus(gradient);

            SimpleMatrix delta = calculateDeltas(layers[j-1], gradient);
            weights[j-1] = weights[j-1].plus(delta);
            SimpleMatrix previousError = weights[j-1].transpose().mult(error);
            target = previousError.plus(layers[j-1]);
        }

    }


    public void saveNN(){

        CSVWriter.saveNN(inputLayer.getNodes(),hiddenLayers.getSize(),hiddenLayers.getNodes(),outputLayer.getNodes(),this.weights,this.biases);

    }




    private SimpleMatrix calculateSoftMaxOutput(SimpleMatrix input, SimpleMatrix weight, SimpleMatrix bias) {
        SimpleMatrix res = weight.mult(input);
        res = res.plus(bias);
        return MathUtil.softMaxOutput(res);
    }

    private SimpleMatrix calculateSoftMaxGradient(SimpleMatrix layer, SimpleMatrix error){
        return layer.elementMult(error).scale(rt);
    }

    //
    private SimpleMatrix calculateOutput(SimpleMatrix input, SimpleMatrix weight, SimpleMatrix bias) {

        SimpleMatrix res = weight.mult(input);
        res = res.plus(bias);
        res = MathUtil.map(res, actFunc);
        return res;
    }

    private SimpleMatrix calculateGradient(SimpleMatrix layer, SimpleMatrix error) {
//        SimpleMatrix res = MathUtil.map(layer, dSigmoidFunction);
        SimpleMatrix res = MathUtil.map(layer, deFunc);
        res = res.elementMult(error).scale(rt);
        return res;
    }

    private SimpleMatrix calculateDeltas(SimpleMatrix layer, SimpleMatrix gradient) {
        SimpleMatrix layerT = layer.transpose();
        SimpleMatrix res = gradient.mult(layerT);
        return res;
    }

    public static NeuralNetwork copy(NeuralNetwork from){
        NeuralNetwork nn = new NeuralNetwork();
        nn.setInputLayer(from.getInputLayer());
        nn.setHiddenLayers(from.getHiddenLayers());
        nn.setOutputLayer(from.getOutputLayer());
        SimpleMatrix[] ws = new SimpleMatrix[from.getHiddenLayers().getSize() + 1];
        SimpleMatrix[] bs = new SimpleMatrix[from.getHiddenLayers().getSize() + 1];
        System.arraycopy(from.getWeights(), 0, ws, 0, from.getWeights().length);
        System.arraycopy(from.getBiases(), 0, bs, 0, from.getBiases().length);
        nn.setWeights(ws);
        nn.setBiases(bs);
        nn.setRt(from.getRt());
        return nn;
    }

    private void randomizeWeight() {
        for (int i = 0; i < weights.length; i++) {
            if (i == 0) {
                weights[i] = SimpleMatrix.random_DDRM(hiddenLayers.getNodes(), inputLayer.getNodes(), -1, 1, random);
            } else if (i == weights.length - 1) {
                weights[i] = SimpleMatrix.random_DDRM(outputLayer.getNodes(), hiddenLayers.getNodes(), -1, 1, random);
            } else {

                weights[i] = SimpleMatrix.random_DDRM(hiddenLayers.getNodes(), hiddenLayers.getNodes(), -1, 1, random);

            }
        }
}

    private void randomizeBias() {
        for (int i = 0; i < biases.length; i++) {
            if (i == biases.length - 1) {
                biases[i] = SimpleMatrix.random_DDRM(outputLayer.getNodes(), 1, -1, 1, random);
            } else {

                biases[i] = SimpleMatrix.random_DDRM(hiddenLayers.getNodes(), 1, -1, 1, random);

            }
        }
    }

    public double getRt() {
        return rt;
    }

    public void setRt(double rt) {
        this.rt = rt;
    }

    public SimpleMatrix[] getWeights() {
        return weights;
    }

    public void setWeights(SimpleMatrix[] weights) {
        this.weights = weights;
    }

    public SimpleMatrix[] getBiases() {
        return biases;
    }

    public void setBiases(SimpleMatrix[] biases) {
        this.biases = biases;
    }

    public Layer getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer(Layer inputLayer) {
        this.inputLayer = inputLayer;
    }

    public void setHiddenLayers(HiddenLayers hiddenLayers) {
        this.hiddenLayers = hiddenLayers;
    }

    public Layer getOutputLayer() {
        return outputLayer;
    }

    public void setOutputLayer(Layer outputLayer) {
        this.outputLayer = outputLayer;
    }

    public HiddenLayers getHiddenLayers() {
        return hiddenLayers;
    }
}