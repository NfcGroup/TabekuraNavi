package jp.nfcgroup.tabekuranavi.model;

import java.util.ArrayList;
import jp.nfcgroup.tabekuranavi.model.vo.DishVO;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;

public class TestModel {

    private static final String[] shopNames = {"“X•Ü‚P","“X•Ü‚Q","“X•Ü‚R"};
    private static final int[] shopWeights = {1,1,2};
    private static final String[] dishNames = {"V‚µ‚¢—¿—","‚³‚ç‚ÉV‚µ‚¢—¿—","ÅV‚Ì—¿—"};
    private static final int[] dishPrices = {1,2,3};
    
    public static ArrayList<StoreVO> getTestStores(){
        ArrayList<StoreVO> stores = new ArrayList<StoreVO>();
        
        for(int i=0;i<shopNames.length;i++){
            StoreVO store = new StoreVO();
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
