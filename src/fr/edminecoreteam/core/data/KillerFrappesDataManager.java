package fr.edminecoreteam.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KillerFrappesDataManager 
{
	private final Map<UUID, KillerFrappesData> players = new HashMap<>();

    public KillerFrappesData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new KillerFrappesData(id, 0));
    }

    public void addFrappes(UUID playerId, int amount) {
        getPlayerData(playerId).addFrappes(amount);
    }

    public void removeFrappes(UUID playerId, int amount) {
        getPlayerData(playerId).removeFrappes(amount);
    }
    
    public Map<UUID, KillerFrappesData> returnPlayers()
    {
    	return this.players;
    }
}
