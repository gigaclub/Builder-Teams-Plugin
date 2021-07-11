package net.gigaclub.builderteamsplugin;

import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.builderteamsplugin.Commands.Team;
import net.gigaclub.builderteamsplugin.Config.Config;
import net.gigaclub.builderteamsplugin.Config.ConfigTeams;
import net.gigaclub.builderteamsplugin.Config.OdooConfig;
import net.gigaclub.translation.Translation;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {
    private static Main plugin;
    private static Translation translation;
    private static BuilderSystem builderSystem;


    @Override
    public void onEnable() {
        plugin = this;
        setPlugin(this);

        Team team;
        team = new Team();
        getCommand("teams").setExecutor( team);
        getCommand("teams").setTabCompleter(team);

        setConfig();
        FileConfiguration config = getConfig();




        setTranslation(new Translation(
                config.getString("Base.Odoo.Host"),
                config.getString("Base.Odoo.Database"),
                config.getString("Base.Odoo.Username"),
                config.getString("Base.Odoo.Password")
        ));

        setBuilderSystem(new BuilderSystem(
                config.getString("Base.Odoo.Host"),
                config.getString("Base.Odoo.Database"),
                config.getString("Base.Odoo.Username"),
                config.getString("Base.Odoo.Password")
        ));

        registerTranslations();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Translation getTranslation() {
        return translation;
    }
    public static void setTranslation(Translation translation) {
        Main.translation = translation;
    }
    public static BuilderSystem getBuilderSystem() {
        return Main.builderSystem;
    }
    public static void setBuilderSystem(BuilderSystem builderSystem) {
        Main.builderSystem = builderSystem;
    }

    private void setConfig() {
        Config.createConfig();

        OdooConfig.setOdooConfig();
        ConfigTeams.setConfigTeams();

        Config.save();

        System.out.println("Config files set.");
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static void setPlugin(Main plugin) {
        Main.plugin = plugin;

    }

    public static void registerTranslations() {
        System.out.println("test");
        Main.translation.registerTranslations(Arrays.asList(
                "BuilderTeam.ToLessArguments",
                "BuilderTeam.Create.TeamName",
                "BuilderTeam.Create.TabDescription",
                "BuilderTeam.Create.TabTeamName",
                "BuilderTeam.Create.Description",
                "BuilderTeam.Edit.newTeamName",
                "BuilderTeam.Edit.newDescription",
                "BuilderTeam.Edit.TabNewTeamName",
                "BuilderTeam.Edit.TabNewDescription"


        ));
    }
}
