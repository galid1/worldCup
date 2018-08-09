package trip.trip.com.worldcup;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import trip.trip.com.worldcup.lib.BitmapFromAsset;
import trip.trip.com.worldcup.lib.Invoker;

public class FinishFragment extends Fragment {
    public static final String ARGS_WINNER = "ARGS_WINNER";

    public static FinishFragment newInstance(Celebrity winner){
        FinishFragment finishFragment = new FinishFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_WINNER , winner);
        finishFragment.setArguments(args);
        return finishFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View finishFragment = inflater.inflate(R.layout.fragment_finish , container , false);

        BitmapFromAsset bitmapFromAsset = new BitmapFromAsset(getContext());

        String winnerName = null;
        String winnerImagePath = null;

        Bundle args = getArguments();
        if(args != null){
            Celebrity winner = (Celebrity)args.get(ARGS_WINNER);
            winnerName = winner.getName();
            winnerImagePath = winner.getImagePath();
        }

        TextView winnerNameTextVIew = finishFragment.findViewById(R.id.textview_finish_winnername);
        winnerNameTextVIew.setText(winnerName);

        ImageView winnerImageView = finishFragment.findViewById(R.id.imageview_finish_winner);
        Bitmap bitmap = bitmapFromAsset.loadBitmap(winnerImagePath);
        winnerImageView.setImageBitmap(bitmap);

        Button okButton = finishFragment.findViewById(R.id.button_finish_ok);
        okButton.setOnClickListener((v)->{
            Invoker.getInstance().runCommand(Invoker.SLOT_GOHOMT);
        });

        return finishFragment;
    }
}
