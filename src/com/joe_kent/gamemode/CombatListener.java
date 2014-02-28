package com.joe_kent.gamemode;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Bukkit listener that listens for all combat events in a game
 */
public class CombatListener implements Listener {

    private final Game game;

    public CombatListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.hasItem()){
            if(event.getItem().getType() == Material.COAL){
                Sniper sniper = game.getSniper();
                if(event.getPlayer().getName().equalsIgnoreCase(sniper.getPlayer().getName())){
                    if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
                        if(sniper.isLoaded() && sniper.isScoped()){
                            sniper.fire();
                        }
                    }
                    else if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                        sniper.toggleScope();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player attacker = (Player) event.getDamager();
            Player attacked = (Player) event.getEntity();
            if(game.isHunter(attacker.getName()) && !game.isHunter(attacked.getName()) && attacker.getItemInHand().getType() == Material.STONE_SWORD){
                event.setDamage(attacked.getHealth());
                game.endGame();
            }
        }
    }

}
