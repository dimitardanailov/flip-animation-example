package filipanimation.exampleflipanimation.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import filipanimation.exampleflipanimation.R;
import filipanimation.exampleflipanimation.enums.fragments.FragmentAnimationType;
import filipanimation.exampleflipanimation.enums.fragments.FragmentTag;

/**
 * Created by Dimitar Danailov on 10/15/15.
 * email: dimityr.danailov@gmail.com
 */
public class CardHolderFragment extends BaseFragment implements View.OnClickListener {

    // Get class name
    private static final String TAG = CardHolderFragment.class.getName();

    // Pointer to view holder
    private View mainView;

    // Flip Card components
    private AnimatorSet frontCardAnimation = null;
    private AnimatorSet backCardAnimation = null;

    // Visible Card
    private FragmentTag visibleCart = FragmentTag.GOOGLE_MAP;

    // Fragment Elements
    Button flipCardButton;

    // Card Elements
    RelativeLayout cardFront;
    RelativeLayout cardBack;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setActivity();

        mainView = inflater.inflate(R.layout.card_holder_fragment, container, false);

        // Load Cart Front Fragments
        BaseFragment.loadFragmentTransaction(this.activity, R.id.map_container, FragmentTag.GOOGLE_MAP.getAbbreviation(), FragmentAnimationType.NONE);

        initializeFragmentElements();

        return mainView;
    }

    @Override
    public void onClick(View view) {
        if (activity == null) {
            return; // Emergency exit
        }

        // Get Integer Code Id
        int id = view.getId();

        // We use if - else construction, because is better from switch block
        // Please visit http://tools.android.com/tips/non-constant-fields for more information
        if (id == R.id.flip_animation) {
            loadFlipAnimation();
        }
    }

    /**
     * Initialize fragment elements
     */
    private void initializeFragmentElements() {
        flipCardButton = (Button) mainView.findViewById(R.id.flip_animation);
        updateFlipCartButton();
        flipCardButton.setOnClickListener(this);
    }

    private void loadFlipAnimation() {
        flipCardButton.setEnabled(false);
        setFlipCardConfiguration();

        if (visibleCart.equals(FragmentTag.GOOGLE_MAP)) {
            loadFlipAnimationForBackCard();
        } else {
            loadFlipAnimationForFrontCard();
        }
    }

    private void updateFlipCartButton() {
        if (visibleCart.equals(FragmentTag.GOOGLE_MAP)) {
            flipCardButton.setText(R.string.flip_card_button_show_front_card);
        } else {
            flipCardButton.setText(R.string.flip_card_button_show_second_card);
        }

        flipCardButton.setEnabled(true);
    }


    /**
     * Current method make initialization of Flip Card animation.
     *
     * Get idea for implementation from: http://stackoverflow.com/questions/26191977/implement-card-flip-animation-between-two-views-using-animatorset
     *
     * XML layout get from: http://developer.android.com/training/animation/cardflip.html
     *
     * Warning: You can't view animation effect on Android Emulator.
     */
    private void setFlipCardConfiguration() {

        if (mainView == null) {
            return;
        }

        if (frontCardAnimation == null || backCardAnimation == null) {

            cardFront = (RelativeLayout) mainView.findViewById(R.id.map_container);
            cardBack = (RelativeLayout) mainView.findViewById(R.id.card_back_container);

            if (cardBack.getChildCount() == 0) {
                // Load Second Card
                BaseFragment.loadFragmentTransaction(this.activity, R.id.card_back_container, FragmentTag.SECOND_CART.getAbbreviation(), FragmentAnimationType.NONE);
            }

            // Load the animator sets from XML and group them together
            AnimatorSet flipCardRightIn = (AnimatorSet) AnimatorInflater
                    .loadAnimator(activity, R.animator.card_flip_right_in);
            AnimatorSet flipCardRightOut = (AnimatorSet) AnimatorInflater
                    .loadAnimator(activity, R.animator.card_flip_right_out);

            // Setup Front Card.
            flipCardRightIn.setTarget(cardFront);
            flipCardRightOut.setTarget(cardBack);

            frontCardAnimation = new AnimatorSet();
            frontCardAnimation.playTogether(flipCardRightIn, flipCardRightOut);

            // Load the animator sets from XML and group them together
            AnimatorSet flipCardLeftIn = (AnimatorSet) AnimatorInflater
                    .loadAnimator(activity, R.animator.card_flip_left_in);
            AnimatorSet flipCardLeftOut = (AnimatorSet) AnimatorInflater
                    .loadAnimator(activity, R.animator.card_flip_left_out);

            // Setup Back Card.
            flipCardLeftIn.setTarget(cardBack);
            flipCardLeftOut.setTarget(cardFront);

            backCardAnimation = new AnimatorSet();
            backCardAnimation.playTogether(flipCardLeftIn, flipCardLeftOut);
        }
    }

    /**
     * Try to display front of card.
     */
    private void loadFlipAnimationForFrontCard() {
        if (frontCardAnimation == null) {
            return;
        }

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        ObjectAnimator animX = ObjectAnimator.ofFloat(cardBack, "x", size.x);

        animX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                cardFront.setVisibility(View.VISIBLE);
                cardBack.setVisibility(View.GONE);
                updateFlipCartButton();
                visibleCart = FragmentTag.GOOGLE_MAP;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                cardBack.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        frontCardAnimation.play(animX);
        frontCardAnimation.start();
    }

    /**
     * Try to display back of card.
     */
    private void loadFlipAnimationForBackCard() {
        if (backCardAnimation == null) {
            return;
        }

        ObjectAnimator animX = ObjectAnimator.ofFloat(cardBack, "x", 0f);

        animX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                cardBack.setVisibility(View.VISIBLE);
                cardFront.setVisibility(View.GONE);
                updateFlipCartButton();
                visibleCart = FragmentTag.SECOND_CART;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                cardBack.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        backCardAnimation.play(animX);
        backCardAnimation.start();
    }
}
