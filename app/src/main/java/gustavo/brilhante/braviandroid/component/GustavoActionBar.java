package gustavo.brilhante.braviandroid.component;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gustavo.brilhante.braviandroid.R;


public class GustavoActionBar extends RelativeLayout {

    LayoutInflater inflater = null;

    RelativeLayout backButtonTouchArea, rightButtonTouchArea, middleLayout;

    TextView titleTextView;

    ImageButton backButton, rightButton;

    ImageView arrowDownImageView;

    String text = "";

    Activity activity;

    boolean backClickable = false;
    boolean rightClickable = false;

    boolean backButtonEnable = false;
    boolean rightButtonEnable = false;

    Drawable rightButtonIcon;

    RightButtonListener listener;

    BackButtonListener backListener;

    MiddleTextListener middleTextListener;

    public GustavoActionBar(Context context) {
        super(context);
        initViews();
        if(context instanceof Activity){
            if(backButtonEnable)enableBackButton((Activity) context);
        }
    }

    public GustavoActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GustavoActionBar, 0, 0);
        try{
            text = typedArray.getString(R.styleable.GustavoActionBar_text);
            backButtonEnable = typedArray.getBoolean(R.styleable.GustavoActionBar_backEnabled, false);
            rightButtonEnable = typedArray.getBoolean(R.styleable.GustavoActionBar_rightEnabled, false);
            rightButtonIcon = typedArray.getDrawable(R.styleable.GustavoActionBar_rightButtonIcon);
        }finally {
            if (!isInEditMode()) {
                initViews();
                if(context instanceof Activity){
                    if(backButtonEnable)enableBackButton((Activity) context);
                    if(rightButtonEnable)enableRightButton((Activity) context);
                    if(rightButtonIcon!=null)setRightButtonIcon(rightButtonIcon);
                }
            }
        }
    }

    public GustavoActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void initViews(){

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_custom_actionbar, this, true);

        backButtonTouchArea = (RelativeLayout) findViewById(R.id.backButtonTouchArea);
        rightButtonTouchArea = (RelativeLayout) findViewById(R.id.rightButtonTouchArea);
        middleLayout = (RelativeLayout) findViewById(R.id.middleLayout);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        backButton = (ImageButton) findViewById(R.id.backButton);
        rightButton = (ImageButton) findViewById(R.id.rightButton);
        arrowDownImageView = (ImageView) findViewById(R.id.arrowDownImageView);

        if(!text.isEmpty())titleTextView.setText(text);

    }

    public void enableRightButton(Activity activity){
        rightButton.setVisibility(View.VISIBLE);
        Animation scaleIn = AnimationUtils.loadAnimation(activity, R.anim.scale_in);
        rightButton.startAnimation(scaleIn);
        rightClickable = true;
        this.activity = activity;
        rightButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return rightAnimation(event);
            }
        });
        rightButtonTouchArea.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return rightAnimation(event);
            }
        });
    }

    public void enableBackButton(Activity activity){
        backButton.setVisibility(View.VISIBLE);
        Animation scaleIn = AnimationUtils.loadAnimation(activity, R.anim.scale_in);
        backButton.startAnimation(scaleIn);
        backClickable = true;
        this.activity = activity;
        backButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return backAnimation(event);
            }
        });
        backButtonTouchArea.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return backAnimation(event);
            }
        });
    }

    public void disableBackButton(){
        backButton.setVisibility(View.GONE);
        backClickable = false;
    }

    public boolean backAnimation(MotionEvent event){
        if(backClickable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Animation shakeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_in);
                    backButton.startAnimation(shakeOutAnimation);
                    return true;
                case MotionEvent.ACTION_UP:
                    Animation shakeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_out);
                    shakeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if(backListener!=null){
                                backListener.onBackButtonClick(backButton);
                            }
                            else if(activity!=null) {
                                activity.onBackPressed();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    backButton.startAnimation(shakeInAnimation);


                    return true;
            }
        }
        return false;
    }

    public boolean rightAnimation(MotionEvent event){
        if(rightClickable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Animation shrinkInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_in);
                    rightButton.startAnimation(shrinkInAnimation);
                    return true;
                case MotionEvent.ACTION_UP:
                    Animation shrinkOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_out);
                    shrinkOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if(listener!=null) {
                                listener.onRightButtonClick(rightButton);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    rightButton.startAnimation(shrinkOutAnimation);


                    return true;
            }
        }
        return false;
    }

    public void setMiddleTextClickListener(final MiddleTextListener middleTextClickListener){
        this.middleTextListener = middleTextClickListener;
        titleTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return animateMiddleText(event);
            }
        });
        arrowDownImageView.setVisibility(View.VISIBLE);
        arrowDownImageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return animateMiddleText(event);
            }
        });
    }

    public boolean animateMiddleText(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Animation shakeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_in_light);
                middleLayout.startAnimation(shakeOutAnimation);
                return true;
            case MotionEvent.ACTION_UP:
                Animation shakeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_out_light);
                shakeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(middleTextListener!=null) {
                            middleTextListener.onMiddleTextClick(titleTextView);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                middleLayout.startAnimation(shakeInAnimation);

        }
        return true;
    }


    //deve ser passado um id do drawable ex: R.drawable.icon
    public void setRightButtonIcon(Drawable drawable){
        rightButton.setBackground( drawable );
    }

    //deve ser passado um id do drawable ex: R.drawable.icon
    public void setRightButtonIcon(int id){
        rightButton.setBackground( ((Context)activity).getResources().getDrawable(id) );
    }

    // deve ser configurado um callback para o click do botão direito.
    // ex: CitrumActionBar.setRightClickListener(new CitrumActionBar.RightButtonListener(){...})
    public void setRightClickListener(RightButtonListener listener) {
        this.listener = listener;
    }

    public void setBackButtonListener(BackButtonListener listener){
        this.backListener = listener;
    }

    public void setTitleTextView(String text){
        titleTextView.setText(text);
    }

    public interface RightButtonListener{
        void onRightButtonClick(ImageButton button);
    }

    public interface BackButtonListener{
        void onBackButtonClick(ImageButton button);
    }

    public interface MiddleTextListener{
        void onMiddleTextClick(TextView button);
    }

}
