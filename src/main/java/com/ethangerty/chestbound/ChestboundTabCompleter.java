package com.ethangerty.chestbound;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChestboundTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        // /chestbound <tab>
        if (args.length == 1) {
            return List.of("challenge", "accept");
        }

        // /chestbound challenge <player>
        // /chestbound accept <player>
        if (args.length == 2 &&
                (args[0].equalsIgnoreCase("challenge") || args[0].equalsIgnoreCase("accept"))) {

            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                names.add(p.getName());
            }
            return names;
        }

        return List.of();
    }
}
