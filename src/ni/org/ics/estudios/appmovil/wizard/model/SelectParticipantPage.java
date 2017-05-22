package ni.org.ics.estudios.appmovil.wizard.model;

import java.util.ArrayList;

import ni.org.ics.estudios.appmovil.wizard.ui.SelectParticipantFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

public class SelectParticipantPage extends Page {
	

	public SelectParticipantPage(ModelCallbacks callbacks, String title, String hintText, String textColor, boolean isVisible) {
		super(callbacks, title, hintText, textColor, isVisible);
	}

	@Override
	public Fragment createFragment() {
		return SelectParticipantFragment.create(getKey());
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

	public SelectParticipantPage setValue(String value) {
		mData.putString(SIMPLE_DATA_KEY, value);
		return this;
	}
}
