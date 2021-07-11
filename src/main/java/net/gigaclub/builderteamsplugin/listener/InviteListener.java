package net.gigaclub.builderteamsplugin.listener;

import net.gigaclub.builderteamsplugin.Andere.HashMapHelper;
import net.gigaclub.builderteamsplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class InviteListener implements Listener {
    public static HashMap<String, String> inviteRequests = new HashMap<>();


    public static void addInvitequest(String sender_uuid, String receiver_uuid) {
        inviteRequests.put(sender_uuid, receiver_uuid);
    }

    public static boolean isInviteRequest(String sender_uuid, String receiver_uuid) {
        if(inviteRequests.containsKey(sender_uuid) && inviteRequests.containsValue(receiver_uuid)) {
            return true;
        }
        return false;
    }

    public static void removeInviteRequest(String sender_uuid, String receiver_uuid) {
        if(inviteRequests.containsKey(sender_uuid) && inviteRequests.containsValue(receiver_uuid)) {
            inviteRequests.remove(sender_uuid, receiver_uuid);
        }
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        String uuid = player.getUniqueId().toString();

        if(inviteRequests.containsKey(uuid)) {
            removeInviteRequest(uuid, inviteRequests.get(player.getUniqueId().toString()));
        } else if(inviteRequests.containsValue(uuid)) {
            String gettedUUIDString = HashMapHelper.getKey(inviteRequests, uuid);
            assert gettedUUIDString != null;
            UUID gettedUUID = UUID.fromString(gettedUUIDString);
            Player gettedPlayer = Bukkit.getPlayer(gettedUUID);
            assert gettedPlayer != null;
            gettedPlayer.sendMessage(t.t("BuilderTeams.Invite.PlayerLeave", playerUUID));
            removeInviteRequest(gettedUUIDString, uuid);
        }
    }


}
