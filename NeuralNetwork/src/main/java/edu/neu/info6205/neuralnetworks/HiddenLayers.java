package edu.neu.info6205.neuralnetworks;

import java.util.ArrayList;
import java.util.List;

public class HiddenLayers {

    private List<Layer> layers;
    private int size;
    private int nodes;


    public HiddenLayers(int amount, int nodes){
        this.layers = new ArrayList<Layer>();
        this.size = amount;
        this.nodes = nodes;
        for (int i=0;i<size;i++){
            Layer layer = new Layer(nodes);
            layers.add(layer);
        }
    }

    public int getSize() {
        return layers.size();
    }

    public int getNodes() {
        return nodes;
    }
}
