package ni.org.ics.estudios.appmovil.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import ni.org.ics.estudios.appmovil.wizard.ui.SelectAreaAmbienteFragment;

import java.util.ArrayList;

public class SelectAreaAmbientePage extends Page {


	public SelectAreaAmbientePage(ModelCallbacks callbacks, String title, String hintText, String textColor, boolean isVisible) {
		super(callbacks, title, hintText, textColor, isVisible, true);
	}

	@Override
	public Fragment createFragment() {
		return SelectAreaAmbienteFragment.create(getKey());
	}
	

	@Override
	public void getReviewItems(ArrayList<ReviewItem> dest) {
		dest.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY),
				getKey()));

	}


	@Override
	public boolean isCompleted() {
		return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY));
	}

	public SelectAreaAmbientePage setValue(String value) {
		mData.putString(SIMPLE_DATA_KEY, value);
		return this;
	}

}
