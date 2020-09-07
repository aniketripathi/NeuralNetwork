package neuralNetwork;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

import neuralNetwork.GroupedNeuralLayer.Group;
import neurons.LinearNeuron;
import neurons.SigmoidNeuron;
import util.MnistDataLoader;

public class Main {

	static String filePath = System.getProperty("user.dir") + "\\data\\network.txt";
	static String filePath2 = System.getProperty("user.dir") + "\\data\\network1.txt";

	public static void run1() throws IOException {
		int inputSize = 28 * 28;
		int output = 10;
		int hidden = 200;
		double b = 0;
		NeuralNetwork nn = new NeuralNetwork(inputSize);

		var nl1 = new NeuralLayer<SigmoidNeuron>(hidden, inputSize);
		var nl2 = new NeuralLayer<SigmoidNeuron>(output, hidden);

		for (int i = 0; i < hidden; i++) {
			nl1.add(new SigmoidNeuron(inputSize, b, true));
		}	
		
		for (int i = 0; i < output; i++) {
			nl2.add(new SigmoidNeuron(hidden, b, true));
		}

		nn.addLayer(nl1).addLayer(nl2);
		MnistDataLoader ld = new MnistDataLoader();
			train(nn,ld,20, filePath);
		ld.close();	
		
	}
	
	public static NeuralNetwork load1() throws IOException {
		int inputSize = 28 * 28;
		int output = 10;
		int hidden = 200;
		double b = 0;
		NeuralNetwork nn = new NeuralNetwork(inputSize);

		var nl1 = new NeuralLayer<SigmoidNeuron>(hidden, inputSize);
		var nl2 = new NeuralLayer<SigmoidNeuron>(output, hidden);

		for (int i = 0; i < hidden; i++) {
			nl1.add(new SigmoidNeuron(inputSize, b, true));
		}	
		
		for (int i = 0; i < output; i++) {
			nl2.add(new SigmoidNeuron(hidden, b, true));
		}

		nn.addLayer(nl1).addLayer(nl2);
		nn.loadFromFile(new File(filePath).toPath());
		MnistDataLoader ld = new MnistDataLoader();
		train(nn, ld, 20, filePath);
		ld.close();
		return nn;
	}
	
	public static void train(NeuralNetwork nn, MnistDataLoader dl, int epochs, String filePath) {
		
		Trainer tr = new Trainer();
		dl.load(tr.imagePath, tr.labelPath );
		MnistDataLoader ld = new MnistDataLoader();
		IntStream.range(0, epochs).forEach((i) -> {
			try {
				System.out.println("Epoch " + i);
				tr.backPropagationTrain1(nn, dl, true, filePath);
				test(nn,ld);
				System.out.println();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		});
		ld.close();
	}
	
	public static void test(NeuralNetwork nn, MnistDataLoader dl) throws IOException {
				
		Tester ts = new Tester();
		dl.load(ts.getImagePath(), ts.getLabelPath());
		
		ts.printTestResult(false, ts.test(nn, dl));
	}

	

/*	public static void run2() throws IOException {
		int inputSize = 28 * 28;
		int output = 10;
		int groups = 49;
		int hidden = 98;
		int x = 16;
		double b = 0;
		NeuralNetwork nn = new NeuralNetwork(inputSize);
		
		var nl1 = new GroupedNeuralLayer<SigmoidNeuron>(groups);
		var nl2 = new NeuralLayer<SigmoidNeuron>(output, hidden);
		
		
		
		for(int i = 0; i < groups; i++) {
			var g = new Group<SigmoidNeuron>(i*x, (i+1)*x);
				g.addNeuron(new SigmoidNeuron(x,b,true));
				g.addNeuron(new SigmoidNeuron(x,b,true));
			nl1.addGroup(g);
			}
			
		
		
				
		for (int i = 0; i < output; i++) {
			nl2.add(new SigmoidNeuron(hidden, b, true));
		}

		nn.addLayer(nl1).addLayer(nl2);
		MnistDataLoader ld = new MnistDataLoader();
			train(nn,ld,20, filePath2);
		ld.close();
	}
*/	
	public static NeuralNetwork load2() throws IOException {
		int inputSize = 28 * 28;
		int output = 10;
		int hidden1 = 100;
		int hidden2 = 50;
		double b = 0;
		NeuralNetwork nn = new NeuralNetwork(inputSize);

		var nl1 = new NeuralLayer<SigmoidNeuron>(hidden1, inputSize);
		var nl2 = new NeuralLayer<SigmoidNeuron>(hidden2, hidden1);
		var nl3 = new NeuralLayer<SigmoidNeuron>(output, hidden2);
		
		for (int i = 0; i < hidden1; i++) {
			nl1.add(new SigmoidNeuron(inputSize, b, true));
		}	
		
		for (int i = 0; i < hidden2; i++) {
			nl2.add(new SigmoidNeuron(hidden1, b, true));
		}
		
		for (int i = 0; i < output; i++) {
			nl3.add(new SigmoidNeuron(hidden2, b, true));
		}
		
		MnistDataLoader ld = new MnistDataLoader();
		nn.addLayer(nl1).addLayer(nl2).addLayer(nl3);		nn.loadFromFile(new File(filePath2).toPath());
		train(nn, ld, 20, filePath2);
		ld.close();
		return nn;
	}

	
	
	
	public static void main(String args[]) throws IOException {
		//run();
		//load1();
		
		//run2();
		//load2();
		//NeuralNetwork nn = load2();
		//RedundancyTest.test(nn.getLayer(2), nn.getLayer(2).getNeuron(0));
	}
}
