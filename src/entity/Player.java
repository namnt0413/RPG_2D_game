package entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;

		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {	// lay anh = thu vien anh java
		try	{
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		} catch(IOException e) {
				e.printStackTrace();
		} 
			
		
	}
	
	public void update() {	//update position
		if( keyH.upPressed == true) {
			worldY -= speed;
			direction = "up";
		} 
		else if( keyH.downPressed == true) {
			worldY += speed;
			direction = "down";
		}
		else if( keyH.leftPressed == true) {
			worldX -= speed;
			direction = "left";
		}
		else if( keyH.rightPressed == true) {
			worldX += speed;
			direction = "right";
		}
			
	}
	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
			case "up":
				image = up1;
				break;
			case "down":
				image = down1;
				break;
			case "left":
				image = left1;
				break;
			case "right":
				image = right1;
				break;
		
		}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	
	}
	
	
	
}
