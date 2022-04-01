package fr.robotv2.cymerlinetools.command;

import fr.robotv2.cymerlinetools.CymerlineTools;
import fr.robotv2.cymerlinetools.impl.Tool;
import fr.robotv2.cymerlinetools.impl.ToolType;
import fr.robotv2.cymerlinetools.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CymerlineToolCommand implements CommandExecutor, TabExecutor {

    private final List<String> tabComplete;
    public CymerlineToolCommand() {
        List<String> tabComplete = new ArrayList<>();
        for(ToolType type : ToolType.values()) {
            tabComplete.add(type.getNameLower());
        }
        tabComplete.add("reload");
        this.tabComplete = tabComplete;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("cymerlinetools.admin"))
            return true;

        if(args.length == 0) {
            StringUtil.sendMessage(sender, "&c&lUSAGE: &c/tool <tool-type> [<player>]");
            return true;
        }

        if(args[0].equalsIgnoreCase("reload")) {
            CymerlineTools.getInstance().onReload();
            StringUtil.sendMessage(sender, "&aVous venez de recharger la configuration.");
            return true;
        }

        String holyItemStr = args[0].toUpperCase();
        ToolType type;

        try {
            type = ToolType.valueOf(holyItemStr);
        } catch(IllegalArgumentException e) {
            StringUtil.sendMessage(sender, "&cCet outil n'existe pas.");
            return true;
        }

        Tool tool = type.get();

        if(args.length == 1) {
            if(!(sender instanceof Player player)) {
                StringUtil.sendMessage(sender, "&cImpossible depuis la console.");
            } else {
                tool.giveItem(player);
                StringUtil.sendMessage(sender, "&aVous venez de reçevoir un outil.");
            }
            return true;
        }

        String targetName = args[1];
        Player target = Bukkit.getPlayer(targetName);

        if(target == null || !target.isOnline()) {
            StringUtil.sendMessage(sender, "&cCe joueur n'existe pas ou n'est pas connecté.");
            return true;
        }

        tool.giveItem(target);
        StringUtil.sendMessage(sender, "&aVous venez de donner un outil au joueur: " + target.getName());
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            return tabComplete;
        }

        if(args.length == 1) {
            return tabComplete.stream()
                    .filter(name -> name.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return null;
    }
}
