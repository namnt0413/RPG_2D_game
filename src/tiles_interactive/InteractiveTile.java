package tiles_interactive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
	
	GamePanel gp;
	
	public InteractiveTile(GamePanel gp , int col, int row) {
		super(gp);
		this.gp = gp;
		
	}
	
	public boolean isCorrectItem( Entity entity) {
		boolean isCorrectItem = false;
		return isCorrectItem;
	}
	
	public void playSE() {
		
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		return tile;
	}
	
	public void update() {
		if( invincible == true ) {
			invincibleCounter++;
			if( invincible == true ) {
				invincibleCounter++;
				if( invincibleCounter > 20 ) {
					invincible = false;
					invincibleCounter = 0;
				}
				
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		double screenX = worldX - gp.player.worldX + gp.player.screenX;	// +gp.player.screenX: vi camera luon o chinh giua
		double screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if ( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			 worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			 worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			 worldY - gp.tileSize <	 gp.player.worldY + gp.player.screenY ) {
			
			g2.drawImage(down1, (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);

		}
		
	}
	
}
