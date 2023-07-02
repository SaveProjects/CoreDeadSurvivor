package fr.edminecoreteam.core.teams;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.State;
import fr.edminecoreteam.core.rank.Rank;
import fr.edminecoreteam.core.utils.RankInfo;

public class ChatTeam implements Listener
{
	private static Core core = Core.getInstance();
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		e.setCancelled(true);
		if (core.isState(State.WAITING) || core.isState(State.STARTING) || core.isState(State.FINISH))
		{
			RankInfo rankInfo = new RankInfo(p);
			if (rankInfo.getRankType().equalsIgnoreCase("static"))
	        {
	        	if (rankInfo.getRankID() > 0)
	        	{
	        		e.setFormat(Rank.powerToRank(rankInfo.getRankID()).getDisplayName() + p.getName() + Rank.powerToRank(rankInfo.getRankID()).getSuffix() + " §8» §f" + e.getMessage().replace("&", "§"));
	                for (Player players : Bukkit.getOnlinePlayers()) {
	                    players.sendMessage(e.getFormat());
	                }
	        	}
	        	else if (rankInfo.getRankID() == 0)
	        	{
	        		e.setFormat(Rank.powerToRank(rankInfo.getRankID()).getDisplayName() + p.getName() + " §8» §7" + e.getMessage());
	                for (Player players : Bukkit.getOnlinePlayers()) {
	                    players.sendMessage(e.getFormat());
	                }
	        	}
	        }
	        if (rankInfo.getRankType().equalsIgnoreCase("tempo")) 
	        {
	        	if (rankInfo.getRankID() >= 3)
	        	{
	        		e.setFormat(Rank.powerToRank(rankInfo.getRankID()).getDisplayName() + p.getName() + Rank.powerToRank(rankInfo.getRankID()).getSuffix() + " §8» §f" + e.getMessage());
	                for (Player players : Bukkit.getOnlinePlayers()) {
	                    players.sendMessage(e.getFormat());
	                }
	        	}
	        	else if (rankInfo.getRankID() < 3)
	        	{
	        		e.setFormat(Rank.powerToRank(rankInfo.getRankID()).getDisplayName() + p.getName() + Rank.powerToRank(rankInfo.getRankID()).getSuffix() + " §8» §7" + e.getMessage());
	                for (Player players : Bukkit.getOnlinePlayers()) {
	                    players.sendMessage(e.getFormat());
	                }
	        	}
	        }
	        if (rankInfo.getRankType().equalsIgnoreCase("module")) 
	        {
	        	e.setFormat(Rank.powerToRank(rankInfo.getRankModule()).getDisplayName() + p.getName() + Rank.powerToRank(rankInfo.getRankModule()).getSuffix() + " §8» §f" + e.getMessage());
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(e.getFormat());
                }
	        }
	        if (rankInfo.getRankType().equalsIgnoreCase("staff")) 
	        {
	        	e.setFormat(Rank.powerToRank(rankInfo.getRankModule()).getDisplayName() + p.getName() + Rank.powerToRank(rankInfo.getRankModule()).getSuffix() + " §8» §f" + e.getMessage().replace("&", "§"));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(e.getFormat());
                }
	        }
		}
		if (core.isState(State.INGAME))
		{
			if (core.getPlayersName().contains(p.getName()))
			{
				e.setFormat(Teams.powerToTeam(1).getDisplayName() + p.getName() + "§r §8» §7" + e.getMessage());
				for (Player players : Bukkit.getOnlinePlayers()) {
	                players.sendMessage(e.getFormat());
	            }
			}
			else if (!core.getPlayersName().contains(p.getName()))
			{
				e.setFormat("§7§lSPEC " + p.getName() + " §8» §7" + e.getMessage());
				List<Player> pList = new ArrayList<Player>();
				for (Player players : Bukkit.getOnlinePlayers()) {
					if (!core.getPlayersName().contains(players.getName()))
					{
						pList.add(players);
					}
	            }
				for (Player players : pList)
				{
					players.sendMessage(e.getFormat());
				}
			}
		}
	}
}
