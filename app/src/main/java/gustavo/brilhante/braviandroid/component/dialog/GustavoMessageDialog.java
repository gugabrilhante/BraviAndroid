package gustavo.brilhante.braviandroid.component.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import gustavo.brilhante.braviandroid.R;


/**
 * Created by Administrador on 21/06/2017.
 */

public class GustavoMessageDialog extends AlertDialog.Builder {

    TextView titleTextView, messageTextView;
    Button option1;
    ImageView iconImageView;
    Context context;
    Activity activity;
    AlertDialog dialog;

    public final static int ACTION_DIALOG_DISMISS = 1;
    public final static int ACTION_DIALOG_DELEGATE = 2;

    public GustavoMessageDialog(Context context) {
        super(context);

        this.context = context;
        this.activity = (Activity)context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_message_simple, null);

        this.setView(layout);

        titleTextView = (TextView) layout.findViewById(R.id.titleTextView);
        iconImageView = (ImageView) layout.findViewById(R.id.iconImageView);
        messageTextView = (TextView) layout.findViewById(R.id.messageTextView);
        option1 = (Button) layout.findViewById(R.id.button);

        Animation scaleIn = AnimationUtils.loadAnimation(activity, R.anim.scale_in_fast);
        iconImageView.startAnimation(scaleIn);
        //setOption1Click(ACTION_DIALOG_DISMISS);
    }

    public GustavoMessageDialog(Context context, int themeResId) {
        super(context, themeResId);

        this.context = context;
        this.activity = (Activity)context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_message_simple, null);

        this.setView(layout);

        titleTextView = (TextView) layout.findViewById(R.id.titleTextView);
        messageTextView = (TextView) layout.findViewById(R.id.messageTextView);
        option1 = (Button) layout.findViewById(R.id.button);
    }

    public void setIconImageView(int drawableId){
        iconImageView.setVisibility(View.VISIBLE);
        iconImageView.setImageResource(drawableId);
    }

    public void setTitleColor(int colorId){
        titleTextView.setTextColor(colorId);
    }

    public void setTitle(String text){
        titleTextView.setVisibility(View.VISIBLE);
        titleTextView.setText(text);
    }
    public void setMessage(String text){
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(text);
    }

    public void setOption1BtnText(String text){
        option1.setVisibility(View.VISIBLE);
        option1.setText(text);
    }

    public void setOption1Click(View.OnClickListener listener){
        option1.setOnClickListener(listener);
    }

    public void setOption1Click(int action){
        setActionButtonDialog(option1, action);
    }

    public void setActionButtonDialog(Button btn, int action){

        if(action==ACTION_DIALOG_DISMISS){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }else if(action==ACTION_DIALOG_DELEGATE){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

    }


    public void dismiss(){
        dialog.dismiss();
    }

    public void inflateDialog(){
        dialog = this.create();
        dialog.show();
    }

    public void inflateDialog(boolean cancelable){
        dialog = this.create();
        dialog.setCancelable(cancelable);
        dialog.show();

    }

}
