package gustavo.brilhante.braviandroid.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import gustavo.brilhante.braviandroid.R;
import gustavo.brilhante.braviandroid.util.AnimUtils;

/**
 * Created by Gustavo on 16/12/17.
 */

public abstract class NavigationActivity extends BaseActivity {

    LinearLayout loadingLayout, errorMessageLayout;
    FrameLayout contentFragmentLayout;
    CircularProgressView progressView;

    public boolean isLoadingSpinning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(findViewById(R.id.loadingLayout)!=null)loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
        if(findViewById(R.id.errorMessageLayout)!=null){
            errorMessageLayout = (LinearLayout) findViewById(R.id.errorMessageLayout);
            errorMessageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    errorMessageClicked();
                }
            });
        }
        if(findViewById(R.id.contentFragmentLayout)!=null)contentFragmentLayout = (FrameLayout) findViewById(R.id.contentFragmentLayout);
        if(findViewById(R.id.progressView)!=null)progressView = (CircularProgressView) findViewById(R.id.progressView);
    }

    public void goToFragment(Fragment fragment, boolean pushStack){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFragmentLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if(pushStack)fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    public void popFragmentFromStack(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    public abstract void errorMessageClicked();

    public void setLoading(boolean isLoading, boolean error) {
        if (isLoading && loadingLayout.getVisibility() == View.GONE) {
            isLoadingSpinning = isLoading;
            loadingLayout.setVisibility(View.VISIBLE);
            progressView.startAnimation();
            contentFragmentLayout.setVisibility(View.GONE);
            errorMessageLayout.setVisibility(View.GONE);
            AnimUtils.fadeInView(loadingLayout);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        } else if (!isLoading && loadingLayout.getVisibility() == View.VISIBLE && !error) {
            isLoadingSpinning = isLoading;
            loadingLayout.setVisibility(View.GONE);
            progressView.stopAnimation();
            contentFragmentLayout.setVisibility(View.VISIBLE);
            AnimUtils.fadeInView(contentFragmentLayout);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else if (!isLoading && loadingLayout.getVisibility() == View.VISIBLE && error) {
            isLoadingSpinning = isLoading;
            loadingLayout.setVisibility(View.GONE);
            progressView.stopAnimation();
            errorMessageLayout.setVisibility(View.VISIBLE);
            AnimUtils.fadeInView(errorMessageLayout);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

}
