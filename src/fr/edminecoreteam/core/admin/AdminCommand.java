package fr.edminecoreteam.core.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.edminecoreteam.core.utils.RankInfo;

public class AdminCommand implements CommandExecutor
{
	
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("game")) {
            	if (hasPermission(player))
            	{
            		AdminMenu.gui(player);
            	}
            }
        }
        return false;
	}
	
	private static boolean hasPermission(Player p)
    {
    	RankInfo rankInfo = new RankInfo(p);
    	if (rankInfo.getRankModule() >= 15)
    	{
    		return true;
    	}
    	p.sendMessage("§cErreur, vous n'avez pas la permission...");
		return false;
    }
}
