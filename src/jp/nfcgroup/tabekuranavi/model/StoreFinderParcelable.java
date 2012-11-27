package jp.nfcgroup.tabekuranavi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreFinderParcelable implements Parcelable {
	//private StoreFinder[] mStoreFinders = new StoreFinder[1];
	private StoreFinder mStoreFinder;

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		//dest.writeArray((Object[])mStoreFinders);
		dest.writeParcelable(mStoreFinder, flags);
	}
	
	public static final Parcelable.Creator<StoreFinderParcelable> CREATOR =
			new Parcelable.Creator<StoreFinderParcelable>() {
		
		public StoreFinderParcelable createFromParcel(Parcel in) {
			return new StoreFinderParcelable(in);
		}
		
		public StoreFinderParcelable[] newArray(int size) {
			return new StoreFinderParcelable[size];
		}
	};

	private StoreFinderParcelable(Parcel in) {
		//Object[] objects = in.readArray(StoreFinder.class.getClassLoader());
		//mStoreFinders[0] = (StoreFinder)objects[0];
		mStoreFinder = in.readParcelable(StoreFinder.class.getClassLoader());
	}
	
	public StoreFinderParcelable(StoreFinder finder) {
		//mStoreFinders[0] = finder;
		mStoreFinder = finder;
	}
	
	public StoreFinder getStoreFinder() {
		//return mStoreFinders[0];
		return mStoreFinder;
	}
}
