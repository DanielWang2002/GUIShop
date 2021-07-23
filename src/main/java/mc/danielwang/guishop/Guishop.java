package mc.danielwang.guishop;

import me.pikamug.localelib.LocaleLib;
import me.pikamug.localelib.LocaleManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Guishop extends JavaPlugin {

    private LocaleManager localeManager;
    private static Economy econ = null;
    private static Guishop instance;

    @Override
    public void onEnable() {

        instance = this;

        //LocaleLib 翻譯API
        LocaleLib localeLib = (LocaleLib) getServer().getPluginManager().getPlugin("LocaleLib");
        if (localeLib != null) { localeManager = localeLib.getLocaleManager(); }

        //Vault 經濟API
        if (!setupEconomy() ) {
            System.out.println("No economy plugin found. Disabling Vault");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.saveDefaultConfig();

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
    public static Guishop getPlugin(){
        return instance;
    }
}
