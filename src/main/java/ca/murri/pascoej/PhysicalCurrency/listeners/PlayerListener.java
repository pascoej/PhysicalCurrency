package ca.murri.pascoej.PhysicalCurrency.listeners;

import ca.murri.pascoej.PhysicalCurrency.Currency;
import ca.murri.pascoej.PhysicalCurrency.PhysicalCurrency;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by john on 5/3/14.
 */
public class PlayerListener implements Listener {
    PhysicalCurrency plugin;

    public PlayerListener(PhysicalCurrency plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void craft(CraftItemEvent event) {
        for (ItemStack itemStack : event.getInventory()) {
            if (itemStack != null) {
                if (Currency.isCurrency(itemStack)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
