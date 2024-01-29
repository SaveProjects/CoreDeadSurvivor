// 
// Decompiled by Procyon v0.5.36
// 

package fr.edminecoreteam.core.listeners;

import org.bukkit.inventory.meta.ItemMeta;

import fr.edminecoreteam.core.utils.SkullNBT;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.Listener;

public class ItemJoinListener implements Listener
{
	private static ItemStack getSkull(String url) {
		return SkullNBT.getSkull(url);
	}
	
    public static void joinItem(Player p) {
        /*ItemStack team = getSkull("http://textures.minecraft.net/texture/ec778558b3e858a92e3a31971d95eb4316fb868982c0f380aaa38b690cc41ce8");
        ItemMeta teamM = team.getItemMeta();
        teamM.setDisplayName("§f§lChoix du tueur §7• Clique");
        team.setItemMeta(teamM);
        p.getInventory().setItem(0, team);
        
        ItemStack weapon = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta weaponM = weapon.getItemMeta();
        weaponM.setDisplayName("§f§lChoix de l'arme §7• Clique");
        weapon.setItemMeta(weaponM);
        p.getInventory().setItem(1, weapon);*/
        
        ItemStack leave = new ItemStack(Material.BED, 1);
        ItemMeta leaveM = leave.getItemMeta();
        leaveM.setDisplayName("§c§lQuitter §7• Clique");
        leave.setItemMeta(leaveM);
        p.getInventory().setItem(8, leave);
    }
    
    public static void spectacleItem(Player p) {
        ItemStack map = new ItemStack(Material.COMPASS, 1);
        ItemMeta mapM = map.getItemMeta();
        mapM.setDisplayName("§a§lJoueurs §7• Clique");
        map.setItemMeta(mapM);
        p.getInventory().setItem(0, map);
        
        ItemStack replay = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta replayM = replay.getItemMeta();
        replayM.setDisplayName("§d§lRejouer §7• Clique");
        replay.setItemMeta(replayM);
        p.getInventory().setItem(4, replay);
        
        ItemStack leave = new ItemStack(Material.BED, 1);
        ItemMeta leaveM = leave.getItemMeta();
        leaveM.setDisplayName("§c§lQuitter §7• Clique");
        leave.setItemMeta(leaveM);
        p.getInventory().setItem(8, leave);
    }
    
    public static void endItem(Player p) {
    	ItemStack replay = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta replayM = replay.getItemMeta();
        replayM.setDisplayName("§d§lRejouer §7• Clique");
        replay.setItemMeta(replayM);
        p.getInventory().setItem(0, replay);
    	
        ItemStack leave = new ItemStack(Material.BED, 1);
        ItemMeta leaveM = leave.getItemMeta();
        leaveM.setDisplayName("§c§lQuitter §7• Clique");
        leave.setItemMeta(leaveM);
        p.getInventory().setItem(8, leave);
    }
}
