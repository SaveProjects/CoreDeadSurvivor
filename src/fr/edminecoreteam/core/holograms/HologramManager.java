package fr.edminecoreteam.core.holograms;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.State;
import fr.edminecoreteam.core.gamemanager.InteractionGames;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;

public class HologramManager 
{
	private static Core core = Core.getInstance();
	
	private Player p;
	
	public HologramManager(Player p) {
		this.p = p;
	}
	
	public void generateHolograms()
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
		for (String generator : getGenerators())
		{
			if (generator.contains("1"))
			{
				holoNormalManageNMS(generator1.getWorld().getName(), (float) generator1.getX(), (float) generator1.getY() + (float) 1.0, (float) generator1.getZ(), "§7Réacteur §b#1");
				holoStateManageNMS(generator1.getWorld().getName(), (float) generator1.getX(), (float) generator1.getY() + (float) 0.7, (float) generator1.getZ(), "1");
				holoProgessManageNMS(generator1.getWorld().getName(), (float) generator1.getX(), (float) generator1.getY() + (float) 0.4, (float) generator1.getZ(), "1");
			}
			if (generator.contains("2"))
			{
				holoNormalManageNMS(generator2.getWorld().getName(), (float) generator2.getX(), (float) generator2.getY() + (float) 1.0, (float) generator2.getZ(), "§7Réacteur §b#2");
				holoStateManageNMS(generator2.getWorld().getName(), (float) generator2.getX(), (float) generator2.getY() + (float) 0.7, (float) generator2.getZ(), "2");
				holoProgessManageNMS(generator2.getWorld().getName(), (float) generator2.getX(), (float) generator2.getY() + (float) 0.4, (float) generator2.getZ(), "2");
			}
			if (generator.contains("3"))
			{
				holoNormalManageNMS(generator3.getWorld().getName(), (float) generator3.getX(), (float) generator3.getY() + (float) 1.0, (float) generator3.getZ(), "§7Réacteur §b#3");
				holoStateManageNMS(generator3.getWorld().getName(), (float) generator3.getX(), (float) generator3.getY() + (float) 0.7, (float) generator3.getZ(), "3");
				holoProgessManageNMS(generator3.getWorld().getName(), (float) generator3.getX(), (float) generator3.getY() + (float) 0.4, (float) generator3.getZ(), "3");
			}
			if (generator.contains("4"))
			{
				holoNormalManageNMS(generator4.getWorld().getName(), (float) generator4.getX(), (float) generator4.getY() + (float) 1.0, (float) generator4.getZ(), "§7Réacteur §b#4");
				holoStateManageNMS(generator4.getWorld().getName(), (float) generator4.getX(), (float) generator4.getY() + (float) 0.7, (float) generator4.getZ(), "4");
				holoProgessManageNMS(generator4.getWorld().getName(), (float) generator4.getX(), (float) generator4.getY() + (float) 0.4, (float) generator4.getZ(), "4");
			}
			if (generator.contains("5"))
			{
				holoNormalManageNMS(generator5.getWorld().getName(), (float) generator5.getX(), (float) generator5.getY() + (float) 1.0, (float) generator5.getZ(), "§7Réacteur §b#5");
				holoStateManageNMS(generator5.getWorld().getName(), (float) generator5.getX(), (float) generator5.getY() + (float) 0.7, (float) generator5.getZ(), "5");
				holoProgessManageNMS(generator5.getWorld().getName(), (float) generator5.getX(), (float) generator5.getY() + (float) 0.4, (float) generator5.getZ(), "5");
			}
			if (generator.contains("6"))
			{
				holoNormalManageNMS(generator6.getWorld().getName(), (float) generator6.getX(), (float) generator6.getY() + (float) 1.0, (float) generator6.getZ(), "§7Réacteur §b#6");
				holoStateManageNMS(generator6.getWorld().getName(), (float) generator6.getX(), (float) generator6.getY() + (float) 0.7, (float) generator6.getZ(), "6");
				holoProgessManageNMS(generator6.getWorld().getName(), (float) generator6.getX(), (float) generator6.getY() + (float) 0.4, (float) generator6.getZ(), "6");
			}
			if (generator.contains("7"))
			{
				holoNormalManageNMS(generator7.getWorld().getName(), (float) generator7.getX(), (float) generator7.getY() + (float) 1.0, (float) generator7.getZ(), "§7Réacteur §b#7");
				holoStateManageNMS(generator7.getWorld().getName(), (float) generator7.getX(), (float) generator7.getY() + (float) 0.7, (float) generator7.getZ(), "7");
				holoProgessManageNMS(generator7.getWorld().getName(), (float) generator7.getX(), (float) generator7.getY() + (float) 0.4, (float) generator7.getZ(), "7");
			}
		}
	}
	
	private void holoNormalManageNMS(String world, float x, float y, float z, String display) {
		new BukkitRunnable() {
            int t = 0;
            Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            WorldServer ws = ((CraftWorld)loc.getWorld()).getHandle();
            EntityArmorStand nmsStand = new EntityArmorStand((World)ws);
            
            public void run() {
            	
            	if (Bukkit.getPlayer(p.getName()) == null) { cancel(); }
            	if (!core.isState(State.INGAME)) { cancel(); }
            	
            	if (t == 1)
            	{
            		
                	
                	nmsStand.setLocation(x, y, z, 0.0f, 0.0f);
            		nmsStand.setSmall(true);
                    
                    nmsStand.setCustomName(display);
                    
                    nmsStand.setCustomNameVisible(true);
                    nmsStand.setGravity(false);
                    nmsStand.setInvisible(true);
                    PacketPlayOutEntityDestroy packetDestroy = new PacketPlayOutEntityDestroy(nmsStand.getId());
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetDestroy);
                    PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving((EntityLiving)nmsStand);
                    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packetSpawn);
            	}
            	
                if (t == 10) {
                    this.t = 0;
                }
                ++t;
            }
        }.runTaskTimer((Plugin)Core.getInstance(), 0L, 10L);
	}
	
	private void holoStateManageNMS(String world, float x, float y, float z, String name1) {
		new BukkitRunnable() {
            int t = 0;
            Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            WorldServer ws = ((CraftWorld)loc.getWorld()).getHandle();
            EntityArmorStand nmsStand = new EntityArmorStand((World)ws);
            String name = name1;
            
            public void run() {
            	
            	if (Bukkit.getPlayer(p.getName()) == null) { cancel(); }
            	if (!core.isState(State.INGAME)) { cancel(); }
            	
            		InteractionGames game = new InteractionGames();
                	
                	nmsStand.setLocation(x, y, z, 0.0f, 0.0f);
            		nmsStand.setSmall(true);
                    
            		if (name.contains("1"))
            		{
            			if (core.timeGenerator1 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§7Statut: §aRéparé ✔");
            			}
            			else
            			{
            				nmsStand.setCustomName("§7Statut: §cEndommagé ✘");
            			}
            		}
            		if (name.contains("2"))
            		{
            			if (core.timeGenerator2 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§7Statut: §aRéparé ✔");
            			}
            			else
            			{
            				nmsStand.setCustomName("§7Statut: §cEndommagé ✘");
            			}
            		}
            		if (name.contains("3"))
            		{
            			if (core.timeGenerator3 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§7Statut: §aRéparé ✔");
            			}
            			else
            			{
            				nmsStand.setCustomName("§7Statut: §cEndommagé ✘");
            			}
            		}
            		if (name.contains("4"))
            		{
            			if (core.timeGenerator4 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§7Statut: §aRéparé ✔");
            			}
            			else
            			{
            				nmsStand.setCustomName("§7Statut: §cEndommagé ✘");
            			}
            		}
            		if (name.contains("5"))
            		{
            			if (core.timeGenerator5 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§7Statut: §aRéparé ✔");
            			}
            			else
            			{
            				nmsStand.setCustomName("§7Statut: §cEndommagé ✘");
            			}
            		}
            		if (name.contains("6"))
            		{
            			if (core.timeGenerator6 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§7Statut: §aRéparé ✔");
            			}
            			else
            			{
            				nmsStand.setCustomName("§7Statut: §cEndommagé ✘");
            			}
            		}
            		if (name.contains("7"))
            		{
            			if (core.timeGenerator7 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§7Statut: §aRéparé ✔");
            			}
            			else
            			{
            				nmsStand.setCustomName("§7Statut: §cEndommagé ✘");
            			}
            		}
                    
                    nmsStand.setCustomNameVisible(true);
                    nmsStand.setGravity(false);
                    nmsStand.setInvisible(true);
                    PacketPlayOutEntityDestroy packetDestroy = new PacketPlayOutEntityDestroy(nmsStand.getId());
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetDestroy);
                    PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving((EntityLiving)nmsStand);
                    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packetSpawn);
            	
                if (t == 5) {
                    this.t = 0;
                }
                ++t;
            }
        }.runTaskTimer((Plugin)Core.getInstance(), 0L, 10L);
	}
	
	private void holoProgessManageNMS(String world, float x, float y, float z, String name1) {
		new BukkitRunnable() {
            int t = 0;
            Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            WorldServer ws = ((CraftWorld)loc.getWorld()).getHandle();
            EntityArmorStand nmsStand = new EntityArmorStand((World)ws);
            String name = name1;
            
            public void run() {
            	
            	if (Bukkit.getPlayer(p.getName()) == null) { cancel(); }
            	if (!core.isState(State.INGAME)) { cancel(); }
            	
            		InteractionGames game = new InteractionGames();
                	
                	nmsStand.setLocation(x, y, z, 0.0f, 0.0f);
            		nmsStand.setSmall(true);
                    
            		if (name.contains("1"))
            		{
            			if (core.timeGenerator1 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§5");
            			}
            			else
            			{
            				nmsStand.setCustomName(sendProgressBar("", core.timeGenerator1, game.timeGeneratorTime));
            			}
            		}
            		if (name.contains("2"))
            		{
            			if (core.timeGenerator2 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§5");
            			}
            			else
            			{
            				nmsStand.setCustomName(sendProgressBar("", core.timeGenerator2, game.timeGeneratorTime));
            			}
            		}
            		if (name.contains("3"))
            		{
            			if (core.timeGenerator3 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§5");
            			}
            			else
            			{
            				nmsStand.setCustomName(sendProgressBar("", core.timeGenerator3, game.timeGeneratorTime));
            			}
            		}
            		if (name.contains("4"))
            		{
            			if (core.timeGenerator4 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§5");
            			}
            			else
            			{
            				nmsStand.setCustomName(sendProgressBar("", core.timeGenerator4, game.timeGeneratorTime));
            			}
            		}
            		if (name.contains("5"))
            		{
            			if (core.timeGenerator5 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§5");
            			}
            			else
            			{
            				nmsStand.setCustomName(sendProgressBar("", core.timeGenerator5, game.timeGeneratorTime));
            			}
            		}
            		if (name.contains("6"))
            		{
            			if (core.timeGenerator6 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§5");
            			}
            			else
            			{
            				nmsStand.setCustomName(sendProgressBar("", core.timeGenerator6, game.timeGeneratorTime));
            			}
            		}
            		if (name.contains("7"))
            		{
            			if (core.timeGenerator7 == game.timeGeneratorTime)
            			{
            				nmsStand.setCustomName("§5");
            			}
            			else
            			{
            				nmsStand.setCustomName(sendProgressBar("", core.timeGenerator7, game.timeGeneratorTime));
            			}
            		}
                    
                    nmsStand.setCustomNameVisible(true);
                    nmsStand.setGravity(false);
                    nmsStand.setInvisible(true);
                    PacketPlayOutEntityDestroy packetDestroy = new PacketPlayOutEntityDestroy(nmsStand.getId());
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetDestroy);
                    PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving((EntityLiving)nmsStand);
                    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packetSpawn);
            	
                if (t == 5) {
                    this.t = 0;
                }
                ++t;
            }
        }.runTaskTimer((Plugin)Core.getInstance(), 0L, 10L);
	}
	
	public String sendProgressBar(String message, int current, int max) {
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
	    return actionBarMessage;
	}
	
	public List<String> getGenerators()
	{
		List<String> generators = new ArrayList<String>();
		generators.add("reactor1");
		generators.add("reactor2");
		generators.add("reactor3");
		generators.add("reactor4");
		generators.add("reactor5");
		generators.add("reactor6");
		generators.add("reactor7");
		return generators;
	}
}
