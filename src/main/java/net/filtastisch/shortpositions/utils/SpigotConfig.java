package net.filtastisch.shortpositions.utils;

//CopyRight at Carlos17Kopra | Arvid

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class SpigotConfig {

    private Plugin plugin;

    private LinkedHashMap<String, Object> defaults = new LinkedHashMap<>();

    private String path;
    private String name;

    private YamlConfiguration cfg;
    private File file;


    public static SpigotConfig fromYamlConfiguration(Plugin plugin, YamlConfiguration cfg) {

        return new SpigotConfig(plugin, cfg.getCurrentPath(), cfg.getName());

    }

    public SpigotConfig(Plugin plugin, String name) {

        this.plugin = plugin;

        if (name.contains(".yml")) {
            this.name = name;
        } else {
            this.name = name + ".yml";
        }
        this.path = "plugins//" + plugin.getName() + "//";

    }

    public SpigotConfig(Plugin plugin, String path, String name) {

        this.plugin = plugin;

        if (name.contains(".yml")) {
            this.name = name;
        } else {
            this.name = name + ".yml";
        }
        this.path = path;

    }

    public SpigotConfig(Plugin plugin, String name, boolean doNotTouch) {

        this.plugin = plugin;

        if (name.contains(".yml")) {
            this.name = name;
        } else {
            this.name = name + ".yml";
        }
        if (!doNotTouch) {
            this.path = "plugins//" + plugin.getName() + "//";
        } else {
            File doNotTouchDir = new File("plugins//" + plugin.getName() + "//donottouch//");
            doNotTouchDir.mkdir();
            this.path = "plugins//" + plugin.getName() + "//donottouch//";
        }
    }

    public SpigotConfig register() {
        this.file = new File(this.path, this.name);
        this.cfg = YamlConfiguration.loadConfiguration(this.file);
        return this;
    }

    public SpigotConfig addDefault(String path, Object value) {
        defaults.put(path, value);
        return this;
    }


    public SpigotConfig save() {

        try {
            this.cfg.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }


    public SpigotConfig registerDefaults() {

        this.cfg.options().copyDefaults(true);

        for (String path : defaults.keySet()) {

            Object value = defaults.get(path);
            this.cfg.addDefault(path, value);

        }
        save();
        return this;
    }

    public Plugin getPlugin() {
        return plugin;
    }


    //getter

    public void setLocation(String path, Location loc) {
        cfg.set(path + ".World", loc.getWorld().getName());
        cfg.set(path + ".X", loc.getX());
        cfg.set(path + ".Y", loc.getY());
        cfg.set(path + ".Z", loc.getZ());
        cfg.set(path + ".Yaw", loc.getYaw());
        cfg.set(path + ".Pitch", loc.getPitch());
    }

    public void setLocation(String path, Location loc, String posCreator) {
        cfg.set(path + ".World", loc.getWorld().getName());
        cfg.set(path + ".X", loc.getX());
        cfg.set(path + ".Y", loc.getY());
        cfg.set(path + ".Z", loc.getZ());
        cfg.set(path + ".Yaw", loc.getYaw());
        cfg.set(path + ".Pitch", loc.getPitch());
        cfg.set(path + ".creator", posCreator);
    }

    public Location getLocation(String path) {

        if (cfg.get(path) == null) {
            return null;
        }

        String worldName = cfg.getString(path + ".World");
        double x = cfg.getDouble(path + ".X");
        double y = cfg.getDouble(path + ".Y");
        double z = cfg.getDouble(path + ".Z");
        float yaw = (float) cfg.getDouble(path + ".Yaw");
        float pitch = (float) cfg.getDouble(path + ".Pitch");
        World world = null;
        while (true) {
            if (worldName != null) {
                world = Bukkit.getWorld(worldName);
                break;
            }
        }
        return new Location(world, x, y, z, yaw, pitch);

    }

    public ConfigurationSection getConfigurationSection(String section) {
        return cfg.getConfigurationSection(section);
    }

    public String getString(String path) {
        return cfg.getString(path);
    }

    public String getString(String path, boolean chatcolor) {
        if (chatcolor) {
            return ChatColor.translateAlternateColorCodes('&', getString(path));
        }
        return cfg.getString(path);
    }

    public String getString(String path, boolean chatcolor, char tt) {
        if (chatcolor) {
            return ChatColor.translateAlternateColorCodes(tt, getString(path));
        }
        return cfg.getString(path);
    }


    public int getInt(String var1) {
        return cfg.getInt(var1);
    }


    public boolean getBoolean(String var1) {
        return cfg.getBoolean(var1);
    }


    public List<?> getList(String var1) {
        return cfg.getList(var1);
    }


}

