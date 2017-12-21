package gustavo.brilhante.braviandroid.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import gustavo.brilhante.braviandroid.R;

/**
 * Created by Gustavo on 16/12/17.
 */

public class BaseFragment extends Fragment {



    public void goToFragment(Fragment fragment, boolean pushStack){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFragmentLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if(pushStack)fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    public void popFragmentFromStack(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

}
