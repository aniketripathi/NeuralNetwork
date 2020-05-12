package neurons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import util.MutableDouble;

public abstract class AbstractNeuron {

	public static final double DEFAULT_WEIGHT = 0.5;
	protected List<Double> weights;
	private double bias;
	private double output; // temporary holds the output
	private static Random random = new Random(System.currentTimeMillis());

	public AbstractNeuron(int size, double bias) {
		this(size, bias, DEFAULT_WEIGHT);
	}

	public AbstractNeuron(int size, double bias, boolean randomWeights) {
		weights = new ArrayList<Double>(size);
		output = 0;
		this.bias = bias;
		for (int i = 0; i < size; i++) {
			weights.add((randomWeights) ? randomWeight() : DEFAULT_WEIGHT);
		}

	}

	private double randomWeight() {
		return (2*random.nextDouble() - 1);
	}

	// set default weight
	public AbstractNeuron(int size, double bias, double weight) {

		weights = new ArrayList<Double>(size);
		output = 0;
		this.bias = bias;
		for (int i = 0; i < size; i++) {
			weights.add(weight);
		}
	}

	public void addWeight(double weight) {
		weights.add(weight);
	}

	public double getOutput() {
		return output;
	}

	public void setWeights(double weights[]) {
		for (int i = 0; i < weights.length; i++) {
			this.weights.add(weights[i]);
		}
	}

	public double getWeight(int i) {
		return weights.get(i);
	}

	public void setWeight(int i, double weight) {
		weights.set(i, weight);
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public int getInputSize() {
		return weights.size();
	}

	/*
	 * IMPLEMENT ACTIVATION FUNCTION
	 */
	protected abstract double activationFunction(double weightedSum);

	/*
	 * Fires the neurons on basis of given inputs The Summation of weights are
	 * calculated using parallel stream
	 */
	public double fire(final double inputs[]) {

		MutableDouble res;
		if (inputs.length < weights.size()) {
			throw new IllegalArgumentException("Less number of inputs.");
		} else {
			res = IntStream.range(0, weights.size()).parallel().collect(

					MutableDouble::new, (result, i) -> {
						result.add(weights.get(i) * inputs[i]);
					}, (x, y) -> x.add(y));
		}
		this.output = activationFunction(res.get());
		return output;
		
	}

	public double serialFire(double inputs[]) {
		MutableDouble res;
		if (inputs.length < weights.size()) {
			throw new IllegalArgumentException("Less number of inputs.");
		} else {
			res = IntStream.range(0, weights.size()).sequential().collect(

					MutableDouble::new, (result, i) -> result.add(weights.get(i) * inputs[i]), (x, y) -> x.add(y));
		}
		this.output = activationFunction(res.get());
		return output;
	}

	public abstract double getDerivativeAtOutput(double output2);

}
