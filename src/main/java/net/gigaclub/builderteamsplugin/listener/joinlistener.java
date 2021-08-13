package net.gigaclub.builderteamsplugin.listener;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.service.CloudServiceConnectNetworkEvent;
import de.dytanic.cloudnet.driver.service.*;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.builderteamsplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;


import static net.gigaclub.builderteamsplugin.Config.Config.getConfig;

public class joinlistener implements Listener {
    private int serviceId;
    private Player player;
    private @NotNull BukkitTask taskID;
    @EventListener
    public void handleServiceConnected(CloudServiceConnectNetworkEvent event) {
        ServiceInfoSnapshot serviceInfoSnapshot = event.getServiceInfo(); //The serviceInfoSnapshot with all important information from a service

        ServiceLifeCycle serviceLifeCycle = serviceInfoSnapshot.getLifeCycle();
        ServiceId serviceId = serviceInfoSnapshot.getServiceId();

        if (this.serviceId == serviceId.getTaskServiceId()) {

            if (serviceLifeCycle == ServiceLifeCycle.RUNNING) {

                if (player != null) {
                    System.out.println(4);
                    List<? extends ICloudPlayer> cloudPlayers = this.playerManager.getOnlinePlayers(this.player.getName());
                    if (!cloudPlayers.isEmpty()) {
                        ICloudPlayer entry = cloudPlayers.get(0);
                        IPlayerManager playerManager = this.playerManager;
                        int serviceId1 = this.serviceId;
                        Player player2 = (Player)player ;
                        @NotNull BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                        taskID = scheduler.runTaskTimer(Main.getPlugin(), new Runnable() {
                            int countdown = 10;

                            public void run() {
                                if (countdown > 0) {
                                    player2.sendMessage(String.valueOf(countdown));
                                } else {
                                    playerManager.getPlayerExecutor(entry).connect(event.getServiceInfo().getServiceId().getTaskName() + "-" + serviceId1);
                                    scheduler.cancelTask(taskID.getTaskId());
                                    return;
                                }
                                countdown--;
                            }
                        }, 0, 20);
                    }}}}}

    private final IPlayerManager playerManager = CloudNetDriver.getInstance().getServicesRegistry()
            .getFirstService(IPlayerManager.class);

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();
        FileConfiguration config = getConfig();
        if (config.getBoolean("server.server_autostart")) {
            Object o = builderSystem.getTeamNameByMember(playerUUID);
            HashMap m = (HashMap) o;
            String team_name = m.get("name").toString();
            System.out.println(1);

                for (Object world_id : (Object[]) m.get("world_ids")) {
                    HashMap world_idMap = (HashMap) world_id;
                    System.out.println(1.1);
                    int word_id = Integer.parseInt(world_idMap.get("id").toString());

                    for (Object o1 : (Object[]) builderSystem.getWorld(word_id)) {
                        System.out.println(1.2);
                        HashMap m1 = (HashMap) o1;
                        String world_name = m1.get("name").toString();
                        Integer task_id = Integer.parseInt(m1.get("task_id").toString());
                        for (Object task_o : (Object[]) builderSystem.getTask(task_id)) {
                            System.out.println(1.3);
                            HashMap task_m = (HashMap) task_o;
                            String task_name = task_m.get("name").toString();
                            String worlds_typ = m1.get("world_type").toString();
                            //  world_name, task_name, task_id, worlds_typ, word_id, team_name
                            System.out.println(2);
                            player.sendMessage(t.t("bsc.Command.CreateServer", playerUUID));
                            player.sendMessage(t.t("bsc.Command.Teleport", playerUUID));
                            ServiceInfoSnapshot serviceInfoSnapshot = ServiceConfiguration.builder()
                                    .task(team_name + "_" + task_name+"_"+task_id +"_" + word_id)
                                    .node("Node-1")
                                    .autoDeleteOnStop(true)
                                    .staticService(false)
                                    .templates(new ServiceTemplate("Builder", worlds_typ, "local"))
                                    .groups("Builder")
                                    .maxHeapMemory(1525)
                                    .environment(ServiceEnvironmentType.MINECRAFT_SERVER)
                                    .build()
                                    .createNewService();


                            if (serviceInfoSnapshot != null) {
                                System.out.println(3);
                                serviceInfoSnapshot.provider().start();
                                serviceId = serviceInfoSnapshot.getServiceId().getTaskServiceId();
                            }
                        }

                    }
                }

        }
        }
    }

