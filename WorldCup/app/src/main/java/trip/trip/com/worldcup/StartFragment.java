package trip.trip.com.worldcup;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import trip.trip.com.worldcup.lib.Invoker;

public class StartFragment extends Fragment {

    private Invoker invoker;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        invoker = Invoker.getInstance();

        View root = inflater.inflate(R.layout.start_fragment, container, false);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invoker.runCommand(Invoker.SLOT_GAMESTART);
            }
        });

        return root;

    }
    public static StartFragment newInstance(){
        StartFragment startFragment = new StartFragment();
        return startFragment;
    }

}
