package jp.nfcgroup.tabekuranavi.model.vo;

public class DishVO {
	public int id;			// 商品識別ID
    public String name;		// 商品名称
    public int shopId;		// 店舗識別ID
    public int price;		// 値段（単位はrokka）
    public int priceTo;		// 値段の下限（単位はrokka）
    public int priceFrom;	// 値段の上限（単位はrokka）
    
    public DishVO() {
    	price = 0;
    	priceTo = 0;
    	priceFrom = 0;
    }
    
    @Override
    public String toString() {
    	String str;
    	if(priceTo > 0 && priceFrom == 0) {
    		str = String.valueOf(priceTo) + " 〜 ";
    	} else if(priceTo == 0 && priceFrom > 0) {
    		str = " 〜 " + String.valueOf(priceFrom);
    	} else if(priceTo > 0 && priceFrom > 0) {
    		str = String.valueOf(priceTo) + " 〜 " + String.valueOf(priceFrom);
    	} else {
    		str = String.valueOf(price);
    	}
    	return str;
    }
}
