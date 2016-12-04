package net.arcanemc.ninjas.inv;

import net.arcanemc.core.util.Color;
import net.arcanemc.ninjas.Main;
import net.arcanemc.ninjas.Util.Armor;
import net.arcanemc.ninjas.players.Ninja;
import net.arcanemc.ninjas.players.NinjaManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by eric on 12/2/16.
 */
public class InvMain {
    private static String armorInvTitle = "Pick Your Armor";
    private static String mainInvTitle = "Ninjas Main Menu";

    public static void displayArmor(Player tgt){
        Inventory inv = Bukkit.createInventory(null, 45, armorInvTitle);
        int index = 9;
        Ninja ninja = NinjaManager.getNinja(tgt);
        for(ItemStack item : Armor.getInstance().getArmor()){
            if(item.hasItemMeta()){
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Color.color(String.valueOf(ninja.isSlotBought(index)?"&aBOUGHT":"&6Price: "+Main.getConfiguration().getString("armor.price"))+String.valueOf(ninja.isSlotEquipped(index) /*&& ninja.isSlotBought(index)*/?" + &nEquipped":"")));
                item.setItemMeta(meta);
            }
            inv.setItem(index, item);
            index++;
        }

        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Color.color("&9< Back"));
        item.setItemMeta(meta);
        inv.setItem(0, item);
        item = new ItemStack(Material.SLIME_BALL, 1);
        meta = item.getItemMeta();
        meta.setDisplayName(Color.color("&a"+ninja.getBalance()+" Coins"));
        item.setItemMeta(meta);
        inv.setItem(4, item);
        inv.setItem(8, getItem(Material.BEACON, "&cHelp", Arrays.asList("Left Click to equip", "Right Click to buy")));

        tgt.openInventory(inv);
    }

    //   0    1   2   3   4   5   6   7    8
    //|Armor|   |   |   |   |   |   |   |Join|

    public static void dispMainMenu(Player tgt){
        Inventory inv = Bukkit.createInventory(null, 9, mainInvTitle);
        inv.setItem(0, getItem(Material.ARMOR_STAND, "&5Armor Shop", Arrays.asList("&7Buy Colored Armor")));
        inv.setItem(8, getItem(Material.DIAMOND_SWORD, "&a&lJoin Game", Arrays.asList("Left Click to join!")));

        tgt.openInventory(inv);
    }

    private static ItemStack getItem(Material material, String displayName, List<String> lore){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Color.color(displayName));
        List<String> l = new ArrayList<>();
        for(String line : lore){
            l.add(Color.color(line));
        }
        meta.setLore(l);
        item.setItemMeta(meta);
        return item;
    }

    public static String getArmorInvTitle(){return armorInvTitle;}

    public static String getMainInvTitle(){return mainInvTitle;}
}
