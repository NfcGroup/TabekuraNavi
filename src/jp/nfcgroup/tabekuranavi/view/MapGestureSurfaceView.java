package jp.nfcgroup.tabekuranavi.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.R;
import jp.nfcgroup.tabekuranavi.fragment.MapFragment;
import jp.nfcgroup.tabekuranavi.fragment.StoreDialogFragment;
import jp.nfcgroup.tabekuranavi.model.StoreColorVO;

public class MapGestureSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private static final String TAG = "MapGestureSurfaceView";
    private static final RectF[] mShopRects = {
        new RectF(482.0f, 542.0f, 525.0f, 595.0f),
        new RectF(482.0f, 596.0f, 525.0f, 640.0f),
        new RectF(482.0f, 651.0f, 525.0f, 690.0f),
        new RectF(482.0f, 706.0f, 525.0f, 735.0f),
        new RectF(482.0f, 760.0f, 525.0f, 785.0f),
        new RectF(482.0f, 815.0f, 525.0f, 830.0f),
        new RectF(482.0f, 870.0f, 525.0f, 880.0f),
        new RectF(482.0f, 924.0f, 525.0f, 925.0f),
        new RectF(445.0f, 1021.0f, 410.0f, 1070.0f),
        new RectF(390.0f, 1021.0f, 365.0f, 1075.0f),
        new RectF(336.0f, 1021.0f, 315.0f, 1080.0f),
        new RectF(281.0f, 1021.0f, 270.0f, 1085.0f),
        new RectF(226.0f, 1021.0f, 220.0f, 1090.0f),
        new RectF(172.0f, 1021.0f, 175.0f, 1095.0f),
        new RectF(105.0f, 926.0f, 175.0f, 980.0f),
        new RectF(132.0f, 878.0f, 200.0f, 935.0f),
        new RectF(160.0f, 831.0f, 240.0f, 885.0f),
        new RectF(187.0f, 784.0f, 265.0f, 840.0f),
        new RectF(214.0f, 736.0f, 305.0f, 790.0f),
        new RectF(242.0f, 689.0f, 330.0f, 745.0f),
        new RectF(161.0f, 458.0f, 185.0f, 480.0f),
        new RectF(189.0f, 411.0f, 210.0f, 435.0f),
        new RectF(216.0f, 364.0f, 250.0f, 385.0f),
        new RectF(243.0f, 316.0f, 275.0f, 340.0f),
        new RectF(271.0f, 269.0f, 315.0f, 290.0f),
        new RectF(298.0f, 222.0f, 340.0f, 245.0f),
        new RectF(325.0f, 174.0f, 380.0f, 195.0f),
        new RectF(353.0f, 127.0f, 405.0f, 150.0f),
        new RectF(482.0f, 313.0f, 525.0f, 375.0f)
    };
	
	private SurfaceHolder mHolder;
	private Thread mThread;
	private int mScreenWidth,mScreenHeight;
	private BitmapDrawable mMapImage;
	private Matrix mMapMatrix;
	private Point mMapOffset;
	private float mMapScale;
	
	private Paint mShopPaint;
	private Paint mTextPaint;
	
	private ArrayList<StoreColorVO> mColors;
	
	public MapFragment mParentFragment;
    private boolean isDragging;
	
	public MapGestureSurfaceView(Context context) {
        super(context);
        
        initialize();
    }
	
	public MapGestureSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        initialize();
    }
	
	public MapGestureSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        initialize();
    }
	
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;
        Log.d(TAG, "width="+mScreenWidth+" height="+mScreenHeight);
    }
    
    private void initialize(){
        mHolder = getHolder(); 
        mHolder.addCallback(this);
        
        mMapImage = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.map));
        Log.d(TAG,"imageWidth="+mMapImage.getIntrinsicWidth()+" imageHeight="+mMapImage.getIntrinsicHeight());
        
        mMapMatrix = new Matrix();
        mMapOffset = new Point();
        mMapScale = 1.0f;
        mShopPaint = new Paint();
        mShopPaint.setAntiAlias(true);
        
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(26);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Align.CENTER);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new Thread(this);
        mThread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread = null;
    }

    public void run() {
        while(mThread != null){
            Canvas canvas = mHolder.lockCanvas();
            if(canvas != null){
                
                render(canvas);
                
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
    
    private void render(Canvas canvas) {
        
        canvas.save();
        
        canvas.drawColor(Color.parseColor("#000000"));
        
        //mMapMatrix.postConcat(canvas.getMatrix());
        canvas.setMatrix(mMapMatrix);
        
        mMapImage.setBounds(mMapOffset.x,mMapOffset.y,
                mMapImage.getIntrinsicWidth()+mMapOffset.x,
                mMapImage.getIntrinsicHeight()+mMapOffset.y);
        mMapImage.draw(canvas);
        
        for(int i=0;i<mShopRects.length;i++){
            RectF rect = mShopRects[i];
            StoreColorVO c = mColors.get(i);
            mShopPaint.setColor(Color.argb(c.alpha, c.red, c.green, c.blue));
            
            canvas.drawCircle(rect.left+24+mMapOffset.x,
                    rect.top+24+mMapOffset.y,
                    24,
                    mShopPaint);
            
            canvas.drawText(String.valueOf(i+1),
                    rect.left+24+mMapOffset.x,
                    rect.top+24+mMapOffset.y+10,
                    mTextPaint);
        }
        
        canvas.restore();
        
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        int historySize = event.getHistorySize();
        int pointSize = event.getPointerCount();
        
        
        
        if(pointSize == 1){
            
            if(historySize == 1){
                    
                float velocityX = event.getX(0) - event.getHistoricalX(0, 0);
                float velocityY = event.getY(0) - event.getHistoricalY(0, 0);
                
                onDrag(velocityX,velocityY);
                
            }else if(historySize == 0){
                if(isDragging == false){
                    for(int i=0;i<mShopRects.length;i++){
                        RectF rect = mShopRects[i];
                        RectF hitArea = new RectF(rect.left+mMapOffset.x,
                                rect.top+mMapOffset.y,
                                rect.right+mMapOffset.x,
                                rect.bottom+mMapOffset.y);
                        mMapMatrix.mapRect(hitArea);
                        
                        if(hitArea.contains(event.getX(), event.getY())){
                            if(mParentFragment.getFragmentManager().findFragmentByTag("dialog") == null){
                                StoreDialogFragment sdialog = StoreDialogFragment.newInstance(i);
                                sdialog.show(mParentFragment.getFragmentManager(), "dialog");
                            }
                        }
                    } 
                }else{
                    onDrag(0,0);
                }
            }
            
            
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                isDragging = true;
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                isDragging = false;
            }
        }else if(pointSize == 2){
            if(historySize == 1){
                float oldDistance = FloatMath.sqrt((event.getHistoricalX(0,0) - event.getHistoricalX(1,0))*(event.getHistoricalX(0,0)
                        - event.getHistoricalX(1,0))+(event.getHistoricalY(0,0) - event.getHistoricalY(1,0))*(event.getHistoricalY(0,0) - event.getHistoricalY(1,0)));
                float distance = FloatMath.sqrt((event.getX(0) - event.getX(1))*(event.getX(0) - event.getX(1))+(event.getY(0)
                        - event.getY(1))*(event.getY(0) - event.getY(1)));
                PointF center = new PointF((event.getX(0) + event.getX(1))/2,(event.getY(0) + event.getY(1))/2);
                
                onPinch(distance/oldDistance,center);
            }
        }
        
        return true;
    }
    
    private void onDrag(float velocityX,float velocityY){
        RectF frame = new RectF(mMapOffset.x,
                mMapOffset.y,
                mMapImage.getIntrinsicWidth()+mMapOffset.x,
                mMapImage.getIntrinsicHeight()+mMapOffset.y);
        mMapMatrix.mapRect(frame);
        
        if(frame.left+velocityX > 0){
            velocityX -= (frame.left+velocityX);
        }else if(frame.right+velocityX < mScreenWidth){
            velocityX -= ((frame.right+velocityX) - mScreenWidth);
        }
        if(frame.top+velocityY > 0){
            velocityY -= (frame.top+velocityY);
        }else if(frame.bottom+velocityY < mScreenHeight){
            velocityY -= ((frame.bottom+velocityY) - mScreenHeight); 
        }
        
        mMapOffset.offset((int)velocityX, (int)velocityY);
    }
    
    private void onPinch(float scale,PointF center){
        float tempScale = mMapScale * scale;
        if(tempScale > 2 || tempScale < 1) scale = 1.0f;
        
        mMapScale *= scale;
        
        mMapMatrix.postScale(scale, scale, center.x, center.y);
        
        //写真枠が画面外に出たときにonDragの移動処理を利用して正しい位置に移動させてる
        onDrag(0,0);
    }
    
    public void updateColors(ArrayList<StoreColorVO> colors){
        mColors = colors;
    }
}
