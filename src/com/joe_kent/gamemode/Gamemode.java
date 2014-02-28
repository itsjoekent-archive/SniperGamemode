package com.joe_kent.gamemode;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Bukkit extension point and command executor
 */
public class Gamemode extends JavaPlugin implements CommandExecutor {

    /**
     * Current game instance
     */
    private Game game;

    @Override
    public void onEnable(){
        getCommand("start").setExecutor(this);
        this.saveDefaultConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
        if(command.getName().equalsIgnoreCase("start")){
            game = new Game(this);
            game.startGame();
            return true;
        }
        return false;
    }

    /**
     * Gets the current game instance
     * @return Current game, null if its not been created
     */
    public Game getGame() {
        return game;
    }
}
