package com.joe_kent.gamemode;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sniper
 */
public class Sniper {

    /**
     * Plugin instance
     */
    private final Gamemode plugin;

    /**
     * Player associated with the sniper
     */
    private final Player player;

    /**
     * Value representing if the gun is loaded or not
     */
    private boolean loaded;

    /**
     * Value representing if the gun is scoped or not
     */
    private boolean scoped;

    public Sniper(Gamemode plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.loaded = true;
        this.scoped = false;
    }

    /**
     * Fires the snipers rifle. Kills all targets in it's line of sight within
     * a range of 150 blocks or when the bullet collides with a block.
     * Automatically reloads the rifle.
     */
    public void fire(){
        List<LivingEntity> entities = new ArrayList<LivingEntity>(player.getWorld().getLivingEntities());
        List<Block> lineOfSight = Utils.getLineOfSightNoTransparent(player, 150, 0);
        entities.remove(player);
        for(LivingEntity entity : entities){
            for(Block block : lineOfSight){
                final int blockX = block.getX();
                final int blockY = block.getY();
                final int blockZ = block.getZ();
                if(blockX == entity.getLocation().getBlockX() && blockY == entity.getLocation().getBlockY()
                        && blockZ == entity.getLocation().getBlockZ()){
                    doSniperDamage(entity);
                }
                else if(blockX == entity.getEyeLocation().getBlockX() && blockY == entity.getEyeLocation().getBlockY()
                        && blockZ == entity.getEyeLocation().getBlockZ()){
                    doSniperDamage(entity);
                }
            }
        }
        player.getLocation().getWorld().playSound(player.getEyeLocation(), Sound.EXPLODE, 10.0f, 0.5f);
        player.getEyeLocation().getWorld().playEffect(player.getEyeLocation(), Effect.SMOKE, 1);
        reload();
    }

    /**
     * Kills the given entity.
     * If the entity is a hunter, sets them to dead & tells the game
     * to evaluate if it should be ended.
     * @param entity LivingEntity to kill
     */
    private void doSniperDamage(LivingEntity entity){
        entity.setHealth(0.0);
        if(entity instanceof Player){
            Player deadPlayer = (Player) entity;
            if(plugin.getGame().isHunter(deadPlayer.getName())){
                Hunter hunter = plugin.getGame().getHunter(deadPlayer.getName());
                hunter.setDead(true);
                plugin.getGame().checkForEndGame();
            }
        }
    }

    /**
     * Toggles the scope status by either adding or removing
     * the slowness effect.
     */
    public void toggleScope(){
        if(scoped){
            player.removePotionEffect(PotionEffectType.SLOW);
            scoped = false;
        }
        else{
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000 * 20, 20000000, false));
            scoped = true;
        }
        player.playSound(player.getEyeLocation(), Sound.CLICK, 3.0f, 0.5f);
    }

    /**
     * Reloads the rifle after 4 seconds
     */
    private void reload(){
        loaded = false;
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                loaded = true;
                player.playSound(player.getEyeLocation(), Sound.SHEEP_SHEAR, 3.0f, 0.5f);
            }
        }, 20 * 4);
    }

    /**
     * Gets the player associated with the sniper
     * @return Bukkit player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns whether the rifle is loaded or not
     * @return true if loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Returns whether the rifle is currently scoped or not
     * @return true if scoped
     */
    public boolean isScoped() {
        return scoped;
    }
}
