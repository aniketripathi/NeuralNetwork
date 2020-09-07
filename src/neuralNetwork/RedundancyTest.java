package neuralNetwork;

import neurons.AbstractNeuron;

public class RedundancyTest {

	/*
	 *  To check how one neuron is similar to other in its layer.
	 *  Lower value means more similarity
	 */
	
	public static void test(NeuralLayer<? extends AbstractNeuron> layer, AbstractNeuron n ) {
		
		for(int i = 0; i < layer.getSize(); i++) {
			double redund = 0;
			var e = layer.getNeuron(i);
			for(int j = 0; j < e.getInputSize(); j++) {
				redund += Math.abs(e.getWeight(j) - n.getWeight(j));
			}
			redund += Math.abs(e.getBias() - n.getBias());
			redund /= (e.getInputSize() + 1);
			System.out.println(redund);
		}
		
		
	}
}
