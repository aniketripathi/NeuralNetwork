package neuralNetwork;

import java.util.List;

import util.MnistReader;

public class MnistDataLoader {
	
	private int[] labels;
	private List<int[][]> images;
	
	public MnistDataLoader() {
		labels = null;
		images = null;
	}
	
	public void load(String imagePath, String labelPath) {
		labels = MnistReader.getLabels(labelPath);
		images = MnistReader.getImages(imagePath);
	}
	
	
	public int[][] getImage(int i){
		return images.get(i);
	}
	
	public int getLabel(int i) {
		return labels[i];
	}
	
	public int getNumberOfImages() {
		return images.size();
	}
	
	public int getNumberOfLabels() {
		return labels.length;
	}
}
