package com.characterlim.voidpit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface Subcommand {
    boolean onCommand(CommandSender commandSender, String[] args);
}
