package net.serveron.hane.authserver.command;

import net.kyori.adventure.text.Component;
import net.serveron.hane.authserver.AuthServer;
import net.serveron.hane.authserver.util.DiscordWebhook;
import net.serveron.hane.authserver.util.PlayerSearch;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthCommand implements CommandExecutor {

    private final AuthServer plugin;

    private final List<String> permitModList;

    public AuthCommand(AuthServer plugin) {
        this.plugin = plugin;
        this.permitModList = plugin.getAuthServerConfig().getPermitModList();
        plugin.getCommand("auth").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof BlockCommandSender) {
            BlockCommandSender cb = (BlockCommandSender) sender;
            Player player = PlayerSearch.getNearbyPlayer(cb.getBlock().getLocation());
            if(player!=null) {
                if(!plugin.containPlayerList(player.getName())){
                    player.sendMessage(Component.text("はねサーバー認証完了"));
                    teleportAfterAuth(player,args);
                } else {
                    player.sendMessage(Component.text("Authentication"));
                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            if(plugin.containPlayerList(player.getName())){
                                player.kick(Component.text("はね鯖ランチャーより参加してください。\n" +
                                        "ランチャ―以外での複数回のログインはBAN対象です。"));
                            } else {
                                teleportAfterAuth(player,args);
                            }
                        }
                    }.runTaskLater(plugin,40);
                }
            }
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if(plugin.containPlayerList(player.getName())){
                    plugin.removePlayer(player.getName());

                    List<String> data = new ArrayList<>(Arrays.asList(args[0].split(":")));
                    for(String pm : permitModList){
                        data.removeIf(d -> d.toLowerCase().contains(pm));
                    }
                    plugin.runAsyncDiscord(player.getName()+data.toString());

                    player.sendMessage(Component.text("はねサーバー認証完了:MOD,データパック情報を記録しました。"));
                } else {
                    player.sendMessage(Component.text("はねサーバー認証は完了しています。"));
                }
            } else {
                player.kick(Component.text("認証コマンドエラー:ログに記録されました。"));
            }
        }
        return true;
    }

    private void teleportAfterAuth(Player player, String[] args){
        if(args.length==3){
            try{
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int z = Integer.parseInt(args[2]);
                player.teleportAsync(new Location(player.getWorld(),x,y,z));
            } catch (NumberFormatException ignored){}
        }
    }
}
