package neurons;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import util.MutableDouble;

public abstract class AbstractNeuron{
	
	public static final double DEFAULT_WEIGHT = 0.5;
	protected List<Double> weights;
	private double bias;
	
	public AbstractNeuron(int size, double bias) {
		this(size, bias, DEFAULT_WEIGHT);
	}
	
	public AbstractNeuron(int size, double bias, double weight) {
		
		weights = new ArrayList<Double>(size);
		this.bias = bias;
		for(int i = 0; i < size; i++) {
			weights.add(weight);
		}
	}
	
	
	public void setWeights(double weights[]) {
		for(int i = 0; i < weights.length; i++) {
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
	
	
	/*
	 * IMPLEMENT ACTIVATION FUNCTION
	 */
	protected abstract double activationFunction(double weightedSum);
	
	
	public double getOutput(double inputs[]) {
		double output = bias;
		if(inputs.length < weights.size()) {
			throw new IllegalArgumentException();
		}
			else	{
				for(int i = 0; i < weights.size(); i++) {
					output += weights.get(i) * inputs[i];
				}
				output = activationFunction(output);
			}
			return output;
		}
	
	
	/*
	 * Fires the neurons on basis of given inputs
	 * The Summation of weights are calculated using parallel stream
	 */
	public double fire(final double inputs[]) {	
		
		MutableDouble res;
		if(inputs.length < weights.size()) {
			throw new IllegalArgumentException("Less number of inputs.");
		}
		else {
			res = IntStream.range(0, weights.size()).parallel().collect(
					
					MutableDouble::new,
				(result, i) -> { result.add(weights.get(i) * inputs[i]);},
				(x,y) -> x.add(y)); 
		}
			System.out.println(res.get());
		return activationFunction(res.get());
	}
	
	public double serialFire(double inputs[]) {
		MutableDouble res;
		if(inputs.length < weights.size()) {
			throw new IllegalArgumentException("Less number of inputs.");
		}
		else {
			res = IntStream.range(0, weights.size()).sequential().collect(
					
					MutableDouble::new,
				(result, i) -> result.add(weights.get(i) * inputs[i]),
				(x,y) -> x.add(y)); 
		}
		return activationFunction(res.get());
	}

	
	}

