package net.arcanemc.ninjas.db;

import net.arcanemc.core.database.ConnectOptions;
import net.arcanemc.core.database.Database;
import net.arcanemc.ninjas.Main;
import net.arcanemc.ninjas.players.Ninja;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by eric on 11/25/16.
 */
public class DBMain {
    private static DBMain instance;
    private static Database db;
    private static String tbl;

    private DBMain(){
        db = new Database(ConnectOptions.of(Main.getConfiguration().getString("database.hostname"), Main.getConfiguration().getString("database.database"), Main.getConfiguration().getString("database.username"), Main.getConfiguration().getString("database.password"), Main.getConfiguration().getInt("database.port")));
        tbl = Main.getConfiguration().getString("database.table");
        db.connect();
    }

    public static void pushPlayerData(Ninja ninja){
        db.executeUpdate("DELETE FROM "+tbl+" WHERE playerUUID = '"+ninja.getUUID()+"';");
        db.executeUpdate("INSERT INTO "+tbl+" VALUES ('"+ninja.getUUID()+"',"+ninja.getKills()+","+ninja.getDeaths()+",'"+ninja.getFormattedBoughtArmor()+"','"+ninja.getFormattedEquippedArmor()+"',"+ninja.getBalance()+");");
    }

    public static Map<String, Object> getPlayerData(UUID uuid){
        Map<String, Object> send = new HashMap<>();
        try {
            Optional<ResultSet> results = db.executeQuery("SELECT * FROM "+tbl+" WHERE playerUUID = '"+uuid+"';");
            boolean good = results.get().next();
            if(!good){ // Empty
                db.executeUpdate("INSERT INTO "+tbl+" VALUES ('"+uuid+"',0,0,null,null,"+Main.getConfiguration().getString("players.startingBalance")+");");
                getPlayerData(uuid);
            }
            if(good){
                send.put("playerUUID", results.get().getString("playerUUID"));
                send.put("kills", results.get().getInt("kills")); // FIXME: When it's the first time, it's null
                send.put("deaths", results.get().getInt("deaths"));
                send.put("balance", results.get().getInt("balance"));

                if(results.get().getString("boughtArmor") != null && !results.get().getString("boughtArmor").equals("")){
                    String[] split = results.get().getString("boughtArmor").split(",");
                    List<Integer> list = new ArrayList<>();
                    for(String num : split){
                        list.add(Integer.parseInt(num));
                    }
                    send.put("boughtArmor", list);
                }else send.put("boughtArmor", new ArrayList<Integer>());

                if(results.get().getString("equippedArmor") != null && !results.get().getString("equippedArmor").equals("")){
                    String[] split = results.get().getString("equippedArmor").split(",");
                    List<Integer> list = new ArrayList<>();
                    for(String num : split){
                        list.add(Integer.parseInt(num));
                    }
                    send.put("equippedArmor", list);
                }else send.put("equippedArmor", new ArrayList<Integer>());
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return send;
    }

    public static DBMain getInstance(){
        if(instance == null)
            instance = new DBMain();
        return instance;
    }
}
