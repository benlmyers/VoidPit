package com.characterlim.voidpit.commands.subcommands;

import com.characterlim.voidpit.Subcommand;
import com.characterlim.voidpit.VoidPitPlugin;
import com.characterlim.voidpit.managers.PitHologramManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class HologramSubcommand implements Subcommand {

    private final PitHologramManager hologramManager;

    public HologramSubcommand(VoidPitPlugin instance) {
        this.hologramManager = new PitHologramManager(instance);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String[] args) {
        Player player = null;
        if(commandSender instanceof Player) player = (Player) commandSender;

        if(args.length == 0) {
            assert player != null;
            player.sendMessage("§cImproper use of command. Use §e/pit hologram help §cfor more information.");
        } else {
            switch(args[0]) {
                case "setposition":
                    assert player != null;
                    hologramManager.setPosition(player);
                    break;
                case "showposition":
                    assert player != null;
                    hologramManager.showPosition(player);
                    break;
                case "setlabel":
                    assert player != null;
                    hologramManager.setLabel(player, args[1]);
                case "refresh":
                    hologramManager.refresh();
                    break;
                case "help":
                    assert player != null;
                    player.sendMessage("§e/pit hologram setposition§b: Set the position of the Hologram");
                    player.sendMessage("§e/pit hologram showposition§b: Display the position of the Hologram");
                    player.sendMessage("§e/pit hologram refresh§b: Refresh the Hologram");
                    player.sendMessage("§e/pit hologram help§b: Get help on this command");
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
