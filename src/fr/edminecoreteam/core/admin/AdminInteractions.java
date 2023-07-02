package fr.edminecoreteam.core.admin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import fr.edminecoreteam.core.Core;
import fr.edminecoreteam.core.State;
import fr.edminecoreteam.core.listeners.Instance;
import fr.edminecoreteam.core.tasks.AutoStart;
import fr.edminecoreteam.core.utils.ChangeHubInfo;
import fr.edminecoreteam.core.utils.FoundLobby;


public class AdminInteractions implements Listener
{
	private static Core core = Core.getInstance();
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        //Player p = (Player)e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        ItemStack it = e.getCurrentItem();
        if (it == null) {  return; }
        if (inv.getName().equalsIgnoreCase("§8Game Manager")) 
        {
        	e.setCancelled(true);
        	if (it.getType() == Material.COMMAND_MINECART || it.getType() == Material.EXPLOSIVE_MINECART || it.getType() == Material.POWERED_MINECART || it.getType() == Material.INK_SACK)
        	{
        		if(it.getItemMeta().getDisplayName() == "§e§lForcer le démarrage")
        		{
        			if (core.isState(State.WAITING))
        			{
        				core.isForceStart = true;
        				AutoStart start = new AutoStart(core);
	                    start.runTaskTimer((Plugin)core, 0L, 20L);
	                    core.setState(State.STARTING);
	                    ChangeHubInfo srvInfo = new ChangeHubInfo(core.getServer().getServerName());
	                    srvInfo.setMOTD("STARTING");
	                    boolean checkSrv = Instance.checkServersForStart();
	                    if (checkSrv == false)
	                    {
	                    	srvInfo.setStatus(2);
	                    }
        			}
        		}
        		
        		if(it.getItemMeta().getDisplayName() == "§c§lArrêter le forcage")
        		{
        			if (core.isState(State.STARTING))
        			{
        				core.isForceStart = false;
	                    core.setState(State.WAITING);
	                    ChangeHubInfo srvInfo = new ChangeHubInfo(core.getServer().getServerName());
	                    srvInfo.setMOTD("WAITING");
        			}
        		}
        		
        		if(it.getItemMeta().getDisplayName() == "§6§lFinir la partie")
        		{
        			
        		}
        		
        		if(it.getItemMeta().getDisplayName() == "§c§lStop le serveur")
        		{
        			for(Player pls : Bukkit.getOnlinePlayers())
        			{
        				FoundLobby.foundLobby(pls);
        				core.getServer().dispatchCommand(core.getServer().getConsoleSender(), "stop");
        			}
        		}
        		
        	}
        }
    }
}
