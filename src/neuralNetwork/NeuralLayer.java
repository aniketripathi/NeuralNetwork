package neuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import neurons.AbstractNeuron;

public class NeuralLayer<T extends AbstractNeuron> {

	private ArrayList<T> neurons;
	private int inputSize;

	public NeuralLayer(int size, int inputSize) {
		this.inputSize = inputSize;
		neurons = new ArrayList<T>(size);
	}
	
	public NeuralLayer<T> add(T neuron) {
		neurons.add(neuron);
		return this;
	}

	public void addAll(List<T> neurons) {
		this.neurons.addAll(neurons);
	}

	public T getNeuron(int i) {
		return neurons.get(i);
	}

	public int getInputSize() {
		return inputSize;
	}

	public int getSize() {
		return neurons.size();
	}

	public double[] fire(double inputs[]) {
		double output[] = new double[neurons.size()];

		if (inputs.length < inputSize) {
			throw new IllegalArgumentException("Less number of inputs.");
		} else {
			IntStream.range(0, neurons.size()).parallel().forEach((i) -> {
				output[i] = neurons.get(i).fire(inputs);

			});
		}
		 //Arrays.stream(output).forEach(x->System.out.print(x+","));System.out.println();
		return output;
	}

	/*
	 * Fires the layer sequentially. parallelFire parameter tells whether the
	 * individual neuron should use parallel firing or not
	 */
	public double[] serialFire(double inputs[], boolean parallelFire) {
		double output[] = new double[neurons.size()];

		if (inputs.length < inputSize) {
			throw new IllegalArgumentException("Less number of inputs.");
		} else {
			IntStream.range(0, neurons.size()).sequential().forEach((i) -> {
				output[i] = (parallelFire) ? neurons.get(i).fire(inputs) : neurons.get(i).serialFire(inputs);
			});
		}
		return output;
	}

}