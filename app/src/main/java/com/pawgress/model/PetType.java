package com.pawgress.model;

import com.pawgress.R;

public enum PetType {
    BIRD(R.drawable.pet_happy_bird, R.drawable.pet_bird, R.drawable.pet_sad_bird, R.drawable.pet_angry_bird, R.drawable.pet_dead_bird),
    CAT(R.drawable.pet_happy_cat, R.drawable.pet_cat, R.drawable.pet_sad_cat, R.drawable.pet_angry_cat, R.drawable.pet_dead_cat),
    DOG(R.drawable.pet_happy_dog, R.drawable.pet_dog, R.drawable.pet_sad_dog, R.drawable.pet_angry_dog, R.drawable.pet_dead_dog),
    HAMSTER(R.drawable.pet_happy_hamster, R.drawable.pet_hamster, R.drawable.pet_sad_hamster, R.drawable.pet_angry_hamster, R.drawable.pet_dead_hamster);

    private int happyResId;
    private int normalResId;
    private int sadResId;
    private int angryResId;
    private int deadResId;

    PetType(int happyResId, int normalResId, int sadResId, int angryResId, int deadResId) {
        this.happyResId = happyResId;
        this.normalResId = normalResId;
        this.sadResId = sadResId;
        this.angryResId = angryResId;
        this.deadResId = deadResId;
    }

    public int getAngryResId() {
        return angryResId;
    }

    public int getDeadResId() {
        return deadResId;
    }

    public int getHappyResId() {
        return happyResId;
    }

    public int getNormalResId() {
        return normalResId;
    }

    public int getSadResId() {
        return sadResId;
    }
}
