package neuralNetwork;

public class Main {

	Trainer trainer;
	
	public  void run() {
		trainer = new Trainer();
		int inputSize = 0;
		
		NeuralNetwork nn = new NeuralNetwork(2, inputSize);
		
		NeuralLayer nl1 = new NeuralLayer(15,inputSize);
		NeuralLayer nl2 = new NeuralLayer(10, 15);
		
		nn.addLayer(nl1).addLayer(nl2);
		
	}
	
}
