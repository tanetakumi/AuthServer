package net.serveron.hane.authserver;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.nio.charset.StandardCharsets;

public class MessageReceiver implements PluginMessageListener {

    private AuthServer plugin;

    public MessageReceiver(AuthServer plugin){
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data){
        if(channel.equalsIgnoreCase("minecraft:band")){
            if(new String(data, StandardCharsets.UTF_8).equals("forge")){
                plugin.addPlayer(player.getName());

            }
        }
    }
}
