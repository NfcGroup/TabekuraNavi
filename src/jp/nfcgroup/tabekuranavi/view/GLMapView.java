
package jp.nfcgroup.tabekuranavi.view;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import jp.nfcgroup.tabekuranavi.fragment.MapFragment;
import jp.nfcgroup.tabekuranavi.model.StoreColorVO;

public class GLMapView extends GLSurfaceView {

    
    
    private ArrayList<StoreColorVO> mColors;
    private GLRenderer mRenderer;
    
    public MapFragment mParentFragment;

    public GLMapView(Context context) {
        super(context);

        initialize();
    }

    public GLMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    private void initialize() {
        //setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);
        mRenderer = new GLRenderer();
        setRenderer(mRenderer);
    }
    
    public void updateColors(ArrayList<StoreColorVO> colors){
        mColors = colors;
    }
}

class GLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "GLRenderer";
    
    public void onDrawFrame(GL10 gl) {
        gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

}
