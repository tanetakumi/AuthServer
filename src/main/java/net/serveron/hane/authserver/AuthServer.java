package net.serveron.hane.authserver;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class AuthServer extends JavaPlugin {

    private final List<String> playerList = new ArrayList<>();
    private ExecutorService threadPool;

    @Override
    public void onEnable() {
        threadPool = Executors.newFixedThreadPool(3);
        new AuthCommand(this);
        getServer().getMessenger().registerIncomingPluginChannel(this,"minecraft:brand" ,new MessageReceiver(this));
    }

    @Override
    public void onDisable() {
        threadPool.shutdown();
        HandlerList.unregisterAll();
    }

    public void addPlayer(String name){
        if(!playerList.contains(name)){
            playerList.add(name);
        }
    }

    public void removePlayer(String name){
        playerList.remove(name);
    }

    public boolean containPlayerList(String name){
        return playerList.contains(name);
    }
}
