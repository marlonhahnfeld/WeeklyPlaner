package items;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.EditText;

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

    /**
     * Methode um den Unterstrich eines EditText-Fields farblich zu kennzeichen,
     * wenn er angeklickt wurde
     *
     * @param editText das jeweilige Textfeld
     * @param color    die Farbe die der Unterstrich bekommen soll
     */
    public static void setEditTextUnderlineColor(EditText editText, int color) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                editText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            } else {
                editText.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            }
        });
    }
}
