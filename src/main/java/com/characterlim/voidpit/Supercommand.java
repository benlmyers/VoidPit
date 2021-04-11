package com.characterlim.voidpit;

import org.bukkit.command.CommandSender;

public abstract class Supercommand {

    protected void executeSubcommand(CommandSender commandSender, String[] args, Subcommand subcommand) {
        String[] subargs = new String[args.length - 1];
        System.arraycopy(args, 1, subargs, 0, args.length - 1);
        subcommand.onCommand(commandSender, subargs);
    }
}