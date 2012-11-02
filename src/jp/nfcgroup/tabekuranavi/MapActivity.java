package jp.nfcgroup.tabekuranavi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapActivity extends BaseActivity implements OnClickListener{
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO �����������ꂽ���\�b�h�E�X�^�u
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO �����������ꂽ���\�b�h�E�X�^�u
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button1:
                Intent intent = new Intent(this.getApplicationContext(),ListActivity.class);
                startActivity(intent);
                break;
        }
    }
    
}
