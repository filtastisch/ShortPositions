package net.filtastisch.shortpositions;

import lombok.Getter;
import lombok.Setter;
import net.filtastisch.shortpositions.commands.PositionsCommand;
import net.filtastisch.shortpositions.tabcompleter.PositionsTabCompleter;
import net.filtastisch.shortpositions.utils.SpigotConfig;
import net.filtastisch.shortpositions.utils.types.Position;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShortPositions extends JavaPlugin {

    @Getter
    public static ShortPositions plugin;

    @Getter
    private String prefix;

    private String positionCreated;
    private String noPositions;
    private String availablePositions;

    @Getter @Setter
    private SpigotConfig defaultConfig, messageConfig, positionConfig;

    @Override
    public void onEnable() {
        plugin = this;
        this.registerConfigs();
        this.loadValues();
        this.loadPositions();
        this.loadCommands();
    }

    @Override
    public void onDisable() {
        this.safePositions();
    }

    public void loadCommands(){
        this.getCommand("position").setExecutor(new PositionsCommand());
        this.getCommand("position").setTabCompleter(new PositionsTabCompleter());
    }

    public void setDefaultConfig(){
        this.defaultConfig.addDefault("General.Prefix", "&7[&2ShortPosition&7]&r ");
    }


    public void setMessageConfig(){
        this.messageConfig.addDefault("Position.Created", "&aPosition &6%position% &ahas been created!");
        this.messageConfig.addDefault("Position.nopositions", "&cNo positions available!");
        this.messageConfig.addDefault("Position.availableposmessage", "&aAvailable Positions: ");
    }

    public void loadValues(){
        this.prefix = this.defaultConfig.getString("General.Prefix");
        this.noPositions = this.messageConfig.getString("Position.nopositions");
        this.positionCreated = this.messageConfig.getString("Position.Created");
        this.availablePositions = this.messageConfig.getString("Position.availableposmessage");
    }

    public void loadPositions(){
        for (String pos : this.positionConfig.getConfigurationSection("").getKeys(false)){
            Position.add(pos, this.positionConfig.getLocation(pos), this.positionConfig.getString(pos + ".creator"));
        }
    }

    public void safePositions(){
        for (Position pos : Position.all){
            this.positionConfig.setLocation(pos.getPosName(), pos.getLocation(), pos.getCreator());
        }
        this.positionConfig.save();
    }

    public void registerConfigs(){
        this.defaultConfig = new SpigotConfig(this, "config.yml").register();
        this.messageConfig = new SpigotConfig(this, "messages.yml").register();
        this.positionConfig = new SpigotConfig(this, "positions.yml", true).register();

        this.setDefaultConfig();
        this.setMessageConfig();

        this.messageConfig.registerDefaults();
        this.defaultConfig.registerDefaults();
        this.positionConfig.registerDefaults();
    }

    public String getPrefix() {
        return prefix.replace('&', '§');
    }

    public String getPositionCreated(String pos) {
        return positionCreated.replace('&', '§').replaceAll("%position%", pos);
    }

    public String getNoPositions() {
        return noPositions.replace('&', '§');
    }

    public String getAvailablePositions() {
        return availablePositions.replace('&', '§');
    }
}
