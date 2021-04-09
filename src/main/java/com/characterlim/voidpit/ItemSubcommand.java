package com.characterlim.voidpit;

import org.bukkit.command.CommandSender;

public class ItemSubcommand implements Subcommand {

    private VoidPitPlugin plugin;

    public ItemSubcommand(VoidPitPlugin instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String[] args) {
        return false;
    }
}
