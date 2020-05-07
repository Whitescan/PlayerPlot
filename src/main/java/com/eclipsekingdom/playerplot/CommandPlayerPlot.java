package com.eclipsekingdom.playerplot;

import com.eclipsekingdom.playerplot.sys.PluginHelp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandPlayerPlot implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        PluginHelp.showTo(sender);

        return false;
    }
}
