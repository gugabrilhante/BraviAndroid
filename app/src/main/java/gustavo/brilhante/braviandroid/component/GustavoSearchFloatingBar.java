package gustavo.brilhante.braviandroid.component;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import gustavo.brilhante.braviandroid.R;



public class GustavoSearchFloatingBar extends LinearLayout {

    LayoutInflater inflater = null;

    LinearLayout searchEditLayout, movementLayout, lupaLayout;

    LinearLayout currentInstance = this;

    Button searchButton;

    int movementLayoutWidth = 0;
    boolean movementSizeSetted = false;

    boolean isCollapsed = false;
    boolean requestFocus = false;

    EditText editText;

    String hint = "";

    public GustavoSearchFloatingBar(Context context) {
        super(context);
        initViews();
    }

    public GustavoSearchFloatingBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GustavoSearchFloatingBar, 0, 0);
        try{
            hint = typedArray.getString(R.styleable.GustavoSearchFloatingBar_hint);
            requestFocus = typedArray.getBoolean(R.styleable.GustavoSearchFloatingBar_requestFocus, false);
        }finally {
            if (!isInEditMode()) {
                initViews();
            }
        }

    }

    public GustavoSearchFloatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void initViews(){

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_search_floating_bar, this, true);

        searchEditLayout = (LinearLayout) findViewById(R.id.seachEditLayout);

        searchButton = (Button) findViewById(R.id.searchTextView);

        editText = (EditText) findViewById(R.id.editText);
        editText.setHint(hint);
        if(requestFocus)editText.requestFocus();
        //getLayoutSizes();


    }

    public void setSearchButtonClickListener(OnClickListener listener){
        searchButton.setOnClickListener(listener);
    }

    public void setText(String text){
        editText.setText(text);
    }

    public String getText(){
        return editText.getText().toString();
    }

    public void show(){
        this.setVisibility(VISIBLE);
        this.setAlpha(0f);
        this.animate().alpha(1f).setDuration(300);
    }

    public void hide(){
        this.setAlpha(1f);
        this.animate().alpha(0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentInstance.setVisibility(GONE);
            }
        });
    }



    public void showLayout(){

    }

    interface listener{
        void onAction();
    }

    /*private void getLayoutSizes(){
        movementLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(movementLayoutWidth==0) {
                    movementLayoutWidth = movementLayout.getWidth();
                    movementSizeSetted = true;
                    collapseLayout();
                }
            }
        });
    }*/

}
