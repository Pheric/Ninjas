package net.arcanemc.ninjas.events.listeners;

import net.arcanemc.ninjas.Main;
import net.arcanemc.ninjas.players.NinjaManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

/**
 * Created by eric on 12/3/16.
 */
public class DoubleJump implements Listener { // FIXME
    public DoubleJump(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void fly(PlayerToggleFlightEvent event){
        if(!event.getPlayer().isOp() && event.getPlayer().getGameMode() == GameMode.SURVIVAL && NinjaManager.getNinja(event.getPlayer()).isInGame()){
            event.setCancelled(true);
            event.getPlayer().setAllowFlight(false);
            event.getPlayer().setFlying(false);
            event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(2).setY(1));
        }
    }

    @EventHandler
    public void move(PlayerMoveEvent event){
        if(!event.getPlayer().isOp() && event.getPlayer().getGameMode() == GameMode.SURVIVAL && NinjaManager.getNinja(event.getPlayer()).isInGame() && event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR && !event.getPlayer().isFlying()){
            event.getPlayer().setAllowFlight(true);
        }
    }
}
