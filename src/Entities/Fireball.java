package Entities;

import Entities.GameObjectDynamic.Direction;


// public class Fireball extends Bullet{
// 	public Fireball(int x, int y) {
// 		super(x, y);
// 		loadImage("res/textures/img/fireball.png");
// 		getImageDimension();
// 	}

public class Fireball extends Bullet {
	public Fireball(GameObjectDynamic bulletGP) {
        super(bulletGP);
    	bulletGP.loadImage("res/textures/img/fireball.png");
        bulletGP.getImageDimension();
		setBullet_speed(2);
		setBullet_length(300);
		setUseCost(0);
		setFly(0);
    }

    public void move() {
    	if (getBulletGP().getObjectDricetion() == Direction.RIGHT ) getBulletGP().setX(getBulletGP().getX()+ getBullet_speed()) ; // viên đạn bay ngang
        if (getBulletGP().getObjectDricetion() == Direction.UP) getBulletGP().setY(getBulletGP().getY()- getBullet_speed()) ; // viên đạn bay d�?c 
        if (getBulletGP().getObjectDricetion() == Direction.LEFT ) getBulletGP().setX(getBulletGP().getX()- getBullet_speed());
        if (getBulletGP().getObjectDricetion() == Direction.DOWN) getBulletGP().setY(getBulletGP().getY()+ getBullet_speed());
        setFly(getFly()+ getBullet_speed()); // so sánh độ dài đã bay với độ dài đạn
        if (getFly() > getBullet_length()) {
        	getBulletGP().setExist(false);
        }
        if(getBulletGP().getX()<0||getBulletGP().getX()>550||getBulletGP().getY()<0||getBulletGP().getY()>550) {
        	getBulletGP().setExist(false);
        }
    }
}