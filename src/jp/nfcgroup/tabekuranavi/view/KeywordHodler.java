package jp.nfcgroup.tabekuranavi.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.nfcgroup.tabekuranavi.R;
import jp.nfcgroup.tabekuranavi.model.vo.TagVO;

public class KeywordHodler implements OnClickListener {
    
    @SuppressWarnings("unused")
	private static final String TAG = null;
	private Context mContext;
    private KeywordChangedListener mListener;
    private LinearLayout mHolder;
    
    public KeywordHodler(Context context, LinearLayout holder, KeywordChangedListener listener) {
        mContext = context;
        mHolder = holder;
        mListener = listener;
    }
    
    public void addKeyword(TagVO tag){
        if(tag == null) return;
        
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.keyword, null);
        convertView.setTag(tag.id);
        convertView.setOnClickListener(this);
        
        TextView text = (TextView) convertView.findViewById(R.id.text_keyword);
        text.setText(tag.name);
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 0, 5, 0);
        convertView.setLayoutParams(params);
        mHolder.addView(convertView);
    }

    public void onClick(View view) {
        int tagId = (Integer) view.getTag();
        
        for(int i=0,max=mHolder.getChildCount();i<max;i++){
            View v = mHolder.getChildAt(i);
            int id = (Integer) v.getTag();
            
            if(id == tagId){
                mHolder.removeView(v);
                break;
            }
        }
        
        //notify
        if(mListener != null){
            mListener.onKeywordChangedListener(tagId);
        }
    }
    
    public void clearKeywords(){
        //TODO モデルからTagVOが単体取得できたら消す
        
        mHolder.removeAllViews();
    }
    
    public interface KeywordChangedListener{
        void onKeywordChangedListener(int id);
    }
    
}
