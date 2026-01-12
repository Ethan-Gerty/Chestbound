package com.ethangerty.chestbound;

import com.ethangerty.commands.ChestboundCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Chestbound extends JavaPlugin {

    // Logger to Print to Console
    private final Logger logger = getLogger();

    // Manages Incoming and Outgoing challenges between players
    private ChallengeManager challengeManager;

    @Override
    public void onEnable() {
        logger.info("Plugin Has Been Enabled");

        this.challengeManager = new ChallengeManager(this);

        loadCommands();
    }

    @Override
    public void onDisable() {
        logger.info("Plugin Has Been Disabled");

        if (challengeManager != null) challengeManager.clearAll();
    }



    public void loadCommands() {
        ChestboundCommand cmd = new ChestboundCommand(challengeManager);

        getCommand("chestbound").setExecutor(cmd);
        getCommand("chestbound").setTabCompleter(new ChestboundTabCompleter());
    }
}