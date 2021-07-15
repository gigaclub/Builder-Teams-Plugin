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

        if (sender instanceof Player) {



                List<String> checkPlayer2 = Collections.singletonList(builderSystem.getTeamNameByMember(GetInvitePlayer(playerUUID)).get("name"));
                String checkPlayer = (String) checkPlayer2.get(0);


                if (InviteAcceptListener.isTradeRequest(checkPlayer, player.getUniqueId().toString())) {

                    sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite_accept.successfull", playerUUID)), checkPlayer));


                    System.out.print(GetInvitePlayer(playerUUID));


                    int status = builderSystem.addMember(playerUUID, GetInvitePlayer(playerUUID));
                    GetInviteTeam(sender);
                } else {
                    sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite_accept.not_successfull", playerUUID)), checkPlayer));
                }

        } else {
            sender.sendMessage(Objects.requireNonNull(t.t("builder_team.invite_accept.no_player", playerUUID)));
        }


        return true;
    }

    private String GetInvitePlayer(String playerUUID) {

        System.out.println(1);
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];


        Bukkit.getServer().getOnlinePlayers().toArray(players);
        System.out.println(playerUUID);
        System.out.println(2.5);
//        iPlayerManager.getOnlinePlayers().toArray();
        for (Player value : players){
            Player player2 = Bukkit.getPlayer(value.getName());
            assert player2 != null;
            String InviteerplayerUuid = player2.getUniqueId().toString();


            System.out.println(2);
            if (InviteAcceptListener.isTradeRequest(InviteerplayerUuid, playerUUID) && InviteAcceptListener.isTradeRequest(playerUUID, InviteerplayerUuid)){
                System.out.println(InviteerplayerUuid);
                System.out.println(playerUUID);

                return InviteerplayerUuid;
            }
        }
    return null;
    }





    private void GetInviteTeam(CommandSender sender){
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();

        List<String> team2 = Collections.singletonList(builderSystem.getTeamNameByMember(GetInvitePlayer(GetInvitePlayer(playerUUID))).get("name"));
        HashMap m2 = (HashMap) builderSystem.getTeam(team2.get(0));
        System.out.println(team2.get(0));
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


    }


}