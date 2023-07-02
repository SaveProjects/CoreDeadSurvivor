package fr.edminecoreteam.core.data;

import java.util.UUID;

public class SurvivorSauverData
{
	private final UUID playerId;
    private int points;

    public SurvivorSauverData(UUID playerId, int points) {
        this.playerId = playerId;
        this.points = points;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getSauver() {
        return points;
    }

    public void addSauver(int amount) {
    	points += amount;
    }

    public void removeSauver(int amount) {
    	points -= amount;
    }
}
