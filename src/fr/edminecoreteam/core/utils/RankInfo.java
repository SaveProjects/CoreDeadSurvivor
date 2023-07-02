package fr.edminecoreteam.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class RankInfo 
{
	private Map<Player, RankInfo> rankInfo;
	private Player p;
	private RankData rankData;
	//accountInfo = new HashMap<Player, AccountInfo>();
	
	public RankInfo(Player p) {
        this.p = p;
        this.rankInfo = new HashMap<Player, RankInfo>();
        this.rankData = new RankData(p);
        this.rankInfo.put(p, this);
    }
	
	public RankInfo getAccountInfos(Player player) { return rankInfo.get(player); }
	
	public String getPlayerName() { return p.getName(); }
	
	public void createRank() { rankData.createRank(); }
	public boolean hasRank() { return rankData.hasRank(); }
	
	public void setRank(String type, int rankID, String rankName, int modulRank, int monthNumber) { rankData.setRank(type, rankID, rankName, modulRank, monthNumber); }
	
	public void updateUUIDRank() { rankData.updateUUIDRank(); }
	
	public int getRankID() { return rankData.getRankID(); }
	public void updateRankID(int rankID) { rankData.updateRankID(rankID); }
	
	public int getRankModule() { return rankData.getRankModule(); }
	public void updateRankModule(int RankModule) { rankData.updateRankModule(RankModule); }
	
	public String getPurchaseDate() { return rankData.getPurchaseDate(); }
	public void updatePurchaseDate(String date) { rankData.updatePurchaseDate(date); }
	
	public String getDeadLineDate() { return rankData.getDeadLineDate(); }
	public void updateDeadLineDate(String date) { rankData.updateDeadLineDate(date); }
	
	public String getRankType() { return rankData.getRankType(); }
	public void updateRankType(String type) { rankData.updateRankType(type); }
}
