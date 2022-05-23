package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {

	public OBJ_Shield_Blue( GamePanel gp) {
		
		super(gp);
		
		name = "Blue Shield";
		down1 = setup("/objects/shield_blue",gp.tileSize,gp.tileSize);
		type = type_shield;
		defenseValue = 2;
		description = "[" + name + "]\nA skinny blue shield.";
		
	}
	
}
