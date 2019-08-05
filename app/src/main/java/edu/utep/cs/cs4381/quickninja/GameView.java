package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends SurfaceView implements Runnable {

    private SurfaceHolder holder;
    private Thread gameThread;

    private Canvas canvas;
    private Paint paint;

    Context context;
    int screenWidth;
    int screenHeight;

    private boolean playing;
    private boolean gameEnded;
    boolean gamePaused = false;

    SoundEffect soundEffect;
    ScoreManager scoreRecorder;

    private PlayerNinja player;
    private List<EnemyNinja> enemyNinjas = new CopyOnWriteArrayList<>();
    private List<FriendlyNinja> friendlyNinjas = new CopyOnWriteArrayList<>();

    public GameView(Context context, int screenWidth, int screenHeight){
        super(context);
        this.context = context;
        holder = getHolder();
        paint = new Paint();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        //soundEffect = new SoundEffect(context);
        startGame();
    }

    private void startGame() {
//        player = new PlayerNinja(context, screenWidth, screenHeight);
//        enemyNinjas.clear();
//        enemyNinjas.add(new EnemyNinja(context,screenWidth,screenHeight));
//        friendlyNinjas.add(new EnemyNinja(context,screenWidth,screenHeight));
//        enemyNinjas.add(new EnemyNinja(context,screenWidth,screenHeight));

        gameEnded = false;
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    public void update(){

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true; //boolean place holder for return type
        // We might have to use Level Manager like we did in the Platformer game
        // because we have to handle three type of actions. Jump, attack, and pause.
        // Unless you can think of a way to handle it in here.
    }

    private void draw() {
        Paint paint = new Paint();
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();

            // Draw graphics in here
            canvas.drawColor(Color.WHITE);

            // Draw background image
            Bitmap gameBackground = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
            gameBackground = Bitmap.createScaledBitmap(gameBackground,screenWidth,(screenHeight / 2) + (screenWidth / 4),true);
            canvas.drawBitmap(gameBackground,0,0, paint);

            // Draw platform
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(150); // Adjust transparency
            // You can change the second parameter of drawRect *(screenHeight/2)+(screenWidth/4)* to adjust the height of the platform.
            canvas.drawRect(0, (screenHeight / 2) + (screenWidth / 4), screenWidth, screenHeight, paint);
            paint.setAlpha(255); // Reset transparency

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try {
            gameThread.sleep(17); // in milliseconds
        } catch (InterruptedException e) {
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

}