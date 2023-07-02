package fr.edminecoreteam.core.data;

import java.util.UUID;

public class KillerKillsData
{
	private final UUID playerId;
    private int points;

    public KillerKillsData(UUID playerId, int points) {
        this.playerId = playerId;
        this.points = points;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getKills() {
        return points;
    }

    public void addKills(int amount) {
    	points += amount;
    }

    public void removeKills(int amount) {
    	points -= amount;
    }
}
