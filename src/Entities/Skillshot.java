package Entities;

import Entities.GameObjectDynamic.Direction;

public class Skillshot extends Bullet {
//	private final int bullet_speed = 10;
//	private final int bullet_length = 600;
//	private int useCost = 1;
	
	public Skillshot(GameObjectDynamic bulletGP) {
        super(bulletGP);
    	bulletGP.loadImage("res/textures/img/laser_attack.png");
        bulletGP.getImageDimension();
		setBullet_speed(10);
		setBullet_length(600);
		setUseCost(1);
		setFly(0);
    }

    public void move() {
    	if (getBulletGP().getObjectDricetion() == Direction.RIGHT ) getBulletGP().setX(getBulletGP().getX()+getBullet_speed()); // viên đạn bay ngang
        if (getBulletGP().getObjectDricetion() == Direction.UP) getBulletGP().setY(getBulletGP().getY()-getBullet_speed()); // viên đạn bay d�?c 
        if (getBulletGP().getObjectDricetion() == Direction.LEFT ) getBulletGP().setX(getBulletGP().getX()-getBullet_speed());
        if (getBulletGP().getObjectDricetion() == Direction.DOWN) getBulletGP().setY(getBulletGP().getY()+getBullet_speed());
        setFly(getFly() + getBullet_speed()  ); // so sánh độ dài đã bay với độ dài đạn
        if (getFly() > getBullet_length() ) {
        	getBulletGP().setExist(false);
        }
        if(getBulletGP().getX()<0||getBulletGP().getX()>600||getBulletGP().getY()<0||getBulletGP().getY()>600) {
        	getBulletGP().setExist(false);
        }
    }
}