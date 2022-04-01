package fr.robotv2.cymerlinetools.impl;

import fr.robotv2.cymerlinetools.CymerlineTools;
import fr.robotv2.cymerlinetools.impl.inter.EnchantList;
import fr.robotv2.cymerlinetools.util.ItemAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

public abstract class Tool implements Listener {

    private final CymerlineTools plugin;

    public Tool(CymerlineTools plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public abstract ToolType getType();
    public abstract Material getMat();

    public abstract void reload();

    public CymerlineTools getPlugin() {
        return plugin;
    }

    public FileConfiguration getConfig() {
        return getPlugin().getConfig();
    }

    public String name() {
        return getConfig().getString(getType().getNameLower() + ".name");
    }

    public List<String> lore() {
        return getConfig().getStringList(getType().getNameLower() + ".lore");
    }

    public boolean isItem(ItemStack itemStack) {
        if(itemStack.getType() == Material.AIR)
            return false;
        if(ItemAPI.hasKey(itemStack, "tool", PersistentDataType.STRING)) {
            String id = (String) ItemAPI.getKeyValue(itemStack, "tool", PersistentDataType.STRING);
            return id.equalsIgnoreCase(getType().toString());
        }
        return false;
    }

    public ItemStack getItem() {
        ItemAPI.ItemBuilder builder = new ItemAPI.ItemBuilder();

        builder.setType(getMat());
        builder.setKey("tool", getType().toString());
        builder.setLore(lore());

        if(name() != null)
            builder.setName(name());

        if(this instanceof EnchantList enchantList) {
            for(Map.Entry<Enchantment, Integer> enchant : enchantList.getEnchant().entrySet()) {
                builder.addEnchant(enchant.getKey(), enchant.getValue(), true);
            }
        }

        return builder.build();
    }

    public void giveItem(Player player) {
        player.getInventory().addItem(getItem());
        player.updateInventory();
    }
}
