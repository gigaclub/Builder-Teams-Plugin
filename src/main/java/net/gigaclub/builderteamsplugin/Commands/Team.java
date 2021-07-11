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

import java.util.*;

import static net.gigaclub.builderteamsplugin.Config.Config.getConfig;

public class Team implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();


        if (sender instanceof Player) {


            if (args.length >= 1) {

                switch (args[0].toLowerCase()) {

                    case "create":

                        if (args.length == 2) {

                            int status = builderSystem.createTeam(playerUUID, args[1]);
                            switch (status) {
                                case 0:
                                    //                  z.b  Du hast die gruppe args[1] erstellt
                                    player.sendMessage(t.t("builder_team.create.only_name", playerUUID));
                                    break;
                                case 1:
                                    player.sendMessage(t.t("builder_team.create.team_could_not_be_created", playerUUID));
                                    break;
                                case 2:
                                    player.sendMessage(t.t("builder_team.team_with_name_already_exists", playerUUID));
                                    break;
                                case 3:
                                    player.sendMessage(t.t("builder_team.player_can_only_create_one_team", playerUUID));
                                    break;
                                case 4:
                                    player.sendMessage(t.t("builder_team.other_error", playerUUID));
                                    break;
                            }


                        } else if (args.length >= 3) {

                            //                     z.b du hast die gruppe args[1] mit der beschreibung ...
                            int status = builderSystem.createTeam(playerUUID, args[1], getDescription(args, 2));
                            switch (status) {
                                case 0:
                                    //                      z.b du hast die gruppe args[1] mit der beschreibung ...
                                    player.sendMessage(t.t("builder_team.Create.name_desc", playerUUID, Arrays.asList(getDescription(args, 2))));
                                    break;
                                case 1:
                                    player.sendMessage(t.t("builder_team.create.team_could_not_be_created", playerUUID));
                                    break;
                                case 2:
                                    player.sendMessage(t.t("builder_team.team_with_name_already_exists", playerUUID));
                                    break;
                                case 3:
                                    player.sendMessage(t.t("builder_team.player_can_only_create_one_team", playerUUID));
                                    break;
                                case 4:
                                    player.sendMessage(t.t("builder_team.other_error", playerUUID));
                                    break;
                            }

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
                                    } else if (args.length == 4) {
                                        int status = builderSystem.editTeam(playerUUID, args[2], args[3]);
                                        switch (status) {
                                            case 0:
                                                player.sendMessage(t.t("builder_team.edit.name", playerUUID));
                                                break;
                                            case 1:
                                                player.sendMessage(t.t("builder_team.user_is_not_manager_of_this_team", playerUUID));
                                                break;
                                            case 2:
                                                player.sendMessage(t.t("builder_team.user_is_not_member_of_this_team", playerUUID));
                                                break;
                                            case 3:
                                                player.sendMessage(t.t("builder_team.team_does_not_exist", playerUUID));
                                                break;
                                            case 4:
                                                player.sendMessage(t.t("builder_team.user_has_no_team", playerUUID));
                                                break;
                                            case 5:
                                                player.sendMessage(t.t("builder_team.other_error", playerUUID));
                                                break;
                                        }

                                    }
                                }
                                case "description" -> {
                                    String team = (String) builderSystem.getTeam(args[2]);
                                    int status = builderSystem.editTeam(playerUUID, args[2], team, getDescription(args, 2));

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
                        if (args.length == 1) {
                            player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                            return false;
                        } else if (args.length == 2) {
                            String p = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();

                            builderSystem.kickMember(playerUUID, p);
                            player.sendMessage(t.t("builder_team.kick", playerUUID));

                        }
                        break;
                    case "addmanager":
                        String p = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                        builderSystem.promoteMember(playerUUID, p);
                        player.sendMessage(t.t("builder_team.add_manager", playerUUID));
                        break;
                    case "add":
                        String p2 = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                        if (player.hasPermission("BuilderTeam.add")) {
                            player.sendMessage(t.t("builder_team.add", playerUUID));
                            builderSystem.addMember(playerUUID, p2);
                            break;
                        }
                        break;
                    case "Invite":
                        player.sendMessage("NÖÖ");
                        break;
                    default:
                        player.sendMessage(t.t("builder_team.wrong_arguments", playerUUID));
                        return false;
                }
            }

//        } else
//                player.sendMessage(t.t("builder_team.no_permission", playerUUID));
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

                                    return Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).get("name"));
                                } else if (args.length == 4) {
                                    List<String> createnewname = new ArrayList<>();
                                    createnewname.add("<" + t.t("builder_team.edit.tab_teamname", playerUUID) + ">");
                                    return createnewname;
                                }
                                break;
                            case "description":
                                if (args.length == 3) {
                                    return Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).get("name"));
                                } else if (args.length == 4) {
                                    List<String> description = new ArrayList<>();
                                    description.add("<" + t.t("builder_team.edit.tab_description", playerUUID) + ">");
                                    return description;
                                }
                                break;

                        }
                    }
                    break;


                case "leave":
                    if (args.length == 2) {
                        return Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).get("name"));
                    }
                    break;


                case "kick":
                case "addManager":
                    List<String> team = Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).get("name"));
                    HashMap m = (HashMap) builderSystem.getTeam(team.get(0));
                    Object[] n = (Object[]) m.get("user_ids");
                    ArrayList<String> names = new ArrayList<>();
                    for (Object o : n) {
                        HashMap k = (HashMap) o;
                        String uuid = (String) k.get("mc_uuid");
                        String playerName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                        names.add(playerName);
                    }
                    return names;

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
            if (i < maxwords + at) {
                res.append(args[i]).append(" ");
            } else
                break;
        }

        return res.toString();
    }

/*public void rrrr(){
        int status = 1;

        switch (status){
            case 0:
                break;
            case 1:
                player.sendMessage(t.t("builder_team.", playerUUID));
                break;
            case 2:
                player.sendMessage(t.t("builder_team.", playerUUID));
                break;
            case 3:
                player.sendMessage(t.t("builder_team.", playerUUID));
                break;
            case 4:
                player.sendMessage(t.t("builder_team.", playerUUID));
                break;
            case 5:
                player.sendMessage(t.t("builder_team.", playerUUID));
                break;
            case 6:
                player.sendMessage(t.t("builder_team.", playerUUID));
                break;
    }

} */

}

