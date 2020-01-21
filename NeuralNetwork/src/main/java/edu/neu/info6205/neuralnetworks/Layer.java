package edu.neu.info6205.neuralnetworks;


import org.ejml.simple.SimpleMatrix;

public class Layer {

    private Neuron[] neurons;
    private SimpleMatrix weight;
    private SimpleMatrix bias;
    private int nodes;

    public Layer(int nodes){
        this.nodes = nodes;
        this.neurons = new Neuron[nodes];
    }

    public int getNodes() {
        return nodes;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }

    public SimpleMatrix getWeight() {
        return weight;
    }

    public void setWeight(SimpleMatrix weight) {
        this.weight = weight;
    }

    public SimpleMatrix getBias() {
        return bias;
    }

    public void setBias(SimpleMatrix bias) {
        this.bias = bias;
    }
}
