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

public class BiggerPickaxe extends Tool implements BlockList, EnchantList {

    private final List<Player> cooldown = new ArrayList<>();

    public BiggerPickaxe(CymerlineTools plugin) {
        super(plugin);
    }

    @Override
    public ToolType getType() {
        return ToolType.BIGGER_PICKAXE;
    }

    @Override
    public Material getMat() {
        return Material.NETHERITE_PICKAXE;
    }

    @Override
    public List<Material> getBlockMat() {
        return getBlockMat(getType());
    }

    @Override
    public Map<Enchantment, Integer> getEnchant() {
        return getEnchant(getType());
    }

    @Override
    public void reload() {
        this.reloadEnchantList();
        this.reloadBlockList();
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
