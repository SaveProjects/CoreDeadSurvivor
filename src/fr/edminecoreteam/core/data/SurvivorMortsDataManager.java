package fr.edminecoreteam.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SurvivorMortsDataManager 
{
	private final Map<UUID, SurvivorMortsData> players = new HashMap<>();

    public SurvivorMortsData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new SurvivorMortsData(id, 0));
    }

    public void addMorts(UUID playerId, int amount) {
        getPlayerData(playerId).addMorts(amount);
    }

    public void removeMorts(UUID playerId, int amount) {
        getPlayerData(playerId).removeMorts(amount);
    }
    
    public Map<UUID, SurvivorMortsData> returnPlayers()
    {
    	return this.players;
    }
}
