package com.characterlim.voidpit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemSubcommand implements Subcommand {

    private final PitItemManager itemManager;

    public ItemSubcommand(VoidPitPlugin instance) {
        this.itemManager = new PitItemManager(instance);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String[] args) {
        Player player = null;
        if(commandSender instanceof Player) player = (Player) commandSender;

        if(args.length == 0) {
            assert player != null;
            player.sendMessage("§cImproper use of command. Use §e/pit item help §cfor more information.");
        } else {
            switch(args[0]) {
                case "set":
                    itemManager.setItem(player);
                    break;
                case "add":
                    itemManager.addItem(player);
                    break;
                case "remove":
                    itemManager.removeItem(player);
                    break;
                case "reset":
                    itemManager.resetItems(player);
                    break;
                case "list":
                    itemManager.listItems(player);
                    break;
                case "help":
                    assert player != null;
                    player.sendMessage("§e/pit item set§b: Set the item that the Void Pit consumes to the item in your main hand");
                    player.sendMessage("§e/pit item add§b: Add the item in your main hand to the list of items that the Void Pit consumes");
                    player.sendMessage("§e/pit item remove§b: Remove the item in your main hand from the list of items that the Void Pit consumes");
                    player.sendMessage("§e/pit item reset§b: Make the Void Pit consume no item types");
                    player.sendMessage("§e/pit item list§b: Display a list of all the accepted item types the Void Pit can consume");
                    player.sendMessage("§e/pit item help§b: Get help on this command");
                    break;
            }
        }
        return true;
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

        StringUtil.copyPartialMatches(arg, commands, completions);
        return completions;
    }
}
