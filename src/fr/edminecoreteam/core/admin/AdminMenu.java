package fr.edminecoreteam.core.admin;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.State;


public class AdminMenu implements Listener
{
	private static Core api = Core.getInstance();
	//private static ItemStack getSkull(String url) { return SkullNBT.getSkull(url); }
	
	public static void gui(Player p) {
		
		Inventory inv = Bukkit.createInventory(null, 54, "§8Game Manager");
		p.openInventory(inv);
            
            new BukkitRunnable() {
                int t = 0;   
    	        public void run() {
    	        	
    	        	if (!p.getOpenInventory().getTitle().equalsIgnoreCase("§8Game Manager")) { cancel(); }
    	        	
    	        	ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
		            ItemMeta decoM = deco.getItemMeta();
		            decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		            decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
		            decoM.setDisplayName("§r");
		            deco.setItemMeta(decoM);
		            inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
		            inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);
		            
		            
                    ++t;
                    if (t == 5) {
                    	t = 0;
                        run();
                    }
                }
            }.runTaskTimer((Plugin)api, 0L, 15L);
            
            
            new BukkitRunnable() {
                int t = 0;   
    	        public void run() {
    	        	
    	        	if (!p.getOpenInventory().getTitle().equalsIgnoreCase("§8Game Manager")) { cancel(); }
    	        	
    	        	if (api.isState(State.WAITING))
    	        	{
    	        		ItemStack start = new ItemStack(Material.COMMAND_MINECART, 1);
        	            ItemMeta startM = start.getItemMeta();
        	            startM.setDisplayName("§e§lForcer le démarrage");
        	            ArrayList<String> lorestart = new ArrayList<String>();
        	            lorestart.add("");
        	            lorestart.add(" §aDescription:");
        	            lorestart.add(" §f▶ §7La partie est actuellement");
        	            lorestart.add(" §f  §7en attente de joueurs...");
        	            lorestart.add("");
        	            lorestart.add("§8➡ §fCliquez pour démarrer.");
        	            startM.setLore(lorestart);
        	            start.setItemMeta(startM);
        	            inv.setItem(21, start);
    	        	}
    	        	if (api.isState(State.STARTING))
    	        	{
    	        		ItemStack start = new ItemStack(Material.EXPLOSIVE_MINECART, 1);
        	            ItemMeta startM = start.getItemMeta();
        	            startM.setDisplayName("§c§lArrêter le forcage");
        	            ArrayList<String> lorestart = new ArrayList<String>();
        	            lorestart.add("");
        	            lorestart.add(" §aDescription:");
        	            lorestart.add(" §f▶ §7La partie est actuellement");
        	            lorestart.add(" §f  §7en démarrage...");
        	            lorestart.add("");
        	            lorestart.add("§8➡ §fCliquez pour arrêter.");
        	            startM.setLore(lorestart);
        	            start.setItemMeta(startM);
        	            inv.setItem(21, start);
    	        	}
    	        	if (api.isState(State.INGAME))
    	        	{
    	        		ItemStack start = new ItemStack(Material.POWERED_MINECART, 1);
        	            ItemMeta startM = start.getItemMeta();
        	            startM.setDisplayName("§6§lFinir la partie");
        	            ArrayList<String> lorestart = new ArrayList<String>();
        	            lorestart.add("");
        	            lorestart.add(" §aDescription:");
        	            lorestart.add(" §f▶ §7La partie est actuellement");
        	            lorestart.add(" §f  §7en jeu.");
        	            lorestart.add("");
        	            lorestart.add(" §dInformations:");
        	            lorestart.add(" §f▶ §7Temps restant: §a" + api.timerGame + "§as");
        	            lorestart.add("");
        	            lorestart.add("§8➡ §fCliquez pour finir.");
        	            startM.setLore(lorestart);
        	            start.setItemMeta(startM);
        	            inv.setItem(21, start);
    	        	}
    	        	
    	        	ItemStack stop = new ItemStack(Material.INK_SACK, 1, (short)1);
    	            ItemMeta stopM = stop.getItemMeta();
    	            stopM.setDisplayName("§c§lStop le serveur");
    	            ArrayList<String> lorestop = new ArrayList<String>();
    	            lorestop.add("");
    	            lorestop.add("§8➡ §fCliquez pour arrêter.");
    	            stopM.setLore(lorestop);
    	            stop.setItemMeta(stopM);
    	            inv.setItem(23, stop);
    	            
                    ++t;
                    if (t == 100) {
                    	t = 0;
                        run();
                    }
                }
            }.runTaskTimer((Plugin)api, 0L, 15L);
	}
}
