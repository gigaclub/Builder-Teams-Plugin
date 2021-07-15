package net.gigaclub.builderteamsplugin.Commands;

import net.gigaclub.builderteamsplugin.listener.InviteAcceptListener;
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
                                case 0 ->
                                        //                  z.b  Du hast die gruppe args[1] erstellt
                                        player.sendMessage(t.t("builder_team.create.only_name", playerUUID));
                                case 1 -> player.sendMessage(t.t("builder_team.create.team_could_not_be_created", playerUUID));
                                case 2 -> player.sendMessage(t.t("builder_team.team_with_name_already_exists", playerUUID));
                                case 3 -> player.sendMessage(t.t("builder_team.player_can_only_create_one_team", playerUUID));
                                case 4 -> player.sendMessage(t.t("builder_team.other_error", playerUUID));
                            }


                        } else if (args.length >= 3) {

                            //                     z.b du hast die gruppe args[1] mit der beschreibung ...
                            int status = builderSystem.createTeam(playerUUID, args[1], getDescription(args, 2));
                            String p = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                            switch (status) {
                                case 0 ->
                                        //                      z.b du hast die gruppe args[1] mit der beschreibung ...
                                        player.sendMessage(t.t("builder_team.Create.name_desc", playerUUID, Arrays.asList(getDescription(args, 2))));
                                case 1 -> player.sendMessage(t.t("builder_team.create.team_could_not_be_created", playerUUID));
                                case 2 -> player.sendMessage(t.t("builder_team.team_with_name_already_exists", playerUUID));
                                case 3 -> player.sendMessage(t.t("builder_team.player_can_only_create_one_team", playerUUID));
                                case 4 -> player.sendMessage(t.t("builder_team.other_error", playerUUID));
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
                                            case 0 -> player.sendMessage(t.t("builder_team.edit.name", playerUUID));
                                            case 1 -> player.sendMessage(t.t("builder_team.user_is_not_manager_of_this_team", playerUUID));
                                            case 2 -> player.sendMessage(t.t("builder_team.user_is_not_member_of_this_team", playerUUID));
                                            case 3 -> player.sendMessage(t.t("builder_team.team_does_not_exist", playerUUID));
                                            case 4 -> player.sendMessage(t.t("builder_team.user_has_no_team", playerUUID));
                                            case 5 -> player.sendMessage(t.t("builder_team.other_error", playerUUID));
                                        }

                                    }
                                }
                                case "description" -> {
                                    String team = (String) builderSystem.getTeam(args[2]);
                                    int status = builderSystem.editTeam(playerUUID, args[2], team, getDescription(args, 2));
                                    switch (status) {
                                        case 0 -> player.sendMessage(t.t("builder_team.edit.description", playerUUID));
                                        case 1 -> player.sendMessage(t.t("builder_team.user_is_not_manager_of_this_team", playerUUID));
                                        case 2 -> player.sendMessage(t.t("builder_team.user_is_not_member_of_this_team", playerUUID));
                                        case 3 -> player.sendMessage(t.t("builder_team.team_does_not_exist", playerUUID));
                                        case 4 -> player.sendMessage(t.t("builder_team.user_has_no_team", playerUUID));
                                        case 5 -> player.sendMessage(t.t("builder_team.other_error", playerUUID));
                                    }

                                }
                            }
                            break;
                        }
                    case "leave":
                        int status = builderSystem.leaveTeam(playerUUID);
                        switch (status) {
                            case 0 -> player.sendMessage(t.t("builder_team.leave_Team_Success", playerUUID));
                            case 1 -> player.sendMessage(t.t("builder_team.User has no team", playerUUID));
                            case 2 -> player.sendMessage(t.t("builder_team.other_error", playerUUID));
                        }
                        break;
                    case "kick":
                        if (args.length == 1) {
                            player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                            return false;
                        } else if (args.length == 2) {
                            String p = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();

                            int status2 = builderSystem.kickMember(playerUUID, p);
                            switch (status2) {
                                case 0 -> player.sendMessage(t.t("builder_team.kick_user_success", playerUUID));
                                case 1 -> player.sendMessage(t.t("builder_team.user_is_not_user_of_this_team", playerUUID));
                                case 2 -> player.sendMessage(t.t("builder_team.user_is_not_manager", playerUUID));
                                case 3 -> player.sendMessage(t.t("builder_team.team_does_not_exist", playerUUID));
                                case 4 -> player.sendMessage(t.t("builder_team.other_error", playerUUID));
                            }
                            player.sendMessage(t.t("builder_team.kick", playerUUID));

                        }
                        break;
                    case "addmanager":
                        String p = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                        int status2 = builderSystem.promoteMember(playerUUID, p);
                        switch (status2) {
                            case 0 -> player.sendMessage(t.t("builder_team.promoteMember_success", playerUUID));
                            case 1 -> player.sendMessage(t.t("builder_team.user_is_already_manager_of_this_team", playerUUID));
                            case 2 -> player.sendMessage(t.t("builder_team.user_to_promote_is_not_in_this_team", playerUUID));
                            case 3 -> player.sendMessage(t.t("builder_team.user_to_promote_is_not_a_team", playerUUID));
                            case 4 -> player.sendMessage(t.t("builder_team.user_is_not_manager", playerUUID));
                            case 5 -> player.sendMessage(t.t("builder_team.team_does_not_exist", playerUUID));
                            case 6 -> player.sendMessage(t.t("builder_team.other_error", playerUUID));
                        }
                    case "removemanager":
                        String p5 = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                        int status4 = builderSystem.demoteMember(playerUUID, p5);
                        switch (status4) {
                            case 0 -> player.sendMessage(t.t("builder_team.promoteMember_success", playerUUID));
                            case 1 -> player.sendMessage(t.t("builder_team.user_is_already_manager_of_this_team", playerUUID));
                            case 2 -> player.sendMessage(t.t("builder_team.user_to_kick_is_not_in_this_team", playerUUID));
                            case 3 -> player.sendMessage(t.t("builder_team.user_to_kick_is_not_a_team", playerUUID));
                            case 4 -> player.sendMessage(t.t("builder_team.user_is_not_manager", playerUUID));
                            case 5 -> player.sendMessage(t.t("builder_team.team_does_not_exist", playerUUID));
                            case 6 -> player.sendMessage(t.t("builder_team.other_error", playerUUID));
                        }

                        break;
                    case "add":
                        String p2 = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                        if (player.hasPermission("BuilderTeam.add")) {
                            int status3 = builderSystem.addMember(playerUUID, p2);
                            switch (status3) {
                                case 0 -> player.sendMessage(t.t("builder_team.add_success", playerUUID));
                                case 1 -> player.sendMessage(t.t("builder_team.user_is_not_user_of_this_team", playerUUID));
                                case 2 -> player.sendMessage(t.t("builder_team.user_is_not_manager", playerUUID));
                                case 3 -> player.sendMessage(t.t("builder_team.team_does_not_exist", playerUUID));
                                case 4 -> player.sendMessage(t.t("builder_team.other_error", playerUUID));
                            }
                            break;
                        }
                        break;
                    case "invite":
                        List<String> senderplayer = Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).get("name"));
                         Player checkPlayer = Bukkit.getPlayer(args[1]);

                        if(!args[1].equals(player.getName())) {
                            if (checkPlayer != null && checkPlayer.isOnline()) {
                                if (!InviteAcceptListener.isTradeRequest(senderplayer.get(0), checkPlayer.getUniqueId().toString()) && !InviteAcceptListener.isTradeRequest(checkPlayer.getUniqueId().toString(), senderplayer.get(0))) {
                                    InviteAcceptListener.addTradeRequest(senderplayer.get(0), checkPlayer.getUniqueId().toString());

                                    sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite.send-request", playerUUID)), checkPlayer.getName()));

                                    checkPlayer.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite.new-invite-request", playerUUID)), player.getName()));
                                } else if(InviteAcceptListener.isTradeRequest(checkPlayer.getUniqueId().toString(), senderplayer.get(0))) {
                                    sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite.you-have-already-a-request", playerUUID)), checkPlayer.getName()));
                                } else {
                                    sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite.request-sended-earlier", playerUUID)), checkPlayer.getName()));
                                }
                            } else {
                                if (checkPlayer != null) {
                                    sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite.player-is-not-online", playerUUID)), checkPlayer.getName()));
                                } else {
                                    sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite.player-does-not-exist", playerUUID)), args[1]));
                                }
                            }
                        } else {
                            sender.sendMessage(Objects.requireNonNull(t.t("builder_team.invite-no-own-request", playerUUID)));
                        }
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
            arguments.add("removeManager");
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
                case "removemanager":
                    List<String> team2 = Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).get("name"));
                    HashMap m2 = (HashMap) builderSystem.getTeam(team2.get(0));
                    Object[] n2 = (Object[]) m2.get("manager_ids");
                    ArrayList<String> names2 = new ArrayList<>();
                    for (Object o : n2) {
                        HashMap k = (HashMap) o;
                        String uuid = (String) k.get("mc_uuid");
                        String playerName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                        names2.add(playerName);
                    }
                    return names2;


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



}



