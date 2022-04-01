package fr.robotv2.cymerlinetools.impl.inter;

import fr.robotv2.cymerlinetools.CymerlineTools;
import fr.robotv2.cymerlinetools.impl.ToolType;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public interface BlockList {

    Map<ToolType, List<Material>> materials = new ConcurrentHashMap<>();

    default void reloadBlockList() {
        materials.clear();
    }

    default List<String> getBlockList(ToolType type) {
        return CymerlineTools.getInstance().getConfig().getStringList(type.getNameLower() + ".block-list");
    }

    default List<Material> getBlockMat(ToolType type) {
        List<Material> materials = this.materials.get(type);
        if(materials == null) {

            List<String> blockList = getBlockList(type);

            if(blockList.isEmpty()) {
                return Collections.emptyList();
            }

            materials = blockList.stream().map(Material::valueOf).collect(Collectors.toList());
            this.materials.put(type, materials);
        }
        return materials;
    }

    List<Material> getBlockMat();
}
