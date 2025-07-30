package first.minecraft.plugin.testCowCannon;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CowCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("set")) {
                EntityType type;

                try {
                    type = EntityType.valueOf(args[1].toUpperCase());
                } catch (IllegalArgumentException ex) {
                    sender.sendMessage("Invalid entity type: " + args[1]);
                    return true;
                }

                //checks whether the EntityType is not spawnable or not alive
                if (!type.isSpawnable() || !type.isAlive()) {
                    sender.sendMessage("You can only use living entities!");
                    return true;
                }

                CowSettings.getInstance().setExplodingType(type);
                sender.sendMessage(ChatColor.GREEN + "Set exploding type to " + type);
                return true;
            }
            return false;
        }
        Player player = (Player) sender;

//        Cow cow = player.getWorld().spawn(player.getLocation(), Cow.class);
        LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), CowSettings.getInstance().getExplodingType());

        if (args.length == 1 && args[0].equalsIgnoreCase("baby")) {
            if (entity instanceof Ageable)
                ((Ageable) entity).setBaby();
            else {
                sender.sendMessage("This entity can't be a baby");
                return true;
            }
        }

        // get the current spawn object
        entity.setMetadata("CowCannon", new FixedMetadataValue(TestCowCannon.getInstance(), true));

        // tag the object
        entity.setCustomName(ChatColor.RED + "Bomb entity");
        entity.setCustomNameVisible(true);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        // tab auto complete
        if (args.length == 1)
            return Arrays.asList("baby", "set");


        if (args.length == 2) {
            // Convert the second argument to uppercase for case-insensitive matching
            String name = args[1].toUpperCase();

            // Return a list of entity type names that:
            // 1. Are spawnable
            // 2. Are alive
            // 3. Start with the provided name
            return Arrays.stream(EntityType.values()) // Stream all EntityType enum values
                    .filter(type ->
                            type.isSpawnable() &&           // Ensure entity can be spawned
                                    type.isAlive() &&               // Ensure entity is a living being
                                    type.name().startsWith(name)    // Check if the name starts with user input
                    )
                    .map(Enum::name)                    // Convert EntityType to its name (String)
                    .collect(Collectors.toList());      // Collect results into a List<String>
        }
        return new ArrayList<>();
    }
}
