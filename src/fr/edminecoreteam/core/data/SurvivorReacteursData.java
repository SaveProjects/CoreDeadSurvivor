package fr.edminecoreteam.core.data;

import java.util.UUID;

public class SurvivorReacteursData
{
	private final UUID playerId;
    private int points;

    public SurvivorReacteursData(UUID playerId, int points) {
        this.playerId = playerId;
        this.points = points;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getReacteurs() {
        return points;
    }

    public void addReacteurs(int amount) {
    	points += amount;
    }

    public void removeReacteurs(int amount) {
    	points -= amount;
    }
}
