package net.gigaclub.builderteamsplugin.Commands;

import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.builderteamsplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Team implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();
        if (args.length > 0) {
            if(sender instanceof Player){
                if(player.hasPermission("BuilderTeam.use"))
            switch (args[1].toLowerCase(Locale.ROOT)) {
                case "create":
                    if(args.length ==2){
                        player.sendMessage(t.t(playerUUID,"BuilderTeam.ToLessArguments"));
                        return false;
                    }else
                    if(args.length == 3) {
                        builderSystem.createTeam(playerUUID, args[2]);
                    }else if(args.length == 4)
                        builderSystem.createTeam(playerUUID, args[2],"skript für args");
                    break;
                case "edit":
                    if(args.length ==2){
                        player.sendMessage(t.t(playerUUID,"BuilderTeam.ToLessArguments"));
                        return false;
                    }else
                    if(args.length ==3) {
                        player.sendMessage(t.t(playerUUID, "BuilderTeam.ToLessArguments"));
                        return false;
                    }else
                        switch (args[1].toLowerCase(Locale.ROOT)) {
                            case "name":
                                if(args.length == 4){
                                    player.sendMessage(t.t(playerUUID,"BuilderTeam.ToLessArguments"));
                                    return false;
                                }else if(args.length == 5)
                                    builderSystem.editTeam(playerUUID,args[3],args[4]);
                                break;
                            case "description":
                              String team = (String) builderSystem.getTeam(args[3]);
                              builderSystem.editTeam(playerUUID,args[3],team,"skript für args");
                                    break;
                                }
                    break;
                case "leave":
                  builderSystem.leaveTeam(playerUUID);
                    break;
                case "kick":
                    if(args.length == 3){
                        player.sendMessage(t.t(playerUUID,"BuilderTeam.ToLessArguments"));
                        return false;
                    }
                    if(args.length == 4)
                    builderSystem.kickMember(playerUUID,args[3]);
                    break;
                case "addmanager":
                    builderSystem.promoteMember(playerUUID,args[2]);
                    break;
                case "add":
                    builderSystem.addMember(playerUUID,args[2]);
                    break;
             }

            }
            return false;
        }
     return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();

       if(args.length == 1){
           List<String> arguments = new ArrayList<>();
           arguments.add("Create");
           arguments.add("Edit");
           arguments.add("Leave");
           arguments.add("Kick");
           arguments.add("addManager");
           arguments.add("add");

           return arguments;
       }
       switch (args[1].toLowerCase(Locale.ROOT)) {
            case "create":
                if(args.length == 3) {
                    List<String> createname = new ArrayList<>();
                    createname.add("<"+t.t(playerUUID,"BuilderTeam.Create.TeamName"+">"));
                    return createname;
                }else if (args.length == 4) {
                    List<String> createDescription = new ArrayList<>();
                    createDescription.add("<"+ t.t(playerUUID, "BuilderTeam.Create.Description"+">"));
                    return createDescription;
                }
                break;
            case "Edit":
                if(args.length ==3){
                    switch (args[1].toLowerCase(Locale.ROOT)) {
                        case "name":
                            if(args.length == 4) {
                                List<String> editTeam = new ArrayList<>();
                               editTeam.add("");
                                builderSystem.getTeamNameByMember(playerUUID);
                            return editTeam;
                            }else
                            if(args.length == 5) {
                                List<String> createnewname = new ArrayList<>();
                                createnewname.add("<"+t.t(playerUUID,"BuilderTeam.Create.newTeamName"+">"));
                                return createnewname;

                            break;
                        case "description":
                            if(args.length == 4)
                            break;
                    }

                }
                break;

        }
        return null;
    }



}
