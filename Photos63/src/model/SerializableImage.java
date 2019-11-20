package model;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * The Class SerializableImage.
 */
@SuppressWarnings("serial")
public class SerializableImage implements Serializable{

	/** The height. */
	private int width, height;
	
	/** The pixels. */
	private int[][] pixels;
	
	/**
	 * Instantiates a new serializable image.
	 */
	public SerializableImage() {}
	
	/**
	 * Converts Image to 2d array.
	 *
	 * @param image the new image
	 */
	public void setImage(Image image) {
		width = ((int) image.getWidth());
		height = ((int) image.getHeight());
		pixels = new int[width][height];
		
		PixelReader r = image.getPixelReader();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				pixels[i][j] = r.getArgb(i, j);
	}
	
	/**
	 * Converts 2d array to Image.
	 *
	 * @return the image
	 */
	public Image getImage() {
		WritableImage image = new WritableImage(width, height);
		
		PixelWriter w = image.getPixelWriter();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				w.setArgb(i, j, pixels[i][j]);
		
		return image;
	}
	
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Gets the pixels.
	 *
	 * @return the pixels
	 */
	public int[][] getPixels() {
		return pixels;
	}
	
	/**
	 * This is a function that compares if 2 images are equal.
	 *
	 * @param si The serializable image to be checked
	 * @return true when equal
	 */
	public boolean equals(SerializableImage si) {
		if (width != si.getWidth())
			return false;
		if (height != si.getHeight())
			return false;
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				if (pixels[i][j] != si.getPixels()[i][j])
					return false;
		return true;
	}
}
