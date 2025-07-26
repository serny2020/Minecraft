package first.minecraft.plugin.testCowCannon;

import org.bukkit.plugin.java.JavaPlugin;

public final class TestCowCannon extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("@ My first Minecraft plugin has been enabled!");
        getServer().getPluginManager().registerEvents(new EntityListener(), this);

        // register command classes
        getCommand("heal").setExecutor(new HealCommand()); // restore heal
        getCommand("cow").setExecutor(new CowCommand()); // command with tab auto complete to spawn a cow
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("@ Minecraft plugin has been disabled!");
    }

    public static TestCowCannon getInstance() {
        return getPlugin(TestCowCannon.class);
    }
}
