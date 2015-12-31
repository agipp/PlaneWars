package com.plane.wars.com.plane.wars.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.plane.wars.MainActivity;
import com.plane.wars.R;

/**
 * 主视图界面
 */
public class GameView extends View implements Runnable {
    private Context context;
    //获取设备屏幕宽度
    private int screenWidth;
    //获取设备屏幕高度
    private int screenHeight;
    //获取主视图宽度
    private int gameViewWidth;
    //获取主视图高度
    private int gameViewHeight;
    //游戏背景图片
    private Bitmap imgBackground;
    //开始游戏-位置确认
    private ButtonUtil imgStartButtonUtil;
    //开始游戏
    private Bitmap imgStart;
    //帮助-位置确认
    private ButtonUtil imgHelpButtonUtil;
    //帮助
    private Bitmap imgHelp;
    //返回-位置确认
    private ButtonUtil imgBackButtonUtil;
    //返回
    private Bitmap imgBack;

    //游戏关卡-第1关位置确认
    private ButtonUtil imgFirstLevelButtonUtil;
    //游戏关卡-第1关
    private Bitmap imgFirstLevel;

    //游戏关卡-第2关位置确认
    private ButtonUtil imgSecondLevelButtonUtil;
    //游戏关卡-第2关
    private Bitmap imgSecondLevel;

    //游戏关卡-第3关位置确认
    private ButtonUtil imgThreeLevelButtonUtil;
    //游戏关卡-第3关
    private Bitmap imgThreeLevel;

    //游戏关卡-第4关位置确认
    private ButtonUtil imgFourLevelButtonUtil;
    //游戏关卡-第4关
    private Bitmap imgFourLevel;

    //游戏过关图片
    private Bitmap imgPassLevel;
    //游戏血量背景图
    private Bitmap imgBloodBg;
    //游戏血量图片
    private Bitmap imgBlood;
    //星星图片
    private Bitmap imgStar;
    //飞机图片
    private Bitmap imgPlane;
    //飞机图片-位置
//    private ButtonUtil imgPlaneButtonUtil;
    //子弹图片
    private Bitmap imgBullet;
    //敌机1图片
    private Bitmap imgEnemy1;
    //敌机2图片
    private Bitmap imgEnemy2;
    //爆炸图片图片
    private Bitmap imgBomb;
    //声音开关
    private Bitmap imgSoundOn;
    private Bitmap imgSoundOff;
    //声音开关-位置
    private ButtonUtil imgSoundButtonUtil;

    //星星数量
    private static final int STAR_NUM = 30;
    //主角子弹数量
    private static final int BULLET_NUM = 20;
    //敌人死亡时候爆炸对象数量
    private static final int BOMB_NUM = 5;
    //主角对象
    private SpriteCmd rgPlaneCmd = new SpriteCmd();
    //主角子弹对象数组
    private SpriteCmd rgBulletCmd[] = new SpriteCmd[BULLET_NUM];
    //星星对象数组
    private SpriteCmd rgStarCmd[] = new SpriteCmd[STAR_NUM];
    //敌人1对象
    private SpriteCmd rgEmeny1Cmd = new SpriteCmd();
    //敌人2对象
    private SpriteCmd rgEmeny2Cmd = new SpriteCmd();
    //敌人3对象
    private SpriteCmd rgEmeny3Cmd = new SpriteCmd();
    //敌人死亡时候爆炸对象数组
    private SpriteCmd rgBombCmd[] = new SpriteCmd[BOMB_NUM];
    //背景对象1
    private SpriteCmd rgBackground1 = new SpriteCmd();
    //背景对象2
    private SpriteCmd rgBackground2 = new SpriteCmd();
    //恭喜过关对象
    private SpriteCmd rgLevel = new SpriteCmd();
    //血量框对象
    private SpriteCmd rgBloodBg = new SpriteCmd();
    //血量对象
    private SpriteCmd rgBlood = new SpriteCmd();
    //主角宽度
    private static final int planeWidth = 75;
    //主角高度
    private static final int planeHeight = 68;
    //敌机宽度
    private static final int emenyWidth = 35;
    //敌机高度
    private static final int emenyHeight = 35;
    //精灵不可见
    private static final int layerGone = 255;
    //精灵可见
    private static final int layerVisible = 0;
    //恭喜过关对象宽度
    private static final int LEVEL_WIDTH = 200;
    //恭喜过关对象高度
    private static final int LEVEL_HEIGHT = 40;
    //血量背景宽度
    private static final int BLOODBG_WIDTH = 100;
    //血量背景高度
    private static final int BLOODBG_HEIGHT = 32;
    //血量背景宽度
    private static final int BLOOD_WIDTH = 96;
    //血量背景高度
    private static final int BLOOD_HEIGHT = 14;
    //子弹宽度
    private static final int BULLET_WIDTH = 25;
    //子弹高度
    private static final int BULLET_HEIGHT = 29;

