package com.joe_kent.gamemode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a game instance
 */
public class Game {

    /**
     * Represents the plugin instance
     */
    private final Gamemode plugin;

    /**
     * The sniper for this game
     */
    private Sniper sniper;

    /**
     * List of all the hunters in this game
     */
    private List<Hunter> hunters;

    /**
     * Combat listener for the game
     */
    private final CombatListener listener;


    public Game(Gamemode plugin) {
        this.plugin = plugin;
        hunters = new ArrayList<Hunter>();
        this.listener = new CombatListener(this);
    }

    /**
     * Starts the game instance
     * Picks random sniper & hunters
     * Spawns them at there proper spawn point
     */
    public void startGame(){
        Player[] players = plugin.getServer().getOnlinePlayers();
        Random random = new Random();
        int index = random.nextInt(players.length);

        Location sniperSpawn = new Location(plugin.getServer().getWorld("world"), plugin.getConfig().getInt("sniper-spawn.x"),
                plugin.getConfig().getInt("sniper-spawn.y"), plugin.getConfig().getInt("sniper-spawn.z"));
        Location hunterSpawn = new Location(plugin.getServer().getWorld("world"), plugin.getConfig().getInt("hunter-spawn.x"),
                plugin.getConfig().getInt("hunter-spawn.y"), plugin.getConfig().getInt("hunter-spawn.z"));

        sniper = new Sniper(plugin, players[index]);
        sniper.getPlayer().sendMessage("You're the sniper!");
        players[index].teleport(sniperSpawn);

        for(Player player : players){
            if(!(player.getName().equalsIgnoreCase(sniper.getPlayer().getName()))){
                hunters.add(new Hunter(player));
                player.sendMessage("You're a hunter!");
                player.teleport(hunterSpawn);
            }
        }
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    /**
     * Ends the game, reloads the server
     */
    public void endGame(){
        String message = "Good game, thanks for playing!";
        for(Hunter hunter : hunters){
            hunter.getPlayer().sendMessage(message);
        }
        sniper.getPlayer().sendMessage(message);
        Bukkit.reload();
    }

    /**
     * Gets the sniper for the game
     * @return sniper instance
     */
    public Sniper getSniper() {
        return sniper;
    }

    /**
     * Gets all of the hunters in the game
     * @return list of hunters
     */
    public List<Hunter> getHunters() {
        return hunters;
    }

    /**
     * Checks if the given name matches a hunters name
     * @param name Name to check
     * @return true if player is a hunter
     */
    public boolean isHunter(String name){
        for(Hunter hunter : hunters){
            if(hunter.getPlayer().getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the hunter with the given null
     * @param name Name of the hunter
     * @return hunter instance if found, null if it doesn't exist
     */
    public Hunter getHunter(String name) {
        for(Hunter hunter : hunters){
            if(hunter.getPlayer().getName().equalsIgnoreCase(name)){
                return hunter;
            }
        }
        return null;
    }

    /**
     * Checks of all the hunters have died
     */
    public void checkForEndGame() {
        for(Hunter hunter : hunters){
            if(!hunter.isDead()){
                return;
            }
        }
        endGame();
    }
}
