package jp.nfcgroup.tabekuranavi.model.vo;

import java.util.ArrayList;

public class StoreVO {
	public int storeId;
    public String name;
    public int weight;
    public ArrayList<DishVO> dishes = new ArrayList<DishVO>();
}
