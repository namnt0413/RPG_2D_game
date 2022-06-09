package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import tiles_interactive.IT_DryTree;


public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter (GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		int i = 0;
		gp.obj[i] = new OBJ_Coin_Bronze(gp);
		gp.obj[i].worldX = 25 * gp.tileSize;
		gp.obj[i].worldY = 23 *  gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_Coin_Bronze(gp);
		gp.obj[i].worldX = 21 * gp.tileSize;
		gp.obj[i].worldY = 19 *  gp.tileSize;		
		i++;
		
		gp.obj[i] = new OBJ_Coin_Bronze(gp);
		gp.obj[i].worldX = 26 * gp.tileSize;
		gp.obj[i].worldY = 21 *  gp.tileSize;
		i++;
		
//		gp.obj[i] = new OBJ_Door(gp);
//		gp.obj[i].worldX = 10 * gp.tileSize;
//		gp.obj[i].worldY = 11 *  gp.tileSize;
//		i++;
//		
//		gp.obj[i] = new OBJ_Door(gp);
//		gp.obj[i].worldX = 8 * gp.tileSize;
//		gp.obj[i].worldY = 28 *  gp.tileSize;
//		i++;
//		
//		gp.obj[i] = new OBJ_Door(gp);
//		gp.obj[i].worldX = 12 * gp.tileSize;
//		gp.obj[i].worldY = 22 *  gp.tileSize;
//		i++;
		
		gp.obj[i] = new OBJ_Chest(gp);
		gp.obj[i].worldX = 10 * gp.tileSize;
		gp.obj[i].worldY = 7 *  gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_Boots(gp);
		gp.obj[i].worldX = 37 * gp.tileSize;
		gp.obj[i].worldY = 42 *  gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_Axe(gp);
		gp.obj[i].worldX = 24 * gp.tileSize;
		gp.obj[i].worldY = 22 *  gp.tileSize;
		i++;

		gp.obj[i] = new OBJ_Shield_Blue(gp);
		gp.obj[i].worldX = 30 * gp.tileSize;
		gp.obj[i].worldY = 22 *  gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_Potion_Red(gp);
		gp.obj[i].worldX = 19 * gp.tileSize;
		gp.obj[i].worldY = 22 *  gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_Heart(gp);
		gp.obj[i].worldX = 22 * gp.tileSize;
		gp.obj[i].worldY = 29 *  gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_ManaCrystal(gp);
		gp.obj[i].worldX = 22 * gp.tileSize;
		gp.obj[i].worldY = 31 *  gp.tileSize;
		i++;
	}
	
	public void setNPC() {
		
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize * 21;
		gp.npc[0].worldY = gp.tileSize *21;
		
		gp.npc[1] = new NPC_OldMan(gp);
		gp.npc[1].worldX = gp.tileSize * 11;
		gp.npc[1].worldY = gp.tileSize *21;
		
	}
	
	public void setMonster() {
		int i = 0;
		gp.monster[i] = new MON_GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize*23;
		gp.monster[i].worldY = gp.tileSize*37;
		i++;
		
		gp.monster[i] = new MON_GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize*23;
		gp.monster[i].worldY = gp.tileSize*36;
		i++;
		
		gp.monster[i] = new MON_GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize*24;
		gp.monster[i].worldY = gp.tileSize*37;
		i++;
		
		gp.monster[i] = new MON_GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize*34;
		gp.monster[i].worldY = gp.tileSize*42;
		i++;
		
		gp.monster[i] = new MON_GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize*38;
		gp.monster[i].worldY = gp.tileSize*42;
		i++;
		
	}
	
	public void setInteractiveTile() {
		int i = 0;
		//		gp.iTile[i] = new IT_DryTree(gp,col,row);
		gp.iTile[i] = new IT_DryTree(gp,27,12);
		i++;
		
		gp.iTile[i] = new IT_DryTree(gp,28,12);
		i++;
		
		gp.iTile[i] = new IT_DryTree(gp,29,12);
		i++;
		
		gp.iTile[i] = new IT_DryTree(gp,30,12);
		i++;
		
		gp.iTile[i] = new IT_DryTree(gp,31,12);
		i++;
		
		gp.iTile[i] = new IT_DryTree(gp,32,12);
		i++;
		
		gp.iTile[i] = new IT_DryTree(gp,33,12);
		i++;
	} 
	
}
