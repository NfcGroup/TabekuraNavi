package jp.nfcgroup.tabekuranavi.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.HorizontalScrollView;

public class CustomScrollView extends HorizontalScrollView {

    public CustomScrollView(Context context) {
        super(context);
        
        initialize();
    }
    
    public CustomScrollView(Context context,AttributeSet attr) {
        super(context,attr);
        
        initialize();
    }
    
    public CustomScrollView(Context context,AttributeSet attr,int defStyle) {
        super(context,attr,defStyle);
        
        initialize();
    }
    
    private void initialize(){
        //setHorizontalFadingEdgeEnabled(true);
    }
    
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
            int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY,
            boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                200, maxOverScrollY, isTouchEvent);
    }
    
    @Override
    @ExportedProperty(category = "drawing")
    public int getSolidColor() {
        return Color.parseColor("#ffffff");
    }

}
