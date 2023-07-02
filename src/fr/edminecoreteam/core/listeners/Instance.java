package fr.edminecoreteam.core.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.data.Data;
import fr.edminecoreteam.core.gamemanager.InteractionGames;
import fr.edminecoreteam.core.holograms.HologramManager;
import fr.edminecoreteam.core.purchase.StoreData;
import fr.edminecoreteam.core.utils.ChangeHubInfo;
import fr.edminecoreteam.core.utils.ItemStackSerializer;
import fr.edminecoreteam.core.utils.MessageUtils;
import fr.edminecoreteam.core.utils.SkullNBT;

public class Instance implements Listener
{
	
	private static Core core = Core.getInstance();
	
	private static ItemStack getSkull(String url) {
		return SkullNBT.getSkull(url);
	}
	
    public static void joinWAITING(final Player p) {
        Location spawn = new Location(Bukkit.getWorld("hub"), 2.5, 31.0, 0.5, -90.5f, -4.0f);
        PlayerInventory pi = p.getInventory();
        pi.setHelmet(null);
        pi.setChestplate(null);
        pi.setLeggings(null);
        pi.setBoots(null);
        p.setAllowFlight(false);
		p.setFlying(false);
        
        p.getInventory().clear();
        p.setLevel(0);
        p.setFoodLevel(20);
        p.setHealth(20.0);
        p.setGameMode(GameMode.ADVENTURE);
        p.teleport(spawn);
        ItemJoinListener.joinItem(p);
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.5f);
    }
    
	public static void joinInGame(Player p) {
    	Location spawn = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".specSpawn.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".specSpawn.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".specSpawn.z")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".specSpawn.f")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".specSpawn.t"));
        
    		PlayerInventory pi = p.getInventory();
            pi.setHelmet(null);
            pi.setChestplate(null);
            pi.setLeggings(null);
            pi.setBoots(null);
            p.setAllowFlight(false);
    		p.setFlying(false);
            
            p.getInventory().clear();
            p.setLevel(0);
            p.setFoodLevel(20);
            p.setHealth(20.0);
            p.setGameMode(GameMode.SPECTATOR);
            p.teleport(spawn);
            ItemJoinListener.spectacleItem(p);
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.5f);
    }
    
	
	@SuppressWarnings("deprecation")
	public static void startGame() 
	{
		for (String name : core.getPlayersName())
		{
			core.getSurvivors().add(name);
		}
		Location survivorSpawn = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".survivorSpawn.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".survivorSpawn.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".survivorSpawn.z")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".survivorSpawn.f")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".survivorSpawn.t"));
		Location killerSpawn = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".killerSpawn.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".killerSpawn.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".killerSpawn.z")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".killerSpawn.f")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".killerSpawn.t"));
		defineRole();
    	for (Player pS : core.getPlayers())
    	{
    		if (core.getSurvivors().contains(pS.getName()))
    		{
    			pS.getInventory().clear();
    			pS.setGameMode(GameMode.ADVENTURE);
    			pS.teleport(survivorSpawn);
    			pS.sendTitle("§7Votre rôle:", "§b§lSurvivant");
    			HologramManager holoManage = new HologramManager(pS);
    			holoManage.generateHolograms();
    			pS.playSound(pS.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
    	        pS.playSound(pS.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
    	        MessageUtils.messageStarting(pS, "survivor");
    		}
    		if (core.getKiller().contains(pS.getName()))
    		{
    			Data data = new Data(pS.getName());
    			String head = "";
    			StoreData sData1 = new StoreData(data.getSkin());
    			if (data.getSkin() == 0) { head = "ec778558b3e858a92e3a31971d95eb4316fb868982c0f380aaa38b690cc41ce8"; }
    			else { head = sData1.getArticleSkull(); }
    			InteractionGames.battementsDeCoeur(pS);
    			pS.getInventory().clear();
    			pS.setGameMode(GameMode.ADVENTURE);
    			pS.teleport(killerSpawn);
    			ItemStack skin = getSkull("http://textures.minecraft.net/texture/" + head);
    	        ItemMeta skinM = skin.getItemMeta();
    	        skinM.setDisplayName("§4Tueur " + pS.getName());
    	        skin.setItemMeta(skinM);
    			pS.getInventory().setHelmet(skin);
    			
    			StoreData sData2 = new StoreData(data.getWeapon());
    			core.weaponKillerID = data.getWeapon();
    			core.weaponKillerItem = sData2.getArticleItem();
    			core.weaponKillerItemName = sData2.getArticleName().replace("Arme:_", "").replace("&", "§").replace("_", " ");
    			if (core.weaponKillerID == 0)
    			{
    				core.weaponKillerItem = "iron_axe";
        			core.weaponKillerItemName = "§4Hache de Jason";
    			}
    			
    			ItemStack killerItem = ItemStackSerializer.deserialize(core.weaponKillerItem);
    	        ItemMeta killerItemM = killerItem.getItemMeta();
    	        killerItemM.setDisplayName(core.weaponKillerItemName);
    	        killerItem.setItemMeta(killerItemM);
    	        pS.getInventory().addItem(killerItem);
    	        pS.sendTitle("§7Votre rôle:", "§c§lTueur");
    	        HologramManager holoManage = new HologramManager(pS);
    			holoManage.generateHolograms();
    			pS.playSound(pS.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
     	        pS.playSound(pS.getLocation(), Sound.GHAST_SCREAM, 1.0f, 1.0f);
     	        MessageUtils.messageStarting(pS, "killer");
    		}
    	}
    }
    
	@SuppressWarnings("deprecation")
	public static void killPlayer(Player p)
    {
		Location spawn = new Location(Bukkit.getWorld("game"), 
				core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".specSpawn.x")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".specSpawn.y")
				, core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".specSpawn.z")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".specSpawn.f")
				, (float) core.getConfig().getDouble("Cartes." + core.getGameWorldName + ".specSpawn.t"));
    	if (core.getInPendul().contains(p.getName()))
    	{
    		core.getSurvivors().remove(p.getName());
    		core.getInPendul().remove(p.getName());
    		core.getPlayersName().remove(p.getName());
    		Player killer = Bukkit.getPlayer(core.getKiller().get(0));
    		core.addKills(killer, 1);
    		p.setFlySpeed((float) 0.1);
    		p.setGameMode(GameMode.SPECTATOR);
    		p.setAllowFlight(true);
    		p.setFlying(true);
    		ItemJoinListener.spectacleItem(p);
    		p.teleport(spawn);
    		InteractionGames.clearEffects(p);
    		Data data = new Data(p.getName());
			data.addSurvivorLose(1);
			data.addSurvivorReacteurs(core.getReacteurs(p));
			data.addSurvivorSauver(core.getSauver(p));
			data.addPlayedGames(1);
			p.sendTitle("§c§lPerdu...", "§7Peut-être une prochaine fois !");
    	}
    	if (core.getInGround().contains(p.getName()))
    	{
    		core.getSurvivors().remove(p.getName());
    		core.getInGround().remove(p.getName());
    		core.getPlayersName().remove(p.getName());
    		Player killer = Bukkit.getPlayer(core.getKiller().get(0));
    		core.addKills(killer, 1);
    		p.setFlySpeed((float) 0.1);
    		p.setGameMode(GameMode.SPECTATOR);
    		p.setAllowFlight(true);
    		p.setFlying(true);
    		ItemJoinListener.spectacleItem(p);
    		p.teleport(spawn);
    		InteractionGames.clearEffects(p);
    		Data data = new Data(p.getName());
			data.addSurvivorLose(1);
			data.addSurvivorReacteurs(core.getReacteurs(p));
			data.addSurvivorSauver(core.getSauver(p));
			data.addPlayedGames(1);
			p.sendTitle("§c§lPerdu...", "§7Peut-être une prochaine fois !");
    	}
    }
    
    
    
    @SuppressWarnings("deprecation")
	public static void endGame(Player p, String winorlose) {
    	
    	if (winorlose == "win")
    	{
    		PlayerInventory pi = p.getInventory();
            pi.setHelmet(null);
            pi.setChestplate(null);
            pi.setLeggings(null);
            pi.setBoots(null);
    		pi.clear();
    		
    		Location spawn = new Location(Bukkit.getWorld("hub"), 2.5, 31.0, 0.5, -90.5f, -4.0f);
    		p.setGameMode(GameMode.ADVENTURE);
    		p.teleport(spawn);
    		p.setAllowFlight(true);
    		p.setFlying(true);
    		ItemJoinListener.endItem(p);
    		p.sendTitle("§e§lVictoire !", "§7Vous avez remporté la partie !");
    		if (core.getSurvivors().contains(p.getName()))
    		{
    			Data data = new Data(p.getName());
    			data.addSurvivorVictoires(1);
    			data.addSurvivorReacteurs(core.getReacteurs(p));
    			data.addSurvivorSauver(core.getSauver(p));
    			data.addPlayedGames(1);
    		}
    		if (core.getKiller().contains(p.getName()))
    		{
    			Data data = new Data(p.getName());
    			data.addKillerVictoires(1);
    			data.addKillerFrappes(core.getFrappes(p));
    			data.addKillerKills(core.getKills(p));
    			data.addPlayedGames(1);
    		}
    		
    	}
    	if (winorlose == "egal")
    	{
    		PlayerInventory pi = p.getInventory();
            pi.setHelmet(null);
            pi.setChestplate(null);
            pi.setLeggings(null);
            pi.setBoots(null);
            pi.clear();
            
            Location spawn = new Location(Bukkit.getWorld("hub"), 2.5, 31.0, 0.5, -90.5f, -4.0f);
            p.setGameMode(GameMode.ADVENTURE);
    		p.teleport(spawn);
    		p.setAllowFlight(true);
    		p.setFlying(true);
    		ItemJoinListener.endItem(p);
    		p.sendTitle("§f§lÉgalité..", "§7Peut-être une prochaine fois !");
    		if (core.getSurvivors().contains(p.getName()))
    		{
    			Data data = new Data(p.getName());
    			data.addSurvivorReacteurs(core.getReacteurs(p));
    			data.addSurvivorSauver(core.getSauver(p));
    			data.addPlayedGames(1);
    		}
    		if (core.getKiller().contains(p.getName()))
    		{
    			Data data = new Data(p.getName());
    			data.addKillerFrappes(core.getFrappes(p));
    			data.addKillerKills(core.getKills(p));
    			data.addPlayedGames(1);
    		}
    	}
    	if (winorlose == "lose")
    	{
    		PlayerInventory pi = p.getInventory();
            pi.setHelmet(null);
            pi.setChestplate(null);
            pi.setLeggings(null);
            pi.setBoots(null);
            pi.clear();
            
            Location spawn = new Location(Bukkit.getWorld("hub"), 2.5, 31.0, 0.5, -90.5f, -4.0f);
            p.setGameMode(GameMode.ADVENTURE);
    		p.teleport(spawn);
    		p.setAllowFlight(true);
    		p.setFlying(true);
    		ItemJoinListener.endItem(p);
    		p.sendTitle("§c§lPerdu...", "§7Peut-être une prochaine fois !");
    		if (core.getSurvivors().contains(p.getName()))
    		{
    			Data data = new Data(p.getName());
    			data.addSurvivorLose(1);
    			data.addSurvivorReacteurs(core.getReacteurs(p));
    			data.addSurvivorSauver(core.getSauver(p));
    			data.addPlayedGames(1);
    		}
    		if (core.getKiller().contains(p.getName()))
    		{
    			Data data = new Data(p.getName());
    			data.addKillerLose(1);
    			data.addKillerFrappes(core.getFrappes(p));
    			data.addKillerKills(core.getKills(p));
    			data.addPlayedGames(1);
    		}
    	}
    }

	public static void quitWAITING(Player p) {
		
	} 
	
	public static void defineRole()
	{
        String killerName = core.getSurvivors().get(0);
        core.getSurvivors().remove(0);
        core.getKiller().add(killerName);
	}
	
	public static boolean checkWin()
	{
		
		return false;
	}
	
	public static boolean checkServersForStart()
	{
		String srvName = "DeadSurvivor";
		ChangeHubInfo servers = new ChangeHubInfo(srvName);
		for (String srvs : servers.getServer())
		{
			ChangeHubInfo finalservers = new ChangeHubInfo(srvs);
			if (finalservers.getServerMotd().equalsIgnoreCase("WAITING"))
			{
				return true;
			}
		}
		return false;
	}
    
}
