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

import java.util.Objects;

public class InviteDenyCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();


        if(sender instanceof Player) {


                if(args.length == 1) {
                    Player checkPlayer = Bukkit.getPlayer(args[0]);
                    if(checkPlayer != null && checkPlayer.isOnline()) {
                        if(InviteAcceptListener.isTradeRequest(checkPlayer.getUniqueId().toString(), player.getUniqueId().toString())) {
                            sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite_deny.trade-was-denied", playerUUID)), checkPlayer.getName()));
                            checkPlayer.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite_deny.trade-denied", playerUUID)), player.getName()));
                            InviteAcceptListener.removeTradeRequest(checkPlayer.getUniqueId().toString(), player.getUniqueId().toString());
                        } else {
                            sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite_deny.trade-not-denied", playerUUID)), checkPlayer.getName()));
                        }
                    } else {
                        if(checkPlayer != null) {
                            sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite.player_is_not_online", playerUUID)), checkPlayer.getName()));
                        } else {
                            sender.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.invite.player_does_not_exist", playerUUID)), args[0]));
                        }
                    }
                } else {
                    sender.sendMessage(Objects.requireNonNull(t.t("builder_team.wrong_arguments", playerUUID)));
                }

        } else {
            sender.sendMessage(Objects.requireNonNull(t.t("builder_team.tradeDeny-no-player", playerUUID)));
        }

        return true;
    }}
