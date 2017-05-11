package ni.org.ics.estudios.appmovil.wizard.model;

import java.util.ArrayList;

import ni.org.ics.estudios.appmovil.wizard.ui.LabelFragment;
import android.support.v4.app.Fragment;

public class LabelPage extends Page {
	
	protected boolean mValPattern = false;
	protected String mPattern="";

	public LabelPage(ModelCallbacks callbacks, String title, String hintText, String textColor) {
		super(callbacks, title, hintText, textColor);
	}

	@Override
	public Fragment createFragment() {
		return LabelFragment.create(getKey());
	}

	@Override
	public void getReviewItems(ArrayList<ReviewItem> dest) {

	}
}
