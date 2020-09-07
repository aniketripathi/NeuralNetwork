package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MnistDataLoader {

	
	
	private class LabelledImage {
		
		private int label;
		private int[][] image;
		
		
		public LabelledImage(int label, int[][] image) {
			this.label = label;
			this.image = image;
		}
		
		public int getLabel() {
			return label;
		}
		
		public int[][] getImage(){
			return image;
		}

	}
	
	
	
	/*
	 * Each image is 28 * 28 pixel box Pixels are from 0 to 255. 0 means white and
	 * 255 means black
	 */

	public static final int PIXELS = 28;

	private List<LabelledImage> list;
	private double[][] images;

	public MnistDataLoader() {
		list = null;
		images = null;
	}

	public void load(String imagePath, String labelPath) {
		int[] labels = MnistReader.getLabels(labelPath);
		List<int[][]> imageList = MnistReader.getImages(imagePath);
		list = new ArrayList<LabelledImage>(labels.length);
		IntStream.range(0, labels.length).forEach((i) -> list.add(new LabelledImage(labels[i], imageList.get(i))));
		convertToDouble();
	}
		// Convert all the images into double format
		
	
	private void convertToDouble() {
		images = new double[list.size()][PIXELS * PIXELS];
		for (int i = 0; i < images.length; i++) {
			for (int j = 0; j < PIXELS; j++) {
				for (int k = 0; k < PIXELS; k++) {
					images[i][j * PIXELS + k] = getPixelInDouble(list.get(i).getImage()[j][k]);
				}

			}
		}
	}	
	
	
	
	public void close() {
		list = null;
		images = null;
		System.gc();
	}
	
	
	public int[][] getImage(int i) {
		return list.get(i).image;
	}

	public double[] getDoubleImage(int i) {
		return images[i];
	}

	public int getLabel(int i) {
		return list.get(i).getLabel();
	}
	
	public int getSize() {
		return list.size();
	}
	
	public double getPixelInDouble(int pixelValue) {
		return ((double) pixelValue / 255);
	}

	public void sort() {
		list.sort((l1, l2)-> l1.getLabel() - l2.getLabel());
		convertToDouble();
	}
	
	
}


