package net.arcanemc.ninjas.events.listeners;

import net.arcanemc.ninjas.Main;
import net.arcanemc.ninjas.players.NinjaManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by eric on 12/3/16.
 */
public class Drops implements Listener {
    public Drops(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent event){
        if(NinjaManager.getNinja(event.getPlayer()).isInGame()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void pickupItem(PlayerPickupItemEvent event){
        if(NinjaManager.getNinja(event.getPlayer()).isInGame()){
            event.setCancelled(true);
        }
    }
}
