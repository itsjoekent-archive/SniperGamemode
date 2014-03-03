package com.joe_kent.gamemode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Joe
 * Date: 2/28/14
 * Time: 6:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class WeatherHandler implements Listener {

    public void setWeather(Gamemode plugin){
        if(!plugin.getServer().getWorld("world").hasStorm()){
            plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "toggledownfall");
        }
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void weatherChange(WeatherChangeEvent event){
        event.setCancelled(true);
    }

}
