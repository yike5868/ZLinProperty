package com.zlin.property.control;



public class FuFmArray {



	private int mManagerNameId;

	private ActionFragment[] mActionArray;

	public int getmManagerNameId() {
		return mManagerNameId;
	}

	public void setmManagerNameId(int id) {
		mManagerNameId = id;
	}

	public ActionFragment[] getmActionArray() {
		return mActionArray;
	}

	public void setmActionArray(ActionFragment array) {

		if (array == null) {
			return;
		}

		if (mActionArray == null) {
			mActionArray = new ActionFragment[1];
		} else {

			ActionFragment[] lTemp = new ActionFragment[mActionArray.length + 1];
			System.arraycopy(mActionArray, 0, lTemp, 0, mActionArray.length);
			mActionArray = lTemp;
			lTemp = null;
		}

		mActionArray[mActionArray.length - 1] = array;
	}
	
	public void setmActionArray(ActionFragment[] mActionArray) {
		this.mActionArray = mActionArray;
	}


}
