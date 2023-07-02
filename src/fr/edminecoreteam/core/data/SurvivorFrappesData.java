package fr.edminecoreteam.core.data;

import java.util.UUID;

public class SurvivorFrappesData
{
	private final UUID playerId;
    private int points;

    public SurvivorFrappesData(UUID playerId, int points) {
        this.playerId = playerId;
        this.points = points;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getFrappes() {
        return points;
    }

    public void addFrappes(int amount) {
    	points += amount;
    }

    public void removeFrappes() {
    	points = 1;
    }
}
