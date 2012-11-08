package jp.nfcgroup.tabekuranavi.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.nfcgroup.tabekuranavi.R;
import jp.nfcgroup.tabekuranavi.model.vo.TagVO;

public class KeywordHodler implements OnClickListener {
    
    private Context mContext;
    private KeywordChangedListener mListener;
    
    public KeywordHodler(Context context, KeywordChangedListener listener) {
        mContext = context;
        mListener = listener;
    }
    
    public void addKeyword(TagVO tag, LinearLayout parent){
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
        parent.addView(convertView);
    }

    public void onClick(View view) {
        int tagId = (Integer) view.getTag();
        //remove
        ViewGroup group = (ViewGroup) view.getParent();
        for(int i=0,max=group.getChildCount();i<max;i++){
            View v = group.getChildAt(i);
            int id = (Integer) v.getTag();
            
            if(id == tagId){
                group.removeView(v);
                return;
            }
        }
        
        //notify
        if(mListener != null){
            mListener.onKeywordChangedListener(tagId);
        }
    }
    
    public interface KeywordChangedListener{
        void onKeywordChangedListener(int id);
    }
    
}
