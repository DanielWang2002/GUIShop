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
import org.yaml.snakeyaml.tokens.DirectiveToken;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class openCommand implements CommandExecutor {
    public String Prefix = ChatColor.GOLD + "伺服器商城 >> " + ChatColor.WHITE;
    public ItemStack Back = new ItemStack(Material.FEATHER);//返回鍵
    public ItemMeta Back_Meta = Back.getItemMeta();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Back_Meta.setDisplayName(ChatColor.RED + "返回上一頁");
        Back.setItemMeta(Back_Meta);
        if (args.length == 0){
            player.sendMessage(ChatColor.YELLOW + "----------" + ChatColor.RED + "伺服器商城 指令小幫手" + ChatColor.YELLOW + "----------");
            player.sendMessage(ChatColor.AQUA + "/gs open " + ChatColor.WHITE + "開啟商城介面");
        }else if (args.length == 1){
            if (args[0].equalsIgnoreCase("open") && sender instanceof Player){
                openBuySellGUI(player);
            }else{
                player.sendMessage(ChatColor.RED + "無效參數 請使用/gs 查詢可用指令");
            }
        }
        return false;
    }

    public void openBuySellGUI(Player player){
        Back_Meta.setDisplayName(ChatColor.RED + "返回上一頁");
        Back.setItemMeta(Back_Meta);
        Inventory Inv = Bukkit.createInventory(null,9,ChatColor.DARK_RED + "伺服器商城");
        ItemStack Buy = new ItemStack(Material.GREEN_WOOL);
        ItemStack Sell = new ItemStack(Material.RED_WOOL);
        ItemMeta BuyMeta = Buy.getItemMeta();
        ItemMeta SellMeta = Sell.getItemMeta();

        BuyMeta.setDisplayName(ChatColor.GREEN + "購買");
        SellMeta.setDisplayName(ChatColor.RED + "出售");
        Buy.setItemMeta(BuyMeta);
        Sell.setItemMeta(SellMeta);

        Inv.setItem(2,Buy);
        Inv.setItem(6,Sell);

        player.openInventory(Inv);
    }
    public void openBuyGUI(Player player){
        Back_Meta.setDisplayName(ChatColor.RED + "返回上一頁");
        Back.setItemMeta(Back_Meta);
        Inventory Buy_Inv = Bukkit.createInventory(null,27,ChatColor.DARK_GREEN + "購買");
        ItemStack Cobblestone = new ItemStack(Material.COBBLESTONE);
        ItemStack Dirt = new ItemStack(Material.DIRT);
        ItemMeta Cobblestone_Meta = Cobblestone.getItemMeta();
        ItemMeta Dirt_Meta = Dirt.getItemMeta();

        ArrayList<String> Cobblestone_Lore = new ArrayList<>();
        Cobblestone_Lore.add(ChatColor.GREEN + "購買價 " + ChatColor.YELLOW + "1" + ChatColor.GREEN + " 遊戲幣");
        Cobblestone_Lore.add("");
        Cobblestone_Lore.add(ChatColor.GOLD + "左鍵 購買 1 個");
        Cobblestone_Lore.add(ChatColor.GOLD + "右鍵 購買 10 個");
        Cobblestone_Meta.setLore(Cobblestone_Lore);

        ArrayList<String> Dirt_Lore = new ArrayList<>();
        Dirt_Lore.add(ChatColor.GREEN + "購買價 " + ChatColor.YELLOW + "2" + ChatColor.GREEN + " 遊戲幣");
        Dirt_Lore.add("");
        Dirt_Lore.add(ChatColor.GOLD + "左鍵 購買 1 個");
        Dirt_Lore.add(ChatColor.GOLD + "右鍵 購買 10 個");
        Dirt_Meta.setLore(Dirt_Lore);

        Cobblestone.setItemMeta(Cobblestone_Meta);
        Dirt.setItemMeta(Dirt_Meta);

        Buy_Inv.setItem(0,Cobblestone);
        Buy_Inv.setItem(1,Dirt);
        Buy_Inv.setItem(26,Back);

        player.openInventory(Buy_Inv);
    }
    public void openSellGUI(Player player){
        Back_Meta.setDisplayName(ChatColor.RED + "返回上一頁");
        Back.setItemMeta(Back_Meta);
        Inventory Sell_Inv = Bukkit.createInventory(null,27,ChatColor.DARK_GREEN + "出售");
        ItemStack Oak_planks = new ItemStack(Material.OAK_PLANKS);
        ItemStack Diamond = new ItemStack(Material.DIAMOND);
        ItemMeta Oak_planks_Meta = Oak_planks.getItemMeta();
        ItemMeta Diamond_Meta = Diamond.getItemMeta();

        ArrayList<String> Oak_planks_Lore = new ArrayList<>();
        Oak_planks_Lore.add(ChatColor.GREEN + "出售價 " + ChatColor.YELLOW + "1" + ChatColor.GREEN + " 遊戲幣");
        Oak_planks_Lore.add("");
        Oak_planks_Lore.add(ChatColor.GOLD + "左鍵 出售 1 個");
        Oak_planks_Lore.add(ChatColor.GOLD + "右鍵 出售 10 個");
        Oak_planks_Meta.setLore(Oak_planks_Lore);

        ArrayList<String> Diamond_Lore = new ArrayList<>();
        Diamond_Lore.add(ChatColor.GREEN + "出售 " + ChatColor.YELLOW + "2" + ChatColor.GREEN + " 遊戲幣");
        Diamond_Lore.add("");
        Diamond_Lore.add(ChatColor.GOLD + "左鍵 出售 1 個");
        Diamond_Lore.add(ChatColor.GOLD + "右鍵 出售 10 個");
        Diamond_Meta.setLore(Diamond_Lore);

        Oak_planks.setItemMeta(Oak_planks_Meta);
        Diamond.setItemMeta(Diamond_Meta);

        Sell_Inv.setItem(0,Oak_planks);
        Sell_Inv.setItem(1,Diamond);
        Sell_Inv.setItem(26,Back);

        player.openInventory(Sell_Inv);
    }
}
