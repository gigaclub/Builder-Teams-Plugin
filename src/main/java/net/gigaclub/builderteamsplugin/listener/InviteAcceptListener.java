package net.gigaclub.builderteamsplugin.listener;

import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.builderteamsplugin.Andere.HashMapHelper;
import net.gigaclub.builderteamsplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class InviteAcceptListener implements Listener {


    public static HashMap<String, String> tradeRequests = new HashMap<>();


    public static void addTradeRequest(String sender_uuid, String receiver_uuid) {
        tradeRequests.put(sender_uuid, receiver_uuid);
    }

    public static boolean isTradeRequest(String sender_uuid, String receiver_uuid) {
        if(tradeRequests.containsKey(sender_uuid) && tradeRequests.containsValue(receiver_uuid)) {
            return true;
        }
        return false;
    }

    public static void removeTradeRequest(String sender_uuid, String receiver_uuid) {
        if(tradeRequests.containsKey(sender_uuid) && tradeRequests.containsValue(receiver_uuid)) {
            tradeRequests.remove(sender_uuid, receiver_uuid);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();

        String uuid = player.getUniqueId().toString();
        if(tradeRequests.containsKey(uuid)) {
            removeTradeRequest(uuid, tradeRequests.get(player.getUniqueId().toString()));
        } else if(tradeRequests.containsValue(uuid)) {
            String gettedUUIDString = HashMapHelper.getKey(tradeRequests, uuid);
            assert gettedUUIDString != null;
            UUID gettedUUID = UUID.fromString(gettedUUIDString);
            Player gettedPlayer = Bukkit.getPlayer(gettedUUID);
            assert gettedPlayer != null;
            gettedPlayer.sendMessage(String.format(Objects.requireNonNull(t.t("builder_team.create.invite.InviteAcceptListener-player-quit", playerUUID)), player.getName()));
            removeTradeRequest(gettedUUIDString, uuid);
        }
    }

}
