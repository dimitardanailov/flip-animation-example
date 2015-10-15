package filipanimation.exampleflipanimation.activities;

import android.app.Activity;
import android.os.Bundle;

import filipanimation.exampleflipanimation.R;
import filipanimation.exampleflipanimation.enums.fragments.FragmentAnimationType;
import filipanimation.exampleflipanimation.enums.fragments.FragmentTag;
import filipanimation.exampleflipanimation.fragments.BaseFragment;

/**
 * Created by Dimitar Danailov on 10/13/15.
 * email: dimityr.danailov@gmail.com
 */
public class AnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            BaseFragment.loadFragmentTransaction(this, R.id.container, FragmentTag.GOOGLE_MAP.getAbbreviation(), FragmentAnimationType.NONE);
            // BaseFragment.loadFragmentTransaction(this, R.id.container, FragmentTag.SECOND_CART.getAbbreviation(), FragmentAnimationType.NONE);
        }
    }
}
