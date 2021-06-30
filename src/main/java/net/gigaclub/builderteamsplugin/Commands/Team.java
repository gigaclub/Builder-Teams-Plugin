package net.gigaclub.builderteamsplugin.Commands;

import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.builderteamsplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Team implements CommandExecutor,TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();


        if (args.length > 0) {
            if (sender instanceof Player) {
                if (player.hasPermission("BuilderTeam.use"))
                    switch (args[1].toLowerCase(Locale.ROOT)) {
                        case "create":
                            if (args.length == 2) {
                                player.sendMessage(t.t(playerUUID, "BuilderTeam.ToLessArguments"));
                                return false;
                            } else if (args.length == 3) {
                                builderSystem.createTeam(playerUUID, args[2]);
                            } else if (args.length == 4)
                                builderSystem.createTeam(playerUUID, args[2], "skript für args");
                            break;
                        case "edit":
                            if (args.length == 2) {
                                player.sendMessage(t.t(playerUUID, "BuilderTeam.ToLessArguments"));
                                return false;
                            } else if (args.length == 3) {
                                player.sendMessage(t.t(playerUUID, "BuilderTeam.ToLessArguments"));
                                return false;
                            } else
                                switch (args[1].toLowerCase(Locale.ROOT)) {
                                    case "name":
                                        if (args.length == 4) {
                                            player.sendMessage(t.t(playerUUID, "BuilderTeam.ToLessArguments"));
                                            return false;
                                        } else if (args.length == 5)
                                            builderSystem.editTeam(playerUUID, args[3], args[4]);
                                        break;
                                    case "description":
                                        String team = (String) builderSystem.getTeam(args[3]);
                                        builderSystem.editTeam(playerUUID, args[3], team, "skript für args");
                                        break;
                                }
                            break;
                        case "leave":
                            builderSystem.leaveTeam(playerUUID);
                            break;
                        case "kick":
                            if (args.length == 3) {
                                player.sendMessage(t.t(playerUUID, "BuilderTeam.ToLessArguments"));
                                return false;
                            }
                            if (args.length == 4)
                                builderSystem.kickMember(playerUUID, args[3]);
                            break;
                        case "addmanager":
                            builderSystem.promoteMember(playerUUID, args[2]);
                            break;
                        case "add":
                            builderSystem.addMember(playerUUID, args[2]);
                            break;

                        case "Invite":
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
//        IPlayerManager iPlayerManager = (IPlayerManager) player;

        List<String> teamlistofplayer = new ArrayList<>();
//        Map<String, String> teams = builderSystem.getTeamNameByMember(playerUUID);
//        Set<String> keys = teams.keySet();
//        for (String key: keys) {
        String key = "test";
            teamlistofplayer.add(key);
//        }
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
//        Object[] players = iPlayerManager.getOnlinePlayers().toArray();
        for (int i = 0; i < players.length; i++) {
            playerNames.add(players[i].toString());}

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("Create");
            arguments.add("Edit");
            arguments.add("Invite");
            arguments.add("Leave");
            arguments.add("Kick");
            arguments.add("addManager");
            arguments.add("add");

            return arguments;
        }else
        switch (args[1].toLowerCase(Locale.ROOT)) {
            case "create":
                if (args.length == 3) {
                    List<String> createname = new ArrayList<>();
                    createname.add("<" + t.t(playerUUID, "BuilderTeam.Create.TeamName" + ">"));
                    return createname;
                } else if (args.length == 4) {
                    List<String> createDescription = new ArrayList<>();
                    createDescription.add("<" + t.t(playerUUID, "BuilderTeam.Create.Description" + ">"));
                    return createDescription;
                }
                break;
            case "Edit":
                if (args.length == 3) {
                    switch (args[1].toLowerCase(Locale.ROOT)) {
                        case "name":
                            if (args.length == 4) {
                                return teamlistofplayer;
                            } else if (args.length == 5) {
                                List<String> createnewname = new ArrayList<>();
                                createnewname.add("<" + t.t(playerUUID, "BuilderTeam.Create.newTeamName" + ">"));
                                return createnewname;
                            }
                                break;
                        case "description":
                                    if (args.length == 4) {
                                        return teamlistofplayer;
                                    }else
                                        if(args.length == 5){
                                        List<String> description = new ArrayList<>();
                                        description.add("<" + t.t(playerUUID, "BuilderTeam.Create.Description" + ">"));
                                        return description;
                                    }
                            }            break;


                    }
                    break;


            case "leave":
                if (args.length == 3){
                    return teamlistofplayer;}
                 break;
            case "kick":
            case "addManager":
            case "add":
                if (args.length == 3) {
                        return playerNames;
                    }else
                if(args.length == 4){
                    return teamlistofplayer;}
                break;
            case "invite":
                if (args.length ==3){
                    return playerNames;
            }


                }
                return null;
        }



}




