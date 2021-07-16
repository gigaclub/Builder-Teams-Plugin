package net.gigaclub.builderteamsplugin;

import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.builderteamsplugin.Andere.InviteAcceptCommand;
import net.gigaclub.builderteamsplugin.Andere.InviteDenyCommand;
import net.gigaclub.builderteamsplugin.Commands.Team;
import net.gigaclub.builderteamsplugin.Config.Config;
import net.gigaclub.builderteamsplugin.Config.ConfigTeams;
import net.gigaclub.builderteamsplugin.Config.OdooConfig;
import net.gigaclub.builderteamsplugin.listener.InviteAcceptListener;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
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
        getCommand("gcteams").setExecutor( team);
        getCommand("gcteams").setTabCompleter(team);
        getCommand("accept").setExecutor(new InviteAcceptCommand());
        getCommand("deny").setExecutor(new InviteDenyCommand());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InviteAcceptListener(), this);
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
        System.out.println("BB");
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
        Main.translation.registerTranslations(Arrays.asList(
                "builder_team.ToLessArguments",
                "builder_team.wrong_arguments",

                "builder_team.create.only_name",
                "builder_team.Create.name_desc",

                "builder_team.edit.name",
                "builder_team.edit.description",

                "builder_team.leave",
                "builder_team.kick",
                "builder_team.add_manager",
                "builder_team.add",
                "builder_team.no_permission",

                "builder_team.create.tab_teamname",
                "builder_team.create.tab_description",

                "builder_team.edit.tab_teamname",
                "builder_team.edit.tab_description"


//      status msg´s


        ));
    }
}
