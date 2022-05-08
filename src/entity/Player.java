package entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
		
		solidArea = new Rectangle(0,0,gp.tileSize-16,gp.tileSize-16); // x,y,width,height
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = gp.maxWorldWidth/600; //2400/600
//		speed = 4;
		
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
		
		
		if(keyH.upPressed ==true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) { 
			// Phai nhan 1 phim bat ky de lot vao ham nay. Neu khong co ham if nay thi se tu dong di thang xuong duoi
			
			if( keyH.upPressed == true) {
				direction = "up";
			} 
			else if( keyH.downPressed == true) {
				direction = "down";
			}
			else if( keyH.leftPressed == true) {
				direction = "left";
			}
			else if( keyH.rightPressed == true) {
				direction = "right";
			}
			
			//Check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//if collision is fall, player can move
			if(collisionOn == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
			
		}
		

			
	}
	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
			case "up":
				if(spriteNum ==1 ) {
					image = up1;
				}
				if(spriteNum ==2) {
					image = up2;
				}
				break;

			case "down":
				if(spriteNum ==1 ) {
					image = down1;
				}
				if(spriteNum ==2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum ==1 ) {
					image = left1;
				}
				if(spriteNum ==2) {
					image = left2;
				}
				break;
				
			case "right":
				if(spriteNum ==1 ) {
					image = right1;
				}
				if(spriteNum ==2) {
					image = right2;
				}
				break;
		
		}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	
	}
	
	
	
}
