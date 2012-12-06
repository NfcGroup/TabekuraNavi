package jp.nfcgroup.tabekuranavi.fragment;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.R;
import jp.nfcgroup.tabekuranavi.adapter.StoreDialogAdapter;
import jp.nfcgroup.tabekuranavi.model.StoreFinder;
import jp.nfcgroup.tabekuranavi.model.StoreFinderParcelable;
import jp.nfcgroup.tabekuranavi.model.vo.DishVO;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class StoreDialogFragment extends DialogFragment implements View.OnClickListener {
	private int mStoreId;
	private StoreFinder mStoreFinder;
	
	public static StoreDialogFragment newInstance(int storeId, StoreFinder storeFinder) {
		StoreDialogFragment f = new StoreDialogFragment();
		
		StoreFinderParcelable parcelable = new StoreFinderParcelable(storeFinder);
		
		Bundle args = new Bundle();
		args.putInt("id", storeId);
		args.putParcelable("finder", parcelable);
		f.setArguments(args);
		
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StoreFinderParcelable parcelable = getArguments().getParcelable("finder");
		
		mStoreId = getArguments().getInt("id");
		mStoreFinder = parcelable.getStoreFinder();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dialog, container, false);
		
		// ダイアログの内容を生成
		ListView dialogList = (ListView)view.findViewById(R.id.dialog_list);
		
		ArrayList<StoreVO> stores = mStoreFinder.getAllStore();
		TextView dialogTitle = (TextView)view.findViewById(R.id.dialog_title);
		TextView dialogSubTitle = (TextView)view.findViewById(R.id.dialog_subtitle);
		dialogTitle.setText(stores.get(mStoreId).name);
		dialogSubTitle.setText(stores.get(mStoreId).subTitle.replace("¥n", "\n"));
		
		ArrayList<DishVO> items = stores.get(mStoreId).dishes;
		ListAdapter adapter = new StoreDialogAdapter(getActivity(), R.layout.dialog_row, items);
		dialogList.setAdapter(adapter);
		
		// クローズボタン生成
		ImageButton button = (ImageButton)view.findViewById(R.id.dialog_close);
		button.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/*
		Dialog dialog = getDialog();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int dialogWidth = (int)(metrics.widthPixels);
		int dialogHeight = (int)(metrics.heightPixels * 0.8);
		lp.width = dialogWidth;
		lp.height = dialogHeight;
		dialog.getWindow().setAttributes(lp);
		 */
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity(), R.style.StoreListTheme);
		return dialog;
	}
	
	public void onClick(View view) {
		dismiss();
	}
}
