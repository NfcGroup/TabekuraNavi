package jp.nfcgroup.tabekuranavi;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import jp.nfcgroup.tabekuranavi.model.StoreFinder;
import jp.nfcgroup.tabekuranavi.model.StoreFinderParcelable;
import jp.nfcgroup.tabekuranavi.util.NfcUtil;
import jp.nfcgroup.tabekuranavi.view.KeywordHodler;
import jp.nfcgroup.tabekuranavi.view.KeywordHodler.KeywordChangedListener;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public abstract class BaseActivity extends Activity implements KeywordChangedListener {
    
    @SuppressWarnings("unused")
	private static final String TAG = "BaseActivity";
	protected NfcAdapter mNfcAdapter;
    protected StoreFinder mStoreFinder;
    protected StoreFinderParcelable mStoreFinderParcelable;
    protected KeywordHodler mKeywordHolder;
    protected Toast mToast;
    protected LayoutInflater mInflater;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mStoreFinder = new StoreFinder(getApplicationContext());
        mStoreFinderParcelable = new StoreFinderParcelable(mStoreFinder);
       	mToast = new Toast(getApplicationContext());
       	mInflater = getLayoutInflater();
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
                	((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(100);
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
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
        mStoreFinder.databaseClose();
    }
    
    protected void onDiscoverd(Intent intent){
        Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if(parcelables.length == 0) return;
        
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
    	int id = Integer.parseInt(tagId);
        boolean result = mStoreFinder.addKeyword(id);
        
        if(result) {
        	mKeywordHolder.addKeyword(mStoreFinder.getKeyword(id));
        } else {
        	View view = mInflater.inflate(R.layout.toast, null);
        	mToast.setView(view);
        	mToast.setDuration(Toast.LENGTH_LONG);
        	mToast.show();
        }
    }

    abstract protected void onUpdateViews();
    
    
    public void onKeywordChangedListener(int id) {
        Log.d(TAG,"onKeywordChangedListener");
        mStoreFinder.deleteKeyword(id);
        
        onUpdateViews();
    }
}
