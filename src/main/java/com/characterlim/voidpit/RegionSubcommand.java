package com.characterlim.voidpit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class RegionSubcommand implements Subcommand {

    private PitRegionManager regionManager;

    public RegionSubcommand(VoidPitPlugin instance) {
        this.regionManager = new PitRegionManager(instance);
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
                    regionManager.setRegion(player);
                    break;
                case "show":
                    regionManager.showRegion(player);
                    break;
                case "help":
                    assert player != null;
                    player.sendMessage("§e/pit region set§b: Set the region of Void Pit to the selected WorldEdit region");
                    player.sendMessage("§e/pit region help§b: Get help on this command");
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
        commands.add("show");
        commands.add("help");

        StringUtil.copyPartialMatches(arg, commands, completions);
        return completions;
    }
}
