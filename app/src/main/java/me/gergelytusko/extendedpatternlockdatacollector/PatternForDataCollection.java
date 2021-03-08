package me.gergelytusko.extendedpatternlockdatacollector;

import java.util.ArrayList;
import java.util.List;

public class PatternForDataCollection {
    private List<LockPatternView.Cell> pattern;
    PatternForDataCollection(){
        pattern = new ArrayList<LockPatternView.Cell>();
        pattern.add(LockPatternView.Cell.of(0,0));
        pattern.add(LockPatternView.Cell.of(1,0));
        pattern.add(LockPatternView.Cell.of(2,0));
        pattern.add(LockPatternView.Cell.of(2,1));
        pattern.add(LockPatternView.Cell.of(1,2));
        pattern.add(LockPatternView.Cell.of(0,1));
        pattern.add(LockPatternView.Cell.of(1,1));
    }


    public void setPattern(List<LockPatternView.Cell> pattern) {
        this.pattern = pattern;
    }

    public List<LockPatternView.Cell> getPattern() {
        return pattern;
    }
}
