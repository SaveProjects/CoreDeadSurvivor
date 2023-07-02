package fr.edminecoreteam.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KillerKillsDataManager 
{
	private final Map<UUID, KillerKillsData> players = new HashMap<>();

    public KillerKillsData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new KillerKillsData(id, 0));
    }

    public void addKills(UUID playerId, int amount) {
        getPlayerData(playerId).addKills(amount);
    }

    public void removeKills(UUID playerId, int amount) {
        getPlayerData(playerId).removeKills(amount);
    }
    
    public Map<UUID, KillerKillsData> returnPlayers()
    {
    	return this.players;
    }
}
