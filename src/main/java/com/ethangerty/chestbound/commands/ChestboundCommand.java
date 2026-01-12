package com.ethangerty.chestbound.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChestboundCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage("§cOnly players can execute this command.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("§6Usage: §2/chestbound <challenge | accept | decline | itemStats>");
            return true;
        }

        Player target;
        switch (args[0]) {
            case "challenge":
                if (args.length < 2) { sender.sendMessage("§6Usage: §2/chestbound challenge §a[player]");
                    return true; }
                target = Bukkit.getPlayerExact(args[1]);
                if (target == null) { player.sendMessage("§6That player isn't online: §f" + args[1]);
                    return true; }
                if (target == player) { player.sendMessage("§cYou can't challenge yourself.");
                    return true; }

                // TODO send challenge

                return true;

            case "accept":
                if (args.length < 2) { sender.sendMessage("§6Usage: §2/chestbound accept §a[player]");
                    return true; }
                target = Bukkit.getPlayerExact(args[1]);    // TODO add challenge check
                if (target == null || target == player) { player.sendMessage("§cChallenge not found.");
                    return true; }

                // TODO accept challenge

                return true;

            case "decline":
                if (args.length < 2) { sender.sendMessage("§6Usage: §2/chestbound decline §a[player]");
                    return true; }
                target = Bukkit.getPlayerExact(args[1]);    // TODO add challenge check
                if (target == null || target == player) { player.sendMessage("§cChallenge not found.");
                    return true; }

                // TODO decline challenge

                return true;


            case "itemStats":
                player.sendMessage("ITEM STATS ENABLED");
                return true;


            default:
                return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                @NotNull String label, @NotNull String @NotNull [] args) {

        final List<String> validArguments = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], List.of("challenge", "accept", "decline", "itemStats"), validArguments);

            return validArguments;
        }

        if (args.length < 2) { return List.of(); }

        if (args[0].equalsIgnoreCase("challenge") ||
                args[0].equalsIgnoreCase("accept") ||
                args[0].equalsIgnoreCase("decline")) {

            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                names.add(p.getName());
            }

            StringUtil.copyPartialMatches(args[1], names, validArguments);
            return validArguments;
        }

        return List.of();
    }
}
