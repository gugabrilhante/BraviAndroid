package gustavo.brilhante.braviandroid.util;

import android.content.Context;

import gustavo.brilhante.braviandroid.R;
import gustavo.brilhante.braviandroid.base.BaseActivity;
import gustavo.brilhante.braviandroid.component.dialog.GustavoMessageDialog;

/**
 * Created by Gustavo on 15/12/17.
 */

public class ContextUtils {

    public static void showErrorDialog(Context context, String msg){
        if(checkIfBaseContextIsInForeground(context))
            createAndShowDialog(context, "Erro", msg, R.drawable.error_icon, true);
    }

    public static void showSuccessDialog(Context context, String msg){
        if(checkIfBaseContextIsInForeground(context))
            createAndShowDialog(context, "Sucesso", msg, R.drawable.checked, true);
    }

    public static void showWarningDialog(Context context, String msg){
        if(checkIfBaseContextIsInForeground(context))
            createAndShowDialog(context, "Atenção", msg, R.drawable.warning_icon, true);
    }

    public static boolean checkIfBaseContextIsInForeground(Context context){
        if(context instanceof BaseActivity){
            BaseActivity activity = (BaseActivity) context;
            if(activity.ACTIVITY_STATE== BaseActivity.ActivityState.STOPPED
                    && activity.ACTIVITY_STATE== BaseActivity.ActivityState.PAUSED
                    && activity.ACTIVITY_STATE== BaseActivity.ActivityState.DESTROYED){
                return false;
            }
        }
        return true;
    }

    private static void createAndShowDialog(Context context, String title, String msg, int icon, boolean showIcon){

        GustavoMessageDialog dialog = new GustavoMessageDialog(context);
        if(showIcon)dialog.setIconImageView(icon);
        dialog.setMessage(msg);
        dialog.setOption1Click(GustavoMessageDialog.ACTION_DIALOG_DISMISS);
        dialog.inflateDialog();

    }

}
