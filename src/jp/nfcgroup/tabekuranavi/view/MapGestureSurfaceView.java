package jp.nfcgroup.tabekuranavi.view;

import jp.nfcgroup.tabekuranavi.R;
import jp.nfcgroup.tabekuranavi.fragment.StoreDialogFragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("DrawAllocation")
public class MapGestureSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	private Bitmap _bmMap;
	private Float  _mapScale;
	private Matrix _matrix;
	
	public  RectF[] _shopHitRects;
	public  RectF[] _shopDrawRects;
	
	public MapGestureSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this); 

        _bmMap  = LoadImageFile(R.drawable.map,800*2);
        _mapScale = 1.0f;
        
        //店舗
        _shopHitRects = setShopButtonRect();  //タッチ範囲（可変）
        _shopDrawRects = setShopButtonRect(); //描画範囲 （固定）
        
        _matrix = new Matrix();
    	_matrix.preScale(1.0f,1.0f);
		_matrix.postTranslate(0,0); //中心座標
	}
	
	//--------------------------------------------------------------//
	//-- マップ画像の読み込み --
	//--------------------------------------------------------------//
	
	private	Bitmap	LoadImageFile(int rsId,int nMaxWidth)
	{
		Bitmap	bmpImage;
		
		//画像の解析（大きい画像が開けない対策）
		BitmapFactory.Options	options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), rsId, options);
		
		int		nWidth = options.outWidth;
		int		nHeight = options.outHeight;

		if(nWidth == 0 || nHeight == 0)
			return	null;

		if(nWidth <= nMaxWidth && nHeight <= nMaxWidth)
		{
			//リサイズ不要。そのまま読み込んで返す
			options.inJustDecodeBounds = false;
			return	bmpImage = BitmapFactory.decodeResource(getResources(), rsId, options);
		}



		int		nScale;

		//nScaleは2の乗数にする
		{
			float	fScale;
			float	fScaleX = (float)nWidth / nMaxWidth;
			float	fScaleY = (float)nHeight / nMaxWidth;

			fScale = (fScaleX > fScaleY) ? fScaleX : fScaleY;

			nScale = 1;
			while(fScale >= nScale)
			{
				nScale *= 2;
			}
		}
		
		//画像の本読み込み
		options.inJustDecodeBounds = false;
		options.inSampleSize = nScale;
		bmpImage = BitmapFactory.decodeResource(getResources(), rsId, options);

		return	bmpImage;
	}
	
	//--------------------------------------------------------------//
	//-- ショップ塗り＆タッチ範囲 --
	//--------------------------------------------------------------//
	
	public RectF[] setShopButtonRect(){
		
		RectF[] sBttonRects = new RectF[29];
		
		//////////////////
		sBttonRects[0] = new RectF(485.0f, 555.0f, 525.0f, 595.0f);
		sBttonRects[1] = new RectF(485.0f, 600.0f, 525.0f, 640.0f);
		
		sBttonRects[2] = new RectF(485.0f, 650.0f, 525.0f, 690.0f);
		sBttonRects[3] = new RectF(485.0f, 695.0f, 525.0f, 735.0f);
		
		sBttonRects[4] = new RectF(485.0f, 745.0f, 525.0f, 785.0f);
		sBttonRects[5] = new RectF(485.0f, 790.0f, 525.0f, 830.0f);

		//////////////////
		sBttonRects[6] = new RectF(485.0f, 840.0f, 525.0f, 880.0f);
		sBttonRects[7] = new RectF(485.0f, 885.0f, 525.0f, 925.0f);
		
		sBttonRects[8] = new RectF(370.0f, 1030.0f, 410.0f, 1070.0f);
		sBttonRects[9] = new RectF(325.0f, 1035.0f, 365.0f, 1075.0f);
		
		sBttonRects[10] = new RectF(275.0f, 1040.0f, 315.0f, 1080.0f);
		sBttonRects[11] = new RectF(230.0f, 1045.0f, 270.0f, 1085.0f);
		
		sBttonRects[12] = new RectF(180.0f, 1050.0f, 220.0f, 1090.0f);
		sBttonRects[13] = new RectF(135.0f, 1055.0f, 175.0f, 1095.0f);
		
		//////////////////
		sBttonRects[14] = new RectF(135.0f, 940.0f, 175.0f, 980.0f);
		sBttonRects[15] = new RectF(160.0f, 895.0f, 200.0f, 935.0f);
		
		sBttonRects[16] = new RectF(200.0f, 845.0f, 240.0f, 885.0f);
		sBttonRects[17] = new RectF(225.0f, 800.0f, 265.0f, 840.0f);
		
		sBttonRects[18] = new RectF(265.0f, 750.0f, 305.0f, 790.0f);
		sBttonRects[19] = new RectF(290.0f, 705.0f, 330.0f, 745.0f);
		
		
		//////////////////
		sBttonRects[20] = new RectF(145.0f, 440.0f, 185.0f, 480.0f);
		sBttonRects[21] = new RectF(170.0f, 395.0f, 210.0f, 435.0f);
		
		
		sBttonRects[22] = new RectF(210.0f, 345.0f, 250.0f, 385.0f);
		sBttonRects[23] = new RectF(235.0f, 300.0f, 275.0f, 340.0f);
		
		sBttonRects[24] = new RectF(275.0f, 250.0f, 315.0f, 290.0f);
		sBttonRects[25] = new RectF(300.0f, 205.0f, 340.0f, 245.0f);
		
		sBttonRects[26] = new RectF(340.0f, 155.0f, 380.0f, 195.0f);
		sBttonRects[27] = new RectF(365.0f, 110.0f, 405.0f, 150.0f);
		
		
		//////////////////
		sBttonRects[28] = new RectF(485.0f, 335.0f, 525.0f, 375.0f);
		
	
		return sBttonRects;	
	}
	
	
	
	public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
		if(_bmMap == null)
			return;

		//画面表示用ズーム率
		float	fImageScale;

		//余白
		float	fMarginX = 0.0f;
		float	fMarginY = 0.0f;
		
		/*
		//ズーム率取得
		{
			int		nImageWidth	= _bmMap.getWidth();
			int		nImageHeight= _bmMap.getHeight();
			int		nViewWidth = getWidth();
			int		nViewHeight = getHeight();
			
			//画像に合わせる
			if((long)nImageWidth * nViewHeight > (long)nViewWidth * nImageHeight)
			{
				fImageScale = (float)nViewWidth / nImageWidth;
				fMarginY = (nViewHeight - fImageScale * nImageHeight) * 0.5f;
			}
			else
			{
				fImageScale = (float)nViewHeight / nImageHeight;
				fMarginX = (nViewWidth - fImageScale * nImageWidth) * 0.5f;
			}
		}
		*/

		//ピンチを含めた総合ズーム率
		//float	fScale = _fPinchScale * fImageScale;
		//float	fScale = _fPinchScale;
		
		//Log.v("_fPinchScale","_fPinchScale" + _fPinchScale);
		_mapScale += _fPinchScale - 1.0f;
		
		
		
		//余白を含めた移動量
		float fMoveX = _fPinchMoveX;
		float fMoveY = _fPinchMoveY;
		
		
		Log.v("_fPinchMoveX","_fPinchMoveX" + _fPinchMoveX);
		//ズーム原点指定
		fMoveX += _ptPinchStart.x - _ptPinchStart.x * _mapScale;
		fMoveY += _ptPinchStart.y - _ptPinchStart.y * _mapScale;
		
		
		//Log.v("fMoveY","fMoveY" + fMoveY);
		
		//最小マップ
		if(_mapScale < 1.0f){
			_mapScale = 1.0f;
			fMoveX = 0.0f;
			fMoveY = 0.0f;
		}

		
		Log.v("fMoveX","fMoveX" + fMoveX);
		
		Matrix	matrix = new Matrix();
		matrix.preScale(_mapScale,_mapScale);   //ズーム
		matrix.postTranslate(fMoveX,fMoveY);	//移動

		//描画
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(_bmMap, matrix, null);
		

		
		//店舗
    	for (int i = 0; i < _shopDrawRects.length; i++) {
    		
    		Paint paint = new Paint();
    		paint.setColor(Color.GREEN);
    		
    		RectF tempRect = new RectF(_shopDrawRects[i]);
    		_shopHitRects[i] = tempRect;
    		matrix.mapRect(tempRect);
    		canvas.drawRect(tempRect, paint);
		}
    }
	
	private void doDraw(SurfaceHolder holder){
		Canvas canvas = holder.lockCanvas();
		canvas.save();
		onDraw(canvas);
		holder.unlockCanvasAndPost(canvas);
	}
	  
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}


	public void surfaceCreated(SurfaceHolder holder) {
		doDraw(holder);
	}


	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub	
	}
	
	
	
	//--------------------------------------------------------------//
	//-- ピンチインアウト --
	//--------------------------------------------------------------//
	
	//ピンチによる現在のズーム率（拡大  > 1.0f > 縮小）
	private	float	_fPinchScale	= 1.0f;

	private	PointF	_ptPinchStart	= new PointF();
	private float	_fPinchStartDistance	= 0.0f;
	//private float	_fPinchMoveDistance	= 0.0f;
	
	private	float	_fPinchMoveX	= 0.0f;
	private	float	_fPinchMoveY	= 0.0f;


	
	//タッチ操作内部処理用
	private static final int TOUCH_NONE = 0;
	private static final int TOUCH_DRAG = 1;
	private static final int TOUCH_ZOOM = 2;
	private int		_nTouchMode	= TOUCH_NONE;
	
	
	//ピンチ開始
	public void startPinch(MotionEvent event){
		if(event.getPointerCount() >= 2)
		{
			_fPinchStartDistance = GetDistance(event);
			if(_fPinchStartDistance > 50f)
			{
				GetCenterPoint(event,_ptPinchStart);
				_nTouchMode = TOUCH_ZOOM;
			}
		}
	}
	
	//ピンチ中
	public void movePinch(MotionEvent event){
		
		if(_nTouchMode == TOUCH_ZOOM && _fPinchStartDistance > 50)
	    {
			//float distance = GetDistance(event);
			//_fPinchMoveDistance = Math.abs(distance - _fPinchStartDistance);
				
			PointF	pt = new PointF();
	   		GetCenterPoint(event,pt);
	   		_fPinchMoveX	= pt.x - _ptPinchStart.x;
	   		_fPinchMoveY	= pt.y - _ptPinchStart.y;
	   		_fPinchScale    = GetDistance(event) / _fPinchStartDistance;	
	   		doDraw(getHolder());
			
	    }
	}
	
	//ピンチ終了
	public void endPinch(MotionEvent event){
		if(_nTouchMode == TOUCH_ZOOM)
		{
				//ピンチ終了処理
				doDraw(getHolder());
			
				_nTouchMode = TOUCH_NONE;
				_fPinchMoveX	= 0.0f;
				_fPinchMoveY	= 0.0f;
				_fPinchScale	= 1.0f;
				_ptPinchStart.x	= 0.0f;
				_ptPinchStart.y	= 0.0f;
			}
	}
	
		
	//ピンチ距離取得用
	private	float	GetDistance(MotionEvent event)
	{
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return android.util.FloatMath.sqrt(x * x + y * y);
	}

	//ピンチ開始座標取得用
	private	void	GetCenterPoint(MotionEvent event,PointF pt)
	{
		pt.x = (event.getX(0) + event.getX(1)) * 0.5f;
		pt.y = (event.getY(0) + event.getY(1)) * 0.5f;
	}
	
	//ドラッグ開始
	public void startDrag(MotionEvent event)
	{
		if(_nTouchMode == TOUCH_NONE)
		{
			Log.v("drag","startdrag");
			_nTouchMode = TOUCH_DRAG;
		}
	}
	
	//ドラッグ中
	public void moveDrag(MotionEvent event)
	{
		if(_nTouchMode == TOUCH_DRAG)
		{	
			Log.v("drag","movedrag");		
		}
	}
	
	//ドラッグ終了
	public void endDrag(MotionEvent event)
	{
		if(_nTouchMode == TOUCH_DRAG)
		{
			//ドラッグ終了処理
			Log.v("end","enddrag");
			_nTouchMode = TOUCH_NONE;
			
		}
	}
}
