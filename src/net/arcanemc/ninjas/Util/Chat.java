package net.arcanemc.ninjas.Util;

import net.arcanemc.core.util.Color;
import net.arcanemc.ninjas.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by eric on 11/25/16.
 */
public class Chat {
    public static void sendMessage(UUID uuid, String...msg){
        for(String line : msg){
            if(line.contains("--noprefix")){
                line = line.replace("--noprefix", "");
            }else{
                line = Main.PREFIX+line;
            }
            try{
                Bukkit.getPlayer(uuid).sendMessage(Color.color(line));
            }catch(NullPointerException e){}
        }
    }
    public static void sendMessage(CommandSender sender, String...msg){
        sendMessage(((Player)sender).getUniqueId(), msg);
    }
}