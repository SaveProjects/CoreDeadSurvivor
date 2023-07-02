// 
// Decompiled by Procyon v0.5.36
// 

package fr.edminecoreteam.core.listeners;

import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import fr.edminecoreteam.core.State;
import fr.edminecoreteam.core.gui.ChooseSkin;
import fr.edminecoreteam.core.gui.ChooseWeapon;
import fr.edminecoreteam.core.purchase.PageData;
import fr.edminecoreteam.core.utils.FoundLobby;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.edminecoreteam.core.Core;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class EventsListener implements Listener
{
    private Core core;
    public EventsListener(Core core) {
        this.core = core;
    }
    
    @EventHandler
    private void onDamage(EntityDamageEvent e) {
    	
    	if (e.getEntityType() != EntityType.PLAYER) { return; }
	    	if(e.getEntity() instanceof Player) {
	    		Player p = (Player)e.getEntity();
	            if (core.isState(State.WAITING) || core.isState(State.STARTING) || core.isState(State.FINISH)) {
	                e.setCancelled(true);
	                if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
	                    Location spawn = new Location(Bukkit.getWorld("hub"), 2.5, 31.0, 0.5, -90.5f, -4.0f);
	                    p.teleport(spawn);
	                }
	            }
	    	}
    }
    
    @EventHandler
    private void onDrop(PlayerDropItemEvent e) {
        if (core.isState(State.WAITING) || core.isState(State.STARTING) || core.isState(State.FINISH)) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    private void onHungerBarChange(FoodLevelChangeEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }
        if (core.isState(State.WAITING) || core.isState(State.STARTING) || core.isState(State.INGAME) || core.isState(State.FINISH)) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    private void onWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        ItemStack it = e.getCurrentItem();
        if (it == null) {
            return;
        }
        if (core.isState(State.INGAME))
        {
        	e.setCancelled(true);
        }
        if (it.getType() == Material.BED && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter §7• Clique")) {
            e.setCancelled(true);
            p.performCommand("hub");
        }
        if (it.getType() == Material.NETHER_STAR && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d§lRejouer §7• Clique")) {
            e.setCancelled(true);
            FoundLobby.foundGame(p);
        }
        if (it.getType() == Material.SKULL_ITEM && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f§lChoix du tueur §7• Clique")) {
            e.setCancelled(true);
            PageData pData = new PageData(p.getName());
            ChooseSkin.gui(p, 1, pData.getPageNumber("deadsurvivorskins"));
        }
        if (it.getType() == Material.IRON_AXE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f§lChoix de l'arme §7• Clique")) {
            e.setCancelled(true);
            PageData pData = new PageData(p.getName());
            ChooseWeapon.gui(p, 1, pData.getPageNumber("deadsurvivorweapons"));
        }
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack it = e.getItem();
        if (it == null) {
            return;
        }
        if (it.getType() == Material.BED && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
            e.setCancelled(true);
            p.performCommand("hub");
        }
        if (it.getType() == Material.NETHER_STAR && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§d§lRejouer §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
            e.setCancelled(true);
            FoundLobby.foundGame(p);
        }
        if (it.getType() == Material.SKULL_ITEM && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§f§lChoix du tueur §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
            e.setCancelled(true);
            PageData pData = new PageData(p.getName());
            ChooseSkin.gui(p, 1, pData.getPageNumber("deadsurvivorskins"));
        }
        if (it.getType() == Material.IRON_AXE && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§f§lChoix de l'arme §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
            e.setCancelled(true);
            PageData pData = new PageData(p.getName());
            ChooseWeapon.gui(p, 1, pData.getPageNumber("deadsurvivorweapons"));
        }
    }
    
    
}
