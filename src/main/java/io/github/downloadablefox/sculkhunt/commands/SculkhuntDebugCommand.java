package io.github.downloadablefox.sculkhunt.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import io.github.downloadablefox.sculkhunt.GameState;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SculkhuntDebugCommand {
    private static final String COMMAND_NAME = "sculkhunt-debug";

    public static int getGameState(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.sendFeedback(Text.of("Current game state: " + GameState.INSTANCE.state.toString()), false);
        return 1;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal(COMMAND_NAME)
            .then(CommandManager.literal("gamestate").executes(SculkhuntDebugCommand::getGameState)) // sculkhunt-debug getGameState
        );
    }
}