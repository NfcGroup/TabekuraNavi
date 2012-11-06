package jp.nfcgroup.tabekuranavi.adapter;

import java.util.List;
import java.util.Map;

import jp.nfcgroup.tabekuranavi.R;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class StoreListAdapter extends SimpleExpandableListAdapter {

    private static final String TAG = "StoreListAdapter";
    private Context mContext;
    
    public StoreListAdapter(Context context, List<? extends Map<String, ?>> groupData,
            int groupLayout, String[] groupFrom, int[] groupTo,
            List<? extends List<? extends Map<String, ?>>> childData, int childLayout,
            String[] childFrom, int[] childTo) {
        super(context, groupData, groupLayout, groupFrom, groupTo, childData, childLayout, childFrom,
                childTo);
        
        mContext = context;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_group,null);
        }
        
        Map<String,String> vo = (Map<String, String>) getGroup(groupPosition);
        
        TextView titleView = (TextView) convertView.findViewById(R.id.shop_title);
        titleView.setText(vo.get("name"));
        
        return convertView;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_child,null);
        }
        
        Map<String,String> vo = (Map<String, String>) getChild(groupPosition, childPosition);
        
        TextView titleView = (TextView) convertView.findViewById(R.id.dish_title);
        titleView.setText(vo.get("name"));
        TextView priceView = (TextView) convertView.findViewById(R.id.dish_price);
        priceView.setText(vo.get("price")+" rokka");
        
        return convertView;
    }
    
    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        Log.d(TAG,"onGroupCollapsed");
        View v = getGroupView(groupPosition, false, null, null);
        v.setBackgroundColor(Color.parseColor("#ffe69a"));
    }
    
    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
        Log.d(TAG,"onGroupExpanded");
        View v = getGroupView(groupPosition, true, null, null);
        v.setBackgroundColor(Color.parseColor("#835a1b"));
    }
}
