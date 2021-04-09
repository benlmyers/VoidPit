package com.characterlim.voidpit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class PitCommand extends Supercommand implements TabExecutor {

    private final VoidPitPlugin plugin;
    private final ItemSubcommand itemSubcommand;
    private final RegionSubcommand regionSubcommand;

    public PitCommand(VoidPitPlugin instance) {
        this.plugin = instance;
        this.itemSubcommand = new ItemSubcommand(instance);
        this.regionSubcommand = new RegionSubcommand(instance);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = null;
        if(commandSender instanceof Player) player = (Player) commandSender;

        if(args.length == 0) {
            player.sendMessage("§cImproper use of command. Use §e/pit help §cfor more information.");
        } else {

            switch(args[0]) {
                case "item":
                    executeSubcommand(commandSender, args, itemSubcommand);
                    break;
                case "region":
                    executeSubcommand(commandSender, args, regionSubcommand);
                    break;
                case "help":
                    player.sendMessage("§e/pit item§b: Configure the item that the Void Pit consumes");
                    player.sendMessage("§e/pit help§b: Get help on this command");
                    break;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 1) {
            List<String> completions = new ArrayList<String>();
            List<String> commands = new ArrayList<String>();

            commands.add("item");
            commands.add("help");
            commands.add("region");

            StringUtil.copyPartialMatches(args[0], commands, completions);
            return completions;
        } else if(args.length == 2) {
            switch(args[0]) {
                case "item":
                    return itemSubcommand.completions(args[1]);
                case "region":
                    return regionSubcommand.completions(args[1]);
            }
        }
        return null;
    }
}
