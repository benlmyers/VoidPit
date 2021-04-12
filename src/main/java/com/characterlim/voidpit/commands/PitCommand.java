package com.characterlim.voidpit.commands;

import com.characterlim.voidpit.Supercommand;
import com.characterlim.voidpit.VoidPitPlugin;
import com.characterlim.voidpit.commands.subcommands.EnergySubcommand;
import com.characterlim.voidpit.commands.subcommands.HologramSubcommand;
import com.characterlim.voidpit.commands.subcommands.ItemSubcommand;
import com.characterlim.voidpit.commands.subcommands.RegionSubcommand;
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
    private final HologramSubcommand hologramSubcommand;
    private final EnergySubcommand energySubcommand;

    public PitCommand(VoidPitPlugin instance) {
        this.plugin = instance;
        this.itemSubcommand = new ItemSubcommand(instance);
        this.regionSubcommand = new RegionSubcommand(instance);
        this.hologramSubcommand = new HologramSubcommand(instance);
        this.energySubcommand = new EnergySubcommand(instance);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = null;
        if(commandSender instanceof Player) player = (Player) commandSender;

        if(args.length == 0) {
            assert player != null;
            player.sendMessage("§cImproper use of command. Use §e/pit help §cfor more information.");
        } else {

            switch(args[0]) {
                case "item":
                    executeSubcommand(commandSender, args, itemSubcommand);
                    break;
                case "region":
                    executeSubcommand(commandSender, args, regionSubcommand);
                    break;
                case "hologram":
                    executeSubcommand(commandSender, args, hologramSubcommand);
                    break;
                case "energy":
                    executeSubcommand(commandSender, args, energySubcommand);
                    break;
                case "help":
                    player.sendMessage("§e/pit item§b: Configure the item that the Void Pit consumes");
                    player.sendMessage("§e/pit region§b: Configure the region where the Void Pit resides");
                    player.sendMessage("§e/pit hologram§b: Configure the hologram that displays info about the Void Pit");
                    player.sendMessage("§e/pit energy§b: Configure the Void Pit's energy settings");
                    player.sendMessage("§e/pit reload§b: Reload this plugin");
                    player.sendMessage("§e/pit help§b: Get help on this command");
                    break;
                case "reload":
                    this.plugin.onDisable();
                    this.plugin.onEnable();
                    assert player != null;
                    player.sendMessage("§bPlugin successfully reloaded.");
                    break;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 1) {
            List<String> completions = new ArrayList<>();
            List<String> commands = new ArrayList<>();

            commands.add("item");
            commands.add("energy");
            commands.add("hologram");
            commands.add("help");
            commands.add("region");
            commands.add("reload");

            StringUtil.copyPartialMatches(args[0], commands, completions);
            return completions;
        } else if(args.length == 2) {
            switch(args[0]) {
                case "item":
                    return itemSubcommand.completions(args[1]);
                case "region":
                    return regionSubcommand.completions(args[1]);
                case "hologram":
                    return hologramSubcommand.completions(args[1]);
                case "energy":
                    return energySubcommand.completions(args[1]);
            }
        }
        return null;
    }
}
