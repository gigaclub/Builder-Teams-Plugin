package net.gigaclub.builderteamsplugin.Andere;


import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.builderteamsplugin.Main;
import net.gigaclub.builderteamsplugin.listener.InviteAcceptListener;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class InviteAcceptCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();

        if(sender instanceof Player) {

            if(sender.hasPermission("trade.deny")) {

        List<String> checkPlayer2 = Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).get("name"));
        String checkPlayer = (String) checkPlayer2.get(1);


            if (InviteAcceptListener.isTradeRequest(checkPlayer, player.getUniqueId().toString())) {

                sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite_accept.successfull", playerUUID)), checkPlayer));
                List<String> team2 = Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).get("name"));
                HashMap m2 = (HashMap) builderSystem.getTeam(team2.get(0));
                Object[] n2 = (Object[]) m2.get("manager_ids");
                ArrayList<String> names2 = new ArrayList<>();
                for (Object o : n2) {
                    HashMap k = (HashMap) o;
                    String uuid = (String) k.get("mc_uuid");
                    String playerName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                    Player player3 = (Player) Bukkit.getPlayerExact(playerName);
                    if(player3 instanceof Player){
                        player3.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite_accept.accepted", playerUUID)), player.getName()));
                    }
                }
                String p3 = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                int status3 = builderSystem.addMember(playerUUID, p3);
            } else {
                sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite_accept.not_successfull", playerUUID)), checkPlayer));
            }

            } else {
                sender.sendMessage(Objects.requireNonNull(t.t("builder_team.no_permission", playerUUID)));
            }
        } else {
            sender.sendMessage(Objects.requireNonNull(t.t("builder_team.invite_accept.no_player", playerUUID)));
        }


        return true;
    }
}