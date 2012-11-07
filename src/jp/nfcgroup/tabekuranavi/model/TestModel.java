package jp.nfcgroup.tabekuranavi.model;

import java.util.ArrayList;
import jp.nfcgroup.tabekuranavi.model.vo.DishVO;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;

public class TestModel {

    private static final String[] shopNames = {"店舗１","店舗２","店舗３"};
    private static final int[] shopWeights = {1,1,2};
    private static final String[] dishNames = {"すごい料理","もっとすごい料理","最もすごい料理"};
    private static final int[] dishPrices = {1,2,3};
    
    public static ArrayList<StoreVO> getTestStores(){
        ArrayList<StoreVO> stores = new ArrayList<StoreVO>();
        
        for(int i=0;i<shopNames.length;i++){
            StoreVO store = new StoreVO();
            store.id = i+1;
            store.name = shopNames[i];
            store.weight = shopWeights[i];
            
            for(int j=0;j<dishNames.length;j++){
                DishVO dish = new DishVO();
                dish.name = dishNames[j];
                dish.price = dishPrices[j];
                store.dishes.add(dish);
            }
            stores.add(store);
        }
        
        return stores;
    }
}
