package fr.robotv2.cymerlinetools.impl;

import fr.robotv2.cymerlinetools.CymerlineTools;
import fr.robotv2.cymerlinetools.items.BiggerHoe;
import fr.robotv2.cymerlinetools.items.BiggerPickaxe;
import fr.robotv2.cymerlinetools.items.BiggerShovel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum ToolType {

    BIGGER_PICKAXE(BiggerPickaxe.class),
    BIGGER_SHOVEL(BiggerShovel.class),
    BIGGER_HOE(BiggerHoe.class);

    private final Map<ToolType, Tool> tools = new HashMap<>();
    private final Class<? extends Tool> clazz;

    ToolType(Class<? extends Tool> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Tool> getClazz() {
        return clazz;
    }

    public String getNameLower() {
        return this.toString().toLowerCase(Locale.ROOT);
    }

    public void load() {
        if(tools.containsKey(this))
            return;
        try {
            Tool tool = getClazz().getDeclaredConstructor(CymerlineTools.class).newInstance(CymerlineTools.getInstance());
            tools.put(this, tool);
        } catch (Exception exception) {
            CymerlineTools.getInstance().getLogger().warning("An error occurred while trying to load item:" + this);
        }
    }

    public Tool get() {
        if(!tools.containsKey(this)) {
            this.load();
        }
        return tools.get(this);
    }
}
