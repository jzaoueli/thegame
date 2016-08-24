package main.model;

import main.view.FrameAnimation;

import java.awt.*;
import java.io.IOException;

/**
 * Enemy Class
 * TODO Documentation
 */
public class Enemy {

    /**
     * Enemy Animation Frames
     */
    public FrameAnimation enemyAnimation;
    /**
     * Enemy Movement Type
     */
    public String movement;
    /**
     * Enemy speed
     */
    public int speed;
    /**
     * Enemy Attack or Players LifePoints loss
     */
    private int attack;
    /**
     * Enemy Defense or Player Hits needed for Kill
     */
    private int defense;
    /**
     * Enemy Coordinates
     */
    private int x;
    private int y;
    /**
     * Enemy State 1 = move, 2 = hit, 3 = dead
     */
    private int state;
    /**
     * Enemy Collision Area
     */
    private Rectangle boundingBox;

    public Enemy(){
    }
    /**
     * TODO Documentation
     * @param enemyData //TODO: docs
     * @throws IOException
     */
    public Enemy(Object[] enemyData, int startX) throws IOException {
        this.enemyAnimation = new FrameAnimation(enemyData, 8);
        this.enemyAnimation.setActionFrames(getState());
        this.setEnemyData(enemyData);
        this.setX(startX);
        this.setY(0);
        this.setBoundingBox(new Rectangle(this.getX(), this.getY(), this.enemyAnimation.sizeX, this.enemyAnimation.sizeY));
    }

    /**
     * Sets Enemy in Movement
     * @param movementType Enemy Movement Type (vertical, zigzag, wave, trapeze)
     */
    public void setMovement(String movementType) {
        switch (movementType){
            case "vertical":
                this.setY(this.getY() + this.getSpeed());
        }
        this.setBoundingBox(new Rectangle(this.getX(), this.getY(), this.enemyAnimation.sizeX, this.enemyAnimation.sizeY));
    }

    /**
     * Sets Enemy Data
     * @param enemyData Object Array of generated Enemy Data
     */
    private void setEnemyData(Object[] enemyData) {
        this.movement = enemyData[5].toString();
        this.speed = (int)(enemyData[6]);
        this.attack = (int)(enemyData[7]);
        this.defense = (int)(enemyData[8]);
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    private int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    private void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
