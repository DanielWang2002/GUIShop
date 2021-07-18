package mc.danielwang.guishop;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {
    public Economy econ = Guishop.getEconomy();
    @EventHandler
    public void clickEvent (InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        openCommand Opencommand = new openCommand();
        String Prefix = Opencommand.Prefix;
        try{
            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_RED + "伺服器商城")){
                if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL)){
                    Opencommand.openBuyGUI(player);
                }else if (e.getCurrentItem().getType().equals(Material.RED_WOOL)){
                    Opencommand.openSellGUI(player);
                }
                e.setCancelled(true);
            }
            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "購買")){
                if (e.getCurrentItem().getType().equals(Material.COBBLESTONE)){ //鵝卵石
                    ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);
                    if (e.getClick().isLeftClick()){
                        if (econ.has(player,1)){
                            EconomyResponse response = econ.withdrawPlayer(player,1);
                            player.sendMessage(Prefix + ChatColor.GREEN + "已購買 1 鵝卵石 花費 " + ChatColor.GOLD +  response.amount + ChatColor.GREEN +  " 遊戲幣");
                            cobblestone.setAmount(1);
                            player.getInventory().addItem(cobblestone);
                        }else{
                            player.sendMessage(Prefix + ChatColor.RED + "金額不足");
                        }
                    }else if(e.getClick().isRightClick()){
                        if (econ.has(player,10)){
                            EconomyResponse response = econ.withdrawPlayer(player,10);
                            player.sendMessage(Prefix + ChatColor.GREEN + "已購買 10 鵝卵石 花費 " + ChatColor.GOLD +  response.amount + ChatColor.GREEN +  " 遊戲幣");
                            cobblestone.setAmount(10);
                            player.getInventory().addItem(cobblestone);
                        }else{
                            player.sendMessage(Prefix + ChatColor.RED + "金額不足");
                        }
                    }
                }else if (e.getCurrentItem().getType().equals(Material.DIRT)){
                    ItemStack dirt = new ItemStack(Material.DIRT); //泥土
                    if (e.getClick().isLeftClick()){
                        if (econ.has(player,1)){
                            EconomyResponse response = econ.withdrawPlayer(player,2);
                            player.sendMessage(Prefix + ChatColor.GREEN + "已購買 1 泥土 花費 " + ChatColor.GOLD +  response.amount + ChatColor.GREEN +  " 遊戲幣");
                            dirt.setAmount(1);
                            player.getInventory().addItem(dirt);
                        }else{
                            player.sendMessage(Prefix + ChatColor.RED + "金額不足");
                        }
                    }else if(e.getClick().isRightClick()){
                        if (econ.has(player,10)){
                            EconomyResponse response = econ.withdrawPlayer(player,20);
                            player.sendMessage(Prefix + ChatColor.GREEN+ "已購買 10 泥土 花費 " + ChatColor.GOLD +  response.amount + ChatColor.GREEN +  " 遊戲幣");
                            dirt.setAmount(10);
                            player.getInventory().addItem(dirt);
                        }else{
                            player.sendMessage(Prefix + ChatColor.RED + "金額不足");
                        }
                    }
                }
                if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "購買")){
                    if (e.getCurrentItem().getType().equals(Material.FEATHER)){
                        Opencommand.openBuySellGUI(player);
                    }
                }
                e.setCancelled(true);
            }else if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "出售")){
                Inventory player_inv = player.getInventory();

                if (e.getCurrentItem().getType().equals(Material.OAK_PLANKS)){
                    if (e.getClick().isLeftClick()){
                        if (player_inv.contains(Material.OAK_PLANKS,1)){
                            ItemStack Oak_Planks = new ItemStack(Material.OAK_PLANKS);
                            Oak_Planks.setAmount(1);
                            player_inv.removeItem(Oak_Planks);

                            EconomyResponse response = econ.depositPlayer(player,5);
                            player.sendMessage(Prefix + ChatColor.RED + "已出售 1 橡木材 獲得 " + ChatColor.GOLD + response.amount + ChatColor.RED + " 遊戲幣");
                        }else{
                            player.sendMessage(Prefix + ChatColor.RED + "你沒有足夠的數量可售出！");
                        }
                    }else if (e.getClick().isRightClick()){
                        if (player_inv.contains(Material.OAK_PLANKS,10)){
                            ItemStack Oak_Planks = new ItemStack(Material.OAK_PLANKS);
                            Oak_Planks.setAmount(10);
                            player_inv.removeItem(Oak_Planks);

                            EconomyResponse response = econ.depositPlayer(player,50);
                            player.sendMessage(Prefix + ChatColor.RED + "已出售 10 橡木材 獲得 " + ChatColor.GOLD + response.amount + ChatColor.RED + " 遊戲幣");
                        }else{
                            player.sendMessage(Prefix + ChatColor.RED + "你沒有足夠的數量可售出！");
                        }
                    }

                }

                if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "出售")){ //返回
                    if (e.getCurrentItem().getType().equals(Material.FEATHER)){
                        Opencommand.openBuySellGUI(player);
                    }
                }
                e.setCancelled(true);
            }
        }catch (NullPointerException NPE){

        }
    }
}
