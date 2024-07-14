package dev.rollczi.litecobblex;

import dev.piotrulla.craftinglib.CraftingLib;
import dev.piotrulla.craftinglib.CraftingManager;
import dev.rollczi.litecobblex.adventure.LegacyPostProcessor;
import dev.rollczi.litecobblex.adventure.LegacyPreProcessor;
import dev.rollczi.litecobblex.cobblex.CobbleXCommand;
import dev.rollczi.litecobblex.cobblex.CobbleXManager;
import dev.rollczi.litecobblex.cobblex.CobbleXRecipeCreator;
import dev.rollczi.litecobblex.config.ConfigManager;
import dev.rollczi.litecobblex.config.PluginConfig;
import dev.rollczi.litecobblex.message.MessageService;
import dev.rollczi.litecobblex.reload.ReloadManager;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.adventure.LiteAdventureExtension;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.join.JoinArgument;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class LiteCobbleX extends JavaPlugin {

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        ReloadManager reloadManager = new ReloadManager();
        ConfigManager configManager = reloadManager.register(new ConfigManager(this.getDataFolder()));
        PluginConfig pluginConfig = configManager.load(new PluginConfig());

        MiniMessage miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyPostProcessor())
            .preProcessor(new LegacyPreProcessor())
            .build();

        MessageService messageService = new MessageService(pluginConfig, miniMessage);
        CobbleXManager cobbleXManager = new CobbleXManager(pluginConfig);

        this.liteCommands = LiteBukkitFactory.builder("lite-cobblex", this)
            .extension(new LiteAdventureExtension<>(), settings -> settings
                .serializer(miniMessage)
                .colorizeArgument(true)
            )

            .commands(
                new LiteCobbleXAdminCommand(reloadManager, configManager, cobbleXManager, pluginConfig),
                new CobbleXCommand(cobbleXManager, messageService)
            )

            .argumentSuggestion(double.class, SuggestionResult.of("0.5", "0.75", "1.0", "10.0", "20.0", "30.0"))
            .argumentSuggestion(String.class, JoinArgument.KEY, SuggestionResult.of("&cNazwa itemku", "<gradient:#1a266a:#27a76a>Fajny itemek</gradient>"))

            .message(LiteBukkitMessages.PLAYER_ONLY, "&cOnly player can execute this command!")

            // Schematic generator is used to generate schematic for command, for example when you run invalid command.
            .schematicGenerator(SchematicFormat.angleBrackets())

            .build();

        CraftingLib craftingLib = new CraftingLib(this);
        CraftingManager craftingManager = craftingLib.getCraftingManager();

        CobbleXRecipeCreator creator = reloadManager.register(new CobbleXRecipeCreator(craftingManager, pluginConfig));
        creator.load();
    }

    @Override
    public void onDisable() {
        // unregister all commands from bukkit
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }

}
