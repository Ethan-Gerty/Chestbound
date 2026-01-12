package com.ethangerty.chestbound;

import com.ethangerty.chestbound.commands.ChestboundCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class Chestbound extends JavaPlugin {

    // Logger to Print to Console
    private final Logger logger = getLogger();


    @Override
    public void onEnable() {
        logger.info("Plugin Has Been Enabled");

        loadCommands();
    }

    @Override
    public void onDisable() {
        logger.info("Plugin Has Been Disabled");
    }



    public void loadCommands() {
        Objects.requireNonNull(getCommand("chestbound")).setExecutor(new ChestboundCommand());
    }
}