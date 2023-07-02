package fr.edminecoreteam.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SurvivorSauverDataManager 
{
	private final Map<UUID, SurvivorSauverData> players = new HashMap<>();

    public SurvivorSauverData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new SurvivorSauverData(id, 0));
    }

    public void addSauver(UUID playerId, int amount) {
        getPlayerData(playerId).addSauver(amount);
    }

    public void removeSauver(UUID playerId, int amount) {
        getPlayerData(playerId).removeSauver(amount);
    }
    
    public Map<UUID, SurvivorSauverData> returnPlayers()
    {
    	return this.players;
    }
}
