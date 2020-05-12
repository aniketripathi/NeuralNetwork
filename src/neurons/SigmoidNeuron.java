package neurons;

import util.SigmoidFunction;

public class SigmoidNeuron extends AbstractNeuron {

	public static final double DEFAULT_BIAS = 0;

	public SigmoidNeuron(double weights[]) {
		super(weights.length, DEFAULT_BIAS);
		super.setWeights(weights);
	}

	public SigmoidNeuron(int size) {
		super(size, DEFAULT_BIAS);
	}

	public SigmoidNeuron(int size, double bias, boolean randomWeight) {
		super(size, bias, randomWeight);
	}

	public SigmoidNeuron(int size, double bias, double weight) {
		super(size, bias, weight);
	}

	@Override
	protected double activationFunction(double weightedSum) {
		return SigmoidFunction.get(weightedSum + getBias());
	}

	@Override
	public double getDerivativeAtOutput(double output2) {
		return SigmoidFunction.getDerivativeAtOutput(output2);
	}

}
