package first.minecraft.plugin.testCowCannon;

import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent e) {
        // print the right-clicked entity
        System.out.println("Right Click: " + e.getRightClicked().getClass().getName());
        if (e.getRightClicked() instanceof Cow) {
            Cow cow = (Cow) e.getRightClicked();
            cow.getWorld().createExplosion(cow.getLocation(), 2.5f);
        }
        
        // trigger the cow explosion when right click with a bucket
        // check if event fired on the main hand
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }

        // Get the player who interacted
        Player player = e.getPlayer();

        // Get the entity that was right-clicked
        Entity entity = e.getRightClicked();

        // Check if the entity is a cow (spawned using /cow),
        // and the player is holding a bucket in their hand
        if (entity instanceof Cow
                && entity.hasMetadata("cow")
                && player.getItemInHand().getType() == Material.BUCKET) {

            // Cast the entity to a Cow
            Cow cow = (Cow) entity;

            // Create an explosion at the cow's location with power 2.5 (TNT is 4.0)
            cow.getWorld().createExplosion(cow.getLocation(), 2.5f);
        }
    }
}
