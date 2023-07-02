package fr.edminecoreteam.core.scoreboard;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import fr.edminecoreteam.core.Core;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;

public class JoinScoreboardEvent implements Listener
{
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Core.getInstance().getScoreboardManager().onLogin(p);
    }
}
