package eu.adiih.teamchest.listeners;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import eu.adiih.teamchest.InventoryManager;
import eu.adiih.teamchest.TeamChestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStateChangeListener implements Listener {

    private final TeamChestPlugin plugin = TeamChestPlugin.getPlugin(TeamChestPlugin.class);

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event) {
        GameState state = event.getNewState();
        switch(state) {
            case playing:
                for(ITeam team : event.getArena().getTeams()) {
                    InventoryManager.TEAM_INVENTORIES.put(team, this.plugin.getServer().createInventory(null, this.plugin.getConfiguration().getInt("teamchest-gui.rows") * 9, ChatColor.translateAlternateColorCodes('&', this.plugin.getConfiguration().getString("teamchest-gui.title"))));
                }
                break;
            case restarting:
                for(ITeam team : event.getArena().getTeams()) {
                    InventoryManager.TEAM_INVENTORIES.remove(team);
                }
                break;
        }
    }

}
