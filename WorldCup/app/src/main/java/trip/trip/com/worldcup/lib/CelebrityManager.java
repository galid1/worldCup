package trip.trip.com.worldcup.lib;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import trip.trip.com.worldcup.Celebrity;
import trip.trip.com.worldcup.database.CelebrityTable;

public class CelebrityManager {
    private ArrayList<Celebrity> candidates;
    private ArrayList<Celebrity> players;
    private Context context;
    private Celebrity leftCandidate;
    private Celebrity rightCandidate;
    private int match;
    private int round;

    private CelebrityManager() {
    }

    public void preRound() {
        match = 0;
        if (!players.isEmpty()) {
            candidates = players;
            createPlayers();
        }
        round = candidates.size();

        setMatch();
    }

    private static class CMHolder {
        private static final CelebrityManager instance = new CelebrityManager();
    }

    public int getMatch() {
        return match;
    }

    public int getRound() {
        return round;
    }

    public int getPlayerCount() {
        if (players != null) {
            return players.size();
        }
        return 0;
    }

    public static CelebrityManager getInstance() {
        return CMHolder.instance;
    }

    public Celebrity getLeftCandidate() {
        return leftCandidate;
    }

    public Celebrity getRightCandidate() {
        return rightCandidate;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCandidates(int cnt) {
        CelebrityTable ct = new CelebrityTable(context);
        List<Integer> wholeList = ct.wholeCelebrityID();
        List<Integer> cList = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            Random r = new Random();
            int num = r.nextInt(wholeList.size());

            int id = wholeList.get(num);
            cList.add(id);
            wholeList.remove(num);
        }


        candidates = ct.specificCelebrity(cList);

    }

    public void createPlayers() {
        players = new ArrayList<Celebrity>();
    }

    public void putLeftCandidate() {
        players.add(leftCandidate);
    }

    public void putRightCandidate() {
        players.add(rightCandidate);
    }

    public boolean isRoundEnd() {
        return candidates.isEmpty();
    }

    public void setMatch() {
        match++;
        // 왼쪽 후보 선출
        Random r = new Random();
        int num = r.nextInt(candidates.size());

        leftCandidate = candidates.remove(num);

        // 오른쪽 후보 선출
        num = r.nextInt(candidates.size());

        rightCandidate = candidates.remove(num);
    }

    public ArrayList<Celebrity> getResult() {
        if (players.isEmpty()) {
            return candidates;
        } else {
            return players;
        }
    }

    public Celebrity getWinner(){
        if(players.size() > 1)
            return null;
        else{
            return players.get(0);
        }
    }

}
