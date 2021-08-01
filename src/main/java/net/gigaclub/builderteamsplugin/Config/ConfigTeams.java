package net.gigaclub.builderteamsplugin.Config;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigTeams {

    public static void setConfigTeams() {

        FileConfiguration config = Config.getConfig();

        config.addDefault("Teams.create.MaxWorld", 25);
        config.addDefault("Teams.invite.Timersek",15);
        config.addDefault("Teams.task.Create.x",2500);
        config.addDefault("Teams.task.Create.y",2500);



        Config.save();

    }

}