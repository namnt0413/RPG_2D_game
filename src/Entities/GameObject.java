package Entities;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class GameObject {
	private int x, y, width, height;
	private Image image;


	public GameObject(int x, int y, int width, int height, Image image) {
		this.x = x;
		this.y = y;
		this.setWidth(width);
		this.setHeight(height);
		this.image = image;
	}

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, getWidth(), getHeight(), null);
	}

	public void loadImage(String imageName) {
		ImageIcon img = new ImageIcon(imageName);
		image = img.getImage();
	}

	public void getImageDimension() {
		setWidth(image.getWidth(null));
		setHeight(image.getHeight(null));
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
