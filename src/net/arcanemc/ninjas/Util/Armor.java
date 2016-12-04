package net.arcanemc.ninjas.Util;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric on 12/2/16.
 */
public class Armor {
    private static Armor instance;
    private static List<ItemStack> armor;

    private Armor(){
        armor = new ArrayList<>();
    }

    private static void convertArmor(){
        List<Integer[]> values = Conf.getInstance().getArmorRGBValues();
        for(Integer[] code : values){
            if(code == null){
                armor.add(new ItemStack(Material.AIR));
                continue; // Move to next code
            }
            int ix = values.indexOf(code);
            if(ix >= 0 && ix <= 8){
                armor.add(getItem(code, Material.LEATHER_HELMET));
            }else if(ix >= 9 && ix <= 17){
                armor.add(getItem(code, Material.LEATHER_CHESTPLATE));
            }else if(ix >= 18 && ix <= 26){
                armor.add(getItem(code, Material.LEATHER_LEGGINGS));
            }else if(ix >= 27 && ix <= 35){
                armor.add(getItem(code, Material.LEATHER_BOOTS));
            }
        }
    }

    private static ItemStack getItem(Integer[] code, Material m){
        ItemStack item = new ItemStack(m, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
        meta.setColor(Color.fromRGB(code[0], code[1], code[2]));
        item.setItemMeta(meta);
        return item;
    }

    public static List<ItemStack> getArmor(){
        if(armor.isEmpty())
            convertArmor();
        return armor;
    }

    public static Armor getInstance(){
        if(instance == null)
            instance = new Armor();
        return instance;
    }
}
