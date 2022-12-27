package com.quasar.voxylenhanced;

import com.quasar.voxylenhanced.misc.VoxylMisc;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;
import org.jetbrains.annotations.NotNull;
import scala.sys.Prop;

import java.awt.*;
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
            type = PropertyType.BUTTON,
            name = "Obstacles Read Segments",
            description = "Used for development purposes",
            category = "General"
    )
    void obstaclesCollectData() {
        VoxylMisc.obstaclesReadSegment(0);
        VoxylMisc.obstaclesReadSegment(-15);
        VoxylMisc.obstaclesReadSegment(-30);
        VoxylMisc.obstaclesReadSegment(-45);
        VoxylMisc.obstaclesReadSegment(-60);
        VoxylMisc.obstaclesReadSegment(-75);
        VoxylMisc.obstaclesReadSegment(-90);
        VoxylMisc.obstaclesReadSegment(-105);
        VoxylMisc.obstaclesReadSegment(-120);
        VoxylMisc.obstaclesReadSegment(-135);
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

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Speed score stuff",
            description = "Enable speed score stuff. Currently WIP",
            category = "Obstacles"
    )
    public boolean obstaclesDoSpeedScore = false;

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
            name = "Cache UUIDS",
            description = "I recommend keeping this on",
            category = "Stats Viewer"
    )
    public boolean statsViewerCacheUUIDS = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Toggled",
            description = "Whether stats viewer table is toggled on or not",
            category = "Stats Viewer"
    )
    public boolean statsViewerToggled = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Left Aligned",
            description = "Should the stats viewer table be left or right aligned",
            category = "Stats Viewer"
    )
    public boolean statsViewerLeftAligned = true;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Sorting Behavior",
            description = "How should the stats viewer table be sorted",
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
            description = "Spacing between stats viewer table entries",
            min = 1,
            max = 10,
            category = "Stats Viewer"
    )
    public int statsViewerSpacing = 3;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show First Line",
            description = "Show the \"Name | Stars | Weighted Wins\" message in stats viewer table",
            category = "Stats Viewer"
    )
    public boolean statsViewerShowFirstLine = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Show Table",
            description = "Toggle stats viewer table",
            category = "Stats Viewer"
    )
    public boolean statsViewerShowTable = false;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Show Levelhead",
            description = "Show levelhead below an opponent's name",
            category = "Stats Viewer"
    )
    public boolean statsViewerShowBelowName = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Show Own Levelhead",
            description = "Show levelhead for your own name too",
            category = "Stats Viewer"
    )
    public boolean statsViewerShowOwnBelowName = false;

    @Property(
            type = PropertyType.SLIDER,
            name = "Levelhead Background Opacity",
            description = "The opacity of the background of the levelhead",
            category = "Stats Viewer",
            max = 100
    )
    public int statsViewerLevelheadOpacity = 25;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Levelhead Prestige Color",
            description = "Make levelhead text color based on prestige instead of just white",
            category = "Stats Viewer"
    )
    public boolean statsViewerLevelheadPrestige = true;

    @Property(
            type = PropertyType.SLIDER,
            name = "Levelhead No Prestige Color",
            description = "The brightness value of text of a player with less than 100 stars (less than iron prestige)",
            category = "Stats Viewer",
            max = 100
    )
    public int statsViewerLevelheadColorOfNoPrestigePlayer = 67;

    @Property(
            type = PropertyType.SWITCH,
            name = "Levelhead Scan Lobby",
            description = "Scan the lobby to grab stats (only works on fast internet)",
            category = "Stats Viewer"
    )
    public boolean statsViewerScanLobby = true;

    // hide messages

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Report message",
            description = "If a player is cheating, please use /report to report them!",
            category = "Hide Messages"
    )
    public boolean hideMessageReport = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Expert color message",
            description = "(!) Expert rank can change their preferred team colour to non-standard teams.",
            category = "Hide Messages"
    )
    public boolean hideMessageExpertColor = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Master color message",
            description = "(!) Master rank after picking a preferred team colour can make the opposite team its complementary colour.",
            category = "Hide Messages"
    )
    public boolean hideMessageMasterColor = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Leave message",
            description = "(!) Leave your current session using /leave.",
            category = "Hide Messages"
    )
    public boolean hideMessageLeave = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Obstacles momentum message",
            description = "(!) When placing blocks on the side of an obstacle, consider jumping before placing a block - continuing the momentum.",
            category = "Hide Messages"
    )
    public boolean hideMessageObstaclesMomentum = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "No players message",
            description = "There appears to be no players in your game. Please report this in the discord!",
            category = "Hide Messages"
    )
    public boolean hideMessageNoPlayers = false;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Invite messages",
            description = "> [Master] rarme has invited all lobby players to join the 2v2 Stick Fight queue!",
            category = "Hide Messages"
    )
    public boolean hideMessageInvites = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Tools message",
            description = "(!) You can change your tool positions with /tools for better practice!",
            category = "Hide Messages"
    )
    public boolean hideMessageTools = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Buy rank message",
            description = "(!) If you enjoy the server consider buying a rank! store.bedwarspractice.club",
            category = "Hide Messages"
    )
    public boolean hideMessageRank = false;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Join compass message",
            description = "(!) Join a game from listed from the compass!",
            category = "Hide Messages"
    )
    public boolean hideMessageCompass = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Out of spawn message",
            description = "You cannot travel out of bounds! You have been sent back to spawn.",
            category = "Hide Messages"
    )
    public boolean hideMessageOutOfBounds = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Twitter giveaways message",
            description = "(!) Keep up with the twitter account for updates and giveaways at twitter.com/bedwarspractice",
            category = "Hide Messages"
    )
    public boolean hideMessageTwitter = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Stats message",
            description = "(!) Kills, beds broken, and wins are all added to your profile. Accessible via /stats.",
            category = "Hide Messages"
    )
    public boolean hideMessageStats = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Report at hub message",
            description = "(!) If you believe a player is hacking use /report to report them!",
            category = "Hide Messages"
    )
    public boolean hideMessageReportAtHub = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Join discord message",
            description = "(!) Join the discord server for updates! discord.gg/TUeUnkqXnY",
            category = "Hide Messages"
    )
    public boolean hideMessageDiscord = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Obstacles diagonal message",
            description = "(!) It is recommended to practice diagonal bridging to complete the last segment of the course as fast as possible.",
            category = "Hide Messages"
    )
    public boolean hideMessageDiagonal = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Practice on a bad day",
            description = "(!) Server doesn't track losses. Feel free to practice on a bad day to warm up.",
            category = "Hide Messages"
    )
    public boolean hideMessagePractice = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Rearrange hotbar",
            description = "(!) Use /hotbar to re-arrange your hotbar.",
            category = "Hide Messages"
    )
    public boolean hideMessageHotbar = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Sprint hits",
            description = "(!) Use /showfailed to see when you fail a sprint hit!",
            category = "Hide Messages"
    )
    public boolean hideMessageSprintHits = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Automatic Rearrange",
            description = "(!) Re-arrange your hotbar and it will automatically save.",
            category = "Hide Messages"
    )
    public boolean hideMessageAutomaticRearrange = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Avoid same players",
            description = "(!) Can avoid same players in a requeued game with 'avoid same players' option in /chatpref.",
            category = "Hide Messages"
    )
    public boolean hideMessageAvoidSame = true;

    @Property(
            type = PropertyType.CHECKBOX,
            name = "Disconnected rejoin",
            description = "(!) If you are disconnected from your game, use /rejoin to reconnect.",
            category = "Hide Messages"
    )
    public boolean hideMessageDisconnect = true;

    @Property(
            type = PropertyType.TEXT,
            name = "Custom regex",
            description = "Only put stuff here if you know what you're doing",
            category = "Hide Messages"
    )
    public String hideMessageCustomRegex = "";

    public VoxylSettingsPage(@NotNull File file) {
        super(file, "Voxyl Enhanced", new JVMAnnotationPropertyCollector(), new VoxylSortingBehavior());

        initialize();

        setCategoryDescription("General", "Random stuff here and there");
        setCategoryDescription("Auto GG", "Say 'gg' after each game");
        setCategoryDescription("Obstacles", "The obstacles gamemode");
        setCategoryDescription("Stats Viewer", "List the stats of players in your game");
        setCategoryDescription("Hide Messages", "Hide repetitive messages sent by the server");
    }
}
