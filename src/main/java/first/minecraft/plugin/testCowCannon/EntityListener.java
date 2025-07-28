package first.minecraft.plugin.testCowCannon;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class EntityListener implements Listener {

    // map storing user permissions
    private Map<UUID, PermissionAttachment> permissions = new HashMap<>();

    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent e) {
        // print the right-clicked entity
        System.out.println("Right Click: " + e.getRightClicked().getClass().getName());
        // trigger the cow explosion when right click with a bucket
        // check if event fired on the main hand
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }

        // Get the player who interacted
        Player player = e.getPlayer();

        // Get the entity that was right-clicked
        Entity entity = e.getRightClicked();

        // manage permission block
        // iterate through player.getEffectivePermissions() as foreach
        for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
            PermissionAttachment attachment = permission.getAttachment();
            // print all active permissions
            System.out.println("Permission: " + permission.getPermission() + " from " + (attachment == null ? "default" : attachment.getPlugin().getName()));
        }
        // toggle permissions
        if (permissions.containsKey(player.getUniqueId())) {
            // remove all permissions if existed
            player.removeAttachment(permissions.remove(player.getUniqueId()));

            player.sendMessage("You no longer have the permissions!");
        } else {
            // reassign the permission (command to spawn cow bomb)
            permissions.put(player.getUniqueId(), player.addAttachment(TestCowCannon.getInstance(), "cowcannon.command.cow", true));

            player.sendMessage(ChatColor.GREEN + "You now have the permission to spawn cow!");
        }

        // Check if the entity is a cow (spawned using /cow),
        // and the player is holding a bucket in their hand
        if (entity instanceof Cow
                && entity.hasMetadata("cow")
                && player.getItemInHand().getType() == Material.BUCKET) {

            // check for permissions
            if (!player.hasPermission("cowcannon.cow.use")) {
                player.sendMessage(ChatColor.RED + "You don't have permission!");
                return;
            }
            // Cast the entity to a Cow
            Cow cow = (Cow) entity;

            // Create an explosion at the cow's location with power 2.5 (TNT is 4.0)
            cow.getWorld().createExplosion(cow.getLocation(), 2.5f);
        }
    }
}
