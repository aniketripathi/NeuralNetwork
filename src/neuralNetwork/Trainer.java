package neuralNetwork;

public class Trainer {

	String imagePath = System.getProperty("user.dir")+"\\data\\train-images-idx3-ubyte\\train-images.idx3-ubyte";
	String labelPath = System.getProperty("user.dir")+"\\data\\train-labels-idx1-ubyte\\train-labels.idx1-ubyte";
	MnistDataLoader ld;
	
	public Trainer() {
		ld = new MnistDataLoader();
		ld.load(imagePath, labelPath);		
	}
	
	
}
