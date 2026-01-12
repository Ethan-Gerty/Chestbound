package com.ethangerty.commands;

import com.ethangerty.chestbound.ChallengeManager;
import com.ethangerty.records.Challengers;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ChestboundCommand implements CommandExecutor {

    private static final long CHALLENGE_DURATION_MS = 5 * 60 * 1000L; // 5 minutes

    private final ChallengeManager challengeManager;



    public ChestboundCommand(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("Usage: /chestbound <challenge|accept> [player]");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[1]);

        String sub = args[0].toLowerCase();
        switch (sub) {
            case "challenge" -> {
                if (target == null) {
                    player.sendMessage("That player isn't online: " + args[1]);
                    return true;
                } else if (player == target) {
                    player.sendMessage("You can't Challenge yourself...");
                    return true;
                }

                UUID challengerId = player.getUniqueId();
                UUID targetId = target.getUniqueId();

                challengeManager.createOrReplace(challengerId, targetId, CHALLENGE_DURATION_MS);

                player.sendMessage("You challenged " + target.getName() + "! (expires in 5 minutes)");
                target.sendMessage(player.getName() + " has challenged you to Chestbound!");
                target.sendMessage("Type: /chestbound accept " + player.getName() + " (expires in 5 minutes)");

                return true;
            }

            case "accept" -> {
                if (target == null) {
                    player.sendMessage("That player isn't online: " + args[1]);
                    return true;
                } else if (player == target) {
                    player.sendMessage("You can't accept a Challenge from yourself...");
                    return true;
                }

                UUID challengerId = target.getUniqueId();
                UUID targetId = player.getUniqueId();

                Challengers c = challengeManager.get(challengerId, targetId);
                if (c == null) {
                    player.sendMessage("No active challenge from " + target.getName() + " to accept.");
                    return true;
                }

                challengeManager.remove(challengerId, targetId);

                player.sendMessage("You accepted " + target.getName() + "'s challenge!");
                target.sendMessage(player.getName() + " accepted your Chestbound challenge!");

                // TODO: Start game here

                return true;
            }

            default -> {
                player.sendMessage("Unknown subcommand: " + args[0]);
                player.sendMessage("Usage: /chestbound <challenge | accept> [player]");
                return true;
            }
        }
    }
}
