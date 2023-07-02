package fr.edminecoreteam.core.teams;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.State;


public class TabListTeams implements Listener
{
	private static Core core = Core.getInstance();
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) 
	{
        Player p = e.getPlayer();
        refreshTablist(p);
    }
	
	private void refreshTablist(Player p) {
		
		new BukkitRunnable() {
            int t = 0;   
	        public void run() {
	        	
	        	if (!p.isOnline()) { cancel(); }
	        	
	        	if (core.isState(State.STARTING) || core.isState(State.WAITING) || core.isState(State.FINISH))
	        	{
	        		TeamsTagsManager.setNameTag(p, Teams.powerToTeam(0).getOrderTeam(), Teams.powerToTeam(0).getDisplayName(), Teams.powerToTeam(0).getSuffix());
	        	}
	        	else if (core.isState(State.INGAME))
	        	{
	        		TeamsTagsManager.setNameTag(p, "§7§k", "§7§k", "§7§k");
	        	}
		        
		        ++t;
                if (t == 50) {
                    run();
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 50L);

	}
}
