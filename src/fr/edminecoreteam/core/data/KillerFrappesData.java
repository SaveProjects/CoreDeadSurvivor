package fr.edminecoreteam.core.data;

import java.util.UUID;

public class KillerFrappesData
{
	private final UUID playerId;
    private int points;

    public KillerFrappesData(UUID playerId, int points) {
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

    public void removeFrappes(int amount) {
    	points -= amount;
    }
}
