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
import java.util.HashMap;
import java.util.List;

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
            }
        }
        return true;
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
            arguments.add("add");
            arguments.add("addUser");

            return arguments;

        }else
        if(args.length == 2){
            switch (args[0].toLowerCase()) {

                case "createasteam","createasuser":

                if(args.length == 3){
                    List<String> createname = new ArrayList<>();
                    createname.add("<" + t.t("builder_team.tab_task_id", playerUUID) + ">");
                    return createname;
                }else if (args.length == 4){
                    List<String> createname = new ArrayList<>();
                    createname.add("<" + t.t("builder_team.world.tab_world_name", playerUUID) + ">");
                    return createname;
                }else if(args.length == 5){
                    List<String> createname = new ArrayList<>();
                    for (Object o : builderSystem.getAllWorldTypes()) {
                        HashMap m = (HashMap) o;
                        String worldTyp = (String) m.get("name");
                        createname.add(worldTyp);
                    }
                    return  createname;
                }break;
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
