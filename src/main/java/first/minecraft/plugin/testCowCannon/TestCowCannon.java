package first.minecraft.plugin.testCowCannon;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public final class TestCowCannon extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("@ My first Minecraft plugin has been enabled!");
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(this, this); // register the current class

        // register command classes
        getCommand("heal").setExecutor(new HealCommand()); // restore heal
        getCommand("cow").setExecutor(new CowCommand()); // command with tab auto complete to spawn a cow

        // accessing config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("config").setExecutor(new ConfigCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("@ Minecraft plugin has been disabled!");
    }

    public static TestCowCannon getInstance() {
        return getPlugin(TestCowCannon.class);
    }


    // add items to player
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        // Helmet - red leather
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.RED);
        helmet.setItemMeta(helmetMeta);
        event.getPlayer().getInventory().addItem(helmet);

        // Chestplate - custom colored leather
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE); // fixed here
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(org.bukkit.Color.fromRGB(179, 255, 255));
        chestplate.setItemMeta(chestplateMeta);
        event.getPlayer().getInventory().addItem(chestplate);

        // Create a diamond sword
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        event.getPlayer().getInventory().addItem(sword);

        // Create the Netherite Sword
        ItemStack nsword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = nsword.getItemMeta();

        // Add enchantments
        meta.addEnchant(Enchantment.getByName("DAMAGE_ALL"), 5, true); // Sharpness V
        meta.addEnchant(Enchantment.getByName("LOOT_BONUS_MOBS"), 3, true); // Looting III
        meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true); // Fire Aspect II
        meta.addEnchant(Enchantment.UNBREAKING, 3, true); // Unbreaking III
        meta.addEnchant(Enchantment.MENDING, 1, true); // Mending I
        meta.addEnchant(Enchantment.KNOCKBACK, 2, true); // Knockback II (optional)

        // Add description
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Forged in the flames of the Nether.");
        lore.add(ChatColor.DARK_PURPLE + "Wielded only by the worthy.");
        meta.setLore(lore);
        // Apply meta to item
        nsword.setItemMeta(meta);
        event.getPlayer().getInventory().addItem(nsword);

        // chestplate with Protection IV
        ItemStack nchestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta nchestplatemeta = nchestplate.getItemMeta();

        meta.addEnchant(Enchantment.THORNS, 3, true); // damage attackers
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addEnchant(Enchantment.getByName("DURABILITY"), 3, true); // Unbreaking III
        meta.addEnchant(Enchantment.getByName("PROTECTION_ENVIRONMENTAL"), 4, true); // Protection IV

        nchestplate.setItemMeta(nchestplatemeta);
        event.getPlayer().getInventory().setChestplate(nchestplate);


        // add bow
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        // Custom name
        bowMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Sky Piercer");
        // Add powerful enchantments
        bowMeta.addEnchant(Enchantment.getByName("ARROW_DAMAGE"), 5, true); // Power V
        bowMeta.addEnchant(Enchantment.getByName("ARROW_FIRE"), 1, true);   // Flame
        bowMeta.addEnchant(Enchantment.getByName("ARROW_KNOCKBACK"), 2, true); // Punch II
        bowMeta.addEnchant(Enchantment.getByName("DURABILITY"), 3, true);   // Unbreaking III
        bowMeta.addEnchant(Enchantment.MENDING, 1, true);      // Mending
        bowMeta.addEnchant(Enchantment.getByName("ARROW_INFINITE"), 1, true); // Infinity
        bow.setItemMeta(bowMeta);
        // Give the player the bow and 1 arrow (required for Infinity)
        event.getPlayer().getInventory().addItem(bow);
        event.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW));


        // Helmet
        ItemStack nhelmet = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta nhelmetMeta = nhelmet.getItemMeta();
        nhelmetMeta.setDisplayName(ChatColor.GOLD + "God Helmet");
        nhelmetMeta.addEnchant(Enchantment.getByName("PROTECTION_ENVIRONMENTAL"), 4, true); // Protection IV
        nhelmetMeta.addEnchant(Enchantment.getByName("DURABILITY"), 3, true); // Unbreaking III
        nhelmetMeta.addEnchant(Enchantment.getByName("MENDING"), 1, true); // Mending
        nhelmetMeta.addEnchant(Enchantment.getByName("THORNS"), 3, true); // Thorns III
        nhelmetMeta.addEnchant(Enchantment.getByName("OXYGEN"), 3, true); // Respiration III
        nhelmetMeta.addEnchant(Enchantment.getByName("WATER_WORKER"), 1, true); // Aqua Affinity
        nhelmet.setItemMeta(helmetMeta);
        event.getPlayer().getInventory().setHelmet(helmet);

        // Chestplate
        ItemStack gchestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta gchestMeta = gchestplate.getItemMeta();
        gchestMeta.setDisplayName(ChatColor.GOLD + "God Chestplate");
        gchestMeta.addEnchant(Enchantment.getByName("PROTECTION_ENVIRONMENTAL"), 4, true);
        gchestMeta.addEnchant(Enchantment.getByName("DURABILITY"), 3, true);
        gchestMeta.addEnchant(Enchantment.getByName("MENDING"), 1, true);
        gchestMeta.addEnchant(Enchantment.getByName("THORNS"), 3, true);
        chestplate.setItemMeta(gchestMeta);
        event.getPlayer().getInventory().setChestplate(chestplate);

        // Leggings
        ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName(ChatColor.GOLD + "God Leggings");
        legMeta.addEnchant(Enchantment.getByName("PROTECTION_ENVIRONMENTAL"), 4, true);
        legMeta.addEnchant(Enchantment.getByName("DURABILITY"), 3, true);
        legMeta.addEnchant(Enchantment.getByName("MENDING"), 1, true);
        legMeta.addEnchant(Enchantment.getByName("THORNS"), 3, true);
        leggings.setItemMeta(legMeta);
        event.getPlayer().getInventory().setLeggings(leggings);

        // Boots
        ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setDisplayName(ChatColor.GOLD + "God Boots");
        bootsMeta.addEnchant(Enchantment.getByName("PROTECTION_ENVIRONMENTAL"), 4, true);
        bootsMeta.addEnchant(Enchantment.getByName("DURABILITY"), 3, true);
        bootsMeta.addEnchant(Enchantment.getByName("MENDING"), 1, true);
        bootsMeta.addEnchant(Enchantment.getByName("THORNS"), 3, true);
        bootsMeta.addEnchant(Enchantment.getByName("PROTECTION_FALL"), 4, true); // Feather Falling IV
        bootsMeta.addEnchant(Enchantment.getByName("DEPTH_STRIDER"), 3, true); // Depth Strider III
        boots.setItemMeta(bootsMeta);
        event.getPlayer().getInventory().setBoots(boots);
        // Apply Speed Effect (Speed II for 1,000,000 ticks = ~13 hours)
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1, false, false));

        // add game title
        event.getPlayer().sendTitle(
                ChatColor.RED + "Hi!",
                ChatColor.GREEN + "Welcome to the server!",
                20, // 20 ticks = 1 second
                100, // stays for 5 seconds
                20
        );
        // add player list
        event.getPlayer().setPlayerListHeaderFooter(ChatColor.RED + "Hello", "This is \n player list footer.");
    }
}
