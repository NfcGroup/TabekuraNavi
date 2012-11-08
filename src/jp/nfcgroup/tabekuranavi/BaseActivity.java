package jp.nfcgroup.tabekuranavi;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import jp.nfcgroup.tabekuranavi.model.StoreFinder;
import jp.nfcgroup.tabekuranavi.model.vo.TagVO;
import jp.nfcgroup.tabekuranavi.util.NfcUtil;
import jp.nfcgroup.tabekuranavi.view.KeywordHodler;
import jp.nfcgroup.tabekuranavi.view.KeywordHodler.KeywordChangedListener;

public abstract class BaseActivity extends Activity implements KeywordChangedListener {
    
    protected NfcAdapter mNfcAdapter;
    protected StoreFinder mStoreFinder;
    protected KeywordHodler mKeywordHolder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mStoreFinder = new StoreFinder(getApplicationContext());
        
        mKeywordHolder = new KeywordHodler(getApplicationContext(),this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            finish();
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(intent);
            return;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
        IntentFilter[] intentFilters = new IntentFilter[] {
                new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
        };
        String[][] techLists = {
            {
                android.nfc.tech.NfcA.class.getName(),
                android.nfc.tech.NfcB.class.getName(),
                android.nfc.tech.IsoDep.class.getName(),
                android.nfc.tech.MifareClassic.class.getName(),
                android.nfc.tech.MifareUltralight.class.getName(),
                android.nfc.tech.NdefFormatable.class.getName(),
                android.nfc.tech.NfcV.class.getName(),
                android.nfc.tech.NfcF.class.getName(),
            }
        };
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, techLists);
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        
        if(intent != null){
            String action = intent.getAction();
            if(action != null){
                if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED) ||
                        action.equals(NfcAdapter.ACTION_TECH_DISCOVERED) ||
                        action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
                    onDiscoverd(intent);
                }
            }
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
        mNfcAdapter.disableForegroundDispatch(this);
    }
    
    protected void onDiscoverd(Intent intent){
        Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage message = (NdefMessage) parcelables[0];
        NdefRecord record = message.getRecords()[0];
        byte[] payload = record.getPayload();
        
        if(record.getTnf() == NdefRecord.TNF_WELL_KNOWN){
            if(Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)){
                try{
                    
                    onUpdateTags(NfcUtil.getText(payload));
                    onUpdateViews();
                    
                }catch(UnsupportedEncodingException e){
                    //showError("不正なタグ情報です\n"+e.getMessage());
                }
            }
        }
    }
    
    protected void onUpdateTags(String tagId){
        mStoreFinder.addKeyword(Integer.parseInt(tagId));
    }

    abstract protected void onUpdateViews();
    
    
    public void onKeywordChangedListener(int id) {
        // TODO 自動生成されたメソッド・スタブ
        
    }
}
