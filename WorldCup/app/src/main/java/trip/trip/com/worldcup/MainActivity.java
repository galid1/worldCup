package trip.trip.com.worldcup;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import trip.trip.com.worldcup.lib.BitmapFromAsset;
import trip.trip.com.worldcup.lib.CelebrityManager;
import trip.trip.com.worldcup.lib.Command;
import trip.trip.com.worldcup.lib.Invoker;

public class MainActivity extends AppCompatActivity {
    private Invoker invoker;
    private CelebrityManager cm;
    private Boolean isRunnung = false;
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    }
    */
    private final long FINISH_INTERVAL_TIME = 2000;
    //2초를 측정하는변수
    private long backPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCommand();
        cm = CelebrityManager.getInstance();
        cm.setContext(getBaseContext());
        setContentView(R.layout.start_activity);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.root_fragment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if (fragment == null) {
            FragmentTransaction tr = fm.beginTransaction();
            StartFragment cf = new StartFragment();
            tr.add(R.id.root_fragment, cf, "counter").commit();
        }

    }

    private void createCommand() {
        invoker = Invoker.getInstance();
        invoker.setCommand(Invoker.SLOT_GAMESTART, new GameStartCommand());
        invoker.setCommand(Invoker.SLOT_PREROUND, new PreRoundCommand());
        invoker.setCommand(Invoker.SLOT_LOADCHOICE, new LoadChoiceCommand());
        invoker.setCommand(Invoker.SLOT_SELECTLEFT, new SelectLeftCommand());
        invoker.setCommand(Invoker.SLOT_SELECTRIGHT, new SelectRightCommand());
        invoker.setCommand(Invoker.SLOT_GAMEROUNDSIXTEEN, new GameRoundSIXTEENCommand());
        invoker.setCommand(Invoker.SLOT_GAMEROUNDTHIRTYTWO, new GameRoundTHIRTYTWOCommand());
        invoker.setCommand(Invoker.SLOT_GOHOMT, new GoHomeCommand());
    }

    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();

        //처음 intervalTime과 tempTime은 같다
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        }
        //맨 처음 무조건 else싫행
        else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 누르면 종료됨니다", Toast.LENGTH_SHORT).show();
        }

    }

    class GameStartCommand extends Command {


        @Override
        public void action() {
            synchronized (isRunnung) {
                if (!isRunnung) {
                    isRunnung = true;
                    dialog_radio();

                }

            }


        }
    }

    private void dialog_radio() {

        final CharSequence[] idol_round_choice = {"16강", "32강"};
        AlertDialog.Builder r_btn = new AlertDialog.Builder(this);
        r_btn.setTitle("라운드를 선택해주세요.!");
        r_btn.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                isRunnung=false;
            }
        });
        r_btn.setSingleChoiceItems(idol_round_choice, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        dialog.cancel();
                        invoker.runCommand(Invoker.SLOT_GAMEROUNDSIXTEEN);
                        break;
                    case 1:
                        dialog.cancel();
                        invoker.runCommand(Invoker.SLOT_GAMEROUNDTHIRTYTWO);
                        break;
                }

            }
        });

        AlertDialog alert = r_btn.create();
        alert.show();

    }

    class GameRoundSIXTEENCommand extends Command {

        @Override
        public void action() {
            cm.setCandidates(16);
            cm.createPlayers();
            attachResultFragment();
        }
    }

    class GameRoundTHIRTYTWOCommand extends Command {

        @Override
        public void action() {
            cm.setCandidates(32);
            cm.createPlayers();
            attachResultFragment();
        }
    }
    class GoHomeCommand extends Command{

        @Override
        public void action() {
            attachStartFragment();
        }
    }


    class PreRoundCommand extends Command {
        @Override
        public void action() {
            cm.preRound();
            attachChoiceFragment();
        }
    }

    class LoadChoiceCommand extends Command {
        @Override
        public void action() {
            if (!cm.isRoundEnd()) {
                cm.setMatch();
                attachChoiceFragment();
                return;
            } else if (cm.getPlayerCount() != 1) {
                attachResultFragment();
            } else {
                attachFinishFragment();

                isRunnung = false;
            }

        }

    }

    class SelectLeftCommand extends Command {
        @Override
        public void action() {
            cm.putLeftCandidate();
        }

    }

    class SelectRightCommand extends Command {
        @Override
        public void action() {
            cm.putRightCandidate();
        }

    }

    public void attachChoiceFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.root_fragment);

        BitmapFromAsset bitmapFromAsset = new BitmapFromAsset(this);

        int round = cm.getRound();
        int match = cm.getMatch();
        Celebrity leftCelebrity = cm.getLeftCandidate();
        Celebrity rightCelebrity = cm.getRightCandidate();
        String leftCelebrityName = leftCelebrity.getName();
        String rightCelebrityName = rightCelebrity.getName();
        Bitmap leftCelebrityBitmap = bitmapFromAsset.loadBitmap(leftCelebrity.getImagePath());
        Bitmap rightCelebrityBitmap = bitmapFromAsset.loadBitmap(rightCelebrity.getImagePath());

        fragment = ChoiceFragment.newInstance(round, match, leftCelebrityName, leftCelebrityBitmap, rightCelebrityName, rightCelebrityBitmap);
        fragmentManager.beginTransaction()
                .replace(R.id.root_fragment, fragment)
                .commit();

    }

    public void attachResultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.root_fragment);

        if (fragment == null || !(fragment instanceof ResultFragment)) {
            fragment = ResultFragment.newInstance(cm.getResult());
            fragmentManager.beginTransaction()
                    .replace(R.id.root_fragment, fragment)
                    .commit();
        }
    }

    public void attachFinishFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.root_fragment);

        if (fragment == null || !(fragment instanceof FinishFragment)) {
            fragment = FinishFragment.newInstance(cm.getWinner());
            fragmentManager.beginTransaction()
                    .replace(R.id.root_fragment, fragment)
                    .commit();
        }
    }
    public void attachStartFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.root_fragment);

        if (fragment == null || !(fragment instanceof StartFragment)) {
            fragment = StartFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.root_fragment, fragment)
                    .commit();
        }
    }

}

