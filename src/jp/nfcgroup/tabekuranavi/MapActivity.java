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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

   public void onClick(View v) {
       switch(v.getId()){
           case R.id.button1:
               Intent intent = new Intent(this.getApplicationContext(),ListActivity.class);
               startActivity(intent);
               break;
       }
   }
    
    @Override
    protected void onUpdateTags(String tagId) {
        // TODO 自動生成されたメソッド・スタブ
        
    }
    
    @Override
    protected void onUpdateViews() {
        // TODO 自動生成されたメソッド・スタブ
        
    }
}
