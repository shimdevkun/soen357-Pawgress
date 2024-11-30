package com.pawgress.model;

import com.pawgress.R;
public enum PetStat {
    HEALTH(R.string.health),
    HAPPINESS(R.string.happiness),
    SATIETY(R.string.satiety);
    private final int labelResId;
    PetStat(int labelResId) {
        this.labelResId = labelResId;
    }

    public int getLabelResId() {
        return labelResId;
    }
}
