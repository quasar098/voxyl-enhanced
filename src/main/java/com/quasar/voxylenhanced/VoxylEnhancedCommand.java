package com.quasar.voxylenhanced;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.List;

public class VoxylEnhancedCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "voxylenhanced";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "voxylenhanced usage here";
    }


    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return Lists.newArrayList("obstacles", "autogg");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("obstacles")) {
                return Lists.newArrayList("toggle", "alignment");
            }
            if (args[0].equalsIgnoreCase("autogg")) {
                return Lists.newArrayList("toggle", "togglekills");
            }
        }
        return null;
    }

    @Override
    public List<String> getCommandAliases() {
        return Lists.newArrayList("ve", "voxylenhanced");
    }

    public void messageResponse(String... strings) {
        VoxylUtils.informPlayer(strings);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            VoxylEnhanced.willOpenSettings = true;
        } else if (args.length == 1) {
            VoxylEnhanced.settings.apiKey = args[0];
            messageResponse(String.format("Set api key to %s", VoxylEnhanced.settings.apiKey));
        } else commandHelpMessage();
    }

    public void commandHelpMessage() {
        messageResponse("/ve");
    }
}
