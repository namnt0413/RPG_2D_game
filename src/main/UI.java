package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {
	GamePanel gp;
	Font arial_40, arial_60B;
	BufferedImage keyImage;
	
	public Boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public boolean gameFinished = false;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial",Font.PLAIN,40);
		arial_60B = new Font("Arial",Font.PLAIN,60);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	
	
	
	public void draw(Graphics2D g2) {
		
		if(gameFinished == false) {
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize,gp.tileSize, null);
			g2.drawString("x "+ gp.player.hasKey, 74, 65);
			
			//draw message
			if( messageOn == true) {
				
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
			
				messageCounter++;
				
				if(messageCounter > 120 ) { // if messageCounter >120 => hide message
					messageCounter = 0;
					messageOn = false;
				}
				
			}
		
		} 
		else {
			g2.setFont(arial_60B);
			g2.setColor(Color.yellow);
			String text;
			int x;
			int y;
			int textLength;
			text = "CONGRATULATIONS";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() ;
			
			

			x = gp.screenWidth/2 -textLength/2;
			y = gp.screenHeight/2 - gp.tileSize*2;
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
			
		}
		
		

		
		
		
	}
	
	
}
