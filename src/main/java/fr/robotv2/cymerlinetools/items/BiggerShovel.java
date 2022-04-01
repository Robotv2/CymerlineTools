package fr.robotv2.cymerlinetools.items;

import fr.robotv2.cymerlinetools.CymerlineTools;
import fr.robotv2.cymerlinetools.impl.Tool;
import fr.robotv2.cymerlinetools.impl.ToolType;
import fr.robotv2.cymerlinetools.impl.inter.BlockList;
import fr.robotv2.cymerlinetools.impl.inter.EnchantList;
import fr.robotv2.cymerlinetools.util.BlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BiggerShovel extends Tool implements EnchantList, BlockList {

    private final List<Player> cooldown = new ArrayList<>();

    public BiggerShovel(CymerlineTools plugin) {
        super(plugin);
    }

    @Override
    public ToolType getType() {
        return ToolType.BIGGER_SHOVEL;
    }

    @Override
    public Material getMat() {
        return Material.NETHERITE_SHOVEL;
    }

    @Override
    public void reload() {
        this.reloadEnchantList();
        this.reloadBlockList();
    }

    @Override
    public List<Material> getBlockMat() {
        return getBlockMat(getType());
    }

    @Override
    public Map<Enchantment, Integer> getEnchant() {
        return getEnchant(getType());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!this.isItem(item)) {
            return;
        }

        if(cooldown.contains(player)) {
            return;
        }

        cooldown.add(player);

        Block blockBreak = event.getBlock();
        List<Block> blockList = BlockUtil.getBlocks(blockBreak, 1);

        for(Block target : blockList) {

            if(!this.getBlockMat().contains(target.getType())) {
                continue;
            }

            player.breakBlock(target);
        }

        cooldown.remove(player);
    }
}
