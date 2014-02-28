package com.joe_kent.gamemode;

import org.bukkit.entity.Player;

/**
 * Represents a hunter
 */
public class Hunter {

    /**
     * Bukkit player associated with the hunter
     */
    private final Player player;

    /**
     * Boolean value representing if the player has been killed yet
     */
    private boolean isDead;

    public Hunter(Player player) {
        this.player = player;
        this.isDead = false;
    }

    /**
     * Gets the player associated with the hunter
     * @return Bukkit player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the hunters life state
     * @param dead True if the player is dead
     */
    public void setDead(boolean dead) {
        isDead = dead;
    }

    /**
     * Returns whether the player is dead or not
     * @return True if the player is dead
     */
    public boolean isDead() {
        return isDead;
    }
}
