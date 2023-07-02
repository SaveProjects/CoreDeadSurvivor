package fr.edminecoreteam.core.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.State;
import fr.edminecoreteam.core.listeners.Instance;
import fr.edminecoreteam.core.utils.ChangeHubInfo;

public class GameTask extends BukkitRunnable 
{
	
	 public int timer;
	 private Core core;  
	    
	 public GameTask(Core core) {
	    this.core = core;
	    this.timer = core.getConfig().getInt("Timers.game");
	 }
	 
	 public void run() {
	    	core.timerGame(timer);
			
	    	if (core.getGeneratorsFinish().size() == 7)
	    	{
	    		for (Player pls : Bukkit.getOnlinePlayers())
	    		{
	    			int finalSize = core.getSurvivors().size() - core.getInGround().size() - core.getInPendul().size();
	    			core.title.sendActionBar(pls, "§aJoueurs échappés §7┃ §f(" + core.getSurvivorsEchape().size() + "/" + finalSize + ")");
	    		}
	    	}
	    	
	    	//Victoire des survivants
	    	if (core.getSurvivorsEchape().size() == core.getSurvivors().size() - core.getInGround().size() - core.getInPendul().size())
	    	{
	    		for (String s : core.getInGround())
	    		{
	    			Player player = Bukkit.getPlayer(s);
	    			Instance.killPlayer(player);
	    		}
	    		for (String s : core.getInPendul())
	    		{
	    			Player player = Bukkit.getPlayer(s);
	    			Instance.killPlayer(player);
	    		}
	    		
	    		core.setState(State.FINISH);
		    		
		    	for (Player pls : Bukkit.getOnlinePlayers())
		    	{
		    		if (core.getKiller().contains(pls.getName()))
		    		{
		    			Instance.endGame(pls, "lose");
		    		}
		    		if (core.getSurvivors().contains(pls.getName()))
		    		{
		    			Instance.endGame(pls, "win");
		    		}
		    	}
		    	ChangeHubInfo srvInfo = new ChangeHubInfo(core.getServer().getServerName());
	            srvInfo.setMOTD("FINISH");
	            EndTask start = new EndTask(core);
			    start.runTaskTimer((Plugin)this.core, 0L, 20L);
			    cancel();
	    	}
	    	
	    	//Tous les survivants sont pendus ou a terre
	    	if (core.getSurvivors().size() == core.getInGround().size() + core.getInPendul().size())
	    	{	
		    	for (Player pls : Bukkit.getOnlinePlayers())
		    	{
		    		if (core.getSurvivors().contains(pls.getName()))
		    		{
		    			Instance.killPlayer(pls);
		    		}
		    	}
	    	}
	    	
	    	//Victoire du tueur
	    	if (core.getSurvivors().size() == 0)
	    	{
	    		core.setState(State.FINISH);
		    		
		    	for (Player pls : Bukkit.getOnlinePlayers())
		    	{
		    		if (core.getKiller().contains(pls.getName()))
		    		{
		    			Instance.endGame(pls, "win");
		    		}
		    		if (core.getSurvivors().contains(pls.getName()))
		    		{
		    			Instance.endGame(pls, "lose");
		    		}
		    	}
		    	ChangeHubInfo srvInfo = new ChangeHubInfo(core.getServer().getServerName());
	            srvInfo.setMOTD("FINISH");
	            EndTask start = new EndTask(core);
			    start.runTaskTimer((Plugin)this.core, 0L, 20L);
			    cancel();
	    	}
	    	
	    	//Le tueur ce déco
	    	if (core.getKiller().size() == 0)
	    	{
	    		core.setState(State.FINISH);
		    		
		    	for (Player pls : Bukkit.getOnlinePlayers())
		    	{
		    		if (core.getSurvivors().contains(pls.getName()))
		    		{
		    			Instance.endGame(pls, "egal");
		    		}
		    	}
		    	ChangeHubInfo srvInfo = new ChangeHubInfo(core.getServer().getServerName());
	            srvInfo.setMOTD("FINISH");
	            EndTask start = new EndTask(core);
			    start.runTaskTimer((Plugin)this.core, 0L, 20L);
			    cancel();
	    	}
	    	List<String> playersOnline = new ArrayList<String>();
	    	List<Player> playerOnline = new ArrayList<Player>();
	    		
	    	for (Player pls : core.getPlayers())
	    	{
	    		String pName = pls.getName();
	    		if (Bukkit.getPlayer(pName) != null)
	    		{
	    			playersOnline.add(pName);
	    			playerOnline.add(pls);
	    		}
	    	}
	        
	        if (timer == 5)
	        {
	        	for (Player pls : core.getPlayers()) {
	                
	                pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);
	            }
	        }
	        if (timer == 4)
	        {
	        	for (Player pls : core.getPlayers()) {
	                
	                pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 1.0f, 1.2f);
	            }
	        }
	        if (timer == 3)
	        {
	        	for (Player pls : core.getPlayers()) {
	                
	                pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
	            }
	        }
	        if (timer == 2)
	        {
	        	for (Player pls : core.getPlayers()) {
	                
	                pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 1.0f, 0.7f);
	            }
	        }
	        if (timer == 1)
	        {
	        	for (Player pls : core.getPlayers()) {
	                
	                pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 1.0f, 0.5f);
	            }
	        }
	        if (timer == 0) {
	        	core.setState(State.FINISH);
	    		
	    		for (Player pls : Bukkit.getOnlinePlayers())
	    		{
	    			if (core.getKiller().contains(pls.getName()))
		    		{
		    			Instance.endGame(pls, "egal");
		    		}
		    		if (core.getSurvivors().contains(pls.getName()))
		    		{
		    			Instance.endGame(pls, "egal");
		    		}
	    		}
	    		ChangeHubInfo srvInfo = new ChangeHubInfo(core.getServer().getServerName());
                srvInfo.setMOTD("FINISH");
	    		EndTask start = new EndTask(core);
		        start.runTaskTimer((Plugin)this.core, 0L, 20L);
		        cancel();
	        }
	        --timer;
	    }
	
}
