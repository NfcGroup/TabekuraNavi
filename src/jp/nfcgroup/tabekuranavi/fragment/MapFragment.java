package jp.nfcgroup.tabekuranavi.fragment;

import jp.nfcgroup.tabekuranavi.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MapFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_map, container);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Button dialogButton = (Button)getActivity().findViewById(R.id.button_dialog);
		dialogButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				StoreDialogFragment sdialog = StoreDialogFragment.newInstance(14);
				sdialog.show(getFragmentManager(), "dialog");
			}
		});
	}
}
