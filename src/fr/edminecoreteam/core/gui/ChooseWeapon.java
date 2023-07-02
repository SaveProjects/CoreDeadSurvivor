package fr.edminecoreteam.core.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.data.Data;
import fr.edminecoreteam.core.purchase.PageData;
import fr.edminecoreteam.core.purchase.StoreData;
import fr.edminecoreteam.core.utils.ItemStackSerializer;
import fr.edminecoreteam.core.utils.SkullNBT;

public class ChooseWeapon implements Listener
{
	private static Core core = Core.getInstance();
	
	private static ItemStack getSkull(String url) {
		return SkullNBT.getSkull(url);
	}
	
	@EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        ItemStack it = e.getCurrentItem();
        if (it == null) {
            return;
        }
        if (it.getType() == Material.IRON_AXE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f§lChoix de l'arme §7• Clique")) { e.setCancelled(true); }
        if (e.getView().getTopInventory().getTitle().equals("§8Choix de l'arme")) {
        	if (it.getType() == Material.STAINED_GLASS_PANE) { e.setCancelled(true); }
        	e.setCancelled(true);
        	StoreData dataStore = new StoreData(it.getItemMeta().getDisplayName());
        	int ID = dataStore.getArticleIDByName();
        	Data data = new Data(p.getName());
        	if (ID != 0)
        	{
        		if(it.getItemMeta().getDisplayName() == dataStore.name())
        		{
            		data.setWeapon(ID);
            		p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
            		p.sendMessage("§7Vous avez défini votre arme sur §a" + it.getItemMeta().getDisplayName() + "§7.");
        		}
        	}
        	if(it.getItemMeta().getDisplayName() == "§fPar défaut")
    		{
        		data.setSkin(0);
        		p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
        		p.sendMessage("§7Vous avez défini votre arme sur §adéfaut§7.");
    		}
        	if(it.getItemMeta().getDisplayName() == "§8➡ §7Page Suivante")
    		{
        		p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
        		int page = core.getPage(p) + 1;
        		core.removePage(p);
        		core.addPage(p, page);
        		PageData dataPage = new PageData(p.getName());
        		gui(p, page, dataPage.getPageNumber("deadsurvivorweapons"));
				
    		}
        	if(it.getItemMeta().getDisplayName() == "§8⬅ §7Page Précédente")
    		{
        		p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
        		int page = core.getPage(p) - 1;
        		core.removePage(p);
        		core.addPage(p, page);
				PageData dataPage = new PageData(p.getName());
				gui(p, page, dataPage.getPageNumber("deadsurvivorweapons"));
				
    		}
        	
        }
    }
	
