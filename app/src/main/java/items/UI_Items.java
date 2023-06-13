package items;

import android.animation.ObjectAnimator;
import android.view.View;

public class UI_Items {

    /**
     * Methode um eine View nach einer angegebenen Dauer einzublenden
     *
     * @param view     welches animiert eingeblendet werden soll
     * @param duration die Dauer der Animation
     */
    public static void animateNonVisible2Visible(View view, long duration) {
        ObjectAnimator animation =
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animation.setDuration(duration);
        animation.start();
    }

}
