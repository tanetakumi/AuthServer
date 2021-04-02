package net.serveron.hane.authserver;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;

public class AuthServerConfig {

    private final AuthServer plugin;
    private FileConfiguration config;

    //Discord
    private String discordWebhook;
    //PermitModList
    private List<String> permitModList;

    public AuthServerConfig(AuthServer plugin) {
        this.plugin = plugin;
        load();
    }

    @SuppressWarnings("unchecked")
    public void load() {
        // 設定ファイルを保存
        plugin.saveDefaultConfig();
        if (config != null) { // configが非null == リロードで呼び出された
            plugin.reloadConfig();
        }
        config = plugin.getConfig();

        discordWebhook = config.getString("discordWebhook");

        permitModList = config.getStringList("PermitMods");
    }

    public String getDiscordWebhook() { return discordWebhook; }

    public List<String> getPermitModList() {
        return permitModList;
    }

}
