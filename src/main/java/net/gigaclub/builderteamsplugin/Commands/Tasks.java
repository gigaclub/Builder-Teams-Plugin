package net.gigaclub.builderteamsplugin.Commands;

import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.builderteamsplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.gigaclub.builderteamsplugin.Config.Config.getConfig;

public class Tasks implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();

        if (player instanceof Player) {

            if (args.length >= 1) {

                switch (args[0].toLowerCase()) {

                    case "create":
                        if (player.hasPermission("builderteam.task.admin")) {
                            if (args.length == 1) { //bei zusatz Baugrösse im 1 erhöhen
                                player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                                return false;
                            } else if (args.length == 2) { //bei zusatz Baugrösse im 1 erhöhen
                               // nur name
                                builderSystem.createTask(args[1]);  // später Baugrösse Verplichtend
                            }else if(args.length == 3){  //bei zusatz Baugrösse im 1 erhöhen
                                // name und welt typ
                                builderSystem.createTask(args[1],args[2]);// später Baugrösse Verplichtend
                            }else if(args.length >= 4){
                                //name,welt typ und beschreibung
                                builderSystem.createTask(args[1],getDescription(args,4));  // später Baugrösse Verplichtend
                            }

                        }
                        break;
                    case "remove":
                        if (player.hasPermission("builderteam.task.admin")){
                            if (args.length == 1){
                                player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                                return false;
                            }else if(args.length == 2){
                              //  confirm bestätigung

                                builderSystem.removeTask();
                            }

                        }

                }
            }
        }


        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {


        return null;
    }


    private String getDescription(String[] args, int at) {
        FileConfiguration config = getConfig();
        StringBuilder res = new StringBuilder();

        int maxwords = config.getInt("Teams.create.MaxWorld");

        for (int i = at; i < args.length; i++) {
            if (i < maxwords + at) {
                res.append(args[i]).append(" ");
            } else
                break;
        }

        return res.toString();
    }


}
