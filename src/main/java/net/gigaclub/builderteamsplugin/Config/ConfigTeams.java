package net.gigaclub.builderteamsplugin.Config;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigTeams {

    public static void setConfigTeams() {

        FileConfiguration config = Config.getConfig("Teams.create.MaxWorld");

        config.addDefault("Teams.create.MaxWorld", "50");


        Config.save();

    }

}