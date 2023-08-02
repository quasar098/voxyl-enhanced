package com.quasar.voxylenhanced;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

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
            if (args[0].equals("inviteabunch") || args[0].equals("i") || args[0].equals("invite")) {
                VoxylEnhanced.settings.inviteABunch = !VoxylEnhanced.settings.inviteABunch;
                if (VoxylEnhanced.settings.inviteABunch) {
                    VoxylUtils.informPlayer(EnumChatFormatting.GREEN, "Invite-a-bunch enabled!");
                } else {
                    VoxylUtils.informPlayer(EnumChatFormatting.RED, "Invite-a-bunch disabled!");
                }
                return;
            }
            VoxylEnhanced.settings.apiKey = args[0];
            messageResponse(String.format("Set api key to %s", VoxylEnhanced.settings.apiKey));
        } else commandHelpMessage();
    }

    public void commandHelpMessage() {
        messageResponse("/ve");
    }
}
