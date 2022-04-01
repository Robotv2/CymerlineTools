package fr.robotv2.cymerlinetools.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BlockUtil {

    private final static EnumSet<BlockFace> cubes = EnumSet.of(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH,
            BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_WEST, BlockFace.SOUTH_EAST);

    public static List<Block> getBlocksAround(Block block) {
        List<Block> result = new ArrayList<>();
        for(BlockFace face : cubes) {
            Block target = block.getRelative(face);
            result.add(target);
        }
        return result;
    }

    public static List<Block> getBlocks(Block start, int radius){
        ArrayList<Block> blocks = new ArrayList<>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }
}
