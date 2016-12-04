package net.arcanemc.ninjas.events.listeners;

import net.arcanemc.ninjas.Main;
import net.arcanemc.ninjas.players.NinjaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by eric on 11/25/16.
 */
public class Movement implements Listener{
    public Movement(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler // CREDIT TO Oberdiah FOR THIS CODE: http://imgur.com/a/k2d6i
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        if(NinjaManager.getNinja(event.getPlayer().getUniqueId()).isFrozen()){
            if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {
                Location loc = event.getFrom();
                event.getPlayer().teleport(loc.setDirection(event.getTo().getDirection()));
            }
        }
    }
}
