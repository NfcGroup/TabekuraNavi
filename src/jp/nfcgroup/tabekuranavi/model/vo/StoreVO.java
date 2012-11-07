package jp.nfcgroup.tabekuranavi.model.vo;

import java.util.ArrayList;

public class StoreVO {
	public int id;
    public String name;
    public String subTitle;
    public int weight;
    public ArrayList<DishVO> dishes = new ArrayList<DishVO>();
}
