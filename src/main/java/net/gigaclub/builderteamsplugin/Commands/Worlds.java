package net.gigaclub.builderteamsplugin.Commands;

import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.builderteamsplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Worlds implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();



        if (sender instanceof Player) {

            switch (args[0].toLowerCase()) {


                case "createasteam":
                    String ownteamname = builderSystem.getTeamNameByMember(playerUUID).get("name");
                    if (ownteamname == "false"){ System.out.println("no team 1"); return false;}
                    if (ownteamname == ""){ System.out.println("no team 2"); return false;}
                    if (args.length == 3) {
                        for (Object o : builderSystem.getAllWorldTypes()) {
                            HashMap m = (HashMap) o;
                            String worldTyp = (String) m.get("name");
                            boolean defaultworldtyp = (boolean) m.get("default");

                            if (defaultworldtyp == true) {
                                System.out.println(Integer.parseInt(args[1]));
                                System.out.println(args[2]);
                                System.out.println(worldTyp);
                                if (isInt(args[1])) {
                                    builderSystem.createWorldAsTeam(playerUUID, Integer.parseInt(args[1]), args[2], worldTyp);

                                }
                            }
                        }
                    } else if (args.length == 4) {
                        for (Object o : builderSystem.getAllWorldTypes()) {
                            HashMap m = (HashMap) o;
                            String worldTyp = (String) m.get("name");
                            String sworldTyp = stackworldtyp(args, 4);
                            if (sworldTyp.toLowerCase().equals(worldTyp.toLowerCase())) {
                                if (isInt(args[1])) {
                                    builderSystem.createWorldAsTeam(playerUUID, Integer.parseInt(args[1]), args[2], worldTyp);

                                }
                            }
                        }
                    } else if (args.length >= 6) {
                        player.sendMessage(t.t("BuilderSystem.toomany_Arguments", playerUUID));
                        return false;
                    }
                    break;
                case "createasuser":
                    if (args.length == 3) {
                        for (Object o : builderSystem.getAllWorldTypes()) {
                            HashMap m = (HashMap) o;
                            String worldTyp = (String) m.get("name");
                            boolean defaultworldtyp = (boolean) m.get("default");
                            if (defaultworldtyp) {
                                if (isInt(args[1])) {
                                    builderSystem.createWorldAsUser(playerUUID, Integer.parseInt(args[1]), args[2], worldTyp);

                                }
                            }
                        }
                    } else if (args.length >= 4) {
                        for (Object o : builderSystem.getAllWorldTypes()) {
                            HashMap m = (HashMap) o;
                            String worldTyp = (String) m.get("name");
                            String sworldTyp = stackworldtyp(args, 4);
                            if (sworldTyp.equalsIgnoreCase(worldTyp.toLowerCase())) {
                                if (isInt(args[1])) {
                                    builderSystem.createWorldAsUser(playerUUID, Integer.parseInt(args[1]), args[2], worldTyp);
                                }
                            }
                        }
                        break;
                    } else if (args.length >= 6) {
                        player.sendMessage(t.t("BuilderSystem.toomany_Arguments", playerUUID));
                        return false;
                    }
                case "remove":
                    String ownteamname3 = builderSystem.getTeamNameByMember(playerUUID).get("name");
                    System.out.println("GetTeam ? " + builderSystem.getTeam(args[1]));
                    if (!(ownteamname3.equalsIgnoreCase(args[1]))) {
                        String Teamname = (String) builderSystem.getTeam(args[1].toLowerCase());
                        if (isInt(args[2])) {

                            builderSystem.removeTeamFromWorld(playerUUID, Teamname, Integer.parseInt(args[2]));
                        }
                    }
                    break;
                case "addteam":
                    String ownteamname1 = builderSystem.getTeamNameByMember(playerUUID).get("name");
                    if (!(ownteamname1.equalsIgnoreCase(args[1]))) {
                        String Teamname = (String) builderSystem.getTeam(args[1]);
                        if (isInt(args[2])) {

                            builderSystem.addTeamToWorld(playerUUID, Teamname, Integer.parseInt(args[2]));
                        }
                    }
                    break;
                case "adduser":
                    if (isInt(args[2])) {
                        String addetuser = Bukkit.getPlayer(args[1]).toString();

                        builderSystem.addTeamToWorld(playerUUID, addetuser, Integer.parseInt(args[2]));

                    }
                    break;
                case "list":
                    for (Object o : builderSystem.getAllWorlds()) {
                        HashMap m = (HashMap) o;
                        player.sendMessage(ChatColor.GRAY + t.t("BuilderSystem.world.id_list", playerUUID)+ " "+ ChatColor.WHITE + m.get("world_id").toString());
                        player.sendMessage(ChatColor.GRAY + t.t("BuilderSystem.world.name_list", playerUUID)+" " + ChatColor.WHITE + m.get("name").toString());
                        player.sendMessage(ChatColor.GRAY + t.t("BuilderSystem.world.world_typ_list", playerUUID)+" " + ChatColor.WHITE + m.get("world_type").toString());
                        player.sendMessage("");
                     player.sendMessage(t.t("BuilderSystem.world.team_list", playerUUID));

                       for (Object team : (Object[]) m.get("team_manager_ids")) {
                           HashMap teamMap = (HashMap) team;

                            String teamname = teamMap.get("name").toString();
                            StringBuilder res = new StringBuilder();
                           res.append(ChatColor.GRAY +"");
                            res.append(teamname).append(""+ChatColor.WHITE + " , " + ChatColor.GRAY +"" );

                            res.toString();

                            player.sendMessage(res.toString());
                            }
                        player.sendMessage("");
                       player.sendMessage(t.t("BuilderSystem.world.user_list", playerUUID));

                        for (Object user : (Object[]) m.get("user_manager_ids")) {
                            HashMap userMap = (HashMap) user;

                            Player player11 = Bukkit.getPlayer(userMap.get("name").toString());
                            String player21 = player11.toString();
                            StringBuilder res1 = new StringBuilder();
                            res1.append(player21).append(ChatColor.WHITE + " , " + ChatColor.GRAY);
                            String strValue = "ChatColor.GRAY +";
                            res1.append(new StringBuilder(strValue).reverse());
                            res1.toString();

                            player.sendMessage(res1.toString());
                        }
                        player.sendMessage(ChatColor.BOLD + ChatColor.DARK_GRAY.toString() + "----------------------------------");

                    }break;

            }

        }
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("CreateAsTeam");
            arguments.add("CreateAsUser");
            arguments.add("Remove");
            arguments.add("addTeam");
            arguments.add("addUser");
            arguments.add("List");

            return arguments;

        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {

                case "createasteam", "createasuser":

                    if (args.length == 3) {
                        List<String> createname = new ArrayList<>();
                        createname.add("<" + t.t("builder_team.tab_task_id", playerUUID) + ">");
                        return createname;
                    } else if (args.length == 4) {
                        List<String> createname = new ArrayList<>();
                        createname.add("<" + t.t("builder_team.world.tab_world_name", playerUUID) + ">");
                        return createname;
                    } else if (args.length == 5) {
                        List<String> createname = new ArrayList<>();
                        for (Object o : builderSystem.getAllWorldTypes()) {
                            HashMap m = (HashMap) o;
                            String worldTyp = (String) m.get("name");
                            createname.add(worldTyp);
                        }
                        return createname;
                    }
                    break;
            }
        }

        return null;
    }

    private String stackworldtyp(String[] args, int at) {
        StringBuilder res = new StringBuilder();
        for (int i = at; i < args.length; i++) {
            if (i < 2 + at) {
                res.append(args[i]).append(" ");
            } else
                break;
        }
        return res.toString();
    }

    private static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}


