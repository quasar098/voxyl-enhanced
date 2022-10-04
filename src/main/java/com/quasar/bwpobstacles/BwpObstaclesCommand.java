package com.quasar.bwpobstacles;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class BwpObstaclesCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "bwpobstacles";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "bwpobstacles usage here";
    }

    public void messageResponse(String... strings) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.join("\n", strings)));
    }
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) improperUsageWarning();
        else if (args[0].equals("help")) commandHelpMessage();
        else if (args[0].equals("alignment")) {
            BwpObstacles.rightAligned = !BwpObstacles.rightAligned;
            messageResponse("Toggled alignment to " + (BwpObstacles.rightAligned ? "right" : "left"));
            BwpObstacles.configurate(false);
        } else if (args[0].equals("toggle")) {
            BwpObstacles.modIsOn = !BwpObstacles.modIsOn;
            messageResponse("Toggled bwp obstacles mod " + (BwpObstacles.modIsOn ? "on" : "off"));
            BwpObstacles.configurate(false);
        } else if (args[0].equals("setkey")) {
            if (args.length == 2) {
                try {
                    BwpObstacles.apiKey = args[1];
                    messageResponse(String.format("Set api key to %s", BwpObstacles.apiKey));
                } catch (Error ignored) {
                    improperUsageWarning();
                }
            } else if (args.length == 1) {
                messageResponse("Api key currently set to ".concat(BwpObstacles.apiKey));
            } else {
                improperUsageWarning();
            }
        } else improperUsageWarning();
        BwpObstacles.configurate(false);
    }
    public void improperUsageWarning() {
        commandHelpMessage();
    }
    public void commandHelpMessage() {
        messageResponse(
                "How to use bwp obstacles mod:",
                "/bwpobstacles setkey <key> - set api key",
                "/bwpobstacles toggle - toggle the mod",
                "/bwpobstacles alignment - toggle alignment",
                "/api new - get an api key from bwp"
        );
    }
}
