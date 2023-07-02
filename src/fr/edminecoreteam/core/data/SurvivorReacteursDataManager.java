package fr.edminecoreteam.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SurvivorReacteursDataManager 
{
	private final Map<UUID, SurvivorReacteursData> players = new HashMap<>();

    public SurvivorReacteursData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new SurvivorReacteursData(id, 0));
    }

    public void addReacteurs(UUID playerId, int amount) {
        getPlayerData(playerId).addReacteurs(amount);
    }

    public void removeReacteurs(UUID playerId, int amount) {
        getPlayerData(playerId).removeReacteurs(amount);
    }
    
    public Map<UUID, SurvivorReacteursData> returnPlayers()
    {
    	return this.players;
    }
}
