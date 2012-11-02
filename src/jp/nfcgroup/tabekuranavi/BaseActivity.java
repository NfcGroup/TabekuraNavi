package jp.nfcgroup.tabekuranavi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class BaseActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
