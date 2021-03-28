package net.serveron.hane.authserver;

import net.kyori.adventure.text.Component;
import net.serveron.hane.authserver.util.PlayerSearch;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AuthCommand implements CommandExecutor {
    private final AuthServer plugin;

    public AuthCommand(AuthServer plugin) {
        this.plugin = plugin;
        plugin.getCommand("auth").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof BlockCommandSender) {
            BlockCommandSender cb = (BlockCommandSender) sender;
            Player player = PlayerSearch.getNearbyPlayer(cb.getBlock().getLocation());
            if(player!=null) {
                player.sendMessage(Component.text("Authentication"));
                if(!plugin.containPlayerList(player.getName())){
                    player.sendMessage(Component.text("はねサーバー認証完了"));
                } else {
                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            if(plugin.containPlayerList(player.getName())){
                                player.kick(Component.text("はね鯖ランチャーより参加してください。\n" +
                                        "ランチャ―以外での複数回のログインはBAN対象です。"));
                            }
                        }
                    }.runTaskLater(plugin,40);
                }
            }
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                plugin.removePlayer(player.getName());
                player.sendMessage(Component.text("はねサーバー認証完了:MOD,データパック情報を記録しました。"));
            } else {
                player.kick(Component.text("認証コマンドエラー:ログに記録されました。"));
            }
        }
        return true;
    }
}
