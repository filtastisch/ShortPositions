package net.filtastisch.shortpositions.utils.types;

import lombok.Getter;
import lombok.Setter;
import net.filtastisch.shortpositions.ShortPositions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This file is a JavaDoc
 * created: 13.08.2022
 *
 * @author filtastisch
 * Discord: filtastisch#7683
 */

@Getter
@Setter
public class Position {

    private String posName;
    private Location location;
    private String creator;

    public static List<Position> all = new ArrayList<>();

    public Position(String posName, Location location, String creator) {
        this.posName = posName;
        this.location = location;
        this.creator = creator;
        if (!isPosition(posName)) {
            all.add(this);
        }
    }

    /*Statics Start*/

    public static void add(String posName, Location loc, String creator) {
        new Position(posName, loc, creator);
    }

    public static String noPosition(String posName) {
        return ShortPositions.getPlugin().getPrefix() + "§cDie Position §e" + posName + " §cexistiert nich!";
    }

    public static String getFancyPositionList() {
        StringBuilder sb = new StringBuilder();
        if (!all.isEmpty()) {
            sb.append(ShortPositions.getPlugin().getAvailablePositions());
            int count = 1;
            for (Position current : all) {
                if (count == all.size()) {
                    sb.append("\n§7└ ").append(ChatColor.GOLD).append(current.getPosName());
                } else {
                    sb.append("\n§7├ ").append(ChatColor.GOLD).append(current.getPosName());
                }
                count++;
            }
            return sb.toString();
        }
        return ShortPositions.getPlugin().getPrefix() + ShortPositions.getPlugin().getNoPositions();
    }

    public static List<String> getPosList(){
        List<String> pos = new ArrayList<>();
        if (!all.isEmpty()){
            for(Position current : all){
                pos.add(current.getPosName());
            }
        }
        return pos;
    }
    public static boolean isPosition(String posName) {
        for (Position current : all) {
            if (current.getPosName().equals(posName)) {
                return true;
            }
        }
        return false;
    }

    public static Position getFromName(String name) {
        for (Position current : all) {
            if (current.getPosName().equals(name)) {
                return current;
            }
        }
        return null;
    }

    /*Statics End*/

    public String sendPosition() {
        StringBuilder sb = new StringBuilder();
        sb.append("§aPosition: \n §7─ §6").append(this.posName);
        sb.append("\n     §7├ §6Creator: §e").append(this.getCreator());
        sb.append("\n     §7├ §6X: §e").append(this.location.getBlockX());
        sb.append("\n     §7├ §6Y: §e").append(this.location.getBlockY());
        sb.append("\n     §7├ §6Z: §e").append(this.location.getBlockZ());

        if (Objects.requireNonNull(this.location.getWorld()).getName().equals("world")) {
            sb.append("\n     §7└ §6World: §aWorld");
        } else if (Objects.requireNonNull(this.location.getWorld()).getName().equals("world_nether")) {
            sb.append("\n     §7└ §6World: §cNether");
        } else if (Objects.requireNonNull(this.location.getWorld()).getName().equals("world_the_end")) {
            sb.append("\n     §7└ §6World: §dEnd");
        } else {
            sb.append("\n     §7└ §6World:").append(Objects.requireNonNull(this.location).getWorld().getName());
        }
        return sb.toString();
    }

    public void teleport(Player p) {
        p.teleport(this.location);
    }

    public void remove() {
        all.remove(this);
    }
}
