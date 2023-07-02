package fr.edminecoreteam.core.data;

import java.util.UUID;

public class SurvivorMortsData
{
	private final UUID playerId;
    private int points;

    public SurvivorMortsData(UUID playerId, int points) {
        this.playerId = playerId;
        this.points = points;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getMorts() {
        return points;
    }

    public void addMorts(int amount) {
    	points += amount;
    }

    public void removeMorts(int amount) {
    	points -= amount;
    }
}
