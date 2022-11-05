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

    @Property(
            type = PropertyType.SLIDER,
            name = "Spacing",
            description = "Spacing between list entries",
            min = 1,
            max = 10,
            category = "Obstacles"
    )
    public int obstaclesSpacing = 3;

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

    // stats viewer

    @Property(
            type = PropertyType.SWITCH,
            name = "Toggled",
            description = "Whether stats viewer is toggled on or not",
            category = "Stats Viewer"
    )
    public boolean statsViewerToggled = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Left Aligned",
            description = "Should the stats viewer be left or right aligned",
            category = "Stats Viewer"
    )
    public boolean statsViewerLeftAligned = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Cache UUIDS",
            description = "I recommend keeping this on",
            category = "Stats Viewer"
    )
    public boolean statsViewerCacheUUIDS = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show Own Name",
            description = "Should the list include your name",
            category = "Stats Viewer"
    )
    public boolean statsViewerShowOwnName = false;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Sorting Behavior",
            description = "How should the list be sorted?",
            options = {
                    "Alphabetical",
                    "Stars",
                    "Weighted Wins"
            },
            category = "Stats Viewer"
    )
    public int statsViewerSortOrder = 0;

    @Property(
            type = PropertyType.SLIDER,
            name = "Spacing",
            description = "Spacing between list entries",
            min = 1,
            max = 10,
            category = "Stats Viewer"
    )
    public int statsViewerSpacing = 3;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show First Line",
            description = "Show the \"Name | Stars | Weighted Wins\" message",
            category = "Stats Viewer"
    )
    public boolean statsViewerShowFirstLine = true;

    public VoxylSettingsPage(@NotNull File file) {
        super(file, "Voxyl Enhanced", new JVMAnnotationPropertyCollector(), new VoxylSortingBehavior());

        initialize();

        setCategoryDescription("General", "Random stuff here and there");
        setCategoryDescription("Auto GG", "Say 'gg' after each game");
        setCategoryDescription("Obstacles", "The obstacles gamemode");
        setCategoryDescription("Stats Viewer", "List the stats of players in your game");
    }
}
