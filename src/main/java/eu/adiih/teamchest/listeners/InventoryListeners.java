package eu.adiih.teamchest.listeners;

import eu.adiih.teamchest.InventoryManager;
import eu.adiih.teamchest.TeamChestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListeners implements Listener {

    private final TeamChestPlugin plugin = TeamChestPlugin.getPlugin(TeamChestPlugin.class);
    private final FileConfiguration config = this.plugin.getConfiguration();
    private final InventoryManager inventoryManager = new InventoryManager();

    private boolean isPermissionRequired() {
        return this.config.getBoolean("teamchest-item.permission-required");
    }

    private boolean isMoveable() {
        return this.config.getBoolean("teamchest-item.moveable");
    }

    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if(player.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', this.config.getString("teamchest-gui.title")))) {
            player.openInventory(inventoryManager.getTeamInventory(player));
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(isMoveable()) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        if(this.isPermissionRequired()) {
            if(event.getWhoClicked().hasPermission(this.config.getString("teamchest-item.permission"))) {
                if(currentItem != null && currentItem.isSimilar(this.plugin.getChestItem())) event.setCancelled(true);
                if(event.getHotbarButton() == this.config.getInt("teamchest-item.default-slot")) event.setCancelled(true);
            }
        } else {
            if(currentItem != null && currentItem.isSimilar(this.plugin.getChestItem())) event.setCancelled(true);
            if(event.getHotbarButton() == this.config.getInt("teamchest-item.default-slot")) event.setCancelled(true);
        }
    }

}
