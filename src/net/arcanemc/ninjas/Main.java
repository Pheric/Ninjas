package net.arcanemc.ninjas;

import net.arcanemc.ninjas.Util.Chat;
import net.arcanemc.ninjas.db.DBMain;
import net.arcanemc.ninjas.events.listeners.DoubleJump;
import net.arcanemc.ninjas.events.listeners.Drops;
import net.arcanemc.ninjas.events.listeners.InvListeners;
import net.arcanemc.ninjas.events.listeners.Movement;
import net.arcanemc.ninjas.game.GameManager;
import net.arcanemc.ninjas.inv.InvMain;
import net.arcanemc.ninjas.players.NinjaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by eric on 11/25/16.
 */
public class Main extends JavaPlugin{
    private static Main instance;
    private static FileConfiguration config;
    public static final String PREFIX = "&8[&5Ninjas&8] &7";

    @Override
    public void onEnable(){
        instance = this;
        config = this.getConfig();
        this.saveDefaultConfig();

        NinjaManager.getInstance(); // Runs constructor.
        new Movement();
        new Drops();
        new InvListeners();
        new DoubleJump();

        GameManager.getInstance();
        GameManager.getInstance().loadGames();

        DBMain.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender!=null && !(sender instanceof Player)) return false;
        if(lbl.equalsIgnoreCase("ninjas")){
            if(args.length == 0 || (args.length == 1 && args[0].equals("help"))){
                Chat.sendMessage(sender
                        ,"--noprefix&7-------------------{ N i n j a s }-------------------"
                        ,"--noprefix&9Commands:"
                        ,"--noprefix&5/ninjas menu &7Opens the main menu"
                        ,"--noprefix&5/ninjas help &7Opens this screen"
                        ,"--noprefix&5/ninjas join &7Joins a game"
                        ,"--noprefix&5/ninjas leave &7Leaves the current game"
                        ,"--noprefix&5/ninjas bal  &7Displays your current balance"
                        );
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("menu")){
                    if(NinjaManager.getNinja((Player)sender).isInGame()){
                        Chat.sendMessage(sender, "You cannot use that menu in-game!");
                    }else{
                        InvMain.dispMainMenu((Player)sender);
                    }
                }else if(args[0].equalsIgnoreCase("bal")){
                    Chat.sendMessage(sender, "Your current balance: "+NinjaManager.getNinja((Player)sender).getBalance()+" Coins");
                }else if(args[0].equalsIgnoreCase("leave")){
                    if(NinjaManager.getNinja(((Player)sender).getUniqueId()).isInGame()){
                        NinjaManager.getNinja(((Player)sender).getUniqueId()).getCurrentGame().get().leave(((Player)sender).getUniqueId());
                        Chat.sendMessage(sender, "You have left the game!");
                    }else // Screw style guidelines
                        Chat.sendMessage(sender, "You are not in a game!");
                }else if(args[0].equalsIgnoreCase("join")){
                    GameManager.joinGame(((Player)sender).getUniqueId()); // Tied to InvListeners (action in main menu)
                }
            }
        }
        return true;
    }

    public static FileConfiguration getConfiguration(){return config;}

    public static Main getInstance(){return instance;}
}
