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
                              Map<String, String> team= builderSystem.getTeamNameByMember(playerUUID);
//                              skript  teamnahmen abgleichen mit args[3]


                              builderSystem.editTeam(playerUUID,args[3],null,"skript für args");

                                    break;
                                }
                    break;
                case "leave":
                  builderSystem.leaveTeam(playerUUID);
                    break;


             }
            }
        }
     return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }



}
