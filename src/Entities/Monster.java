package Entities;

import java.util.Random; 

import Entities.GameObjectDynamic.Direction;
import Main.GamePanel;

public class Monster {
	private int speed;
	private int maxLife;
	private int life = maxLife;
	private int attack;
	private int defense;
	private boolean invincible;
	private int invincibleCounter;
	private int flexible;
	private GameObjectDynamic monsterGP;

	public Monster(int x, int y) {
		setMonsterGP(new GameObjectDynamic(x, y));
		monsterGP.loadImage("res/textures/img/monkeyright.png");
		monsterGP.getImageDimension();
		monsterGP.setObjectDricetion(Direction.DOWN);
	}

	public void move(String right, String left, String up, String down) {
		if (monsterGP.getObjectDricetion() == Direction.RIGHT) {
			monsterGP.setX(monsterGP.getX() + speed);
			monsterGP.loadImage(right);
		} else if (monsterGP.getObjectDricetion() == Direction.LEFT) {
			monsterGP.setX(monsterGP.getX() - speed);
			monsterGP.loadImage(left);
		} else if (monsterGP.getObjectDricetion() == Direction.UP) {
			monsterGP.setY(monsterGP.getY() - speed);
			monsterGP.loadImage(up);
		} else if (monsterGP.getObjectDricetion() == Direction.DOWN) {
			monsterGP.setY(monsterGP.getY() + speed);
			monsterGP.loadImage(down);
		}

		Random rd = new Random();

		flexible += 1;
		if (flexible == 100) { // cứ sau 100 chu kỳ timer.DELAY lại chuyển hướng di chuyển
			// random hướng di chuyển
			int key = rd.nextInt(4);// định 0
			switch (key) {
				case 4:
					monsterGP.setObjectDricetion(Direction.DOWN);
					break;
				case 1:
					monsterGP.setObjectDricetion(Direction.LEFT);
					break;
				case 2:
					monsterGP.setObjectDricetion(Direction.UP);
					break;
				case 3:
					monsterGP.setObjectDricetion(Direction.RIGHT);
					break;

			}
			// System.out.println(key);
			// tương ứng với đứng yên)
			flexible = 0;
		}
		if (monsterGP.getX() < 1) {
			monsterGP.setX(1);
		} // ko cho di chuyển tràn khung
		if (monsterGP.getY() < 1) {
			monsterGP.setY(1);
		}
		monsterGP.getImageDimension();
		if (monsterGP.getX() > GamePanel.getSizeX() - monsterGP.getWidth()) {
			monsterGP.setX(GamePanel.getSizeX() - monsterGP.getWidth());
		}
		if (monsterGP.getY() > GamePanel.getSizeY() - monsterGP.getHeight()) {
			monsterGP.setY(GamePanel.getSizeY() - monsterGP.getHeight());
		}

		if (collisionLeft(monsterGP.getX(), monsterGP.getY(), monsterGP.getWidth(), monsterGP.getHeight())) {
			monsterGP.setX(monsterGP.getX() + speed);
		}
		if (collisionRight(monsterGP.getX(), monsterGP.getY(), monsterGP.getWidth(), monsterGP.getHeight())) {
			monsterGP.setX(monsterGP.getX() - speed);
		}
		if (collisionUp(monsterGP.getX(), monsterGP.getY(), monsterGP.getWidth(), monsterGP.getHeight())) {
			monsterGP.setY(monsterGP.getY() + speed);
		}
		if (collisionDown(monsterGP.getX(), monsterGP.getY(), monsterGP.getWidth(), monsterGP.getHeight())) {
			monsterGP.setY(monsterGP.getY() - speed);
		}
	}

	public boolean collisionRight(int x, int y, int width, int height) {
		x = x + width + 1;
		for (int i = 0; i < height; i++) {
			if (GamePanel.map.getEntityMap(x, y).isCollision())
				return true;
			else {
				y = y + 1;
			}
		}
		return false;
	}

	public boolean collisionUp(int x, int y, int width, int height) {
		y = y - 1;
		for (int i = 0; i < width; i++) {
			if (GamePanel.map.getEntityMap(x, y).isCollision())
				return true;
			else {
				x = x + 1;
			}
		}
		return false;
	}

	public boolean collisionDown(int x, int y, int width, int height) {
		y = y + height + 1;
		for (int i = 0; i < width; i++) {
			if (GamePanel.map.getEntityMap(x, y).isCollision())
				return true;
			else {
				x = x + 1;
			}
		}
		return false;
	}

	public boolean collisionLeft(int x, int y, int width, int height) {
		x = x - 1;
		for (int i = 0; i < height; i++) {
			if (GamePanel.map.getEntityMap(x, y).isCollision())
				return true;
			else {
				y = y + 1;
			}
		}
		return false;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}

	public int getFlexible() {
		return flexible;
	}

	public void setFlexible(int flexible) {
		this.flexible = flexible;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getSpeed() {
		return speed;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public GameObjectDynamic getMonsterGP() {
		return monsterGP;
	}

	public void setMonsterGP(GameObjectDynamic monsterGP) {
		this.monsterGP = monsterGP;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public int getInvincibleCounter() {
		return invincibleCounter;
	}

	public void setInvincibleCounter(int invincibleCounter) {
		this.invincibleCounter = invincibleCounter;
	}
}