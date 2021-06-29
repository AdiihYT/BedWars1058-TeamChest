package eu.adiih.teamchest.listeners;

import eu.adiih.teamchest.InventoryManager;
import eu.adiih.teamchest.TeamChestPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerListeners implements Listener {

    private final TeamChestPlugin plugin = TeamChestPlugin.getPlugin(TeamChestPlugin.class);
    private final FileConfiguration config = this.plugin.getConfiguration();
    private final InventoryManager inventoryManager = new InventoryManager();

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(event.getItemDrop().getItemStack().isSimilar(this.plugin.getChestItem())) event.setCancelled(true);
    }

    @EventHandler
    public void onSwapItems(PlayerSwapHandItemsEvent event) {
        if(event.getPlayer().getInventory().getItemInMainHand().isSimilar(this.plugin.getChestItem())) event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(!this.config.getBoolean("team-enderchest.enabled")) return;
        if(!event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) return;
        Player player = event.getPlayer();
        player.openInventory(this.inventoryManager.getTeamInventory(player));
        event.setCancelled(true);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if(!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK)) return;
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().isSimilar(this.plugin.getChestItem())) player.openInventory(this.inventoryManager.getTeamInventory(player));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getPlayer().getInventory().getItemInMainHand().isSimilar(this.plugin.getChestItem())) event.setCancelled(true);
    }

}
