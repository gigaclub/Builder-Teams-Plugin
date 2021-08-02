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

public class Worlds implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();

        String ownteamname = builderSystem.getTeamNameByMember(playerUUID).get("name");

        if (sender instanceof Player) {

            switch (args[0].toLowerCase()) {


                case "createasteam":
                    if (args.length == 3) {
                        for (Object o : builderSystem.getAllWorldTypes()) {
                            HashMap m = (HashMap) o;
                            String worldTyp = (String) m.get("name");
                            boolean defaultworldtyp = (boolean) m.get("default");

                            if (defaultworldtyp == true) {
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

                    System.out.println("GetTeam ? " + builderSystem.getTeam(args[1]));
                    if (!(ownteamname.equalsIgnoreCase(args[1]))) {
                        String Teamname = (String) builderSystem.getTeam(args[1].toLowerCase());
                        if (isInt(args[2])) {

                            builderSystem.removeTeamFromWorld(playerUUID, Teamname, Integer.parseInt(args[2]));
                        }
                    }
                    break;
                case "addteam":

                    if (!(ownteamname.equalsIgnoreCase(args[1]))) {
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
                        player.sendMessage(ChatColor.GRAY + "ID: " + ChatColor.WHITE + m.get("world_id").toString());
                        player.sendMessage(ChatColor.GRAY + "Name: " + ChatColor.WHITE + m.get("name").toString());
                        player.sendMessage(ChatColor.GRAY + "World typ:" + ChatColor.WHITE + m.get("world_type"));

                        player.sendMessage(stackstringstolist(args, (Object[]) m.get("team_ids"), "name"));

                        for (Object o2 : (Object[]) m.get("user_ids")) {
                            HashMap m2 = (HashMap) o2;
                            Player player1 = Bukkit.getPlayer(m2.get("name").toString());
                            String player2 = player1.toString();
                            StringBuilder res = new StringBuilder();

                            res.append(player2).append(ChatColor.WHITE + " , " + ChatColor.GRAY);

                            String strValue = "ChatColor.GRAY +";
                            res.append(new StringBuilder(strValue).reverse());
                            res.toString();

                            player.sendMessage(res.toString());
                        }

                    }

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

    private String stackstringstolist(String[] args, Object[] object, String name) {
        BuilderSystem builderSystem = Main.getBuilderSystem();
        StringBuilder res = new StringBuilder();
        for (Object o : object) {
            HashMap m = (HashMap) o;
            String name2 = m.get(name).toString();

            res.append(name2).append(ChatColor.WHITE + " , " + ChatColor.GRAY);
        }

        String strValue = "ChatColor.GRAY +";
        res.append(new StringBuilder(strValue).reverse());
        return res.toString();
    }

}


