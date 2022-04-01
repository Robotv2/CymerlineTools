package fr.robotv2.cymerlinetools.items;

import fr.robotv2.cymerlinetools.CymerlineTools;
import fr.robotv2.cymerlinetools.impl.Tool;
import fr.robotv2.cymerlinetools.impl.ToolType;
import fr.robotv2.cymerlinetools.impl.inter.EnchantList;
import fr.robotv2.cymerlinetools.util.BlockUtil;
import fr.robotv2.cymerlinetools.util.ToolUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class BiggerHoe extends Tool implements EnchantList {
    public BiggerHoe(CymerlineTools plugin) {
        super(plugin);
    }

    @Override
    public ToolType getType() {
        return ToolType.BIGGER_HOE;
    }

    @Override
    public Material getMat() {
        return Material.NETHERITE_HOE;
    }

    @Override
    public void reload() {
        this.reloadEnchantList();
    }

    @Override
    public Map<Enchantment, Integer> getEnchant() {
        return getEnchant(getType());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if(event.useInteractedBlock() == Event.Result.DENY) {
            return;
        }

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!this.isItem(item)) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();

        if(clickedBlock == null || !this.canBeFarmland(clickedBlock.getType())) {
            return;
        }

        List<Block> blockList = BlockUtil.getBlocks(clickedBlock, 1);

        for(Block target : blockList) {

            if(target == null) {
                continue;
            }

            if(this.canBeFarmland(target.getType())) {

                Block relative = target.getRelative(BlockFace.UP);

                if(relative.getType() != Material.AIR) {
                    continue;
                }

                target.setType(Material.FARMLAND);
                ToolUtil.applyDamage(player.getInventory(), item, 1);

            }
        }
    }

    public boolean canBeFarmland(Material material) {
        return material == Material.DIRT ||
                material == Material.DIRT_PATH ||
                material == Material.GRASS_BLOCK;
    }
}
