// 
// Decompiled by Procyon v0.5.36
// 

package fr.edminecoreteam.core.gamemanager;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.State;
import fr.edminecoreteam.core.utils.Cuboid;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ZoneManager implements Listener
{
    private static Core core = Core.getInstance();
	private Player p;
    
    public ZoneManager(Player p) {
		this.p = p;
	}
    
    public ZoneManager() {
		// TODO Auto-generated constructor stub
	}
    
    public void checker() {
        new BukkitRunnable() {
            int t = 0;
            
            public void run() {
                regionEnterEvent();
                ++t;
                if (t == 3) {
                    t = 0;
                }
            }
        }.runTaskTimer((Plugin)Core.getInstance(), 0L, 3L);
    }
    
    @SuppressWarnings("deprecation")
	private void regionEnterEvent() {
    	
    	Location portal1 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.spawn.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.spawn.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.spawn.z")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.spawn.f")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.spawn.t"));
    	Location portal2 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.spawn.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.spawn.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.spawn.z")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.spawn.f")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.spawn.t"));
        
    	
    	Location portal1locA = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.a.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.a.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.a.z"));	
    	Location portal1locB = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.b.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.b.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.b.z"));
    	
    	
    	Location portal2locA = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.a.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.a.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.a.z"));
    	Location portal2locB = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.b.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.b.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.b.z"));
    	
        
        Cuboid cb1 = new Cuboid(portal1locA, portal1locB);
        Cuboid cb2 = new Cuboid(portal2locA, portal2locB);
        
        
        if (cb1.contains(p.getLocation())) 
        {
        	if (core.isState(State.INGAME))
            {
            	if (core.getGeneratorsFinish().size() == 7)
            	{
            		if (core.getKiller().contains(p.getName()))
            		{
            			p.teleport(portal1);
            			p.playSound(p.getLocation(), Sound.VILLAGER_NO, 0.5f, 0.5f);
            		}
            		else if (core.getSurvivors().contains(p.getName()))
            		{
            			if (!core.getSurvivorsEchape().contains(p.getName()))
            			{
            				core.getSurvivorsEchape().add(p.getName());
            				p.setGameMode(GameMode.SPECTATOR);
            				p.setAllowFlight(true);
            				p.setFlying(true);
            				p.setFlySpeed((float) 0.1);
            			}
            		}
            	}
            }
        }
        if (cb2.contains(p.getLocation())) 
        {
        	if (core.isState(State.INGAME))
            {
            	if (core.getGeneratorsFinish().size() == 7)
            	{
            		if (core.getKiller().contains(p.getName()))
            		{
            			p.teleport(portal2);
            			p.playSound(p.getLocation(), Sound.VILLAGER_NO, 0.5f, 0.5f);
            		}
            		else if (core.getSurvivors().contains(p.getName()))
            		{
            			if (!core.getSurvivorsEchape().contains(p.getName()))
            			{
            				core.getSurvivorsEchape().add(p.getName());
            				p.setGameMode(GameMode.SPECTATOR);
            				p.setAllowFlight(true);
            				p.setFlying(true);
            				p.setFlySpeed((float) 0.1);
            				p.sendTitle("§aLibre comme l'air...", "§7Vous vous êtes échapé.");
            			}
            		}
            	}
            }
        }
        
    }
    
    @SuppressWarnings("deprecation")
	public void openDoor() {
    	Location portal1locA = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.a.x") - 1
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.a.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.a.z") - 1);	
    	Location portal1locB = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.b.x") - 1
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.b.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal1.b.z") - 1);
    	
    	
    	Location portal2locA = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.a.x") - 1
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.a.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.a.z") - 1);
    	Location portal2locB = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.b.x") - 1
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.b.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".portal2.b.z") - 1);
    	
        
        Cuboid cb1 = new Cuboid(portal1locA, portal1locB);
        Cuboid cb2 = new Cuboid(portal2locA, portal2locB);
        
        removeIronBarsInCuboid(cb1);
        removeIronBarsInCuboid(cb2);
        for (String killerString : core.getKiller())
        {
        	Player killer = Bukkit.getPlayer(killerString);
        	killer.playSound(killer.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
        	killer.sendTitle("§cFaite vite !", "§7Les §b§lSurvivants §7prennent la fuite...");
        }
        for (String survivorString : core.getSurvivors())
        {
        	Player survivor = Bukkit.getPlayer(survivorString);
        	survivor.playSound(survivor.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        	survivor.sendTitle("§aPrenez la fuite !", "§7La porte est maintenant ouverte.");
        }
    }
    
    private void removeIronBarsInCuboid(Cuboid cuboid) 
    {
        for (Block block : cuboid.getBlocks()) 
        {
                block.setType(Material.AIR);
        }
    }
}
