package com.characterlim.voidpit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public abstract class Supercommand {

    protected boolean executeSubcommand(CommandSender commandSender, String[] args, Subcommand subcommand) {
        String[] subargs = new String[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            subargs[i-1] = args[i];
        }
        subcommand.onCommand(commandSender, subargs);
        return true;
    }
}