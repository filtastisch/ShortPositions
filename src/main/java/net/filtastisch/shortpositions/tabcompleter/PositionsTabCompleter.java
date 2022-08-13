package net.filtastisch.shortpositions.tabcompleter;

import net.filtastisch.shortpositions.utils.types.Position;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class PositionsTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            return Position.getPosList();
        }
        return new ArrayList<>();
    }
}
