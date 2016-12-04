package net.arcanemc.ninjas.players;

import net.arcanemc.ninjas.Util.Armor;
import net.arcanemc.ninjas.db.DBMain;
import net.arcanemc.ninjas.game.Game;
import net.arcanemc.ninjas.inv.InvMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by eric on 11/25/16.
 */
public class Ninja {
    private UUID playerUUID;
    private int kills;
    private int deaths;
    private List<Integer> boughtArmor, equippedArmor;
    private Optional<Game> currentGame;
    private boolean frozen;
    private int balance;

    public Ninja(UUID uuid){
        this.playerUUID = uuid;
        Map<String, Object> data = DBMain.getInstance().getPlayerData(uuid);
        kills = (int)data.get("kills");
        deaths = (int)data.get("deaths");
        boughtArmor = (List<Integer>)data.get("boughtArmor");
        equippedArmor = (List<Integer>)data.get("equippedArmor");
        balance = (int)data.get("balance");
        currentGame = null;
        frozen = false;
    }

    public Optional<Player> getPlayer(){
        try{
            return Optional.of(Bukkit.getServer().getPlayer(playerUUID));
        }catch(NullPointerException e){}
        return null;
    }

    public UUID getUUID(){return playerUUID;}

    public Optional<Game> getCurrentGame(){return currentGame;}

    public int getKills(){return kills;}

    public int getDeaths(){return deaths;}

    public int getBalance(){return balance;}

    public List<Integer> getBoughtArmor(){return boughtArmor;}

    public List<Integer> getEquippedArmor(){return equippedArmor;}

    public String getFormattedBoughtArmor(){
        StringBuilder sb = new StringBuilder();
        for(int num : boughtArmor){
            if(boughtArmor.indexOf(num) == boughtArmor.size()-1){
                sb.append(num);
            }else{
                sb.append(num+",");
            }
        }
        return sb.toString();
    }

    public String getFormattedEquippedArmor(){
        StringBuilder sb = new StringBuilder();
        if(equippedArmor == null) return "";
        for(int num : equippedArmor){
            if(equippedArmor.indexOf(num) == equippedArmor.size()-1){
                sb.append(num);
            }else{
                sb.append(num+",");
            }
        }
        return sb.toString();
    }

    public boolean isFrozen(){return frozen;}

    public boolean isInGame(){return currentGame != null;}

    public boolean isSlotBought(int slot){
        return boughtArmor.contains(slot);
    }

    public boolean isSlotEquipped(int slot){
        return equippedArmor.contains(slot);
    }


    public void setFrozen(boolean b){
        frozen = b;
    }

    public void setGame(Game game){
        currentGame = Optional.of(game);
    }

    public void addBoughtArmorSlot(int slot){
        boughtArmor.add(slot);
        Optional<Player> p = getPlayer();
        if(p.isPresent()){
            if(p.get().getOpenInventory().getTitle().equals(InvMain.getArmorInvTitle())){
                InvMain.displayArmor(p.get());
            }
        }
        DBMain.pushPlayerData(this);
    }

    public boolean buy(int price){
        if(balance >= price){
            balance -= price;
            return true;
        }
        return false;
    }

    public void equipArmor(int slot){
        if(!isSlotBought(slot)) return;
        if(slot >= 0 && slot <=8){
            new CopyOnWriteArrayList<>(equippedArmor).stream().filter(num -> num >= 0 && num <= 8).forEach(equippedArmor::remove);
        }else if(slot >= 9 && slot <= 17){
            new CopyOnWriteArrayList<>(equippedArmor).stream().filter(num -> num >= 9 && num <= 17).forEach(equippedArmor::remove);
        }else if(slot >= 18 && slot <= 26){
            new CopyOnWriteArrayList<>(equippedArmor).stream().filter(num -> num >= 18 && num <= 26).forEach(equippedArmor::remove);
        }else if(slot >= 27 && slot <= 35){
            new CopyOnWriteArrayList<>(equippedArmor).stream().filter(num -> num >= 27 && num <= 35).forEach(equippedArmor::remove);
        }
        equippedArmor.add(slot);
        if(getPlayer().isPresent())
        InvMain.displayArmor(getPlayer().get());
    }

    public void setGameNull(){ // Basically, when the player leaves a game / is kicked
        currentGame = null;
        DBMain.getInstance().pushPlayerData(this);
    }
}
