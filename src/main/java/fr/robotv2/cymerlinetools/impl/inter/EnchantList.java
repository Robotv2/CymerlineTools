package fr.robotv2.cymerlinetools.impl.inter;

import fr.robotv2.cymerlinetools.CymerlineTools;
import fr.robotv2.cymerlinetools.impl.ToolType;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface EnchantList {

    Map<ToolType, Map<Enchantment, Integer>> enchants = new ConcurrentHashMap<>();

    default void reloadEnchantList() {
        enchants.clear();
    }

    default List<String> getEnchantList(ToolType type) {
        return CymerlineTools.getInstance().getConfig().getStringList(type.getNameLower() + ".enchants-list");
    }

    default Map<Enchantment, Integer> getEnchant(ToolType type) {
        Map<Enchantment, Integer> enchants = this.enchants.get(type);
        if(enchants == null) {

            enchants = new HashMap<>();
            List<String> enchantList = getEnchantList(type);

            for(String enchantMap : enchantList) {
                try {

                    String[] args = enchantMap.split(":");
                    Enchantment enchantment = Enchantment.getByName(args[0]);
                    Integer level = Integer.parseInt(args[1]);
                    enchants.put(enchantment, level);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            this.enchants.put(type, enchants);
        }
        return enchants;
    }

    Map<Enchantment, Integer> getEnchant();

}
