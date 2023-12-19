package controller;

import java.util.ArrayList;
import java.util.List;

public class Save {
    private List<String> saveLines;
    public int score ;
    public int swaplimit;
    public int fallstate;
    public int swapstate;
    public int goal;


    public List<String> getSaveLines() {
        return saveLines;
    }

    public void setSaveLines(List<String> saveLines) {
        this.saveLines = saveLines;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSwaplimit() {
        return swaplimit;
    }

    public void setSwaplimit(int swaplimit) {
        this.swaplimit = swaplimit;
    }

    public int getFallstate() {
        return fallstate;
    }

    public void setFallstate(int fallstate) {
        this.fallstate = fallstate;
    }

    public int getSwapstate() {
        return swapstate;
    }

    public void setSwapstate(int swapstate) {
        this.swapstate = swapstate;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public List<String> toList() {
        List<String> file = new ArrayList<String>();
        file = saveLines;
        file.add(Integer.toString(score));
        file.add(Integer.toString(swaplimit));
        file.add(Integer.toString(fallstate));
        file.add(Integer.toString(swapstate));
        file.add(Integer.toString(goal));
        return file;
    }
}
