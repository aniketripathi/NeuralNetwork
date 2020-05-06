package neuralNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import neurons.SigmoidNeuron;

public class NeuralLayer {

	
	private List<SigmoidNeuron> neurons;
	private int inputSize;
	
	public NeuralLayer(int size, int inputSize) {
		this.inputSize = inputSize;
		neurons = new ArrayList<SigmoidNeuron>(size);
		for(int i = 0; i < size; i++) {
			neurons.add(new SigmoidNeuron(inputSize));
		}
	}
	
	
	public SigmoidNeuron getNeuron(int i) {
		return neurons.get(i);
	}
	
	
	public int getInputSize() {
		return inputSize;
	}
	
	public int getOutputSize() {
		return neurons.size();
	}
	
	
	public double[] fire(double inputs[]) {
		double output[] = new double[neurons.size()];
		
		if(inputs.length < inputSize) {
			throw new IllegalArgumentException("Less number of inputs.");
		}
		else {
			IntStream.range(0, neurons.size()).parallel().forEach((i) -> {
				output[i] = neurons.get(i).fire(inputs);
			});
		}
		return output;
	}
	
	/*
	 * Fires the layer sequentially.
	 * parallelFire parameter tells whether the individual neuron should use parallel firing or not
	 */
	public double[] serialFire(double inputs[], boolean parallelFire) {
		double output[] = new double[neurons.size()];
		
		if(inputs.length < inputSize) {
			throw new IllegalArgumentException("Less number of inputs.");
		}
		else {
			IntStream.range(0, neurons.size()).sequential().forEach((i) -> {
				output[i] = (parallelFire)?neurons.get(i).fire(inputs):neurons.get(i).serialFire(inputs);
			});
		}
		return output;
	}
	
	
}