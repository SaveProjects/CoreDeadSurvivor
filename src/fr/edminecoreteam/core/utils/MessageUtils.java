package fr.edminecoreteam.core.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.edminecoreteam.core.Core;

public class MessageUtils {
	
	private static Core core = Core.getInstance();
	
	public static void messageStarting(Player p, String isKiller) {
		
		if (isKiller.equalsIgnoreCase("killer"))
		{
			p.sendMessage("");
			p.sendMessage(" §7Bienvenue dans le §6§lDead Survivor §7!");
			p.sendMessage(" §c§lInformations:");
			p.sendMessage(" §7• §fVotre objectif est de");
			p.sendMessage(" §7  §ftuer tous les survivants !");
			p.sendMessage("");
			p.sendMessage(" §d§lAstuces:");
			p.sendMessage(" §7• §fTuez tous les joueurs que vous croisez");
			p.sendMessage(" §7  §fpour pouvoir gagner la paix. Ne les laissez pas");
			p.sendMessage(" §7  §fréparer tous les réacteurs, sinon la perte ");
			p.sendMessage(" §7  §fsera votre.");
			p.sendMessage(" §7• §fSi vous tuez un joueur, rester accroupi sur lui");
			p.sendMessage(" §7  §fafin de le récupérer sur vous et allez l'accrocher");
			p.sendMessage(" §7  §fà l'une des potences.");
			p.sendMessage("");
		}
		else if (isKiller.equalsIgnoreCase("survivor"))
		{
			p.sendMessage("");
			p.sendMessage(" §7Bienvenue dans le §6§lDead Survivor §7!");
			p.sendMessage(" §c§lInformations:");
			p.sendMessage(" §7• §fL'objectif est d'ouvrir");
			p.sendMessage(" §7  §fle portail et fuir !");
			p.sendMessage("");
			p.sendMessage(" §d§lAstuces:");
			p.sendMessage(" §7• §fAccroupissez-vous proche des réacteurs");
			p.sendMessage(" §7  §fafin de les réparer. Réparez-les tous");
			p.sendMessage(" §7  §fsi vous voulez sortir d'ici !");
			p.sendMessage(" §7• §fPour délivrer l'un de vos amis pendus,");
			p.sendMessage(" §7  §frester accroupi en dessous");
			p.sendMessage(" §7  §fde lui afin de le sauver.");
			p.sendMessage("");
		}
	}
	
	public static void deathMessage(Player victim)
	{
		String prefix = "§c§lTueur §8» ";
		Random rand = new Random();
        int min = 1;
        int max = 5;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        if (randomNum == 1)
        {
        	core.getServer().broadcastMessage(prefix + "§c§k" + victim.getName() + "§r §fs'est fait trancher la gorge par le tueur.");
        	for (Player p : Bukkit.getOnlinePlayers())
        	{
        		p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 2)
        {
        	core.getServer().broadcastMessage(prefix + "§fÀ coup de machette, le tueur vient d'éliminé §c§k" + victim.getName() + "§r§f.");
        	for (Player p : Bukkit.getOnlinePlayers())
        	{
        		p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 3)
        {
        	core.getServer().broadcastMessage(prefix + "§fCette nuit, nous avons perdu §c§k" + victim.getName() + "§r§f.");
        	for (Player p : Bukkit.getOnlinePlayers())
        	{
        		p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 4)
        {
        	core.getServer().broadcastMessage(prefix + "§fSurvivant §c§k" + victim.getName() + "§r §fn'est plus de ce monde.");
        	for (Player p : Bukkit.getOnlinePlayers())
        	{
        		p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 5)
        {
        	core.getServer().broadcastMessage(prefix + "§c§k" + victim.getName() + "§r §fn'a pas survécu à la potence.");
        	for (Player p : Bukkit.getOnlinePlayers())
        	{
        		p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
        	}
        }
	}
	
	public static void startRepear(Player player, int reactor)
	{
		String prefix = "§b§lSurvivants §8» ";
		Random rand = new Random();
        int min = 1;
        int max = 4;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        if (randomNum == 1)
        {
        	for (String pS : core.getSurvivors())
        	{
        		Player p = Bukkit.getPlayer(pS);
        		p.sendMessage(prefix + "§a§k" + player.getName() + "§r §frépare le réacteur §b" + reactor + "§f.");
        		p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 2)
        {
        	for (String pS : core.getSurvivors())
        	{
        		Player p = Bukkit.getPlayer(pS);
        		p.sendMessage(prefix + "§fLe réacteur §b" + reactor + " §fest en cours de réparation par le technicien §a§k" + player.getName() + "§r§f.");
        		p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 3)
        {
        	for (String pS : core.getSurvivors())
        	{
        		Player p = Bukkit.getPlayer(pS);
        		p.sendMessage(prefix + "§a§k" + player.getName() + " §ftape à coup de clé à molette sur le réacteur §b" + reactor + "§f.");
        		p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 4)
        {
        	for (String pS : core.getSurvivors())
        	{
        		Player p = Bukkit.getPlayer(pS);
        		p.sendMessage(prefix + "§fL'intervention de " + "§a§k" + player.getName() + "§r §fpermet au réacteur §b" + reactor + " §fde se faire réparer.");
        		p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        	}
        }
	}
	
	public static void finishRepear(Player player, int reactor)
	{
		String prefix = "§b§lSurvivants §8» ";
		Random rand = new Random();
        int min = 1;
        int max = 5;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        if (randomNum == 1)
        {
        	for (Player pS : Bukkit.getOnlinePlayers())
        	{
        		pS.sendMessage(prefix + "§FLe réacteur §b" + reactor + " §fa été réparé par le savant " + "§a§k" + player.getName() + "§r§f.");
        		pS.playSound(pS.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 2)
        {
        	for (Player pS : Bukkit.getOnlinePlayers())
        	{
        		pS.sendMessage(prefix + "§a§k" + player.getName() + "§r §fa évité l'explosion du réacteur §b" + reactor + " §fen le réparant !");
        		pS.playSound(pS.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 3)
        {
        	for (Player pS : Bukkit.getOnlinePlayers())
        	{
        		pS.sendMessage(prefix + "§fMerci à " + "§a§k" + player.getName() + "§r §fqui vient de nous sauver de la destruction du réacteur §b" + reactor + "§f.");
        		pS.playSound(pS.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 4)
        {
        	for (Player pS : Bukkit.getOnlinePlayers())
        	{
        		pS.sendMessage(prefix + "§a§k" + player.getName() + "§r §fa entré le bon code pour désamorcé la destruction du réacteur §b" + reactor + "§f.");
        		pS.playSound(pS.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        	}
        }
        if (randomNum == 5)
        {
        	for (Player pS : Bukkit.getOnlinePlayers())
        	{
        		pS.sendMessage(prefix + "§fAvec toutes ses connaissances, " + "§a§k" + player.getName() + "§r §fa réparé le réacteur §b" + reactor + "§f.");
        		pS.playSound(pS.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        	}
        }
	}
	
	public static void messageEnd(Player p) {
		
		p.sendMessage("");
		p.sendMessage("  §d§lCompte rendu:");
		p.sendMessage("   §7• §7Vitoire: ");
		p.sendMessage("");
		p.sendMessage(" §8➡ §fVisionnez vos statistiques sur votre profil.");
		p.sendMessage("");
	}
	

}
