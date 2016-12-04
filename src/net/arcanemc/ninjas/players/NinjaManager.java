package net.arcanemc.ninjas.players;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by eric on 11/25/16.
 */
public class NinjaManager {
    private static NinjaManager instance;
    static Map<UUID, Ninja> ninjas;

    private NinjaManager(){
        ninjas = new HashMap<>();
    }

    public static Ninja getNinja(Player tgt){
        return getNinja(tgt.getUniqueId());
    }
    public static Ninja getNinja(UUID uuid){
        if(!ninjas.containsKey(uuid)){
            Ninja ninja = new Ninja(uuid); // TEMP
            ninjas.put(uuid, ninja);
            return ninja;
        }else{
            return ninjas.get(uuid);
        }
    }

    public static NinjaManager getInstance(){
        if(instance == null){
            instance=new NinjaManager();
        }
        return instance;
    }
}
