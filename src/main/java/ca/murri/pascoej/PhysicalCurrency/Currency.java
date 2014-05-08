package ca.murri.pascoej.PhysicalCurrency;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 5/3/14.
 */
public class Currency {
    private static String securityCode = ChatColor.GOLD + "SECURITY CODE:";
    public static ItemStack createCurrency(String name, int denom, ChatColor color,  String shortHash, String longHash, String message) {
        ItemStack currency = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = currency.getItemMeta();
        itemMeta.setDisplayName(color + "" + ChatColor.BOLD + denom +" "+  name +ChatColor.RESET +  " " +  ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC  + ChatColor.UNDERLINE +  shortHash);
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.WHITE + "" + message);
        lore.add(securityCode);
        lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + longHash);
        itemMeta.setLore(lore);
        currency.setItemMeta(itemMeta);
        return currency;
    }
    public static boolean isCurrency(ItemStack item) {
        if (!item.hasItemMeta())
            return false;
        if (!item.getItemMeta().hasLore())
            return false;
        for (String lore : item.getItemMeta().getLore()) {
            if (lore.equals(securityCode))
                return true;
        }
        return false;
    }
}
