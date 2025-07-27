package first.minecraft.plugin.testCowCannon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ConfigCommand implements CommandExecutor {
    private TestCowCannon testCowCannon;

    public ConfigCommand(TestCowCannon testCowCannon) {
        this.testCowCannon = testCowCannon;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        // accessing configuration from the class implemented JavaPlugIn
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(testCowCannon.getConfig().getString("Word"));
            player.sendMessage(testCowCannon.getConfig().getInt("Number") + "");

            if (testCowCannon.getConfig().getBoolean("Boolean")) {
                player.sendMessage("This feature is enabled!");
            }

            for (String string : testCowCannon.getConfig().getStringList("String-list")) {
                player.sendMessage(string);
            }

        }
        return false;
    }
}
