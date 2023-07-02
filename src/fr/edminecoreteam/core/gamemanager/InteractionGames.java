package fr.edminecoreteam.core.gamemanager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.State;
import fr.edminecoreteam.core.listeners.Instance;
import fr.edminecoreteam.core.utils.CustomSounds;
import fr.edminecoreteam.core.utils.ItemStackSerializer;
import fr.edminecoreteam.core.utils.MessageUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class InteractionGames implements Listener 
{
	private static Core core = Core.getInstance();
	private int timeForSetOnKiller = 5;
	private int timeForSetOnKillerTime = 0;
	private int timeForDeath = 130;
	public int timeGeneratorTime = 200;
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
	    Player player = event.getPlayer();
	    if (core.isState(State.INGAME)) 
	    {
		    if (core.getInPendul().contains(player.getName()))
		    {
		    	player.setFlySpeed(0);
		    	player.setAllowFlight(true);
		    	player.setFlying(true);
		    }
	    }
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (core.isState(State.INGAME)) 
        {
			if (core.getInGround().contains(p.getName()))
			{
				e.setCancelled(true);
				inGroundLeave(p);
			}
			if (core.getKiller().contains(p.getName()))
			{
				Location pendul1 = new Location(Bukkit.getWorld("game"), 
						core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul1.x")
						, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul1.y")
						, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul1.z"));
				Location pendul2 = new Location(Bukkit.getWorld("game"), 
						core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul2.x")
						, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul2.y")
						, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul2.z"));
				Location pendul3 = new Location(Bukkit.getWorld("game"), 
						core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul3.x")
						, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul3.y")
						, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul3.z"));
				Location pendul4 = new Location(Bukkit.getWorld("game"), 
						core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul4.x")
						, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul4.y")
						, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul4.z"));
				for (String inGround : core.getInGround())
				{
					Player victim = Bukkit.getPlayer(inGround);
					if (getPlayersNearby(p, 1).contains(victim))
					{
						launchSetPlayerOnTheKillerRunnable(p, victim);
					}
				}
				
				for (String onTheKiller : core.getOnTheKiller())
				{
					Player victim = Bukkit.getPlayer(onTheKiller);
					if (getPlayersNearby(pendul1, 3).contains(p))
					{
						launchPendulRunnableKiller(p, victim);
					}
					if (getPlayersNearby(pendul2, 3).contains(p))
					{
						launchPendulRunnableKiller(p, victim);
					}
					if (getPlayersNearby(pendul3, 3).contains(p))
					{
						launchPendulRunnableKiller(p, victim);
					}
					if (getPlayersNearby(pendul4, 3).contains(p))
					{
						launchPendulRunnableKiller(p, victim);
					}
				}
			}
			if (core.getSurvivors().contains(p.getName()))
			{
				generatorPreRepair(p);
				for (String inGround : core.getInGround())
				{
					Player victim = Bukkit.getPlayer(inGround);
					if (p.getName().equalsIgnoreCase(victim.getName())) { return; }
					if (getPlayersNearby(p, 1).contains(victim))
					{
						savePlayer(p, victim);
					}
				}
				for (String inPendul : core.getInPendul())
				{
					Player victim = Bukkit.getPlayer(inPendul);
					if (p.getName().equalsIgnoreCase(victim.getName())) { return; }
					if (getPlayersNearby(p, 3).contains(victim))
					{
						savePlayer(p, victim);
					}
				}
			}
        }
	}
	
	public void generatorPreRepair(Player p)
	{
		Location generator1 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor1.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor1.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor1.z"));
		Location generator2 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor2.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor2.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor2.z"));
		Location generator3 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor3.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor3.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor3.z"));
		Location generator4 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor4.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor4.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor4.z"));
		Location generator5 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor5.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor5.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor5.z"));
		Location generator6 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor6.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor6.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor6.z"));
		Location generator7 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor7.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor7.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor7.z"));
		new BukkitRunnable() {
            int t = 0;
            int prepareForRepair = 0;
            int prepareForRepairtime = 5;
	        public void run() {
	        	
	        	if (!p.isSneaking()) { cancel(); }
	        	if (prepareForRepair == 1)
	        	{
	        		p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
	        	}
	        	
	        	if (t == 1)
	        	{
	        		if (getPlayersNearby(generator1, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator1"))
		        		{
		        			sendProgressBar(p, "§fDémmarrage de la §6§lréparation§f...", prepareForRepair, prepareForRepairtime);
		        			++prepareForRepair;
			        		if (prepareForRepair == prepareForRepairtime)
			        		{
			        			generatorRepair(p);
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator2, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator2"))
		        		{
		        			sendProgressBar(p, "§fDémmarrage de la §6§lréparation§f...", prepareForRepair, prepareForRepairtime);
		        			++prepareForRepair;
			        		if (prepareForRepair == prepareForRepairtime)
			        		{
			        			generatorRepair(p);
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator3, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator3"))
		        		{
		        			sendProgressBar(p, "§fDémmarrage de la §6§lréparation§f...", prepareForRepair, prepareForRepairtime);
		        			++prepareForRepair;
			        		if (prepareForRepair == prepareForRepairtime)
			        		{
			        			generatorRepair(p);
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator4, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator4"))
		        		{
		        			sendProgressBar(p, "§fDémmarrage de la §6§lréparation§f...", prepareForRepair, prepareForRepairtime);
		        			++prepareForRepair;
			        		if (prepareForRepair == prepareForRepairtime)
			        		{
			        			generatorRepair(p);
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator5, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator5"))
		        		{
		        			sendProgressBar(p, "§fDémmarrage de la §6§lréparation§f...", prepareForRepair, prepareForRepairtime);
		        			++prepareForRepair;
			        		if (prepareForRepair == prepareForRepairtime)
			        		{
			        			generatorRepair(p);
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator6, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator6"))
		        		{
		        			sendProgressBar(p, "§fDémmarrage de la §6§lréparation§f...", prepareForRepair, prepareForRepairtime);
		        			++prepareForRepair;
			        		if (prepareForRepair == prepareForRepairtime)
			        		{
			        			generatorRepair(p);
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator7, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator7"))
		        		{
		        			sendProgressBar(p, "§fDémmarrage de la §6§lréparation§f...", prepareForRepair, prepareForRepairtime);
		        			++prepareForRepair;
			        		if (prepareForRepair == prepareForRepairtime)
			        		{
			        			generatorRepair(p);
			        			cancel();
			        		}
		        		}
		        	}
	        	}
                ++t;
                if (t == 2) {
                	t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 10L);
	}
	
	public void generatorRepair(Player p)
	{
		Location generator1 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor1.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor1.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor1.z"));
		Location generator2 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor2.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor2.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor2.z"));
		Location generator3 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor3.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor3.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor3.z"));
		Location generator4 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor4.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor4.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor4.z"));
		Location generator5 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor5.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor5.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor5.z"));
		Location generator6 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor6.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor6.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor6.z"));
		Location generator7 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor7.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor7.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".reactor7.z"));
		p.playSound(p.getLocation(), Sound.ANVIL_USE, 1.0f, 1.8f);
		new BukkitRunnable() {
            int t = 0;   
	        @SuppressWarnings("deprecation")
			public void run() {
	        	
	        	if (!p.isSneaking()) { cancel(); }
	        	
	        	if (t == 1)
	        	{
	        		if (getPlayersNearby(generator1, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator1"))
		        		{
		        			sendProgressBar(p, "§fRéparation du §e§lRéacteur 1§f:", core.timeGenerator1, timeGeneratorTime);
		        			if (core.timeGenerator1 == 1)
		        			{
		        				MessageUtils.startRepear(p, 1);
		        			}
		        			++core.timeGenerator1;
			        		if (core.timeGenerator1 == timeGeneratorTime)
			        		{
			        			MessageUtils.finishRepear(p, 1);
			        			core.getGeneratorsFinish().add("generator1");
			        			p.sendTitle("", "§aRéacteur réparé !");
			        			p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
			        			List<Block> redstoneLampsNearby = getBlocksNearby(generator1, 5);
			        			core.addReacteurs(p, 1);
			        			for (Block block : redstoneLampsNearby)
			        			{
			        				block.setType(Material.GLOWSTONE);
			        			}
			        			if (core.getGeneratorsFinish().size() == 7)
			        			{
			        				ZoneManager manage = new ZoneManager();
			        				manage.openDoor();
			        				for (Player pS : Bukkit.getOnlinePlayers())
			        				{
			        					ZoneManager regionPlayerManage = new ZoneManager(pS);
				        				regionPlayerManage.checker();
			        				}
			        			}
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator2, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator2"))
		        		{
		        			sendProgressBar(p, "§fRéparation du §e§lRéacteur 2§f:", core.timeGenerator2, timeGeneratorTime);
		        			if (core.timeGenerator2 == 1)
		        			{
		        				MessageUtils.startRepear(p, 2);
		        			}
		        			++core.timeGenerator2;
			        		if (core.timeGenerator2 == timeGeneratorTime)
			        		{
			        			MessageUtils.finishRepear(p, 2);
			        			core.getGeneratorsFinish().add("generator2");
			        			p.sendTitle("", "§aRéacteur réparé !");
			        			p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
			        			List<Block> redstoneLampsNearby = getBlocksNearby(generator2, 5);
			        			core.addReacteurs(p, 1);
			        			for (Block block : redstoneLampsNearby)
			        			{
			        				block.setType(Material.GLOWSTONE);
			        			}
			        			if (core.getGeneratorsFinish().size() == 7)
			        			{
			        				ZoneManager manage = new ZoneManager();
			        				manage.openDoor();
			        				for (Player pS : Bukkit.getOnlinePlayers())
			        				{
			        					ZoneManager regionPlayerManage = new ZoneManager(pS);
				        				regionPlayerManage.checker();
			        				}
			        			}
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator3, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator3"))
		        		{
		        			sendProgressBar(p, "§fRéparation du §e§lRéacteur 3§f:", core.timeGenerator3, timeGeneratorTime);
		        			if (core.timeGenerator3 == 1)
		        			{
		        				MessageUtils.startRepear(p, 3);
		        			}
		        			++core.timeGenerator3;
			        		if (core.timeGenerator3 == timeGeneratorTime)
			        		{
			        			MessageUtils.finishRepear(p, 3);
			        			core.getGeneratorsFinish().add("generator3");
			        			p.sendTitle("", "§aRéacteur réparé !");
			        			p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
			        			List<Block> redstoneLampsNearby = getBlocksNearby(generator3, 5);
			        			core.addReacteurs(p, 1);
			        			for (Block block : redstoneLampsNearby)
			        			{
			        				block.setType(Material.GLOWSTONE);
			        			}
			        			if (core.getGeneratorsFinish().size() == 7)
			        			{
			        				ZoneManager manage = new ZoneManager();
			        				manage.openDoor();
			        				for (Player pS : Bukkit.getOnlinePlayers())
			        				{
			        					ZoneManager regionPlayerManage = new ZoneManager(pS);
				        				regionPlayerManage.checker();
			        				}
			        			}
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator4, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator4"))
		        		{
		        			sendProgressBar(p, "§fRéparation du §e§lRéacteur 4§f:", core.timeGenerator4, timeGeneratorTime);
		        			if (core.timeGenerator1 == 4)
		        			{
		        				MessageUtils.startRepear(p, 4);
		        			}
		        			++core.timeGenerator4;
			        		if (core.timeGenerator4 == timeGeneratorTime)
			        		{
			        			MessageUtils.finishRepear(p, 4);
			        			core.getGeneratorsFinish().add("generator4");
			        			p.sendTitle("", "§aRéacteur réparé !");
			        			p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
			        			List<Block> redstoneLampsNearby = getBlocksNearby(generator4, 5);
			        			core.addReacteurs(p, 1);
			        			for (Block block : redstoneLampsNearby)
			        			{
			        				block.setType(Material.GLOWSTONE);
			        			}
			        			if (core.getGeneratorsFinish().size() == 7)
			        			{
			        				ZoneManager manage = new ZoneManager();
			        				manage.openDoor();
			        				for (Player pS : Bukkit.getOnlinePlayers())
			        				{
			        					ZoneManager regionPlayerManage = new ZoneManager(pS);
				        				regionPlayerManage.checker();
			        				}
			        			}
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator5, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator5"))
		        		{
		        			sendProgressBar(p, "§fRéparation du §e§lRéacteur 5§f:", core.timeGenerator5, timeGeneratorTime);
		        			if (core.timeGenerator5 == 1)
		        			{
		        				MessageUtils.startRepear(p, 5);
		        			}
		        			++core.timeGenerator5;
			        		if (core.timeGenerator5 == timeGeneratorTime)
			        		{
			        			MessageUtils.finishRepear(p, 5);
			        			core.getGeneratorsFinish().add("generator5");
			        			p.sendTitle("", "§aRéacteur réparé !");
			        			p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
			        			List<Block> redstoneLampsNearby = getBlocksNearby(generator5, 5);
			        			core.addReacteurs(p, 1);
			        			for (Block block : redstoneLampsNearby)
			        			{
			        				block.setType(Material.GLOWSTONE);
			        			}
			        			if (core.getGeneratorsFinish().size() == 7)
			        			{
			        				ZoneManager manage = new ZoneManager();
			        				manage.openDoor();
			        				for (Player pS : Bukkit.getOnlinePlayers())
			        				{
			        					ZoneManager regionPlayerManage = new ZoneManager(pS);
				        				regionPlayerManage.checker();
			        				}
			        			}
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator6, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator6"))
		        		{
		        			sendProgressBar(p, "§fRéparation du §e§lRéacteur 6§f:", core.timeGenerator6, timeGeneratorTime);
		        			if (core.timeGenerator6 == 1)
		        			{
		        				MessageUtils.startRepear(p, 6);
		        			}
		        			++core.timeGenerator6;
			        		if (core.timeGenerator6 == timeGeneratorTime)
			        		{
			        			MessageUtils.finishRepear(p, 6);
			        			core.getGeneratorsFinish().add("generator6");
			        			p.sendTitle("", "§aRéacteur réparé !");
			        			p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
			        			List<Block> redstoneLampsNearby = getBlocksNearby(generator6, 5);
			        			core.addReacteurs(p, 1);
			        			for (Block block : redstoneLampsNearby)
			        			{
			        				block.setType(Material.GLOWSTONE);
			        			}
			        			if (core.getGeneratorsFinish().size() == 7)
			        			{
			        				ZoneManager manage = new ZoneManager();
			        				manage.openDoor();
			        				for (Player pS : Bukkit.getOnlinePlayers())
			        				{
			        					ZoneManager regionPlayerManage = new ZoneManager(pS);
				        				regionPlayerManage.checker();
			        				}
			        			}
			        			cancel();
			        		}
		        		}
		        	}
		        	if (getPlayersNearby(generator7, 3).contains(p))
		        	{
		        		if (!core.getGeneratorsFinish().contains("generator7"))
		        		{
		        			sendProgressBar(p, "§fRéparation du §e§lRéacteur 7§f:", core.timeGenerator7, timeGeneratorTime);
		        			if (core.timeGenerator7 == 1)
		        			{
		        				MessageUtils.startRepear(p, 7);
		        			}
		        			++core.timeGenerator7;
			        		if (core.timeGenerator7 == timeGeneratorTime)
			        		{
			        			MessageUtils.finishRepear(p, 7);
			        			core.getGeneratorsFinish().add("generator7");
			        			p.sendTitle("", "§aRéacteur réparé !");
			        			p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
			        			List<Block> redstoneLampsNearby = getBlocksNearby(generator7, 5);
			        			core.addReacteurs(p, 1);
			        			for (Block block : redstoneLampsNearby)
			        			{
			        				block.setType(Material.GLOWSTONE);
			        			}
			        			if (core.getGeneratorsFinish().size() == 7)
			        			{
			        				ZoneManager manage = new ZoneManager();
			        				manage.openDoor();
			        				for (Player pS : Bukkit.getOnlinePlayers())
			        				{
			        					ZoneManager regionPlayerManage = new ZoneManager(pS);
				        				regionPlayerManage.checker();
			        				}
			        			}
			        			cancel();
			        		}
		        		}
		        	}
	        	}
                ++t;
                if (t == 2) {
                	t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 5L);
	}
	
	public void launchPendulRunnableKiller(Player killer, Player victim)
	{
		timeForSetOnKillerTime = 0;
		Location pendul1 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul1.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul1.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul1.z")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul1.f")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul1.t"));
		Location pendul2 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul2.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul2.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul2.z")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul2.f")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul2.t"));
		Location pendul3 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul3.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul3.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul3.z")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul3.f")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul3.t"));
		Location pendul4 = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul4.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul4.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul4.z")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul4.f")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".pendul4.t"));
		new BukkitRunnable() {
            int t = 0;   
	        public void run() {
	        	if (killer.isSneaking()) 
	        	{
	        		if (timeForSetOnKillerTime == timeForSetOnKiller)
		        	{
						core.getOnTheKiller().remove(victim.getName());
						core.getInPendul().add(victim.getName());
						if (getPlayersNearby(pendul1, 3).contains(killer))
						{
							setInPendul(victim, pendul1);
						}
						if (getPlayersNearby(pendul2, 3).contains(killer))
						{
							setInPendul(victim, pendul2);
						}
						if (getPlayersNearby(pendul3, 3).contains(killer))
						{
							setInPendul(victim, pendul3);
							launchBlood(victim);
						}
						if (getPlayersNearby(pendul4, 3).contains(killer))
						{
							setInPendul(victim, pendul4);
						}
						timeForSetOnKillerTime = 0;
						core.title.sendActionBar(killer, "");
		        		cancel();
		        	}
	        	}
	        	else
	        	{
	        		cancel();
	        	}
	        	sendProgressBar(killer, "§fVous pendez §c§k" + victim.getName() + "§f:", timeForSetOnKillerTime, timeForSetOnKiller);
                ++t;
                ++timeForSetOnKillerTime;
                if (t == 2) {
                	t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 15L);
	}
	
	public void launchSetPlayerOnTheKillerRunnable(Player killer, Player victim)
	{
		timeForSetOnKillerTime = 0;
		new BukkitRunnable() {
            int t = 0;   
	        public void run() {
	        	if (killer.isSneaking()) 
	        	{
	        		if (timeForSetOnKillerTime == timeForSetOnKiller)
		        	{
		        		core.getInGround().remove(victim.getName());
						core.getOnTheKiller().add(victim.getName());
						setOnTheKiller(victim, killer);
						
						timeForSetOnKillerTime = 0;
						core.title.sendActionBar(killer, "");
		        		cancel();
		        	}
	        	}
	        	else
	        	{
	        		cancel();
	        	}
	        	sendProgressBar(killer, "§fVous attrapez §c§k" + victim.getName() + "§f:", timeForSetOnKillerTime, timeForSetOnKiller);
                ++t;
                ++timeForSetOnKillerTime;
                if (t == 2) {
                	t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 15L);
	}
	
	public static void battementsDeCoeur(Player killer)
	{
		new BukkitRunnable() {
            int t = 0;
                
	        public void run() {
	        	
                ++t;
                if (core.isState(State.INGAME))
	        	{
	        		if (t == 1) {
	        			for (Player players : Bukkit.getOnlinePlayers())
	        			{
	        				int location = 0;
	        				if (getPlayersNearby(killer, 25).contains(players))
	        				{
	        					location = 25;
	        				}
	        				if (getPlayersNearby(killer, 15).contains(players))
	        				{
	        					location = 15;
	        				}
	        				if (getPlayersNearby(killer, 10).contains(players))
	        				{
	        					location = 10;
	        				}
	        				
	        				if (location == 25)
	        				{
	        					if (core.getSurvivors().contains(players.getName()))
			        			{
	        						if (!core.getInGround().contains(players.getName()) && !core.getInPendul().contains(players.getName()) && !core.getOnTheKiller().contains(players.getName()))
	        						{
	        							CustomSounds.battementDeCoeur(players, 1);
	        						}
			        			}
	        				}
	        				if (location == 15)
	        				{
	        					if (core.getSurvivors().contains(players.getName()))
			        			{
	        						if (!core.getInGround().contains(players.getName()) && !core.getInPendul().contains(players.getName()) && !core.getOnTheKiller().contains(players.getName()))
	        						{
	        							CustomSounds.battementDeCoeur(players, 2);
	        						}
			        			}
	        				}
	        				if (location == 10)
	        				{
	        					if (core.getSurvivors().contains(players.getName()))
			        			{
	        						if (!core.getInGround().contains(players.getName()) && !core.getInPendul().contains(players.getName()) && !core.getOnTheKiller().contains(players.getName()))
	        						{
	        							CustomSounds.battementDeCoeur(players, 3);
	        						}
			        			}
	        				}
	        			}
		        	}
	        		t = 0;
	        	}
	        	else
	        	{
	        		cancel();
	        	}
            }
        }.runTaskTimer((Plugin)core, 0L, 20L);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntityType() != EntityType.PLAYER) { return; }
    	if(e.getEntity() instanceof Player) {
    		Player p = (Player) e.getEntity();
            if (core.isState(State.INGAME)) 
            {
                if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) 
                {
                	e.setCancelled(true);
                }
                if (e.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) 
                {
                	e.setCancelled(true);
                }
                p.setMaxHealth(20);
                p.setHealth(20);
            }
    	}
	}
	
	@EventHandler
	public void onDamageByKiller(EntityDamageByEntityEvent e) {
		if (e.getEntityType() != EntityType.PLAYER) { return; }
    	if(e.getEntity() instanceof Player) {
    		if (e.getDamager() instanceof Player)
    		{
    			if (core.isState(State.INGAME))
    			{
    				Player victim = (Player) e.getEntity();
    				Player attacker = (Player) e.getDamager();
    				if (core.getKiller().contains(attacker.getName()))
    				{
    					if (!core.getInGround().contains(victim.getName()) && !core.getOnTheKiller().contains(victim.getName()) && !core.getInPendul().contains(victim.getName()))
    					{
    						if (attacker.getItemInHand().getAmount() > 0)
            				{
            					if (core.getSurvivorFrappes(victim) == 2)
            					{
            						core.getInGround().add(victim.getName());
            						core.addFrappes(attacker, 1);
            						core.addSurvivorFrappes(victim, 1);
            						setInGround(victim);
            						victim.setHealth(20);
            						attacker.getInventory().clear();
            						new BukkitRunnable() {
            				            int t = 0;   
            					        public void run() {
            				                ++t;
            				                if (t == 3) {
            				                	ItemStack killerItem = ItemStackSerializer.deserialize(core.weaponKillerItem);
            				        	        ItemMeta killerItemM = killerItem.getItemMeta();
            				        	        killerItemM.setDisplayName(core.weaponKillerItemName);
            				        	        killerItem.setItemMeta(killerItemM);
            				        	        attacker.getInventory().addItem(killerItem);
            				                	cancel();
            				                }
            				            }
            				        }.runTaskTimer((Plugin)core, 0L, 20L);
            					}
            					else
            					{
            						core.addFrappes(attacker, 1);
            						core.addSurvivorFrappes(victim, 1);
            						victim.setHealth(20);
            						victim.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 1));
            						attacker.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
            						attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1));
            						attacker.getInventory().clear();
            						new BukkitRunnable() {
            				            int t = 0;   
            					        public void run() {
            				                ++t;
            				                if (t == 3) {
            				                	ItemStack killerItem = ItemStackSerializer.deserialize(core.weaponKillerItem);
            				        	        ItemMeta killerItemM = killerItem.getItemMeta();
            				        	        killerItemM.setDisplayName(core.weaponKillerItemName);
            				        	        killerItem.setItemMeta(killerItemM);
            				        	        attacker.getInventory().addItem(killerItem);
            				                	cancel();
            				                }
            				            }
            				        }.runTaskTimer((Plugin)core, 0L, 20L);
            					}
            				}
            				else
            				{
            					e.setCancelled(true);
            				}
    					}
    					else
    					{
    						e.setCancelled(true);
    						attacker.getInventory().clear();
    						ItemStack killerItem = ItemStackSerializer.deserialize(core.weaponKillerItem);
		        	        ItemMeta killerItemM = killerItem.getItemMeta();
		        	        killerItemM.setDisplayName(core.weaponKillerItemName);
		        	        killerItem.setItemMeta(killerItemM);
		        	        attacker.getInventory().addItem(killerItem);
    					}
    				}
    				else
    				{
    					e.setCancelled(true);
    				}
    			}
    		}
    	}
	}
	
	public static List<Player> getPlayersNearby(Player player, int radius) {
        List<Player> playersNearby = new ArrayList<>();

        Location center = player.getLocation();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld() == center.getWorld()) {
                Location loc = p.getLocation();
                double distanceSquared = center.distanceSquared(loc);
                if (distanceSquared <= radius * radius) {
                    playersNearby.add(p);
                }
            }
        }

        return playersNearby;
    }
	
	public List<Player> getPlayersNearby(Location center, int radius) {
        List<Player> playersNearby = new ArrayList<>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld() == center.getWorld()) {
                Location loc = p.getLocation();
                double distanceSquared = center.distanceSquared(loc);
                if (distanceSquared <= radius * radius) {
                    playersNearby.add(p);
                }
            }
        }

        return playersNearby;
    }
	
	public List<Block> getBlocksNearby(Location center, int radius) {
	    List<Block> blocksNearby = new ArrayList<>();

	    for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
	        for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
	            for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
	                Block block = center.getWorld().getBlockAt(x, y, z);
	                if (block.getType() == Material.REDSTONE_LAMP_OFF) {
	                    blocksNearby.add(block);
	                }
	            }
	        }
	    }

	    return blocksNearby;
	}

	public void setInGround(Player p)
	{
		Location blockLock = p.getLocation();
		float y = (float) (blockLock.getBlockY() - 1.5);
					
		Location customloc = new Location(Bukkit.getWorld(p.getWorld().getName()), blockLock.getX(), y, blockLock.getZ());
		ArmorStand armorStand = (ArmorStand)Bukkit.getWorld(p.getWorld().getName()).spawnEntity(customloc, EntityType.ARMOR_STAND);
		armorStand.setSmall(true);
		armorStand.setVisible(false);
		armorStand.setCustomName("ground_" + p.getName());
		armorStand.setCustomNameVisible(false);
		armorStand.setGravity(false);
		armorStand.setPassenger(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 1));
		new BukkitRunnable() {
            int t = 0;   
	        public void run() {
	        	
	        	if (!core.getInGround().contains(p.getName())) { armorStand.remove(); cancel(); }
	        	String name = armorStand.getCustomName();
	        	if (name != null && name.equalsIgnoreCase("ground_" + p.getName()))
	            {
	            	if (core.getInGround().contains(p.getName()))
	            	{
	            		armorStand.setPassenger(p);
	            	}
	            	else
	            	{
	            		armorStand.remove();
	            	}
	            }
	        	
                ++t;
                if (t == 2) {
                	t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 15L);
	}
	
	public void savePlayer(Player p, Player saveP)
	{
		if (core.getInGround().contains(saveP.getName()))
		{
			new BukkitRunnable() {
	            int t = 0;   
	            int timeToSaveTime = 0;
		        public void run() {
		        	
		        	if (!core.getInGround().contains(saveP.getName())) { clearEffects(saveP); cancel(); }
		        	if (!p.isSneaking()) { cancel(); }
		        	
		        	if (timeToSaveTime == core.getConfig().getInt("Timers.revive1"))
		        	{
		        		core.getInGround().remove(saveP.getName());
		        		core.removeSurvivorFrappes(saveP);
		        		inGroundLeave(saveP);
		        		core.title.sendActionBar(p, "");
		        		core.addSauver(p, 1);
		        		clearEffects(saveP);
		        		cancel();
		        	}
		        	sendProgressBar(p, "§fVous sauvez §a§k" + saveP.getName() + "§f:", timeToSaveTime, core.getConfig().getInt("Timers.revive1"));
		        	++timeToSaveTime;
	                ++t;
	                if (t == 2) {
	                	t = 0;
	                }
	            }
	        }.runTaskTimer((Plugin)core, 0L, 15L);
		}
		if (core.getInPendul().contains(saveP.getName()))
		{
			new BukkitRunnable() {
	            int t = 0;   
	            int timeToSaveTime = 0;
		        public void run() {
		        	
		        	if (!core.getInPendul().contains(saveP.getName())) { clearEffects(saveP); cancel(); }
		        	if (!p.isSneaking()) { cancel(); }
		        	
		        	if (timeToSaveTime == core.getConfig().getInt("Timers.revive2"))
		        	{
		        		core.getInPendul().remove(saveP.getName());
		        		core.removeSurvivorFrappes(saveP);
		        		inGroundLeave(saveP);
		        		saveP.setFlySpeed((float) 0.1);
		        		saveP.setAllowFlight(false);
		        		saveP.setFlying(false);
		        		core.title.sendActionBar(p, "");
		        		core.addSauver(p, 1);
		        		clearEffects(saveP);
		        		cancel();
		        	}
		        	sendProgressBar(p, "§fVous sauvez §a§k" + saveP.getName() + "§f:", timeToSaveTime, core.getConfig().getInt("Timers.revive2"));
		        	++timeToSaveTime;
	                ++t;
	                if (t == 2) {
	                	t = 0;
	                }
	            }
	        }.runTaskTimer((Plugin)core, 0L, 15L);
		}
	}
	
	public void setInPendul(Player p, Location location)
	{
		p.setAllowFlight(true);
		p.setFlying(true);
		p.setFlySpeed(0);
		p.teleport(location);
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 1));
		new BukkitRunnable() {
            int t = 0;   
            int timeToDeathTime = 0;
	        public void run() {
	        	
	        	if (!core.getInPendul().contains(p.getName())) { cancel(); }
	        	p.teleport(location);
	        	if (timeToDeathTime == 2)
	        	{
	        		launchBlood(p);
	        	}
	        	if (timeToDeathTime == timeForDeath)
	        	{
	        		Instance.killPlayer(p);
	        		MessageUtils.deathMessage(p);
	        		core.title.sendActionBar(p, "");
	        		cancel();
	        	}
	        	sendProgressBar(p, "§fProgression de votre mort:", timeToDeathTime, timeForDeath);
	        	++timeToDeathTime;
                ++t;
                if (t == 2) {
                	t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 15L);
	}
	
	public void setOnTheKiller(Player p, Player killer)
	{
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 1));
		new BukkitRunnable() {
            int t = 0;   
	        public void run() {
	        	
	        	if (!core.getOnTheKiller().contains(p.getName())) { cancel(); }
	        	if (killer.getPassenger() == null)
	        	{
	        		killer.setPassenger(p);
	        	}
	        	
                ++t;
                if (t == 2) {
                	t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 15L);
	}
	
	public void inGroundLeave(Player p)
	{
		World world = p.getWorld();
		for (Entity aS : world.getEntities())
		{
			if (aS instanceof ArmorStand)
			{
				ArmorStand armorStand = (ArmorStand)aS;
	            String name = armorStand.getCustomName();
	            if (name != null && name.equalsIgnoreCase("ground_" + p.getName()))
	            {
	            	if (core.getInGround().contains(p.getName()))
	            	{
	            		aS.setPassenger(p);
	            	}
	            	else
	            	{
	            		aS.remove();
	            	}
	            }
			}
		}
	}
	
	public void launchBlood(Player p)
	{
		new BukkitRunnable() {
            int t = 0;  
            Location loc = p.getLocation();
        	PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE, true, (float) loc.getX(), (float) loc.getY() + (float) 1.8, (float) loc.getZ(), 0, 0, 0, 0, 1);
	        public void run() {
	        	
	        	if (!core.getInPendul().contains(p.getName())) { cancel(); }
	        	for (Player player : Bukkit.getOnlinePlayers()) {
	        	    CraftPlayer craftPlayer = (CraftPlayer) player;
	        	    craftPlayer.getHandle().playerConnection.sendPacket(packet);
	        	}
	        	
                ++t;
                if (t == 2) {
                	t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 15L);
	}
	
	public void sendProgressBar(Player player, String message, int current, int max) {
	    float percentage = (float) current / max;
	    int progressBars = Math.round(percentage * 10);
	    String progressBarString = "§a";
	    for (int i = 0; i < 10; i++) {
	        if (i < progressBars) {
	            progressBarString += "▌";
	        } else {
	            progressBarString += "§7▒";
	        }
	    }
	    String actionBarMessage = message + " " + progressBarString + " §7(" + current + "/" + max + ")";
	    core.title.sendActionBar(player, actionBarMessage);
	}
	
	public static void clearEffects(Player player) {
	    for (PotionEffect effect : player.getActivePotionEffects()) {
	        player.removePotionEffect(effect.getType());
	    }
	}
}
