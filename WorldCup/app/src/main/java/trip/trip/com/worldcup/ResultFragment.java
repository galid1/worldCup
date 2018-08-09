package trip.trip.com.worldcup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import trip.trip.com.worldcup.lib.BitmapFromAsset;
import trip.trip.com.worldcup.lib.Invoker;

public class ResultFragment extends Fragment {

    private Invoker invoker;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;

    public static final String ARGS_CANDIDATES = "ARGS_CANDIDATES";

    public static ResultFragment newInstance(ArrayList<Celebrity> candidates){
        ResultFragment resultFragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_CANDIDATES , candidates);
        resultFragment.setArguments(args);
        return resultFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container , false);

        invoker = Invoker.getInstance();

        Button onContinueButton = view.findViewById(R.id.progress_button);
        onContinueButton.setOnClickListener(v->{
            invoker.runCommand(Invoker.SLOT_PREROUND);
        });

        Bundle args = getArguments();
        if(args != null){
            ArrayList<Celebrity> candidates = (ArrayList<Celebrity>) args.get(ARGS_CANDIDATES);

            mRecyclerView = view.findViewById(R.id.listview_recyclerview);
            mLayoutManager = new GridLayoutManager(view.getContext() ,4);
            mRecyclerView.setLayoutManager(mLayoutManager);
            RecyclerView.Adapter mAdapter = new MyAdapter(new BitmapFromAsset(getContext()), candidates);
            mRecyclerView.setAdapter(mAdapter);
        }


        return view;
    }




}
