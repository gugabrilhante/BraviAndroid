package gustavo.brilhante.braviandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Gustavo on 15/12/17.
 */

public class BaseActivity extends AppCompatActivity {

    public ActivityState ACTIVITY_STATE = ActivityState.CREATED;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ACTIVITY_STATE = ActivityState.CREATED;


    }

    @Override
    protected void onResume() {
        super.onResume();
        ACTIVITY_STATE = ActivityState.RESUMED;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ACTIVITY_STATE = ActivityState.STARTED;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ACTIVITY_STATE = ActivityState.PAUSED;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ACTIVITY_STATE = ActivityState.STOPPED;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ACTIVITY_STATE = ActivityState.DESTROYED;
    }

    public enum ActivityState{
        CREATED, RESUMED, STARTED, PAUSED, STOPPED, DESTROYED;
    }

    public Context getContext(){
        return this;
    }

    public String getStringFromResource(int id){
        return getContext().getResources().getString(id);
    }


}
