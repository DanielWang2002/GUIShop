package mc.danielwang.guishop;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Guishop extends JavaPlugin {
    private static Economy econ = null;
    @Override
    public void onEnable() {
        if (!setupEconomy() ) {
            System.out.println("No economy plugin found. Disabling Vault");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("---------------");
        getLogger().info("Open GUIShop...");
        getLogger().info("---------------");

        getServer().getPluginManager().registerEvents(new ClickEvent(),this);

        this.getCommand("gs").setExecutor(new openCommand());//gui shop command
    }

    @Override
    public void onDisable() {
        getLogger().info("Disable GUIShop...");
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Economy getEconomy() {
        return econ;
    }
}
