package eu.adiih.teamchest;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InventoryManager {

    public static HashMap<ITeam, Inventory> TEAM_INVENTORIES;

    public static void initializeHashMap() {
        TEAM_INVENTORIES = new HashMap<ITeam, Inventory>();
    }

    public Inventory getTeamInventory(Player player) {
        return TEAM_INVENTORIES.get(BedWars.getAPI().getArenaUtil().getArenaByPlayer(player).getTeam(player));
    }

}
