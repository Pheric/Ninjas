package net.arcanemc.ninjas.events.listeners;

import net.arcanemc.core.util.Color;
import net.arcanemc.ninjas.Main;
import net.arcanemc.ninjas.game.GameManager;
import net.arcanemc.ninjas.inv.InvMain;
import net.arcanemc.ninjas.players.Ninja;
import net.arcanemc.ninjas.players.NinjaManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by eric on 12/3/16.
 */
public class InvListeners implements Listener {
    public InvListeners(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void pickup(InventoryClickEvent event){
        if(event.getClickedInventory() != null){
            if(event.isRightClick()){
                if(event.getClickedInventory().getTitle().equals(InvMain.getArmorInvTitle())){
                    event.setCancelled(true);
                    Ninja ninja = NinjaManager.getNinja((Player)event.getWhoClicked());
                    if(!ninja.isSlotBought(event.getSlot())){
                        if(ninja.buy(Main.getConfiguration().getInt("armor.price"))){
                            ninja.addBoughtArmorSlot(event.getRawSlot());
                        }
                    }
                }else if(event.getClickedInventory().getTitle().equals(InvMain.getMainInvTitle())){
                    event.setCancelled(true);
                }

            }else if(event.isLeftClick()){
                if(event.getClickedInventory().getTitle().equals(InvMain.getArmorInvTitle())){
                    event.setCancelled(true);
                    if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null){
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equals(Color.color("&9< Back"))){
                            InvMain.dispMainMenu((Player)event.getWhoClicked());
                        }
                    }
                    Ninja ninja = NinjaManager.getNinja((Player)event.getWhoClicked());
                    ninja.equipArmor(event.getSlot());
                }else if(event.getClickedInventory().getTitle().equals(InvMain.getMainInvTitle())){
                    event.setCancelled(true);
                    if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null){
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equals(Color.color("&5Armor Shop"))){
                            InvMain.displayArmor((Player)event.getWhoClicked());
                        }else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(Color.color("&a&lJoin Game"))){
                            GameManager.joinGame((event.getWhoClicked()).getUniqueId());
                        }
                    }
                }
            }
        }
    }
}
