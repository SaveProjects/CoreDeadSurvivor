package fr.edminecoreteam.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SurvivorFrappesDataManager 
{
	private final Map<UUID, SurvivorFrappesData> players = new HashMap<>();

    public SurvivorFrappesData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new SurvivorFrappesData(id, 0));
    }

    public void addFrappes(UUID playerId, int amount) {
        getPlayerData(playerId).addFrappes(amount);
    }

    public void removeFrappes(UUID playerId) {
        getPlayerData(playerId).removeFrappes();
    }
    
    public Map<UUID, SurvivorFrappesData> returnPlayers()
    {
    	return this.players;
    }
}
