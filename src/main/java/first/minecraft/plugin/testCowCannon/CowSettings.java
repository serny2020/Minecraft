package first.minecraft.plugin.testCowCannon;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;

public class CowSettings {

    private final static CowSettings instance = new CowSettings();

    private File file;
    private YamlConfiguration config;

    private EntityType explodingType;

    private CowSettings() {
    }

    public void load() {
        file = new File(TestCowCannon.getInstance().getDataFolder(), "settings.yml");

        if (!file.exists()) {
            TestCowCannon.getInstance().saveResource("settings.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        explodingType = EntityType.valueOf(config.getString("Explosion.Entity_Type"));
    }


    public void save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public EntityType getExplodingType() {
        return explodingType;
    }

    public void setExplodingType(EntityType explodingType) {
        this.explodingType = explodingType;
        set("Explosion.Entity_Type", explodingType.name());
    }


    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public static CowSettings getInstance() {
        return instance;
    }
}