package net.arcanemc.ninjas.game;

import net.arcanemc.ninjas.Main;
import net.arcanemc.ninjas.Util.Chat;
import net.arcanemc.ninjas.players.NinjaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by eric on 11/25/16.
 */
public class GameManager {
    private static GameManager instance;
    static List<Game> games;

    private GameManager(){
        games = new ArrayList<>();
    }

    public static void joinGame(UUID uuid){
        for(Game g : games){
            if(g.addPlayer(uuid)){
                NinjaManager.getNinja(uuid).setGame(g);
                Chat.sendMessage(uuid, "You have joined Game #"+g.getGameNumber());
                return;
            }
        }
        Chat.sendMessage(uuid, "Sorry, all games are full or you are already in a game!");
    }

    public static void loadGames(){ // TODO: Shorten
        Set<String> cslist = Main.getConfiguration().getConfigurationSection("games").getKeys(false);
        int num = 1;
        for(String cs : cslist){
            String[] c1split = Main.getConfiguration().getString("games." + cs + ".corner1").split(",");
            List<Long> c1Parsed = new ArrayList<>();
            c1Parsed.add(0, Long.parseLong(c1split[0]));
            c1Parsed.add(1, Long.parseLong(c1split[1]));
            c1Parsed.add(2, Long.parseLong(c1split[2]));
            Location c1 = new Location(Bukkit.getWorld(Main.getConfiguration().getString("games." + cs + ".world")), c1Parsed.get(0), c1Parsed.get(1), c1Parsed.get(2));

            String[] c2split = Main.getConfiguration().getString("games." + cs + ".corner2").split(",");
            List<Long> c2Parsed = new ArrayList<>();
            c2Parsed.add(0, Long.parseLong(c2split[0]));
            c2Parsed.add(1, Long.parseLong(c2split[1]));
            c2Parsed.add(2, Long.parseLong(c2split[2]));
            Location c2 = new Location(Bukkit.getWorld(Main.getConfiguration().getString("games." + cs + ".world")), c2Parsed.get(0), c2Parsed.get(1), c2Parsed.get(2));

            String[] lobbySplit = Main.getConfiguration().getString("games." + cs + ".lobby").split(",");
            List<Long> lobbyParsed = new ArrayList<>();
            lobbyParsed.add(0, Long.parseLong(lobbySplit[0]));
            lobbyParsed.add(1, Long.parseLong(lobbySplit[1]));
            lobbyParsed.add(2, Long.parseLong(lobbySplit[2]));
            Location lobby = new Location(Bukkit.getWorld(Main.getConfiguration().getString("games." + cs + ".lobbyworld")), lobbyParsed.get(0), lobbyParsed.get(1), lobbyParsed.get(2));

            int maxPlayers = Main.getConfiguration().getInt("games."+cs+".maxplayers");
            Game game = new Game(num, maxPlayers, new Range(Bukkit.getWorld(Main.getConfiguration().getString("games." + cs + ".world")), c1, c2), lobby);

            games.add(game);
            num++;
        }
    }

    public static GameManager getInstance(){
        if(instance == null){
            instance = new GameManager();
        }
        return instance;
    }
}
