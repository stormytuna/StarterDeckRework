package starterdeckrework;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import starterdeckrework.cards.BaseCard;
import starterdeckrework.util.GeneralUtils;
import starterdeckrework.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.util.*;

@SpireInitializer
public class StarterDeckRework implements
        EditCardsSubscriber,
        EditStringsSubscriber,
        PostInitializeSubscriber {
    public static ModInfo info;
    public static String modID;
    static { loadModInfo(); }
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    private static final String resourcesFolder = "starterdeckrework";

    public static Properties defaultSettings = new Properties();

    public static boolean swapIroncladStrikes = false;
    public static boolean swapIroncladDefends = false;
    public static boolean swapIroncladStrikeForFrenzy = false;
    public static boolean swapSilentStrikes = false;
    public static boolean swapSilentDefends = false;
    public static boolean removeSilentStrikeAndDefend = false;
    public static boolean swapDefectStrikes = false;
    public static boolean swapDefectDefends = false;
    public static boolean swapWatcherStrikes = false;
    public static boolean swapWatcherDefends = false;

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new StarterDeckRework();
    }

    public StarterDeckRework() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");

        defaultSettings.setProperty("swap-ironclad-strikes", Boolean.toString(true));
        defaultSettings.setProperty("swap-ironclad-defends", Boolean.toString(true));
        defaultSettings.setProperty("swap-ironclad-strike-for-frenzy", Boolean.toString(true));
        defaultSettings.setProperty("swap-silent-strikes", Boolean.toString(true));
        defaultSettings.setProperty("swap-silent-defends", Boolean.toString(true));
        defaultSettings.setProperty("remove-silent-strike-and-defend", Boolean.toString(true));
        defaultSettings.setProperty("swap-defect-strikes", Boolean.toString(true));
        defaultSettings.setProperty("swap-defect-defends", Boolean.toString(true));
        defaultSettings.setProperty("swap-watcher-strikes", Boolean.toString(true));
        defaultSettings.setProperty("swap-watcher-defends", Boolean.toString(true));

        try {
            SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
            config.load();

            swapIroncladStrikes = config.getBool("swap-ironclad-strikes");
            swapIroncladDefends = config.getBool("swap-ironclad-defends");
            swapIroncladStrikeForFrenzy = config.getBool("swap-ironclad-strike-for-frenzy");
            swapSilentStrikes = config.getBool("swap-silent-strikes");
            swapSilentDefends = config.getBool("swap-silent-defends");
            removeSilentStrikeAndDefend = config.getBool("remove-silent-strike-and-defend");
            swapDefectStrikes = config.getBool("swap-defect-strikes");
            swapDefectDefends = config.getBool("swap-defect-defends");
            swapWatcherStrikes = config.getBool("swap-watcher-strikes");
            swapWatcherDefends = config.getBool("swap-watcher-defends");

            logger.info(modID + " added mod settings");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
                .packageFilter(BaseCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receivePostInitialize() {
        UIStrings configStrings = CardCrawlGame.languagePack.getUIString(makeID("ConfigMenuText"));
        ModPanel settingsPanel = new ModPanel();
        ModLabeledToggleButton enableSwapIroncladStrikesButton = new ModLabeledToggleButton(configStrings.TEXT[0], 350.0F, 750.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, swapIroncladStrikes, settingsPanel, label -> { }, button -> {
            swapIroncladStrikes = button.enabled;
            try {
                SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
                config.setBool("swap-ironclad-strikes", swapIroncladStrikes);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ModLabeledToggleButton enableSwapIroncladDefendsButton = new ModLabeledToggleButton(configStrings.TEXT[1], 350.0F, 700.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, swapIroncladDefends, settingsPanel, label -> { }, button -> {
            swapIroncladDefends = button.enabled;
            try {
                SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
                config.setBool("swap-ironclad-defends", swapIroncladDefends);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ModLabeledToggleButton enableSwapIroncladStrikeForFrenzyButton = new ModLabeledToggleButton(configStrings.TEXT[2], 350.0F, 650.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, swapIroncladStrikeForFrenzy, settingsPanel, label -> { }, button -> {
            swapIroncladStrikeForFrenzy = button.enabled;
            try {
                SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
                config.setBool("swap-ironclad-strike-for-frenzy", swapIroncladStrikeForFrenzy);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ModLabeledToggleButton enableSwapSilentStrikesButton = new ModLabeledToggleButton(configStrings.TEXT[3], 350.0F, 600.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, swapSilentStrikes, settingsPanel, label -> { }, button -> {
            swapSilentStrikes = button.enabled;
            try {
                SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
                config.setBool("swap-silent-strikes", swapSilentStrikes);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ModLabeledToggleButton enableSwapSilentDefendsButton = new ModLabeledToggleButton(configStrings.TEXT[4], 350.0F, 550.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, swapSilentDefends, settingsPanel, label -> { }, button -> {
            swapSilentDefends = button.enabled;
            try {
                SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
                config.setBool("swap-silent-defends", swapSilentDefends);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ModLabeledToggleButton enableRemoveSilentStrikeAndDefendButton = new ModLabeledToggleButton(configStrings.TEXT[5], 350.0F, 500.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, removeSilentStrikeAndDefend, settingsPanel, label -> { }, button -> {
            removeSilentStrikeAndDefend = button.enabled;
            try {
                SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
                config.setBool("remove-silent-strike-and-defend", removeSilentStrikeAndDefend);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ModLabeledToggleButton enableSwapDefectStrikesButton = new ModLabeledToggleButton(configStrings.TEXT[6], 350.0F, 450.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, swapDefectStrikes, settingsPanel, label -> { }, button -> {
            swapDefectStrikes = button.enabled;
            try {
                SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
                config.setBool("swap-defect-strikes", swapDefectStrikes);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ModLabeledToggleButton enableSwapDefectDefendsButton = new ModLabeledToggleButton(configStrings.TEXT[7], 350.0F, 400.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, swapDefectDefends, settingsPanel, label -> { }, button -> {
            swapDefectDefends = button.enabled;
            try {
                SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
                config.setBool("swap-defect-defends", swapDefectDefends);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ModLabeledToggleButton enableSwapWatcherStrikesButton = new ModLabeledToggleButton(configStrings.TEXT[8], 350.0F, 350.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, swapWatcherStrikes, settingsPanel, label -> { }, button -> {
            swapWatcherStrikes = button.enabled;
            try {
                SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
                config.setBool("swap-watcher-strikes", swapWatcherStrikes);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ModLabeledToggleButton enableSwapWatcherDefendsButton = new ModLabeledToggleButton(configStrings.TEXT[9], 350.0F, 300.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, swapWatcherDefends, settingsPanel, label -> { }, button -> {
            swapWatcherDefends = button.enabled;
            try {
                SpireConfig config = new SpireConfig("starterdeckrework", "StarterDeckReworkConfig", defaultSettings);
                config.setBool("swap-watcher-defends", swapWatcherDefends);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settingsPanel.addUIElement(enableSwapIroncladStrikesButton);
        settingsPanel.addUIElement(enableSwapIroncladDefendsButton);
        settingsPanel.addUIElement(enableSwapIroncladStrikeForFrenzyButton);
        settingsPanel.addUIElement(enableSwapSilentStrikesButton);
        settingsPanel.addUIElement(enableSwapSilentDefendsButton);
        settingsPanel.addUIElement(enableRemoveSilentStrikeAndDefendButton);
        settingsPanel.addUIElement(enableSwapDefectStrikesButton);
        settingsPanel.addUIElement(enableSwapDefectDefendsButton);
        settingsPanel.addUIElement(enableSwapWatcherStrikesButton);
        settingsPanel.addUIElement(enableSwapWatcherDefendsButton);

        Texture badgeTexture = TextureLoader.getTexture(resourcePath("badge.png"));
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, settingsPanel);
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no except catching for default localization, you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String resourcePath(String file) {
        return resourcesFolder + "/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/relics/" + file;
    }


    //This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(StarterDeckRework.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }
}
