package com.joe_kent.gamemode;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Utilities class
 */
public class Utils {

    /**
     * Gets all blocks in a player line of sight including air
     * Modified from deprecated bukkit method by removing magic value usage
     * and including air.
     * @param entity Entity line of sight to get
     * @param maxDistance Maximum distance to get
     * @param maxLength Maximum length of the array, 0 to ignore this
     * @return List of blocks in the line of sight
     */
    public static List<Block> getLineOfSightNoTransparent(LivingEntity entity, int maxDistance, int maxLength){
        if(maxDistance > 120) {
            maxDistance = 120;
        }
        ArrayList<Block> blocks = new ArrayList<Block>();
        Iterator<Block> itr = new BlockIterator(entity, maxDistance);
        while(itr.hasNext()) {
            Block block = itr.next();
            blocks.add(block);
            if(maxLength != 0 && blocks.size() > maxLength) {
                blocks.remove(0);
            }
        }
        return blocks;
    }
}
