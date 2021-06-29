package eu.adiih.teamchest.listeners;

import com.andrei1058.bedwars.api.events.player.PlayerFirstSpawnEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReJoinEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import eu.adiih.teamchest.TeamChestPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedWarsListeners implements Listener {

    private final TeamChestPlugin plugin = TeamChestPlugin.getPlugin(TeamChestPlugin.class);
    private final FileConfiguration config = this.plugin.getConfiguration();

    private void giveChestItem(Player player) {
        if(!this.config.getBoolean("teamchest-item.enabled")) return;
        if(this.config.getBoolean("teamchest-item.permission-required")) {
            if(player.hasPermission(this.config.getString("teamchest-item.permission"))) {
                player.getInventory().setItem(this.config.getInt("teamchest-item.default-slot"), this.plugin.getChestItem());
            }
        } else {
            player.getInventory().setItem(this.config.getInt("teamchest-item.default-slot"), this.plugin.getChestItem());
        }
    }

    @EventHandler
    public void onFirstSpawn(PlayerFirstSpawnEvent event) {
        this.giveChestItem(event.getPlayer());
    }

    @EventHandler
    public void onRespawn(PlayerReSpawnEvent event) {
        this.giveChestItem(event.getPlayer());
    }

    @EventHandler
    public void onRejoin(PlayerReJoinEvent event) {
        this.giveChestItem(event.getPlayer());
    }

}
