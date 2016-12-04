package net.arcanemc.ninjas.Util;

import net.arcanemc.ninjas.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric on 12/2/16.
 */
public class Conf {
    private static Conf instance;
    private static FileConfiguration config;
    private static List<Integer[]> rgb;
    private Conf(){
        config = Main.getConfiguration();
        rgb = new ArrayList<>();
    }

    private static void loadArmorList(){
        for(String key : config.getConfigurationSection("armor").getKeys(true)){
            for(String code : config.getStringList("armor."+key)){
                rgb.add(split(code));
            }
        }
    }

    private static Integer[] split(String coded){
        if(coded.equals("")){
            return null;
        }
        String[] split = coded.split(",");
        List<Integer> dec = new ArrayList<>();
        for(String num : split){
            try {
                dec.add(Integer.parseInt(num));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Non-numerical value: Ninjas armor RGB codes");
            }
        }
        Integer[] values = new Integer[dec.size()];
        dec.toArray(values);
        return values;
    }

    public static List<Integer[]> getArmorRGBValues(){
        if(rgb.size() == 0)
            loadArmorList();
        return rgb;
    }

    public static Conf getInstance(){
        if(instance == null)
            instance = new Conf();
        return instance;
    }
}
