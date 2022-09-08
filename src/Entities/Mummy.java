package Entities;

import Entities.GameObjectDynamic.Direction;

public class Mummy extends Monster {

  private static final int SPEED = 1;
  private static final int MAX_LIFE = 4;
  private static final int ATTACK = 2;
  private static final int DEFENSE = 1;

  public Mummy(int x, int y) {
    super(x, y);
    setSpeed(SPEED);
    setAttack(ATTACK);
    setDefense(DEFENSE);
    setLife(MAX_LIFE);
    setFlexible(0);
    setInvincibleCounter(0);
    setInvincible(false);
  }

  public void move() {
    move("res/textures/img/mummy_right.png", "res/textures/img/mummy_left.png", "res/textures/img/mummy_up.png",
        "res/textures/img/mummy_down.png");
  }

}
