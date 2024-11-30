package com.pawgress.model;

public class UserPet {
    private String name;
    private PetType petType;

    /**
     * The stats numbers represent percentages (i.e., 50 = 50%)
     */
    private int health = 50;
    private int happiness = 50;
    private int satiety = 30;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.min(100, health);
    }
    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = Math.min(100, happiness);
    }

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = Math.min(100, satiety);
    }

    public void updateStat(PetStat stat, int points) {
        switch (stat) {
            case HEALTH:
                setHealth(this.health + points);
                break;
            case HAPPINESS:
                setHappiness(this.happiness + points);
                break;
            case SATIETY:
                setSatiety(this.satiety + points);
                break;
        }
    }

    public int getStat(PetStat stat) {
        switch (stat) {
            case HEALTH:
                return this.health;
            case HAPPINESS:
                return this.happiness;
            case SATIETY:
                return this.satiety;
        }
        return 0;
    }

    public String getPercentageStat(PetStat stat) {
        return "(" + getStat(stat) + "%)";
    }

    /**
     * If health is 0, the dead resource is returned.
     * If happiness is <= 15, the sad resource is returned.
     * If satiety is <= 15, then angry resource is returned.
     * If all stats are >= 50, then normal resource is returned.
     * Otherwise, happy resource is returned.
     * @return
     */
    public int getResourceBasedOnStats() {
        if (this.health == 0) {
            return petType.getDeadResId();
        } else if (this.happiness <= 15) {
            return petType.getSadResId();
        } else if (this.satiety <= 15) {
            return petType.getAngryResId();
        } else if (this.health >= 50 && this.happiness >= 50 && this.satiety >= 50) {
            return petType.getHappyResId();
        }
        return petType.getNormalResId();
    }
}
