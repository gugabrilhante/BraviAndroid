package gustavo.brilhante.braviandroid.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import gustavo.brilhante.braviandroid.constant.Config;


/**
 * Created by Gustavo on 19/05/17.
 */

public class AnimUtils {

    public static void fadeInView(View view){
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(Config.VIEW_FADE_IN_TIME).start();
    }

    public static void fadeOutView(View view){
        view.setAlpha(1f);
        view.animate().alpha(0f).setDuration(Config.VIEW_FADE_IN_TIME).start();
    }

    public static void fadeInViewAndVisiBle(final View view){
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(Config.VIEW_FADE_IN_TIME).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
                view.setVisibility(View.VISIBLE);
            }
        }).start();
    }

    public static void fadeOutViewAndGone(final View view){
        view.setVisibility(View.VISIBLE);
        view.setAlpha(1f);
        view.animate().alpha(0f).setDuration(Config.VIEW_FADE_IN_TIME).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        }).start();
    }

    public static void fadeInViewWithTime(View view, int time){
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(time).start();
    }

    public static int calculateBottomSwipeRefreshAnim(int progressBottomLayoutHeight, int recyclerViewMaxScroll, int recyclerViewOffSet, int recyclerViewExtendScroll){
        return (progressBottomLayoutHeight - (recyclerViewMaxScroll - (recyclerViewOffSet + recyclerViewExtendScroll)));
    }

}
