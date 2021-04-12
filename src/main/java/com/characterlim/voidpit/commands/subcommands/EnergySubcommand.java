package com.characterlim.voidpit.commands.subcommands;

import com.characterlim.voidpit.Subcommand;
import com.characterlim.voidpit.VoidPitPlugin;
import com.characterlim.voidpit.managers.PitEnergyManager;
import com.characterlim.voidpit.managers.PitHologramManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class EnergySubcommand implements Subcommand {

    private final PitEnergyManager energyManager;

    public EnergySubcommand(VoidPitPlugin instance) {
        this.energyManager = new PitEnergyManager(instance);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String[] args) {
        Player player = null;
        if(commandSender instanceof Player) player = (Player) commandSender;

        if(args.length == 0) {
            assert player != null;
            player.sendMessage("§cImproper use of command. Use §e/pit energy help §cfor more information.");
        } else {
            switch(args[0]) {
                case "setmax":
                    if(args[1] != null)  energyManager.setMaxEnergy(Integer.parseInt(args[1]));
                    break;
                case "help":
                    assert player != null;
                    player.sendMessage("§e/pit energy setmax§b: Set the maximum energy needed to win");
                    player.sendMessage("§e/pit energy help§b: Get help on this command");
                    break;
            }
        }
        return true;
    }

    @Override
    public List<String> completions(String arg) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        commands.add("setposition");
        commands.add("showposition");
        commands.add("refresh");
        commands.add("help");

        StringUtil.copyPartialMatches(arg, commands, completions);
        return completions;
    }
}
