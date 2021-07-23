package mc.danielwang.guishop;

import me.pikamug.localelib.LocaleManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ClickEvent implements Listener {

    private LocaleManager localeManager = new LocaleManager();
    public Economy econ = Guishop.getEconomy();
    private Guishop gs = Guishop.getPlugin();
    public String purchased_m = gs.getConfig().getString("message.purchased_message");
    public String sold_m = gs.getConfig().getString("message.sold_message");

    @EventHandler
    public void clickEvent (InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        openCommand Opencommand = new openCommand();
        String Prefix = Opencommand.Prefix;
        try{
            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',gs.getConfig().getString("message.home_page_name")))){
                if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL)){
                    Opencommand.openBuyGUI(player);
                }else if (e.getCurrentItem().getType().equals(Material.RED_WOOL)){
                    Opencommand.openSellGUI(player);
                }
                e.setCancelled(true);
            }
            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + gs.getConfig().getString("message.buy_shop_name"))){ //Buy Shop
                Material m = e.getCurrentItem().getType();
                ItemStack is = new ItemStack(m);

                gs.getConfig().getConfigurationSection("buyitems").getKeys(false).forEach(key -> {

                    ArrayList<String> Items = new ArrayList<>();
                    ItemStack item = null;

                    gs.getConfig().getConfigurationSection("buyitems").getConfigurationSection(key).getKeys(true).forEach(key2 -> {
                        Items.add(gs.getConfig().getString("buyitems." + key + "." + key2));
                    });
                    item = new ItemStack(Material.matchMaterial(Items.get(0)));
                    int price = Integer.parseInt(Items.get(1));
                    int Lclickamount = Integer.parseInt(Items.get(2));
                    int Rclickamount = Integer.parseInt(Items.get(3));
                    if (item.equals(is)){ //Click material in config.yml
                        if (e.getClick().isLeftClick()){
                            if (econ.has(player,price * Lclickamount)){
                                EconomyResponse response = econ.withdrawPlayer(player,price * Lclickamount);
                                //Purchased %Lclickamount% %material% cost response.amount Coin
                                localeManager.sendMessage(player,Prefix + ChatColor.GREEN + purchased_m
                                        .replace("%amount%",String.valueOf(Lclickamount))
                                        .replace("%material%","<item>" + ChatColor.GREEN)
                                        .replace("%price%",ChatColor.GOLD + Double.toString(response.amount) + ChatColor.GREEN),m,(short) 0, null);
                                is.setAmount(Lclickamount);
                                player.getInventory().addItem(is);
                            }else{
                                player.sendMessage(Prefix + ChatColor.RED + gs.getConfig().getString("message.non-enough_money"));
                            }
                        }else if (e.getClick().isRightClick()){
                            if (econ.has(player,price * Rclickamount)){
                                EconomyResponse response = econ.withdrawPlayer(player,price * Rclickamount);
                                localeManager.sendMessage(player,Prefix + ChatColor.GREEN + purchased_m
                                        .replace("%amount%",String.valueOf(Rclickamount))
                                        .replace("%material%","<item>" + ChatColor.GREEN)
                                        .replace("%price%",ChatColor.GOLD + Double.toString(response.amount) + ChatColor.GREEN),m,(short) 0, null);
                                is.setAmount(Rclickamount);
                                player.getInventory().addItem(is);
                            }else{
                                player.sendMessage(Prefix + ChatColor.RED + gs.getConfig().getString("message.non-enough_money"));
                            }
                        }
                    }
                });
                if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + gs.getConfig().getString("message.buy_shop_name"))){
                    if (e.getCurrentItem().getType().equals(Material.FEATHER)){
                        Opencommand.openBuySellGUI(player);
                    }
                }
                e.setCancelled(true);

            }else if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_RED + gs.getConfig().getString("message.sell_shop_name"))){
                Inventory player_inv = player.getInventory();
                Material m = e.getCurrentItem().getType();
                ItemStack is = new ItemStack(m);

                gs.getConfig().getConfigurationSection("sellitems").getKeys(false).forEach(key -> {

                    ArrayList<String> Items = new ArrayList<>();
                    ItemStack item = null;

                    gs.getConfig().getConfigurationSection("sellitems").getConfigurationSection(key).getKeys(true).forEach(key2 -> {
                        Items.add(gs.getConfig().getString("sellitems." + key + "." + key2));
                    });
                    item = new ItemStack(Material.matchMaterial(Items.get(0)));
                    int price = Integer.parseInt(Items.get(1));
                    int Lclickamount = Integer.parseInt(Items.get(2));
                    int Rclickamount = Integer.parseInt(Items.get(3));
                    if (item.equals(is)){ //Click material in config.yml
                        if (e.getClick().isLeftClick()){
                            if (player_inv.contains(m)){
                                EconomyResponse response = econ.depositPlayer(player, price * Lclickamount);
                                localeManager.sendMessage(player,Prefix + ChatColor.RED + sold_m
                                        .replace("%amount%",String.valueOf(Lclickamount))
                                        .replace("%material%","<item>" + ChatColor.RED)
                                        .replace("%price%",ChatColor.GOLD + Double.toString(response.amount) + ChatColor.RED),m,(short) 0, null);
                                is.setAmount(Lclickamount);
                                player_inv.removeItem(is);
                            }else{
                                localeManager.sendMessage(player,Prefix + ChatColor.RED +  gs.getConfig().getString("message.non-enough_item").replace("%material%","<item>" + ChatColor.RED),m,(short) 0, null);
                            }
                        }else if (e.getClick().isRightClick()){
                            if (player_inv.contains(m)){
                                EconomyResponse response = econ.withdrawPlayer(player,price * Rclickamount);
                                localeManager.sendMessage(player,Prefix + ChatColor.RED + sold_m
                                        .replace("%amount%",String.valueOf(Rclickamount))
                                        .replace("%material%","<item>" + ChatColor.RED)
                                        .replace("%price%",ChatColor.GOLD + Double.toString(response.amount) + ChatColor.RED),m,(short) 0, null);
                                is.setAmount(Rclickamount);
                                player_inv.removeItem(is);
                            }else{
                                localeManager.sendMessage(player,Prefix + ChatColor.RED +  gs.getConfig().getString("message.non-enough_item").replace("%material%","<item>" + ChatColor.RED),m,(short) 0, null);
                            }
                        }
                    }
                });

                if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_RED + gs.getConfig().getString("message.sell_shop_name"))){ //返回
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
