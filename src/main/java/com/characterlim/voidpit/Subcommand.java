package com.characterlim.voidpit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface Subcommand {
    boolean onCommand(CommandSender commandSender, String[] args);
    List<String> completions(String arg);
}
