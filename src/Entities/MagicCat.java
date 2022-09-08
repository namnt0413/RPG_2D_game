package Entities;

import Main.*;
import Main.Map.EntityList;

import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Entities.GameObjectDynamic.Direction;

public class MagicCat {

	private List<MagicBall> magicballs;
	private List<Skillshot> skillshots;

	private int life = 8; // health
	private int maxLife = 8;
	private int speed = 8;
	private int maxMana = 12;

	private int mana = 12;
	private int attack = 4;
	private int defense = 1;
	private int skillAttack = 10;
	private boolean invincible = false;
	private int invincibleCounter = 0;
	private int collidedCounter = 0;
	private int shotAvailable = 0;
	private int skillAvailable = 0;
	private boolean isCollided;
	private GameObjectDynamic magicCatGP;

	public MagicCat(int x, int y) {
		setMagicCatGP(new GameObjectDynamic(x, y));
		magicballs = new ArrayList<>();
		skillshots = new ArrayList<>();
		magicCatGP.loadImage("res/textures/img/down.png");
		magicCatGP.getImageDimension();
		magicCatGP.setObjectDricetion(Direction.DOWN);
	}

	public void move() {
		if (magicCatGP.getX() - speed < 1) {
			magicCatGP.setX(1);
		}
		if (magicCatGP.getY() - speed < 1) {
			magicCatGP.setY(1);
		}

		magicCatGP.getImageDimension();
		if (magicCatGP.getX() + speed > GamePanel.getSizeX() - magicCatGP.getWidth()) {
			magicCatGP.setX(GamePanel.getSizeX() - magicCatGP.getWidth());
		}
		if (magicCatGP.getY() + speed > GamePanel.getSizeY() - magicCatGP.getHeight()) {
			magicCatGP.setY(GamePanel.getSizeY() - magicCatGP.getHeight());
		}

		if (collisionLeft(magicCatGP.getX(), magicCatGP.getY(), magicCatGP.getWidth(), magicCatGP.getHeight())) {
			magicCatGP.setX(magicCatGP.getX() + speed);
		}
		if (collisionRight(magicCatGP.getX(), magicCatGP.getY(), magicCatGP.getWidth(), magicCatGP.getHeight())) {
			magicCatGP.setX(magicCatGP.getX() - speed);
		}
		if (collisionUp(magicCatGP.getX(), magicCatGP.getY(), magicCatGP.getWidth(), magicCatGP.getHeight())) {
			magicCatGP.setY(magicCatGP.getY() + speed);
		}
		if (collisionDown(magicCatGP.getX(), magicCatGP.getY(), magicCatGP.getWidth(), magicCatGP.getHeight())) {
			magicCatGP.setY(magicCatGP.getY() - speed);
		}


//		Check 4 canh cua nhan vat neu nhu vao trong cua thi xuat hien
		if (GamePanel.map.getEntityMap(magicCatGP.getX(), magicCatGP.getY()).getName().equals("New Land Door")
				|| GamePanel.map.getEntityMap(magicCatGP.getX() + magicCatGP.getWidth(), magicCatGP.getY()).getName().equals("New Land Door")
				|| GamePanel.map.getEntityMap(magicCatGP.getX(), magicCatGP.getY() + magicCatGP.getHeight()).getName().equals("New Land Door")
				|| GamePanel.map.getEntityMap(magicCatGP.getX() + magicCatGP.getWidth(), magicCatGP.getY() + magicCatGP.getHeight()).getName().equals("New Land Door")
						) {
			GamePanel.setDoor_appared();
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

	public void castMagicBall() {
		int xz = 0, yz = 0;
		GameObjectDynamic bulletGP;
		if (magicCatGP.getObjectDricetion() == Direction.RIGHT) {
			xz = magicCatGP.getX() + magicCatGP.getWidth();
			yz = magicCatGP.getY() + magicCatGP.getHeight() / 2;
		} else if (magicCatGP.getObjectDricetion() == Direction.LEFT) {
			xz = magicCatGP.getX();
			yz = magicCatGP.getY() + magicCatGP.getHeight() / 2;
		} else if (magicCatGP.getObjectDricetion() == Direction.DOWN) {
			xz = magicCatGP.getX() + magicCatGP.getWidth() / 2;
			yz = magicCatGP.getY() + magicCatGP.getHeight();
		} else if (magicCatGP.getObjectDricetion() == Direction.UP) {
			xz = magicCatGP.getX() + magicCatGP.getWidth() / 2;
			yz = magicCatGP.getY();
		}
		bulletGP = new GameObjectDynamic(xz, yz);
		MagicBall MagicBall_new = new MagicBall(bulletGP);
		MagicBall_new.getBulletGP().setObjectDricetion(magicCatGP.getObjectDricetion());
		magicballs.add(MagicBall_new);
		mana -= MagicBall_new.getUseCost();
	}

	public void castSkillshot() {
		if (mana > 0) {
			GameObjectDynamic bulletGP;
			int xz = 0, yz = 0;
			if (magicCatGP.getObjectDricetion() == Direction.RIGHT) {
				xz = getMagicCatGP().getX() + getMagicCatGP().getWidth()/2;
				yz = getMagicCatGP().getY() + getMagicCatGP().getHeight() / 2;
				for (int i = 0; i < GamePanel.getSizeX() - xz -140; i++) {
					bulletGP = new GameObjectDynamic(xz + i, yz);
					Skillshot skillshot_new = new Skillshot(bulletGP);
					skillshot_new.getBulletGP().setObjectDricetion(magicCatGP.getObjectDricetion());
					skillshots.add(skillshot_new);
				}
				bulletGP = new GameObjectDynamic(0, 0);
				Skillshot skillshot_new = new Skillshot(bulletGP);
				mana -= skillshot_new.getUseCost();
			} else if (magicCatGP.getObjectDricetion() == Direction.LEFT) {
				xz = getMagicCatGP().getX();
				yz = getMagicCatGP().getY() + getMagicCatGP().getHeight() / 2;
				for (int i = 0; i < xz; i++) {
					bulletGP = new GameObjectDynamic(xz - i, yz);
					Skillshot skillshot_new = new Skillshot(bulletGP);
					skillshot_new.getBulletGP().setObjectDricetion(magicCatGP.getObjectDricetion());
					skillshots.add(skillshot_new);
				}
				bulletGP = new GameObjectDynamic(0, 0);
				Skillshot skillshot_new = new Skillshot(bulletGP);
				mana -= skillshot_new.getUseCost();
			} else if (magicCatGP.getObjectDricetion() == Direction.DOWN) {
				xz = getMagicCatGP().getX() + getMagicCatGP().getWidth() / 2;
				yz = getMagicCatGP().getY() + getMagicCatGP().getHeight()/2;
				for (int i = 0; i < GamePanel.getSizeY() - yz; i++) {
					bulletGP = new GameObjectDynamic(xz, yz + i);	 
					Skillshot skillshot_new = new Skillshot(bulletGP);
					skillshot_new.getBulletGP().setObjectDricetion(magicCatGP.getObjectDricetion());
					skillshots.add(skillshot_new);
				}
				bulletGP = new GameObjectDynamic(0, 0);	 
				Skillshot skillshot_new = new Skillshot(bulletGP);
				mana -= skillshot_new.getUseCost();
			} else if (magicCatGP.getObjectDricetion() == Direction.UP) {
				xz = getMagicCatGP().getX() + getMagicCatGP().getWidth() / 3;
				yz = getMagicCatGP().getY();
				for (int i = 0; i < yz; i++) {
					bulletGP = new GameObjectDynamic(xz, yz - i);
					Skillshot skillshot_new = new Skillshot(bulletGP);
					skillshot_new.getBulletGP().setObjectDricetion(magicCatGP.getObjectDricetion());
					skillshots.add(skillshot_new);
				}
				bulletGP = new GameObjectDynamic(0, 0);
				Skillshot skillshot_new = new Skillshot(bulletGP);
				mana -= skillshot_new.getUseCost();
			
			}
		}

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

	public int getMana() {
		return mana;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getSkillAttack() {
		return skillAttack;
	}

	public GameObjectDynamic getMagicCatGP() {
		return magicCatGP;
	}

	public void setMagicCatGP(GameObjectDynamic magicCatGP) {
		this.magicCatGP = magicCatGP;
	}

	public List<MagicBall> getMagicBalls() {
		return magicballs;
	}

	public List<Skillshot> getSkillshots() {
		return skillshots;
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

	public int getCollidedCounter() {
		return collidedCounter;
	}

	public void setCollidedCounter(int collidedCounter) {
		this.collidedCounter = collidedCounter;
	}

	public boolean getCollided() {
		return isCollided;
	}

	public void setCollided(boolean isCollided) {
		this.isCollided = isCollided;
	}

	public boolean isCollided() {
		return isCollided;
	}

	public void setSpeed(int i) {
		this.speed = i;
	}

	public int getShotAvailable() {
		return shotAvailable;
	}

	public void setShotAvailable(int i) {
		this.shotAvailable = i;
	}
	public int getSkillAvailable() {
		return skillAvailable;
	}

	public void setSkillAvailable(int i) {
		this.skillAvailable = i;
	}
}
