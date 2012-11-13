package jp.nfcgroup.tabekuranavi.adapter;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.R;
import jp.nfcgroup.tabekuranavi.model.vo.DishVO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StoreDialogAdapter extends ArrayAdapter<DishVO> {
	private ArrayList<DishVO> mItems;
	private LayoutInflater mInfalter;
	
	public StoreDialogAdapter(Context context, int resourceId, ArrayList<DishVO> items) {
		super(context, resourceId, items);
		mItems = items;
		mInfalter = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = mInfalter.inflate(R.layout.dialog_row, null);
		}
		
		TextView dishName = (TextView)convertView.findViewById(R.id.dialog_dish_name);
		TextView priceValue = (TextView)convertView.findViewById(R.id.dialog_price_value);
		
		dishName.setText(mItems.get(position).name);
		String rokka = String.valueOf(mItems.get(position).price) + " rokka";
		priceValue.setText(rokka);
		
		return convertView;
	}
	
}
