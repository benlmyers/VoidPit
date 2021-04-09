package com.characterlim.voidpit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ItemSubcommand implements Subcommand {

    private VoidPitPlugin plugin;
    private PitItemManager itemManager = new PitItemManager();

    public ItemSubcommand(VoidPitPlugin instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String[] args) {
        Player player = null;
        if(commandSender instanceof Player) player = (Player) commandSender;

        if(args.length == 0) {
            player.sendMessage("§cImproper use of command. Use §e/pit item help §cfor more information.");
        } else {
            switch(args[0]) {
                case "set":
                    break;
                case "help":
                    player.sendMessage("§e/pit item set§b: Set the item that the Void Pit consumes to the item in your main hand");
                    player.sendMessage("§e/pit item add§b: Add the item in your main hand to the list of items that the Void Pit consumes");
                    player.sendMessage("§e/pit item remove§b: Remove the item in your main hand from the list of items that the Void Pit consumes");
                    player.sendMessage("§e/pit item reset§b: Make the Void Pit consume no item types");
                    player.sendMessage("§e/pit item list§b: Display a list of all the accepted item types the Void Pit can consume");
                    player.sendMessage("§e/pit item help§b: Get help on this command");
                    break;
            }
        }
        return false;
    }

    @Override
    public List<String> completions(String arg) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        commands.add("set");
        commands.add("add");
        commands.add("remove");
        commands.add("reset");
        commands.add("list");
        commands.add("help");
        return null;
    }
}
