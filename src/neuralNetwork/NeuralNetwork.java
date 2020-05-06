package neuralNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class NeuralNetwork {

	private List<NeuralLayer> layers;
	private int inputSize;

	public NeuralNetwork(int layers, int inputSize) {
		this.layers = new ArrayList<NeuralLayer>(layers);
		this.inputSize = inputSize;
	}
	
	public int getInputSize() {
		return inputSize;
	}
	
	public int getOutputSize() {
		int outSize = 0;
		if(layers.size() > 0) {
			outSize = layers.get(layers.size()-1).getOutputSize();
		}
		return outSize;
	}
	
	public NeuralNetwork addLayer(NeuralLayer layer) {
		layers.add(layer);
		return this;
	}
	
	public NeuralLayer getLayer(int i) {
		return layers.get(i);
	}
	
	public double[] fire(double inputs[]) {
		
		double output[] = inputs;
		if(inputs.length < inputSize) {
			throw new IllegalArgumentException("Less number of inputs.");
		}
		else {
			ListIterator<NeuralLayer> iterator = layers.listIterator();
			while(iterator.hasNext()) {
				output = iterator.next().fire(output);
			}
		}
		return output;
	}
}
