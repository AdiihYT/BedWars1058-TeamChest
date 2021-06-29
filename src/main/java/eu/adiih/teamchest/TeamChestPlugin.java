package eu.adiih.teamchest;

import eu.adiih.teamchest.listeners.BedWarsListeners;
import eu.adiih.teamchest.listeners.GameStateChangeListener;
import eu.adiih.teamchest.listeners.InventoryListeners;
import eu.adiih.teamchest.listeners.PlayerListeners;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public class TeamChestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.checkDependency();
        this.setupConfiguration();
        InventoryManager.initializeHashMap();
        this.registerListeners();
    }

    public FileConfiguration getConfiguration() {
        return this.getConfig();
    }

    private void setupConfiguration() {
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
    }

    private void checkDependency() {
        if(this.getServer().getPluginManager().getPlugin("BedWars1058") == null) {
            this.getLogger().severe("BedWars1058 was not found. Disabling...");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new BedWarsListeners(), this);
        this.getServer().getPluginManager().registerEvents(new GameStateChangeListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryListeners(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
    }

    public ItemStack getChestItem() {
        ItemStack itemStack = new ItemStack(Material.matchMaterial(this.getConfig().getString("teamchest-item.material")), 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("teamchest-item.display-name")));
        List<String> loreLines = new ArrayList<String>();
        for(String line : this.getConfig().getStringList("teamchest-item.lore"))
            loreLines.add(ChatColor.translateAlternateColorCodes('&', line));
        meta.setLore(loreLines);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
