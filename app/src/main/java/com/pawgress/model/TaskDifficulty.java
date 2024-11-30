package com.pawgress.model;

import com.pawgress.R;

public enum TaskDifficulty {
    EASY(R.string.easy), MEDIUM(R.string.medium), HARD(R.string.hard);

    private final int labelResId;

    TaskDifficulty(int labelResId) {
        this.labelResId = labelResId;
    }

    public int getLabelResId() {
        return labelResId;
    }
}
