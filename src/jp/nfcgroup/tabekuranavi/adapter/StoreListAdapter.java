package jp.nfcgroup.tabekuranavi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import jp.nfcgroup.tabekuranavi.R;

public class StoreListAdapter extends SimpleExpandableListAdapter {

    private static final String TAG = "StoreListAdapter";
    private Context mContext;
    
    private int[] shopIdList = {
            R.drawable.ic_shop_1,
            R.drawable.ic_shop_2,
            R.drawable.ic_shop_3,
            R.drawable.ic_shop_4,
            R.drawable.ic_shop_5,
            R.drawable.ic_shop_6,
            R.drawable.ic_shop_7,
            R.drawable.ic_shop_8,
            R.drawable.ic_shop_9,
            R.drawable.ic_shop_10,
            R.drawable.ic_shop_11,
            R.drawable.ic_shop_12,
            R.drawable.ic_shop_13,
            R.drawable.ic_shop_14,
            R.drawable.ic_shop_15,
            R.drawable.ic_shop_16,
            R.drawable.ic_shop_17,
            R.drawable.ic_shop_18,
            R.drawable.ic_shop_19,
            R.drawable.ic_shop_20,
            R.drawable.ic_shop_21,
            R.drawable.ic_shop_22,
            R.drawable.ic_shop_23,
            R.drawable.ic_shop_24,
            R.drawable.ic_shop_25,
            R.drawable.ic_shop_26,
            R.drawable.ic_shop_27,
            R.drawable.ic_shop_28,
            R.drawable.ic_shop_29
            };
    
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
        
        Map<String,Object> vo = (Map<String, Object>) getGroup(groupPosition);
        
        TextView titleView = (TextView) convertView.findViewById(R.id.shop_title);
        titleView.setText(((String)vo.get("name")));
        
        ImageView shopId = (ImageView) convertView.findViewById(R.id.icon_shop_id);
        shopId.setImageResource(shopIdList[Integer.parseInt((String) vo.get("id"))-1]);
        
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon_arrow);
        if(isExpanded){
            icon.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_expanded));
        }else{
            icon.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_unexpanded));
        }
        
        Log.d(TAG,"position="+groupPosition+" getGroupCount="+getGroupCount()+" isExpanded="+isExpanded);
        if(getGroupCount()-1 == groupPosition && isExpanded == false){
            convertView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_row_bottom));
        }else{
            convertView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_row));
        }
        
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
        
        if(childPosition == 0){
            convertView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_child_row_top));
        }else if(isLastChild == true){
            convertView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_child_row_bottom));
        }else{
            convertView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_child_row));
        }
        
        return convertView;
    }
}
