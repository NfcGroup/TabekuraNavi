package jp.nfcgroup.tabekuranavi.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.nfcgroup.tabekuranavi.MapActivity;
import jp.nfcgroup.tabekuranavi.R;
import jp.nfcgroup.tabekuranavi.adapter.StoreListAdapter;
import jp.nfcgroup.tabekuranavi.model.StoreFinder;
import jp.nfcgroup.tabekuranavi.model.vo.DishVO;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class ListFragment extends Fragment implements OnClickListener {
    
    @SuppressWarnings("unused")
    private static final String TAG = "ListFragment";
    
    private ExpandableListView listView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        StoreFinder storeFinder = new StoreFinder(getActivity().getApplicationContext());
        ArrayList<StoreVO> stores = storeFinder.getStores();
        
        List<Map<String,Object>> parents = new ArrayList<Map<String,Object>>();
        List<List<Map<String,Object>>> children = new ArrayList<List<Map<String,Object>>>();
        
        for(StoreVO store:stores){
            Map<String, Object> parent = new HashMap<String,Object>();
            parent.put("id",String.valueOf(store.id));
            parent.put("name",store.name);
            parent.put("weight", String.valueOf(store.weight));
            parents.add(parent);
            
            List<Map<String,Object>> child = new ArrayList<Map<String,Object>>();
            for(DishVO dish:store.dishes){
                Map<String,Object> childEntity = new HashMap<String,Object>();
                childEntity.put("name", dish.name);
                childEntity.put("price", String.valueOf(dish.price));
                child.add(childEntity);
            }
            children.add(child);
        }
        
        StoreListAdapter adapter = new StoreListAdapter(
                getActivity(),
                parents, 
                android.R.layout.simple_expandable_list_item_1,
                new String[] {"name"},
                new int[] { android.R.id.text1 },
                children,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {"name", "price"},
                new int[] { android.R.id.text1, android.R.id.text2 });
        
        listView = (ExpandableListView) getActivity().findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setGroupIndicator(null);
        listView.setDividerHeight(0);
    }
    
    public void updateViews(){
        //TODO Modelデータ引き受け
    }

    public void onClick(View v) {
        Intent intent = new Intent(getActivity().getApplicationContext(),MapActivity.class);
        getActivity().startActivity(intent);
    }
    
}