    //声音
    MediaPlayer mediaPlayer;
    //当前游戏状态
    public GameState gameState;
    //游戏是否过关
    private boolean isLevelPass;
    //敌人死亡计数器
    private int deadEnemyCnt = 0;
    //过关界面的计数器
    private int levelCnt = 0;
    int gameLevel = 1;
    //主计时器
    private int mainTime = 0;

    private boolean isExit;
    private boolean isPlaySound = true;

    public GameView(Context context) {
        super(context);
        this.context = context;
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        this.screenWidth = metrics.widthPixels;
        this.screenHeight = metrics.heightPixels;
        //启动线程
        new Thread(this).start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.gameViewWidth = w;
        this.gameViewHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
        this.turnToMenu();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mainTime++; // 主计时时间增加
        if (mainTime == 100000000) {
            mainTime = 0;
        }
        if (rgBackground1.getY() >= 0) {
            rgBackground1.setY(-gameViewHeight);
        }
        if (rgBackground2.getY() >= gameViewHeight) {
            rgBackground2.setY(0);
        }
        switch (gameState) {
            case GAMESTATE_MENU:
                drawMenu(canvas);
                break;
            case GAMESTATE_LEVELGUIDE:
                drawGameLevel(canvas);
                break;
            case GAMESTATE_GAME:
                drawGame(canvas);
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (gameState) {
                    case GAMESTATE_MENU:
                        onTouchEventMenu(event);
                        break;
                    case GAMESTATE_LEVELGUIDE:
                        onTouchEventLevel(event);
                        break;
                }
            case MotionEvent.ACTION_MOVE:
                if (gameState == GameState.GAMESTATE_GAME) {
                    onTouchEventGame(event);
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    /**
     * 进入菜单
     */
    private void turnToMenu() {
        this.imgBackground = ((BitmapDrawable) getResources().getDrawable(R.drawable.sky1)).getBitmap();
        this.imgStart = ((BitmapDrawable) getResources().getDrawable(R.drawable.start)).getBitmap();
        this.imgHelp = ((BitmapDrawable) getResources().getDrawable(R.drawable.help)).getBitmap();
        this.imgBack = ((BitmapDrawable) getResources().getDrawable(R.drawable.back)).getBitmap();

        //设定按钮位置
        this.imgStartButtonUtil = new ButtonUtil(this.imgStart, (this.gameViewWidth - this.imgStart.getWidth()) / 2, 100);
        this.imgHelpButtonUtil = new ButtonUtil(this.imgHelp, (this.gameViewWidth - this.imgHelp.getWidth()) / 2, 200);
        this.imgBackButtonUtil = new ButtonUtil(this.imgBack, this.gameViewWidth - this.imgBack.getWidth() - 50, this.gameViewHeight - 100);

        gameState = GameState.GAMESTATE_MENU;
    }

    /**
     * 进入到游戏等级界面
     */
    private void turnToLevelGuide() {
        releaseImage(imgBackground);
        //将背景图片替换为游戏等级背景图
        this.imgBackground = ((BitmapDrawable) getResources().getDrawable(R.drawable.sky2)).getBitmap();
        if (this.imgFirstLevel == null) {
            this.imgFirstLevel = ((BitmapDrawable) getResources().getDrawable(R.drawable.firstlever)).getBitmap();
        }
        if (this.imgSecondLevel == null) {
            this.imgSecondLevel = ((BitmapDrawable) getResources().getDrawable(R.drawable.secondlever)).getBitmap();
        }

        //游戏关卡第1关位置
        this.imgFirstLevelButtonUtil = new ButtonUtil(this.imgFirstLevel, (this.gameViewWidth - this.imgFirstLevel.getWidth()) / 2, 100);
        //游戏关卡第2关位置
        this.imgSecondLevelButtonUtil = new ButtonUtil(this.imgSecondLevel, (this.gameViewWidth - this.imgSecondLevel.getWidth()) / 2, 200);

        gameState = GameState.GAMESTATE_LEVELGUIDE;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    /**
     * 进入到游戏第[?]关
     */
    private void turnGameLevel() {
        isLevelPass = false;
        this.gameState = GameState.GAMESTATE_GAME;
        loadGameResource();
        initLevel();
        //两张背景图
        rgBackground1.setX(0);
        rgBackground1.setY(-gameViewHeight);
        rgBackground1.setUnLayer(layerVisible);
        rgBackground1.setUnWidth(gameViewWidth);
        rgBackground1.setUnHeight(gameViewHeight);

        rgBackground2.setX(0);
        rgBackground2.setY(0);
        rgBackground2.setUnLayer(layerVisible);
        rgBackground2.setUnWidth(gameViewWidth);
        rgBackground2.setUnHeight(gameViewHeight);

        //声音开关
        imgSoundOn = ((BitmapDrawable) getResources().getDrawable(R.drawable.bgsoundon)).getBitmap();
        imgSoundOff = ((BitmapDrawable) getResources().getDrawable(R.drawable.bgsoundoff)).getBitmap();
        imgSoundButtonUtil = new ButtonUtil(imgSoundOn, 50, gameViewHeight - 100);

        //恭喜过关对象
        rgLevel.setUnWidth(LEVEL_WIDTH);
        rgLevel.setUnHeight(LEVEL_HEIGHT);
        rgLevel.setUnLayer(layerGone);

        //血量背景对象
        rgBloodBg.setUnWidth(BLOODBG_WIDTH);
        rgBloodBg.setUnHeight(BLOODBG_HEIGHT);
        rgBloodBg.setUnLayer(layerVisible);
        rgBloodBg.setX(0);
        rgBloodBg.setY(0);

        //血量对象
        rgBlood.setUnWidth(BLOOD_WIDTH);
        rgBlood.setUnHeight(BLOOD_HEIGHT);
        rgBlood.setUnLayer(layerVisible);
        rgBlood.setX(rgBlood.getX());
        rgBlood.setY(10);

        //声音初始化

        mediaPlayer = MediaPlayer.create(this.context, R.raw.game);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

    }

    /**
     * 载入游戏资源
     */
    private void loadGameResource() {
        //释放游戏关卡背景图
        releaseImage(this.imgBackground);
        //载入关卡1/2/3/4背景图
        this.imgBackground = ((BitmapDrawable) getResources().getDrawable(R.drawable.level1)).getBitmap();
        this.imgPassLevel = ((BitmapDrawable) getResources().getDrawable(R.drawable.passlevel)).getBitmap();
        this.imgBloodBg = ((BitmapDrawable) getResources().getDrawable(R.drawable.bloodbg)).getBitmap();
        this.imgBlood = ((BitmapDrawable) getResources().getDrawable(R.drawable.blood)).getBitmap();
        this.imgStar = ((BitmapDrawable) getResources().getDrawable(R.drawable.star)).getBitmap();
        this.imgPlane = ((BitmapDrawable) getResources().getDrawable(R.drawable.plane4)).getBitmap();
        this.imgBullet = ((BitmapDrawable) getResources().getDrawable(R.drawable.bullet2)).getBitmap();
        this.imgEnemy1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.enemy1)).getBitmap();
        this.imgEnemy2 = ((BitmapDrawable) getResources().getDrawable(R.drawable.enemy2)).getBitmap();
        this.imgBomb = ((BitmapDrawable) getResources().getDrawable(R.drawable.bomb)).getBitmap();
    }

    private void initLevel() {
        switch (gameLevel) {

            case 1:
                //初始化主角参数
                rgPlaneCmd.setUnWidth(planeWidth);
                rgPlaneCmd.setUnHeight(planeHeight);
                rgPlaneCmd.setX((gameViewWidth - rgPlaneCmd.getUnWidth()) / 2);
                rgPlaneCmd.setY((gameViewHeight - rgPlaneCmd.getUnHeight() - 100));
                rgPlaneCmd.setPlaneState(SpriteCmd.PlaneState.PLANE_STAND);
                //初始化敌机参数
                rgEmeny1Cmd.setUnWidth(emenyWidth);
                rgEmeny1Cmd.setUnHeight(emenyHeight);
                rgEmeny1Cmd.setUnLayer(layerGone);

                rgEmeny2Cmd.setUnWidth(emenyWidth);
                rgEmeny2Cmd.setUnHeight(emenyHeight);
                rgEmeny2Cmd.setUnLayer(layerGone);

                rgEmeny3Cmd.setUnWidth(emenyWidth);
                rgEmeny3Cmd.setUnHeight(emenyHeight);
                rgEmeny3Cmd.setUnLayer(layerGone);
                rgEmeny3Cmd.setIsRight(false);
                break;
        }

        //星星初始化
        for (int i = 0; i < STAR_NUM; i++) {
            rgStarCmd[i] = new SpriteCmd();
            if (i % 2 == 0) {
                //偶数下标为0
                rgStarCmd[i].setUnSpriteIndex(0);
            } else {
                //奇数下标为1
                rgStarCmd[i].setUnSpriteIndex(1);
            }
            rgStarCmd[i].setUnWidth(3);
            rgStarCmd[i].setUnHeight(3);
            rgStarCmd[i].setX(gameViewWidth / STAR_NUM * i + 10);
            rgStarCmd[i].setY(gameViewHeight / STAR_NUM * i);
        }

        //子弹初始化
        for (int i = 0; i < BULLET_NUM; i++) {
            rgBulletCmd[i] = new SpriteCmd();
            rgBulletCmd[i].setUnWidth(BULLET_WIDTH);
            rgBulletCmd[i].setUnHeight(BULLET_HEIGHT);
            rgBulletCmd[i].setUnLayer(layerGone);
        }

        //炸弹初始化

        for (int i = 0; i < BOMB_NUM; i++) {
            rgBombCmd[i] = new SpriteCmd();
            rgBombCmd[i].setUnWidth(40);
            rgBombCmd[i].setUnHeight(40);
            rgBombCmd[i].setUnLayer(layerGone);
        }

        deadEnemyCnt = 0;
        levelCnt = 0;
    }

    /**
     * 画菜单
     *
     * @param canvas
     */
    private void drawMenu(Canvas canvas) {
        //画菜单中-背景图
        displayImage(canvas, this.imgBackground, this.imgBackground.getWidth(), this.imgBackground.getHeight());
        //画菜单中-开始游戏
        this.imgStartButtonUtil.drawButton(canvas, null);
        //画菜单中-帮助
        this.imgHelpButtonUtil.drawButton(canvas, null);
        //画菜单中-返回
        this.imgBackButtonUtil.drawButton(canvas, null);
    }

    private void drawGameLevel(Canvas canvas) {
        //游戏关卡-背景图
        displayImage(canvas, this.imgBackground, this.imgBackground.getWidth(), this.imgBackground.getHeight());
        //游戏关卡-第1关
        this.imgFirstLevelButtonUtil.drawButton(canvas, null);
        //游戏关卡-第2关
        this.imgSecondLevelButtonUtil.drawButton(canvas, null);
        //游戏关卡-返回
        this.imgBackButtonUtil.drawButton(canvas, null);
    }

    /**
     * 开始绘制游戏
     *
     * @param canvas
     */
    private void drawGame(Canvas canvas) {
        drawPlaneCmd();
        //主角与敌机的碰撞处理
        if ((checkHitEnemy(rgPlaneCmd, rgEmeny1Cmd) || checkHitEnemy(rgPlaneCmd, rgEmeny2Cmd) || checkHitEnemy(rgPlaneCmd, rgEmeny3Cmd)) || !rgPlaneCmd.isHurt()) {
            rgPlaneCmd.setPlaneState(SpriteCmd.PlaneState.PLANE_HURT);
            rgPlaneCmd.setProtectCount(18);
            rgPlaneCmd.setIsHurt(true);
            rgBlood.setUnWidth(rgBlood.getUnWidth() - 19);
            if (rgBlood.getUnWidth() < 0) {
                rgBlood.setUnWidth(96);
            }
        }

        //主角子弹与敌机1的处理
        drawBulletAndEnemy(rgEmeny1Cmd);
        //主角子弹与敌机2的处理
        drawBulletAndEnemy(rgEmeny2Cmd);
        //主角子弹与敌机3的处理
        drawBulletAndEnemy(rgEmeny3Cmd);

        //炸弹处理
        for (int i = 0; i < BOMB_NUM; i++) {
            if (rgBombCmd[i].getUnLayer() != layerGone) {
                if (rgBombCmd[i].getWalkCount() < 5) {
                    rgBombCmd[i].setUnSpriteIndex(rgBombCmd[i].getWalkCount());
                } else {
                    rgBombCmd[i].setUnLayer(layerGone);
                }
                rgBombCmd[i].setWalkCount(rgBombCmd[i].getWalkCount() + 1);
            }
        }

        //画精灵背景

        drawSprites(canvas, imgBackground, rgBackground1);
        drawSprites(canvas, imgBackground, rgBackground2);
        rgBackground1.setY(rgBackground1.getY() + 10);
        rgBackground2.setY(rgBackground2.getY() + 10);

        //画血量
        drawSprites(canvas, imgBloodBg, rgBloodBg);
        drawSprites(canvas, imgBlood, rgBlood);

        //过关提示
        if (deadEnemyCnt > 30 && gameLevel == 1) {
            isLevelPass = true;
        }
        //过关，主机/敌机/子弹全部消失，提示恭喜过关
        if (isLevelPass) {
            levelCnt++;
            rgPlaneCmd.setUnLayer(layerGone);
            rgEmeny1Cmd.setUnLayer(layerGone);
            rgEmeny2Cmd.setUnLayer(layerGone);
            rgEmeny3Cmd.setUnLayer(layerGone);
            for (int i = 0; i < BULLET_NUM; i++) {
                rgBulletCmd[i].setUnLayer(layerGone);
            }
            mediaPlayer.pause();
            rgLevel.setUnLayer(layerVisible);
            rgLevel.setX((gameViewWidth - rgLevel.getUnWidth()) / 2);
            rgLevel.setY((gameViewHeight - rgLevel.getUnHeight()) / 2);

            //恭喜过关
            drawSprites(canvas, imgPassLevel, rgLevel);
            if (levelCnt == 40) {
                gameLevel++;
                //继续进入游戏
            }
        }

        //将每个星星位置画出来
        for (int i = 0; i < STAR_NUM; i++) {
            if (rgStarCmd[i].getY() + rgStarCmd[i].getUnHeight() > gameViewHeight) {
                if (mainTime % 2 == 0) {
                    rgStarCmd[i].setX(gameViewWidth / STAR_NUM * i + 10);
                } else {
                    rgStarCmd[i].setX(gameViewWidth / STAR_NUM * (STAR_NUM - i) + 10);
                }
                rgStarCmd[i].setY(0);
            } else {
                rgStarCmd[i].setY(rgStarCmd[i].getY() + 5);
            }
            drawSprites(canvas, imgStar, rgStarCmd[i]);
        }
        //画返回和声音按钮
        imgBackButtonUtil.drawButton(canvas, null);
        imgSoundButtonUtil.drawButton(canvas, null);

        //子弹重新初始化
        for (int i = 0; i < BULLET_NUM; i++) {
            if (rgBulletCmd[i].getUnLayer() == layerGone && mainTime % 2 == 0 && !isLevelPass) {
                rgBulletCmd[i].setUnLayer(layerVisible);
                rgBulletCmd[i].setX(rgPlaneCmd.getX() + 30);
                rgBulletCmd[i].setY(rgPlaneCmd.getY() - 10);
                break;
            }
        }
        //子弹的运动以及走出屏幕后的处理
        for (int i = 0; i < BULLET_NUM; i++) {
            if (rgBulletCmd[i].getUnLayer() != layerGone) {
                rgBulletCmd[i].setY(rgBulletCmd[i].getY() - 35);
                if (rgBulletCmd[i].getY() < -10) {
                    rgBulletCmd[i].setUnLayer(layerGone);
                }
            }
        }
        //画子弹
        for (int i = 0; i < BULLET_NUM; i++) {
            drawSprites(canvas, imgBullet, rgBulletCmd[i]);
        }

        //敌机1初始化/运动/画敌机
        if (rgEmeny1Cmd.getUnLayer() == layerGone && !isLevelPass) {
            rgEmeny1Cmd.setUnLayer(layerVisible);
            rgEmeny1Cmd.setX((gameViewWidth - rgEmeny1Cmd.getUnWidth()) / 2);
            rgEmeny1Cmd.setY(-gameViewHeight / 5);
        } else {
            rgEmeny1Cmd.setY(rgEmeny1Cmd.getY() + 10);
            if (rgEmeny1Cmd.getY() > gameViewHeight) {
                rgEmeny1Cmd.setUnLayer(layerGone);
            }
        }
        drawSprites(canvas, imgEnemy1, rgEmeny1Cmd);

        //敌机2初始化/运动/画敌机
        if (rgEmeny2Cmd.getUnLayer() == layerGone && !isLevelPass) {
            rgEmeny2Cmd.setUnLayer(layerVisible);
            rgEmeny2Cmd.setX(0);
            rgEmeny2Cmd.setY(0);
        } else {
            rgEmeny2Cmd.setX(rgEmeny2Cmd.getX() + 6);
            rgEmeny2Cmd.setY(rgEmeny2Cmd.getY() + 10);
            if (rgEmeny2Cmd.getY() > gameViewHeight) {
                rgEmeny2Cmd.setUnLayer(layerGone);
            }
        }
        drawSprites(canvas, imgEnemy2, rgEmeny2Cmd);


        //敌机3初始化/运动/画敌机
        if (rgEmeny3Cmd.getUnLayer() == layerGone && !isLevelPass) {
            rgEmeny3Cmd.setUnLayer(layerVisible);
            rgEmeny3Cmd.setX(gameViewWidth - rgEmeny3Cmd.getUnWidth());
            rgEmeny3Cmd.setY(gameViewHeight / 3);
        } else {
            if (rgEmeny3Cmd.isRight()) {
                rgEmeny3Cmd.setX(rgEmeny3Cmd.getX() + 3);
            } else {
                rgEmeny3Cmd.setX(rgEmeny3Cmd.getX() - 3);
            }
            if (rgEmeny3Cmd.getX() < 0) {
                rgEmeny3Cmd.setIsRight(true);
            }
            if (rgEmeny3Cmd.getX() > gameViewWidth - rgEmeny3Cmd.getUnWidth()) {
                rgEmeny3Cmd.setIsRight(false);
            }
        }
        drawSprites(canvas, imgEnemy1, rgEmeny3Cmd);

        //画炸弹
        for (int i = 0; i < BOMB_NUM; i++) {
            drawSprites(canvas, imgBomb, rgBombCmd[i]);
        }

        //画主机
        drawSprites(canvas, imgPlane, rgPlaneCmd);
    }

    private void drawBulletAndEnemy(SpriteCmd enemy) {
        for (int i = 0; i < BULLET_NUM; i++) {
            if (checkHitEnemy(rgBulletCmd[i], enemy)) {
                //子弹与敌机消失
                rgBulletCmd[i].setUnLayer(layerGone);
                enemy.setUnLayer(layerGone);
                //爆炸
                for (int j = 0; j < BOMB_NUM; j++) {
                    if (rgBombCmd[j].getUnLayer() == layerGone) {
                        rgBombCmd[j].setUnLayer(layerVisible);
                        rgBombCmd[j].setX(enemy.getX() + 15);
                        rgBombCmd[j].setY(enemy.getY() + 15);
                        rgBombCmd[j].setWalkCount(0);
                        rgBombCmd[j].setUnSpriteIndex(0);
                        break;
                    }
                }
                deadEnemyCnt++;
            }
        }
    }

    /**
     * 绘制主角
     */
    private void drawPlaneCmd() {
        //主角到达地图左边界
        if (rgPlaneCmd.getX() < 0) {
            rgPlaneCmd.setIsLeftStop(true);
            rgPlaneCmd.setX(0);
        } else {
            rgPlaneCmd.setIsLeftStop(false);
        }
        //主角到达地图有边界
        if (rgPlaneCmd.getX() + rgPlaneCmd.getUnWidth() > gameViewWidth) {
            rgPlaneCmd.setIsRight(true);
            rgPlaneCmd.setX(gameViewWidth - rgPlaneCmd.getUnWidth());
        } else {
            rgPlaneCmd.setIsRightStop(false);
        }
        //主角到达地图上边界
        if (rgPlaneCmd.getY() < 0) {
            rgPlaneCmd.setIsTopStop(true);
            rgPlaneCmd.setY(0);
        } else {
            rgPlaneCmd.setIsTopStop(false);
        }
        //主角到达地图下边界
        if (rgPlaneCmd.getY() > gameViewHeight - rgPlaneCmd.getUnHeight()) {
            rgPlaneCmd.setIsDownStop(true);
            rgPlaneCmd.setY(gameViewHeight - rgPlaneCmd.getUnHeight());
        } else {
            rgPlaneCmd.setIsDownStop(false);
        }
    }

    private boolean checkHitEnemy(SpriteCmd cmd1, SpriteCmd cmd2) {
        if (cmd1.getUnLayer() != layerGone && cmd2.getUnLayer() != layerGone) {
            if ((cmd1.getX() + cmd1.getUnWidth()) > cmd2.getX() &&
                    cmd1.getX() < (cmd2.getX() + cmd2.getUnWidth()) &&
                    (cmd1.getY() + cmd1.getUnHeight()) > cmd2.getY() &&
                    cmd1.getY() < (cmd2.getY() + cmd2.getUnHeight())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 菜单界面触摸
     *
     * @param event
     */
    private void onTouchEventMenu(MotionEvent event) {
        float x, y;
        x = event.getX();
        y = event.getY();

        if (imgStartButtonUtil.isClick(x, y)) {
            //进入到游戏等级界面
            turnToLevelGuide();
        } else if (imgHelpButtonUtil.isClick(x, y)) {
            //进入帮助界面

        } else if (imgBackButtonUtil.isClick(x, y)) {
            //返回，退出游戏
            closeGame();
        }

    }

    /**
     * 游戏关卡界面触摸
     *
     * @param event
     */
    private void onTouchEventLevel(MotionEvent event) {
        float x, y;
        x = event.getX();
        y = event.getY();

        if (imgFirstLevelButtonUtil.isClick(x, y)) {
            //进入到游戏第1关
            gameLevel = 1;
            turnGameLevel();
        } else if (imgSecondLevelButtonUtil.isClick(x, y)) {
            //进入到游戏第2关
            gameLevel = 2;
            turnGameLevel();
        } else if (imgBackButtonUtil.isClick(x, y)) {
            //返回到主界面
            releaseImage(imgBackground);
            releaseImage(imgFirstLevel);
            releaseImage(imgSecondLevel);
            releaseImage(imgThreeLevel);
            releaseImage(imgFourLevel);
            turnToMenu();
        }

    }

    /**
     * 游戏中触摸
     *
     * @param event
     */
    private void onTouchEventGame(MotionEvent event) {
        float x, y;
        x = event.getX();
        y = event.getY();
        int offset = 30;
        if (imgSoundButtonUtil.isClick(x, y)) {
            //声音开/关
            isPlaySound = !isPlaySound;
            if (isPlaySound) {
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                imgSoundButtonUtil = new ButtonUtil(imgSoundOn, 50, gameViewHeight - 100);
            } else {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                imgSoundButtonUtil = new ButtonUtil(imgSoundOff, 50, gameViewHeight - 100);
            }

        } else if (imgBackButtonUtil.isClick(x, y)) {
            //返回到主界面
            releaseImage(imgBackground);
            releaseImage(imgPassLevel);
            releaseImage(imgBloodBg);
            releaseImage(imgBlood);
            releaseImage(imgStar);
            releaseImage(imgPlane);
            releaseImage(imgBullet);
            releaseImage(imgEnemy1);
            releaseImage(imgEnemy2);
            releaseImage(imgBomb);
            releaseImage(imgSoundOn);
            releaseImage(imgSoundOff);
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.release();
            }
            turnToLevelGuide();
        } else {
            rgPlaneCmd.setX((int) x);
            rgPlaneCmd.setY((int) y);
        }
    }


    /**
     * 退出游戏，释放资源
     */
    private void closeGame() {
        isExit = true;
        freeAppData();
        ((MainActivity) this.context).finish();
    }

    /**
     * 释放应用资源
     */
    private void freeAppData() {
        releaseImage(imgBackground);
        releaseImage(imgStart);
        releaseImage(imgHelp);
        releaseImage(imgBack);
    }

    private void releaseImage(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
//            bitmap.recycle();
            bitmap = null;
        }
    }

    /**
     * 画精灵
     *
     * @param canvas
     * @param bitmap
     * @param spriteCmd
     */
    private void drawSprites(Canvas canvas, Bitmap bitmap, SpriteCmd spriteCmd) {
        if (spriteCmd.getUnLayer() != layerGone) {
            displayImage(canvas, bitmap, spriteCmd.getX(), spriteCmd.getY(),
                    spriteCmd.getUnWidth(), spriteCmd.getUnHeight(), 0, spriteCmd.getUnSpriteIndex() * spriteCmd.getUnHeight());
        }
    }

    /**
     * @param canvas
     * @param bitmap
     * @param scrX
     * @param scrY
     * @param width
     * @param height
     * @param imgX
     * @param imgY
     */
    private void displayImage(Canvas canvas, Bitmap bitmap, int scrX, int scrY, int width, int height, int imgX, int imgY) {
        if (bitmap != null) {
            Rect srcRect = new Rect();
            srcRect.left = scrX;
            srcRect.top = scrY;
            srcRect.right = scrX + width;
            srcRect.bottom = scrY + height;

            Rect imgRect = new Rect();
            imgRect.left = imgX;
            imgRect.top = imgY;
            imgRect.right = imgX + width;
            imgRect.bottom = imgY + height;

            canvas.drawBitmap(bitmap, imgRect, srcRect, null);

        }
    }


    /**
     * 绘制图片
     *
     * @param canvas
     * @param bitmap
     * @param width
     * @param height
     */
    private void displayImage(Canvas canvas, Bitmap bitmap, int width, int height) {
        if (bitmap != null) {
            //绘制在屏幕上的矩形
            Rect srcRect = new Rect();
            srcRect.left = 0;
            srcRect.top = 0;
            srcRect.right = gameViewWidth;
            srcRect.bottom = gameViewHeight;

            Rect imgRect = new Rect();
            imgRect.left = 0;
            imgRect.top = 0;
            imgRect.right = width;
            imgRect.bottom = height;
            //绘制图片
            canvas.drawBitmap(bitmap, imgRect, srcRect, null);
        }
    }

    @Override
    public void run() {
        long frameElapse, frameTick;
        try {
            // 本次执行起始时间
            frameTick = System.currentTimeMillis();
            if (!isExit) {
                while (true) {
                    // 画一帧所用的时间
                    frameElapse = System.currentTimeMillis() - frameTick;
                    // 如果不足80毫秒
                    if (frameElapse < 80) {
                        // 线程休眠，保证画一帧的时间为80毫秒
                        Thread.sleep((80 - frameElapse));
                    }
                    // 刷新屏幕
                    postInvalidate();
                    frameTick = System.currentTimeMillis(); // 本次执行结束时间
                }
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
