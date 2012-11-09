package jp.nfcgroup.tabekuranavi;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.fragment.ListFragment;
import jp.nfcgroup.tabekuranavi.model.vo.TagVO;
import jp.nfcgroup.tabekuranavi.view.KeywordHodler;

public class ListActivity extends BaseActivity implements OnClickListener {
    
    @SuppressWarnings("unused")
    private static final String TAG = "ListActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        mKeywordHolder = new KeywordHodler(getApplicationContext(),(LinearLayout) findViewById(R.id.tag_holder),this);
        
        ImageButton btn = (ImageButton) findViewById(R.id.change_view_button);
        btn.setOnClickListener(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    
    protected void onUpdateViews(){
        FragmentManager manager = getFragmentManager();
        ListFragment list =  (ListFragment) manager.findFragmentById(R.id.fragment_list);
        
        list.updateViews();
    }
    
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),MapActivity.class);
        startActivity(intent);
    }
}
