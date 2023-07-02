package fr.edminecoreteam.core.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.utils.FoundLobby;

public class EndTask extends BukkitRunnable 
{

	public int timer;
	private Core core;
	    
	public EndTask(Core core) {
	   this.timer = 25;
	   this.core = core;
	}
	
	@Override
	public void run() {
		core.timerEnd(timer);
		if (timer == 24) {
        		for (Player pls : Bukkit.getOnlinePlayers())
        		{
        			pls.playSound(pls.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
        		}
        }
		if (timer == 23) {
    		for (Player pls : Bukkit.getOnlinePlayers())
    		{
    			pls.playSound(pls.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
    		}
		}
		if (timer == 22) {
    		for (Player pls : Bukkit.getOnlinePlayers())
    		{
    			pls.playSound(pls.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
    		}
		}
		if (timer == 21) {
    		for (Player pls : Bukkit.getOnlinePlayers())
    		{
    			pls.playSound(pls.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
    		}
		}
		if (timer == 20) {
    		for (Player pls : Bukkit.getOnlinePlayers())
    		{
    			pls.playSound(pls.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
    		}
		}
		if (timer == 19) {  
        		for (Player pls : Bukkit.getOnlinePlayers())
        		{
        			pls.playSound(pls.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
        		}
        }
		
		if (timer == 1) {  
			for (Player pls : Bukkit.getOnlinePlayers())
            {
            	FoundLobby.foundGame(pls);
            }
            Bukkit.broadcastMessage("§cArrêt du serveur...");
        }
		
		if (timer == 0) {
            
            for (Player pls : Bukkit.getOnlinePlayers())
            {
            	FoundLobby.foundLobby(pls);
            }
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "stop");
            cancel();
        }
		--timer;
	}
}
