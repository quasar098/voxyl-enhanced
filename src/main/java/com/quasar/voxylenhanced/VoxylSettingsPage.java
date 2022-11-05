package com.quasar.voxylenhanced;

import com.quasar.voxylenhanced.misc.VoxylMisc;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Comparator;

public class VoxylSettingsPage extends Vigilant {

    // general

    @Property(
            type = PropertyType.BUTTON,
            name = "Go To Hub",
            description = "I think this works on any server but idk",
            category = "General"
    )
    void goToHubButton() {
        VoxylMisc.goToHub();
    }

    @Property(
            type = PropertyType.TEXT,
            name = "API Key",
            description = "Can also be set using /ve <key>",
            category = "General",
            placeholder = "Api key goes here",
            protectedText = true
    )
    public String apiKey = "";

    // obstacles

    @Property(
            type = PropertyType.SWITCH,
            name = "Toggled",
            description = "Whether obstacles features are enabled or not",
            category = "Obstacles"
    )
    public boolean obstaclesToggled = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Left Aligned",
            description = "If this is on, the text will be on the left side of the screen",
            category = "Obstacles"
    )
    public boolean obstaclesLeftAligned = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Singleplayer Auto Requeue",
            description = "Automatically start a new private game on death in singleplayer",
            category = "Obstacles"
    )
    public boolean obstaclesAutoRequeue = false;

    // autogg

    @Property(
            type = PropertyType.SWITCH,
            name = "Toggled",
            description = "Whether auto gg features are enabled or not",
            category = "Auto GG"
    )
    public boolean autoggToggled = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Say Kill Count",
            description = "Adds 'X kills' to the gg message where X is the number of kills that game",
            category = "Auto GG"
    )
    public boolean autoggSayKillCount = false;

    @Property(
            type = PropertyType.SLIDER,
            name = "Min Kills For Kill Count",
            description = "Minimum kills required to proc kill count concatenation",
            max = 20,
            category = "Auto GG"
    )
    public int autoggMinKillsForKillCount = 5;

    public VoxylSettingsPage(@NotNull File file) {
        super(file, "Voxyl Enhanced", new JVMAnnotationPropertyCollector(), new VoxylSortingBehavior());

        initialize();

        setCategoryDescription("General", "Random stuff here and there");
        setCategoryDescription("Obstacles", "The obstacles gamemode");
        setCategoryDescription("Auto GG", "Say 'gg' after each game");
    }
}
