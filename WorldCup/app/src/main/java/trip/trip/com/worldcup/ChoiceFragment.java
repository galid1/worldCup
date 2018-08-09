package trip.trip.com.worldcup;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import trip.trip.com.worldcup.lib.Invoker;

public class ChoiceFragment extends Fragment {

    public static final int SELECT_LEFT_CELEBRITY = 0;
    public static final int SELECT_RIGHT_CELEBRITY = 1;

    private static final String ARG_ROUND= "ROUND";
    private static final String ARG_MATCH = "MATCH";
    private static final String ARG_LEFT_CELEBRITY_NAME = "LEFT_CELEBRITY_NAME";
    private static final String ARG_RIGHT_CELEBRITY_NAME = "RIGHT_CELEBRITY_NAME";
    private static final String ARG_LEFT_CELEBRITY_IMAGE = "LEFT_CELEBRITY_IMAGE";
    private static final String ARG_RIGHT_CELEBRITY_IMAGE = "RIGHT_CELEBRITY_IMAGE";


    private int mRound;
    private int mMatch;
    private String mLeftName;
    private Bitmap mLeftImage;
    private String mRightName;
    private Bitmap mRightImage;
    private Invoker invoker;
    public ChoiceFragment() {

    }

    public static ChoiceFragment newInstance(int round, int match, String leftName, Bitmap leftImage, String rightName, Bitmap rightImage) {
        ChoiceFragment fragment = new ChoiceFragment();

        Bundle arguments = new Bundle();
        arguments.putInt(ARG_ROUND,round);
        arguments.putInt(ARG_MATCH,match);
        arguments.putString(ARG_LEFT_CELEBRITY_NAME,leftName);
        arguments.putParcelable(ARG_LEFT_CELEBRITY_IMAGE,leftImage);
        arguments.putString(ARG_RIGHT_CELEBRITY_NAME,rightName);
        arguments.putParcelable(ARG_RIGHT_CELEBRITY_IMAGE,rightImage);

        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invoker = Invoker.getInstance();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mRound = arguments.getInt(ARG_ROUND);
            this.mMatch = arguments.getInt(ARG_MATCH);
            this.mLeftName = arguments.getString(ARG_LEFT_CELEBRITY_NAME);
            this.mRightName = arguments.getString(ARG_RIGHT_CELEBRITY_NAME);
            this.mLeftImage = arguments.getParcelable(ARG_LEFT_CELEBRITY_IMAGE);
            this.mRightImage = arguments.getParcelable(ARG_RIGHT_CELEBRITY_IMAGE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_choice, container, false);
        invoker= Invoker.getInstance();
        ImageView leftImageView = root.findViewById(R.id.image_view_left_celebrity);
        leftImageView.setImageBitmap(mLeftImage);
        leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoker.runCommand(Invoker.SLOT_SELECTLEFT);
                invoker.runCommand(Invoker.SLOT_LOADCHOICE);
            }
        });

        ImageView rightImageView = root.findViewById(R.id.image_view_right_celebrity);
        rightImageView.setImageBitmap(mRightImage);
        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                invoker.runCommand(Invoker.SLOT_SELECTRIGHT);
                invoker.runCommand(Invoker.SLOT_LOADCHOICE);
            }
        });

        TextView roundTextView = root.findViewById(R.id.text_view_round);
        TextView matchTextView = root.findViewById(R.id.text_view_match);
            if(mRound==2){
                roundTextView.setText("결승전");
                matchTextView.setText("");

            } else {
                roundTextView.setText(Integer.toString(mRound)+"강");
                matchTextView.setText(Integer.toString(mMatch)+"매치");
            }

        TextView leftCelebrityName = root.findViewById(R.id.text_view_left_celebrity_name);
        leftCelebrityName.setText(mLeftName);
        TextView rightCelebrityName = root.findViewById(R.id.text_view_right_celebrity_name);
        rightCelebrityName.setText(mRightName);

        return root;
    }
}
