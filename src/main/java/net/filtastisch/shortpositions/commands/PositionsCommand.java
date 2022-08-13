package net.filtastisch.shortpositions.commands;

import lombok.Getter;
import lombok.Setter;
import net.filtastisch.shortpositions.ShortPositions;
import net.filtastisch.shortpositions.utils.types.Position;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This file is a JavaDoc
 * created: 13.08.2022
 *
 * @author filtastisch
 * Discord: filtastisch#7683
 */

@Getter
@Setter
public class PositionsCommand implements CommandExecutor {

    ShortPositions plugin = ShortPositions.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("shortpositions.commands.positions")) {
                if (args.length == 1) {
                    if (!args[0].equalsIgnoreCase("list")) {
                        if (Position.isPosition(args[0])) {
                            Position position = Position.getFromName(args[0]);
                            assert position != null;
                            p.sendMessage(position.sendPosition());
                        } else {
                            Position.add(args[0], p.getLocation(), p.getName());
                            Bukkit.broadcastMessage(plugin.getPrefix() + plugin.getPositionCreated(args[0]));
                        }
                    } else {
                        p.sendMessage(Position.getFancyPositionList());
                    }
                } else {
                    p.sendMessage(Position.getFancyPositionList());
                }
            }
        }

        return false;
    }
}
