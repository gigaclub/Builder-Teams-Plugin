package net.gigaclub.builderteamsplugin.Commands;

import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.builderteamsplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static net.gigaclub.builderteamsplugin.Config.Config.getConfig;

public class Team implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();


        if (sender instanceof Player) {

            if (player.hasPermission("BuilderTeam.use")) {

                if (args.length >= 1) {

                    switch (args[0].toLowerCase()) {

                        case "create":

                            if (args.length == 2) {

                                builderSystem.createTeam(playerUUID, args[1]);
                                //                       z.b  Du hast die gruppe args[1] erstellt mit der Beschreibung
                                player.sendMessage(t.t("builder_team.create.only_name", playerUUID));
                            } else if (args.length >= 3) {

                                //                     z.b du hast die gruppe args[1] mit der beschreibung ...
                                builderSystem.createTeam(playerUUID, args[1], getDescription(args, 3));
                                player.sendMessage(t.t("builder_team.Create.name_desc", playerUUID, Arrays.asList(getDescription(args, 3))));
                            }
                            break;
                        case "edit":
                            if (args.length == 2) {
                                player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                                return false;
                            } else {
                                switch (args[0].toLowerCase()) {
                                    case "name" -> {
                                        if (args.length == 3) {
                                            player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                                            return false;
                                        } else if (args.length == 4)
                                            builderSystem.editTeam(playerUUID, args[2], args[3]);
                                        player.sendMessage(t.t("builder_team.edit.name", playerUUID));
                                    }
                                    case "description" -> {
                                        String team = (String) builderSystem.getTeam(args[2]);

                                        builderSystem.editTeam(playerUUID, args[2], team, getDescription(args, 3));
                                        player.sendMessage(t.t("builder_team.edit.description", playerUUID));
                                    }
                                }
                                break;
                            }
                        case "leave":
                            builderSystem.leaveTeam(playerUUID);
                            player.sendMessage(t.t("builder_team.leave", playerUUID));
                            break;
                        case "kick":
                            if (args.length == 2) {
                                player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                                return false;
                            }
                            if (args.length == 3)
                                builderSystem.kickMember(playerUUID, args[2]);
                            player.sendMessage(t.t("builder_team.kick", playerUUID));
                            break;
                        case "addmanager":
                            builderSystem.promoteMember(playerUUID, args[1]);
                            player.sendMessage(t.t("builder_team.add_manager", playerUUID));
                            break;
                        case "add":
                            if (player.hasPermission("BuilderTeam.add"))
                                builderSystem.addMember(playerUUID, args[1]);
                            player.sendMessage(t.t("builder_team.add", playerUUID));
                            break;

                        case "Invite":
                            break;
                        default:
                            player.sendMessage(t.t("builder_team.wrong_arguments", playerUUID));
                            return false;
                    }
                }
            } else
                player.sendMessage(t.t("builder_team.no_permission", playerUUID));
        } else
            System.out.println("You´r not a Player  XD");
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
        for (Object o : builderSystem.getAllTeams()) {
            HashMap m = (HashMap) o;
            teamlistofplayer.add((String) m.get("name"));
        }
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);

//        iPlayerManager.getOnlinePlayers().toArray();
        for (Player value : players) {
            playerNames.add(value.getName());
        }

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
        } else
            switch (args[0].toLowerCase()) {
                case "create":
                    if (args.length == 2) {
                        List<String> createname = new ArrayList<>();
                        createname.add("<" + t.t("builder_team.create.tab_teamname", playerUUID) + ">");
                        return createname;
                    } else if (args.length == 3) {
                        List<String> createDescription = new ArrayList<>();
                        createDescription.add("<" + t.t("builder_team.create.tab_description", playerUUID) + ">");
                        return createDescription;
                    }
                    break;


                case "edit":
                    if (args.length == 2) {
                        List<String> arguments = new ArrayList<>();
                        arguments.add("Name");
                        arguments.add("Description");
                        return arguments;
                    }
                    if (args.length >= 3) {

                        switch (args[1].toLowerCase()) {
                            case "name":
                                if (args.length == 3) {
                                    return teamlistofplayer;
                                } else if (args.length == 4) {
                                    List<String> createnewname = new ArrayList<>();
                                    createnewname.add("<" + t.t("builder_team.edit.tab_teamname" + ">", playerUUID));
                                    return createnewname;
                                }
                                break;
                            case "description":
                                if (args.length == 3) {
                                    return teamlistofplayer;
                                } else if (args.length == 4) {
                                    List<String> description = new ArrayList<>();
                                    description.add("<" + t.t("builder_team.edit.tab_description" + ">", playerUUID));
                                    return description;
                                }
                                break;

                        }
                    }
                    break;


                case "leave":
                    if (args.length == 2) {
                        return teamlistofplayer;
                    }
                    break;


                case "kick":

                    break;
                case "addManager":

                    break;
                case "add":
                    if (args.length == 2) {
                        return playerNames;
                    } else if (args.length == 3) {
                        return teamlistofplayer;
                    }
                    break;


                case "invite":
                    if (args.length == 2) {
                        return playerNames;
                    }

            }
        return null;
    }

    private String getDescription(String[] args, int at) {
        FileConfiguration config = getConfig();
        StringBuilder res = new StringBuilder();
        int maxwords = config.getInt("Teams.create.MaxWorld");
        for (int i = at; i < args.length; i++) {
            if (args.length >= at + maxwords)
                res.append(args[i]).append(" ");
        }
        return res.toString();
    }

}
