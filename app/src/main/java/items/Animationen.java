package items;

import android.animation.ObjectAnimator;
import android.view.View;

public class Animationen {

    public static void animateText(View view) {
        view.setVisibility(View.INVISIBLE);
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animation.setDuration(2000);
        animation.start();
    }
}
