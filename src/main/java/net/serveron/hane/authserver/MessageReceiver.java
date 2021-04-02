package net.serveron.hane.authserver;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import java.nio.charset.StandardCharsets;

public class MessageReceiver implements PluginMessageListener {

    private final AuthServer plugin;

    public MessageReceiver(AuthServer plugin){
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data){


        if(channel.equalsIgnoreCase("minecraft:brand")){
            if(new String(data, StandardCharsets.UTF_8).contains("forge")){
                System.out.println("[認証]"+player.getName());
                plugin.addPlayer(player.getName());
            }
        }
    }
}
