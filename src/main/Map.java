package Main;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.SwitchPoint;
import java.util.Scanner;

import javax.swing.ImageIcon;

import Entities.GameObject;
import Entities.Grass;
import Entities.StaticEntity;

public class Map {
	private Scanner m;
//	private String Map[] = new String[20];
//	String pathname = "res/worlds/map.txt";
	private final int mapRow = 20;
	private final int mapCol = 20;
	StaticEntity gameMap[][] = new StaticEntity[mapCol][mapRow];

	// List map entity
	public enum EntityList {
		GRASS(0), TREE(1), WATER(2), ROCK(3), BRIDGE(4), EARTH(5), NEWLANDDOOR(9);

		private final int value;

		private EntityList(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public Map(String pathName) {
		newGameMap(pathName);
	}

	public void newGameMap(String pathName) {
		String map[] = new String[mapRow];
		try {
			// m= new Scanner(new File("src/data/map.txt"));
			m = new Scanner(new File(pathName));
		} catch (FileNotFoundException e) {
			System.out.println("File not found !");
		}
		while (m.hasNext()) {
			for (int i = 0; i < mapRow; i++) {
				map[i] = m.next();
			}
		}
		m.close();
		int entityIndex;
		GameObject graphics;
		ImageIcon entityImage;
		for (int i = 0; i < mapRow; i++) {
			for (int j = 0; j < mapCol; j++) {
				entityIndex = Integer.parseInt(map[i].substring(j, j + 1));

				if (entityIndex == EntityList.GRASS.getValue()) {
					entityImage = new ImageIcon("res/textures/img/grass.png");
					graphics = new GameObject(i, j, GamePanel.getTilesize(), GamePanel.getTilesize(), entityImage.getImage());
					gameMap[j][i] = new Grass(graphics, "Grass", false);
				} else if (entityIndex == EntityList.TREE.getValue()) {
					entityImage = new ImageIcon("res/textures/img/tree_grass_1.png");
					graphics = new GameObject(i, j, GamePanel.getTilesize(), GamePanel.getTilesize(), entityImage.getImage());
					gameMap[j][i] = new Grass(graphics, "Tree", true);

				} else if (entityIndex == EntityList.WATER.getValue()) {
					entityImage = new ImageIcon("res/textures/img/water.png");
					graphics = new GameObject(i, j, GamePanel.getTilesize(), GamePanel.getTilesize(), entityImage.getImage());
					gameMap[j][i] = new Grass(graphics, "Water", true);

				} else if (entityIndex == EntityList.ROCK.getValue()) {
					entityImage = new ImageIcon("res/textures/img/rock_dirt.png");
					graphics = new GameObject(i, j, GamePanel.getTilesize(), GamePanel.getTilesize(), entityImage.getImage());
					gameMap[j][i] = new Grass(graphics, "Rock", true);

				} else if (entityIndex == EntityList.BRIDGE.getValue()) {
					entityImage = new ImageIcon("res/textures/img/bridge.png");
					graphics = new GameObject(i, j, GamePanel.getTilesize(), GamePanel.getTilesize(), entityImage.getImage());
					gameMap[j][i] = new Grass(graphics, "Bridge", false);

				} else if (entityIndex == EntityList.EARTH.getValue()) {
					entityImage = new ImageIcon("res/textures/img/dirt.png");
					graphics = new GameObject(i, j, GamePanel.getTilesize(), GamePanel.getTilesize(), entityImage.getImage());
					gameMap[j][i] = new Grass(graphics, "Earth", false);
				} else if (entityIndex == EntityList.NEWLANDDOOR.getValue()) {
					entityImage = new ImageIcon("res/textures/img/portal.png");
					graphics = new GameObject(i, j, GamePanel.getTilesize(), GamePanel.getTilesize(), entityImage.getImage());
					gameMap[j][i] = new Grass(graphics, "New Land Door", false);
				}
				
			}

		}
	}

	public StaticEntity getEntityMap(int x, int y) {
		return gameMap[x / GamePanel.getTilesize()][y / GamePanel.getTilesize()];
	}

	public int getMapCol() {
		return mapCol;
	}

	public int getMapRow() {
		return mapRow;
	}
}