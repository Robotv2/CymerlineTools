package fr.robotv2.cymerlinetools.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ToolUtil {

    public static void applyDamage(Inventory inventory, ItemStack item, int durability) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            Damageable damageable = (Damageable) meta;

            int current = damageable.getDamage();
            damageable.setDamage(current + durability);

            item.setItemMeta(meta);

            if(damageable.getDamage() >= item.getType().getMaxDurability()) {
                inventory.remove(item);
            }
        }
    }
}
