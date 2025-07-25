package first.minecraft.plugin.testCowCannon;

import org.bukkit.plugin.java.JavaPlugin;

public final class TestCowCannon extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("@ My first Minecraft plugin has been enabled!");
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("@ Minecraft plugin has been disabled!");
    }
}
