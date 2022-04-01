package fr.robotv2.cymerlinetools;

import fr.robotv2.cymerlinetools.command.CymerlineToolCommand;
import fr.robotv2.cymerlinetools.impl.ToolType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;

public final class CymerlineTools extends JavaPlugin {

    private static CymerlineTools instance;

    public static CymerlineTools getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        Arrays.asList(ToolType.values()).forEach(ToolType::load);
        Objects.requireNonNull(getCommand("tool")).setExecutor(new CymerlineToolCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void onReload() {
        this.reloadConfig();
        for(ToolType type : ToolType.values()) {
            type.get().reload();
        }
    }
}
