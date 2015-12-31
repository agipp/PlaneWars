package com.plane.wars.com.plane.wars.widgets;

/**
 * 精灵类
 */
public class SpriteCmd {
    //精灵的x位置
    private int x;
    //精灵的y位置
    private int y;
    //绘制的宽度
    private int unWidth;
    //绘制的高度
    private int unHeight;
    //精灵在图片上的索引值
    private int unSpriteIndex;
    //精灵是否显示，0显示，255不显示
    private int unLayer;
    //计数参量
    private int walkCount;
    private int protectCount;
    private boolean isActive;
    private boolean isRight;
    private boolean isHurt;
    //主角是否停止向左移动
    private boolean isLeftStop;
    //主角是否停止向右移动
    private boolean isRightStop;
    //主角是否停止向上移动
    private boolean isTopStop;
    //主角是否停止向下移动
    private boolean isDownStop;
    //主角状态
    private PlaneState planeState;
    /**
     * 飞机状态
     */
    public enum PlaneState{
        PLANE_STAND,//站
        PLANE_RIGHT,//右移
        PLANE_LEFT,//左移
        PLANE_UP,//上移
        PLANE_DOWN,//下移
        PLANE_HURT,//受伤
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getUnWidth() {
        return unWidth;
    }

    public void setUnWidth(int unWidth) {
        this.unWidth = unWidth;
    }

    public int getUnHeight() {
        return unHeight;
    }

    public void setUnHeight(int unHeight) {
        this.unHeight = unHeight;
    }

    public int getUnSpriteIndex() {
        return unSpriteIndex;
    }

    public void setUnSpriteIndex(int unSpriteIndex) {
        this.unSpriteIndex = unSpriteIndex;
    }

    public int getUnLayer() {
        return unLayer;
    }

    public void setUnLayer(int unLayer) {
        this.unLayer = unLayer;
    }

    public int getWalkCount() {
        return walkCount;
    }

    public void setWalkCount(int walkCount) {
        this.walkCount = walkCount;
    }

    public int getProtectCount() {
        return protectCount;
    }

    public void setProtectCount(int protectCount) {
        this.protectCount = protectCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }

    public boolean isHurt() {
        return isHurt;
    }

    public void setIsHurt(boolean isHurt) {
        this.isHurt = isHurt;
    }

    public boolean isLeftStop() {
        return isLeftStop;
    }

    public void setIsLeftStop(boolean isLeftStop) {
        this.isLeftStop = isLeftStop;
    }

    public boolean isRightStop() {
        return isRightStop;
    }

    public void setIsRightStop(boolean isRightStop) {
        this.isRightStop = isRightStop;
    }

    public boolean isTopStop() {
        return isTopStop;
    }

    public void setIsTopStop(boolean isTopStop) {
        this.isTopStop = isTopStop;
    }

    public boolean isDownStop() {
        return isDownStop;
    }

    public void setIsDownStop(boolean isDownStop) {
        this.isDownStop = isDownStop;
    }

    public PlaneState getPlaneState() {
        return planeState;
    }

    public void setPlaneState(PlaneState planeState) {
        this.planeState = planeState;
    }
}
