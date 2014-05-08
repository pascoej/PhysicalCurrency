package ca.murri.pascoej.PhysicalCurrency.commands;

import ca.murri.pascoej.PhysicalCurrency.Currency;
import ca.murri.pascoej.PhysicalCurrency.PhysicalCurrency;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by john on 5/3/14.
 */
public class IssueCommand implements CommandExecutor{
    PhysicalCurrency plugin;
    MessageDigest messageDigest;
    public IssueCommand(PhysicalCurrency plugin) {
        this.plugin = plugin;
        plugin.getCommand("issue").setExecutor(this);
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public static String hash(String base, int length) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]).toUpperCase();
                if (hex.length() == 1)
                    stringBuffer.append('0');
                stringBuffer.append(hex);
            }
            return stringBuffer.toString().substring(0,length-1);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "error";
    }
    public String getShortCode(String pass) {
        return hash(pass, 6);
    }
    public String getLong(String pass) {
        return hash(pass,30);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player))
            return false;
        Player p = (Player) commandSender;
        if (args.length < 4)
            return false;
        int denom = 0;
        try {
            denom = Integer.valueOf(args[0]);
        }catch (Exception e) {
            return false;
        }
        if (denom % 10 != 0) {
            p.sendMessage(ChatColor.RED + "Denomination must be a multiple of 10.");
            return true;
        }
        String name = args[1];
        name = name.replace("_"," ");
        if (name.length() > 20) {
            name = name.substring(0,19);
        }
        ChatColor color = null;
        try {
            color = ChatColor.valueOf(args[2].toUpperCase());
        }catch (Exception e) {
            p.sendMessage(getValidColorMessage());
            return true;
        }
        if (color == null || !plugin.getValidColors().contains(color)) {
            p.sendMessage(getValidColorMessage());
            return true;
        }
        String pass = args[3];
        String saltedPass = pass + plugin.getSalt();
        String message = "";
        if (args.length > 4) {
            for (int i = 4; i < args.length; i++) {
                message = message + args[i] + " ";
            }
        }
        if (message.length() > 30) {
            message = message.substring(0,29);
        }
        message = message.replace("_"," ");
        ItemStack oldItem = p.getItemInHand();
        if (oldItem == null || oldItem.getType() == Material.AIR) {
            p.sendMessage(ChatColor.RED + "You must be holding an item to convert into currency!");
        }
        ItemStack currency = Currency.createCurrency(name,denom,color,getShortCode(saltedPass),getLong(saltedPass),message);
        currency.setAmount(oldItem.getAmount());
        p.setItemInHand(currency);
        p.sendMessage(ChatColor.GREEN + "Issued " + oldItem.getAmount() + " " + currency.getItemMeta().getDisplayName());
        return true;
    }
    public String getValidColorMessage() {
        String message = "Valid currency colors are: ";
        for (ChatColor color : plugin.getValidColors()) {
            message = message + color + color.name().toLowerCase() + ChatColor.WHITE + ", ";
        }
        if (plugin.getValidColors().size() > 0) {
            message = message.substring(0,message.length()-3);
        }
        message = message + ".";
        return message;
    }
}
