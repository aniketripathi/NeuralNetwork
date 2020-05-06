package neurons;

import util.SigmoidFunction;

public class SigmoidNeuron extends AbstractNeuron {

	
	public static final double DEFAULT_BIAS = 0;
	
	public SigmoidNeuron(double weights[]) {
		super(weights.length,DEFAULT_BIAS);
		super.setWeights(weights);
	}
	
	
	public SigmoidNeuron(int size) {
		super(size, DEFAULT_BIAS);
		}
	

	@Override
	protected double activationFunction(double weightedSum) {
		return SigmoidFunction.get(weightedSum+getBias());
	}

	
	

	
}
