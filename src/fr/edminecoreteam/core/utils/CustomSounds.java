package fr.edminecoreteam.core.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.edminecoreteam.core.Core;

public class CustomSounds 
{
	private static Core core = Core.getInstance();
	
	public static void battementDeCoeur(Player p, int radius)
	{
		if (radius == 1)
		{
			new BukkitRunnable() {
	            int t = 0; 
		        public void run() {
		        	
		        	if (!p.isOnline()) { cancel(); }
		        	
			        
			        if (t == 0)
			        {
			        	p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 0.5f, 0.5f);
			        }
			        if (t == 1)
			        {
			        	p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 0.5f, 0.5f);
			        }
			        ++t;
	                if (t == 3) {
	                    cancel();
	                }
	            }
	        }.runTaskTimer((Plugin)core, 0L, 7L);
		}
		
		if (radius == 2)
		{
			new BukkitRunnable() {
	            int t = 0; 
		        public void run() {
		        	
		        	if (!p.isOnline()) { cancel(); }
		        	
			        
			        if (t == 0)
			        {
			        	p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 1.0f, 0.5f);
			        }
			        if (t == 1)
			        {
			        	p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 1.0f, 0.5f);
			        }
			        ++t;
	                if (t == 3) {
	                    cancel();
	                }
	            }
	        }.runTaskTimer((Plugin)core, 0L, 7L);
		}
		
		if (radius == 3)
		{
			new BukkitRunnable() {
	            int t = 0; 
		        public void run() {
		        	
		        	if (!p.isOnline()) { cancel(); }
		        	
			        
			        if (t == 0)
			        {
			        	p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 1.5f, 0.5f);
			        }
			        if (t == 1)
			        {
			        	p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 1.5f, 0.5f);
			        }
			        ++t;
	                if (t == 3) {
	                    cancel();
	                }
	            }
	        }.runTaskTimer((Plugin)core, 0L, 3L);
		}
	}
	
}
