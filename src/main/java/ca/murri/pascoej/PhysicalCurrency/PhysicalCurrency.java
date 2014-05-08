package ca.murri.pascoej.PhysicalCurrency;

import ca.murri.pascoej.PhysicalCurrency.commands.IssueCommand;
import ca.murri.pascoej.PhysicalCurrency.listeners.PlayerListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 5/3/14.
 */
public class PhysicalCurrency extends JavaPlugin {
    private List<ChatColor> validColors;
    private String salt;
    public void onEnable() {
        this.saveDefaultConfig();
        validColors = new ArrayList<ChatColor>();
        for (String colorString : this.getConfig().getStringList("allowed-colors")) {
            validColors.add(ChatColor.valueOf(colorString));
        }
        salt = this.getConfig().getString("salt");
        new PlayerListener(this);
        new IssueCommand(this);
    }

    public List<ChatColor> getValidColors() {
        return validColors;
    }

    public String getSalt() {
        return salt;
    }
}
