package controller;

import java.util.ArrayList;
import java.util.List;

public class Save {
    private List<String> saveLines;
    public int score = 100;
    public int swaplimit = 10;
    public int fallstate = 1;
    public int swapstate = 1;
    public int goal = 50;


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
