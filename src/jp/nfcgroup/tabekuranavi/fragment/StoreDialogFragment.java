package jp.nfcgroup.tabekuranavi.fragment;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.R;
import jp.nfcgroup.tabekuranavi.adapter.StoreDialogAdapter;
import jp.nfcgroup.tabekuranavi.model.StoresData;
import jp.nfcgroup.tabekuranavi.model.vo.DishVO;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class StoreDialogFragment extends DialogFragment {
	private int mStoreId;
	
	public static StoreDialogFragment newInstance(int id) {
		StoreDialogFragment f = new StoreDialogFragment();
		
		Bundle args = new Bundle();
		args.putInt("StoreId", id);
		f.setArguments(args);
		
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mStoreId = getArguments().getInt("StoreId");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dialog, container, false);
		
		ListView dialogList = (ListView)view.findViewById(R.id.dialog_list);
		
		ArrayList<StoreVO> stores = StoresData.getInstance().getAllStore(getActivity());
		TextView dialogTitle = (TextView)view.findViewById(R.id.dialog_title);
		dialogTitle.setText(stores.get(mStoreId).name);
		
		ArrayList<DishVO> items = stores.get(mStoreId).dishes;
		ListAdapter adapter = new StoreDialogAdapter(getActivity(), R.layout.dialog_row, items);
		dialogList.setAdapter(adapter);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Dialog dialog = getDialog();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int dialogWidth = (int)(metrics.widthPixels);
		//int dialogHeight = (int)(metrics.heightPixels * 0.8);
		lp.width = dialogWidth;
		//lp.height = dialogHeight;
		//dialog.getWindow().setAttributes(lp);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity(), R.style.StoreListTheme);
		return dialog;
	}
}
