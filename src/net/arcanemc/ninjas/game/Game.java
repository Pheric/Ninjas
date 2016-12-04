package net.arcanemc.ninjas.game;

import net.arcanemc.ninjas.Main;
import net.arcanemc.ninjas.players.Ninja;
import net.arcanemc.ninjas.players.NinjaManager;
import net.arcanemc.core.util.Color;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Created by eric on 11/25/16.
 */
public class Game {
    List<UUID> currentPlayers;
    Range ran;
    Location lobby;
    int maxPlayers;
    int gameNumber;
    boolean running = false;


    public Game(int gameNumber, int maxPlayers, Range ran, Location lobby){
        currentPlayers = new ArrayList<>();
        this.ran = ran;
        this.lobby = lobby;
        this.maxPlayers = maxPlayers;
        this.gameNumber = gameNumber;
    }

    // Other

    public void startGame(){
        for(UUID uuid : currentPlayers){
            Ninja n = NinjaManager.getNinja(uuid);
            if(n.getPlayer().isPresent()){
                n.getPlayer().get().teleport(ran.getRandomSafeLocation());
                n.setFrozen(true); // TODO: Frozen code
            }else{
                currentPlayers.remove(n); // TEMP FIXME: CME
            }
        }
        ASyncClock();
    }

    public void stopGame(){
        running = false;
        for(UUID uuid : currentPlayers){
            Ninja n = NinjaManager.getNinja(uuid);
            n.setGame(null);
            n.getPlayer().get().teleport(lobby);
        }
    }

    private void ASyncClock(){
        new BukkitRunnable(){
            int time = 10;
            @Override
            public void run() {
                for(UUID uuid : currentPlayers){
                    Ninja n = NinjaManager.getNinja(uuid);
                    if(time > -1){
                        sendTitle(((CraftPlayer)n.getPlayer().get()),"&5&lGame Starting in: " + time, 0, 20, 0);
                    }else{
                        sendTitle(((CraftPlayer)n.getPlayer().get()),"&a&lGood luck!", 0, 30, 10);
                        n.setFrozen(false);
                        running = true;
                        this.cancel();
                    }
                }
                time--;
            }
        }.runTaskTimerAsynchronously(Main.getInstance(),10,20);
    }

    private void sendTitle(CraftPlayer tgt, String text, int fIn, int stay, int fOut){
        JSONObject json = new JSONObject(); // TODO: Optimize?
        json.put("text", Color.color(text));
        PacketPlayOutTitle fade = new PacketPlayOutTitle(fIn, stay, fOut);
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,IChatBaseComponent.ChatSerializer.a(json.toJSONString()));
        tgt.getHandle().playerConnection.sendPacket(fade);
        tgt.getHandle().playerConnection.sendPacket(packet);
    }

    // Getters

    public List<UUID> getCurrentPlayers(){return currentPlayers;}

    public Range getRange(){return ran;}

    public boolean isRunning(){return running;}

    public int getGameNumber(){return gameNumber;}

    // Setters

    public boolean addPlayer(UUID uuid){ // TODO: Banned from certain games???
        if(!currentPlayers.contains(uuid) && !NinjaManager.getNinja(uuid).isInGame() && currentPlayers.size() < maxPlayers){
            currentPlayers.add(uuid);
            NinjaManager.getNinja(uuid).getPlayer().get().teleport(lobby);
            if(currentPlayers.size() >= maxPlayers){
                startGame();
            }
            return true;
        }else{
            return false;
        }
    }

    public void leave(UUID uuid){
        if(currentPlayers.contains(uuid)){
            NinjaManager.getNinja(uuid).setGameNull();
            currentPlayers.remove(uuid);
            if(NinjaManager.getNinja(uuid).getPlayer().isPresent())
            NinjaManager.getNinja(uuid).getPlayer().get().teleport(lobby);
        }
    }

    public void setRange(Range ran){
        this.ran=ran;
    }
}