	public static void gui(Player p, int Page, int MaxPage) {
		
		Inventory inv = Bukkit.createInventory(null, 54, "§8Choix de l'arme");
		int blocsPerPage = 10;
        
        ItemStack randomTeam = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta randomTeamM = randomTeam.getItemMeta();
        randomTeamM.setDisplayName("§fPar défaut");
        ArrayList<String> loreRandom = new ArrayList<String>();
        loreRandom.add("");
        loreRandom.add(" §dInformation:");
        loreRandom.add(" §f▶ §7Définisez ce skin par");
        loreRandom.add(" §f▶ §7défaut dans vos parties.");
        loreRandom.add("");
        loreRandom.add("§8➡ §fCliquez pour y accéder.");
        randomTeamM.setLore(loreRandom);
        randomTeam.setItemMeta(randomTeamM);
        inv.setItem(4, randomTeam);
        
        if (Page != MaxPage)
        {
        	if (Page == 1)
        	{
        		ItemStack suivant = getSkull("http://textures.minecraft.net/texture/956a3618459e43b287b22b7e235ec699594546c6fcd6dc84bfca4cf30ab9311");
                ItemMeta suivantM = suivant.getItemMeta();
                suivantM.setDisplayName("§8➡ §7Page Suivante");
                suivant.setItemMeta(suivantM);
                inv.setItem(50, suivant);
        	}
        	if (Page != 1)
        	{
        		ItemStack suivant = getSkull("http://textures.minecraft.net/texture/956a3618459e43b287b22b7e235ec699594546c6fcd6dc84bfca4cf30ab9311");
                ItemMeta suivantM = suivant.getItemMeta();
                suivantM.setDisplayName("§8➡ §7Page Suivante");
                suivant.setItemMeta(suivantM);
                inv.setItem(50, suivant);
                
                ItemStack precedent = getSkull("http://textures.minecraft.net/texture/cdc9e4dcfa4221a1fadc1b5b2b11d8beeb57879af1c42362142bae1edd5");
                SkullMeta precedentM = (SkullMeta)precedent.getItemMeta();
                precedentM.setDisplayName("§8⬅ §7Page Précédente");
                precedent.setItemMeta((ItemMeta)precedentM);
                inv.setItem(48, precedent);
        	}
        }
        else if (Page == MaxPage)
        {
        	if (MaxPage == 1)
        	{
        		//Rien
        	}
        	else if (MaxPage > 1)
        	{
        		ItemStack precedent = getSkull("http://textures.minecraft.net/texture/cdc9e4dcfa4221a1fadc1b5b2b11d8beeb57879af1c42362142bae1edd5");
                SkullMeta precedentM = (SkullMeta)precedent.getItemMeta();
                precedentM.setDisplayName("§8⬅ §7Page Précédente");
                precedent.setItemMeta((ItemMeta)precedentM);
                inv.setItem(48, precedent);
        	}
        }
		
        ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0);
        ItemMeta decoM = deco.getItemMeta();
        decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        decoM.setDisplayName("§r");
        deco.setItemMeta(decoM);
        inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
        inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);
        
        ItemStack blocs = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta blocsM = blocs.getItemMeta();
        blocsM.setDisplayName("§a§lVos armes");
        ArrayList<String> loreblocs = new ArrayList<String>();
        loreblocs.add("");
        loreblocs.add(" §dInformation:");
        loreblocs.add(" §f▶ §7Ici, visionnez vos articles");
        loreblocs.add(" §f▶ §7débloqués et utilisez-les !");
        loreblocs.add("");
        blocsM.setLore(loreblocs);
        blocs.setItemMeta(blocsM);
        inv.setItem(19, blocs);
        
        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.HORSE_ARMOR, 1.0f, 1.0f);
        
        PageData pData = new PageData(p.getName());
        Data data = new Data(p.getName());
        new BukkitRunnable() {
            int t = 0;   
	        public void run() {
	        	
	        	if (!p.getOpenInventory().getTitle().contains("§8Choix de l'arme")) { cancel(); }
	        	int slot = 20;
	        	int blocsCount = 0;
	        	for (int blocs : pData.getForPage(Page, "deadsurvivorweapons")) {
	            	++blocsCount;
	            	StoreData sData = new StoreData(blocs);
	            	ItemStack friend = null;
                    ItemMeta friendM = null;
	            	if (!sData.getArticleItem().equalsIgnoreCase("skull"))
        			{
        				friend = ItemStackSerializer.deserialize(sData.getArticleItem());
        				friendM = friend.getItemMeta();
        			}
        			else
        			{
        				String head = sData.getArticleSkull();
        				friend = getSkull("http://textures.minecraft.net/texture/" + head);
        				friendM = friend.getItemMeta();
        			}
	                friendM.setDisplayName(sData.getArticleName().replace("&", "§").replace("_", " "));
	                ArrayList<String> lorefriend = new ArrayList<String>();
	                lorefriend.add("");
	                lorefriend.add(" §dInformation:");
	                lorefriend.add(" §f▶ §7Définisez cette arme par");
	                lorefriend.add(" §f▶ §7défaut dans vos parties.");
	                lorefriend.add("");
	                if (data.getWeapon() == blocs)
	                {
	                	lorefriend.add("§8➡ §aArme sélectionné.");
	                	friendM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
	                	friendM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
	                }
	                else
	                {
	                	lorefriend.add("§8➡ §fCliquez pour sélectionner.");
	                }
	                friendM.setLore(lorefriend);
	                friend.setItemMeta(friendM);
	                
	                
	                if (blocsCount == blocsPerPage)
	                {
	                	inv.setItem(slot, friend);
	                }
	                else if (blocsCount != blocsPerPage)
	                {
	                	inv.setItem(slot, friend);
	                }
	                if (slot == 24) {
	                	slot += 4;
	                }
	                if (slot != 25 || slot != 33  && slot < 33) {
	                	slot += 1;
	                }
	                if (slot == 34) {
	                	return;
	                }
	            }
                ++t;
                if (t == 10) {
                    run();
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 10L);
    }
}
