package fr.edminecoreteam.core.listeners;

import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import fr.edminecoreteam.core.tasks.AutoStart;
import fr.edminecoreteam.core.utils.ChangeHubInfo;
import fr.edminecoreteam.core.State;
import fr.edminecoreteam.core.data.Data;

import org.bukkit.event.player.PlayerJoinEvent;
import fr.edminecoreteam.core.Core;
import org.bukkit.event.Listener;

public class PlayerListeners implements Listener
{
    private Core core;
    
    public PlayerListeners(Core core) {
        this.core = core;
    }
    
    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Data data = new Data(p.getName());
        data.createData();
	        	if (core.isState(State.WAITING)) 
	        	{
	        		if (core.getPlayers().size() == 5) {
	                    p.kickPlayer("§cPartie pleine...");
	                    return;
	                }
	        		if (!core.getPlayers().contains(p)) 
	        		{
	                    core.getPlayers().add(p);
	                    core.getPlayersName().add(p.getName());
	                    Instance.joinWAITING(p);
	                }
	        		core.getServer().broadcastMessage("§e" + p.getName() + "§7 a rejoint le jeu. §d" + core.getPlayers().size() + "§d/5");
	        		e.setJoinMessage(null);
		        	if (core.getPlayers().size() == 5) {
	                    AutoStart start = new AutoStart(core);
	                    start.runTaskTimer((Plugin)this.core, 0L, 20L);
	                    core.setState(State.STARTING);
	                    ChangeHubInfo srvInfo = new ChangeHubInfo(core.getServer().getServerName());
	                    srvInfo.setMOTD("STARTING");
	                    boolean checkSrv = Instance.checkServersForStart();
	                    if (checkSrv == false)
	                    {
	                    	srvInfo.setStatus(2);
	                    }
	                    return;
	                }
	        	}
	        	if (core.isState(State.STARTING))
	        	{
	        		if (core.getPlayers().size() == 5) {
	                    p.kickPlayer("§cPartie pleine...");
	                    return;
	                }
	        		if (!core.getPlayers().contains(p)) 
	        		{
	                    core.getPlayers().add(p);
	                    core.getPlayersName().add(p.getName());
	                    Instance.joinWAITING(p);
	                }
	        		core.getServer().broadcastMessage("§e" + p.getName() + "§7 a rejoint le jeu. §d" + core.getPlayers().size() + "§d/5");
	        		e.setJoinMessage(null);
	        	}
	        	if (core.isState(State.INGAME))
	        	{
	        		e.setJoinMessage(null);
	                Instance.joinInGame(p);
	        	}
	}
    
    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        
        	if (core.isState(State.WAITING)) {
                if (core.getPlayers().contains(p)) {
                    core.getPlayers().remove(p);
                    core.getPlayersName().remove(p.getDisplayName());
                    core.getServer().broadcastMessage("§e" + p.getName() + "§7 a quitté le jeu. §d" + core.getPlayers().size() + "§d/5");
                    e.setQuitMessage(null);
                }
                Instance.quitWAITING(p);
            }
            if (core.isState(State.STARTING)) {
                if (core.getPlayers().contains(p)) {
                    core.getPlayers().remove(p);
                    core.getPlayersName().remove(p.getDisplayName());
                    core.getServer().broadcastMessage("§e" + p.getName() + "§7 a quitté le jeu. §d" + core.getPlayers().size() + "§d/5");
                    e.setQuitMessage(null);
                }
                Instance.quitWAITING(p);
            }
            if (core.isState(State.INGAME)) {
                if (core.getPlayers().contains(p)) {
                    core.getServer().broadcastMessage("§e§k" + p.getName() + "§7 a quitté le jeu, il est désormais éliminé.");
                    
                    if (core.getSurvivors().contains(p.getName()))
                    {
                    	core.getSurvivors().remove(p.getName());
                        core.getPlayersName().remove(p.getName());
                    }
                    else if (core.getKiller().contains(p.getName()))
                    {
                    	core.getKiller().remove(p.getName());
                        core.getPlayersName().remove(p.getName());
                    }
                    e.setQuitMessage(null);
                }else {
                	e.setQuitMessage(null);
                }
                Instance.quitWAITING(p);
            }
    }
}
