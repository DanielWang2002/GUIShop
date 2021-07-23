package mc.danielwang.guishop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class openCommand implements CommandExecutor {

    private Guishop gs = Guishop.getPlugin();
    public String Prefix = ChatColor.translateAlternateColorCodes('&',gs.getConfig().getString("message.prefix"));
    public String Previous_page = ChatColor.RED + gs.getConfig().getString("message.previous_page");
    public ItemStack Back = new ItemStack(Material.FEATHER);//返回鍵
    public ItemMeta Back_Meta = Back.getItemMeta();
    public String buy_shop_name = ChatColor.DARK_GREEN + gs.getConfig().getString("message.buy_shop_name");
    public String sell_shop_name = ChatColor.DARK_RED + gs.getConfig().getString("message.sell_shop_name");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            Back_Meta.setDisplayName(Previous_page);
            Back.setItemMeta(Back_Meta);
            if (args.length == 0){
                List<String> ch = gs.getConfig().getStringList("message.command_helper");
                for (String s : ch){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
                }
            }else if (args.length == 1){
                if (args[0].equalsIgnoreCase("open") && sender instanceof Player){
                    openBuySellGUI(player);
                }else if (args[0].equalsIgnoreCase("reload")) {
                    for (String msg : gs.getConfig().getStringList("message.reload")) {
                        player.sendMessage(Prefix + ChatColor.translateAlternateColorCodes('&', msg));
                    }
                    gs.reloadConfig();
                }else if (args[0].equalsIgnoreCase("help")){
                    List<String> ch = gs.getConfig().getStringList("message.command_helper");
                    for (String s : ch){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
                    }
                }else{
                    player.sendMessage(ChatColor.RED + gs.getConfig().getString("message.non-command"));
                }
            }
        }else{
            try {
                if (args[0].equalsIgnoreCase("reload")){
                    for (String msg : gs.getConfig().getStringList("message.reload")){
                        sender.sendMessage(Prefix + ChatColor.translateAlternateColorCodes('&',msg));
                    }
                    gs.reloadConfig();
                }
            }catch (Exception e){
                sender.sendMessage(Prefix + ChatColor.RED + " Console can't use this command!");
            }
        }

        return false;
    }

    public void openBuySellGUI(Player player){
        Inventory Inv = Bukkit.createInventory(null,9,ChatColor.translateAlternateColorCodes('&',gs.getConfig().getString("message.home_page_name")));
        ItemStack Buy = new ItemStack(Material.GREEN_WOOL);
        ItemStack Sell = new ItemStack(Material.RED_WOOL);
        ItemMeta BuyMeta = Buy.getItemMeta();
        ItemMeta SellMeta = Sell.getItemMeta();

        BuyMeta.setDisplayName(ChatColor.DARK_GREEN + gs.getConfig().getString("message.buy_shop_name"));
        SellMeta.setDisplayName(ChatColor.DARK_RED + gs.getConfig().getString("message.sell_shop_name"));
        Buy.setItemMeta(BuyMeta);
        Sell.setItemMeta(SellMeta);

        Inv.setItem(2,Buy);
        Inv.setItem(6,Sell);

        player.openInventory(Inv);
    }
    public void openBuyGUI(Player player){
        Back_Meta.setDisplayName(Previous_page);
        Back.setItemMeta(Back_Meta);
        Inventory Buy_Inv = Bukkit.createInventory(null,Integer.parseInt(gs.getConfig().getString("message.buy_shop_size")),buy_shop_name);
        AtomicInteger position = new AtomicInteger();

        gs.getConfig().getConfigurationSection("buyitems").getKeys(false).forEach(key -> {

            ArrayList<String>  Items = new ArrayList<>();
            List<String> item_Lore = gs.getConfig().getStringList("message.buy_item_lore");
            ItemStack item = null;
            ItemMeta item_meta = null;

            gs.getConfig().getConfigurationSection("buyitems").getConfigurationSection(key).getKeys(true).forEach(key2 -> {
                Items.add(gs.getConfig().getString("buyitems." + key + "." + key2));
            });
            item = new ItemStack(Material.matchMaterial(Items.get(0)));
            String price = Items.get(1);
            String Lclickamount = Items.get(2);
            String Rclickamount = Items.get(3);

            if (item != null){
                item_meta = item.getItemMeta();

                for(String s : item_Lore){
                    if (item_Lore.indexOf(s) == item_Lore.size()-1 || item_Lore.indexOf(s) == item_Lore.size()-2){ //last 2 line
                        item_Lore.set(item_Lore.indexOf(s),ChatColor.translateAlternateColorCodes('&',ChatColor.GOLD + s)
                                .replace("%price%",ChatColor.YELLOW + price + ChatColor.GREEN)
                                .replace("%Lclickamount%",Lclickamount)
                                .replace("%Rclickamount%",Rclickamount));
                    }else{
                        item_Lore.set(item_Lore.indexOf(s),ChatColor.translateAlternateColorCodes('&',ChatColor.GREEN + s)
                                .replace("%price%",ChatColor.YELLOW + price + ChatColor.GREEN)
                                .replace("%Lclickamount%",Lclickamount)
                                .replace("%Rclickamount%",Rclickamount));
                    }
                }

                item_meta.setLore(item_Lore);
                item.setItemMeta(item_meta);
            }else{
                player.sendMessage(Prefix + ChatColor.RED + gs.getConfig().getString("material_error"));
            }

            Buy_Inv.setItem(position.get(),item);
            position.getAndAdd(1);

        });

        Buy_Inv.setItem(Integer.parseInt(gs.getConfig().getString("message.buy_shop_size"))-1,Back);

        player.openInventory(Buy_Inv);
    }
    public void openSellGUI(Player player){
        Back_Meta.setDisplayName(Previous_page);
        Back.setItemMeta(Back_Meta);
        Inventory Sell_Inv = Bukkit.createInventory(null,Integer.parseInt(gs.getConfig().getString("message.sell_shop_size")),sell_shop_name);
        AtomicInteger position = new AtomicInteger();

        gs.getConfig().getConfigurationSection("sellitems").getKeys(false).forEach(key -> {

            ArrayList<String>  Items = new ArrayList<>();
            List<String> item_Lore = gs.getConfig().getStringList("message.sell_item_lore");
            ItemStack item = null;
            ItemMeta item_meta = null;

            gs.getConfig().getConfigurationSection("sellitems").getConfigurationSection(key).getKeys(true).forEach(key2 -> {
                Items.add(gs.getConfig().getString("sellitems." + key + "." + key2));
            });

            item = new ItemStack(Material.matchMaterial(Items.get(0)));
            String price = Items.get(1);
            String Lclickamount = Items.get(2);
            String Rclickamount = Items.get(3);

            if (item != null){
                item_meta = item.getItemMeta();

                for(String s : item_Lore){
                    if (item_Lore.indexOf(s) == item_Lore.size()-1 || item_Lore.indexOf(s) == item_Lore.size()-2){ //last 2 line
                        item_Lore.set(item_Lore.indexOf(s),ChatColor.translateAlternateColorCodes('&',ChatColor.GOLD + s)
                                .replace("%price%",ChatColor.YELLOW + price + ChatColor.RED)
                                .replace("%Lclickamount%",Lclickamount)
                                .replace("%Rclickamount%",Rclickamount));
                    }else{
                        item_Lore.set(item_Lore.indexOf(s),ChatColor.translateAlternateColorCodes('&',ChatColor.RED + s)
                                .replace("%price%",ChatColor.YELLOW + price + ChatColor.RED)
                                .replace("%Lclickamount%",Lclickamount)
                                .replace("%Rclickamount%",Rclickamount));
                    }
                }

                item_meta.setLore(item_Lore);
                item.setItemMeta(item_meta);
            }else{
                player.sendMessage(Prefix + ChatColor.RED + gs.getConfig().getString("message.material_error"));
            }

            Sell_Inv.setItem(position.get(),item);
            position.getAndAdd(1);

        });
        Sell_Inv.setItem(Integer.parseInt(gs.getConfig().getString("message.sell_shop_size"))-1,Back);

        player.openInventory(Sell_Inv);
    }
}
