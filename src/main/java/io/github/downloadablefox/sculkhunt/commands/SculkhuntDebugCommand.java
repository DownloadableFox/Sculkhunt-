package io.github.downloadablefox.sculkhunt.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import io.github.downloadablefox.sculkhunt.GameState;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class SculkhuntDebugCommand {
    private static final String COMMAND_NAME = "sculkhunt-debug";

    public static int getGameState(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.sendFeedback(Text.of("Current game state: " + GameState.INSTANCE.state.toString()), false);
        return 1;
    }

    public static int toggleSculk(CommandContext<ServerCommandSource> context, ServerPlayerEntity player) {
        ServerCommandSource source = context.getSource();
        boolean isSculk = GameState.INSTANCE.isSculk(player);
        
        if (isSculk) GameState.INSTANCE.removeSculk(player);
        else GameState.INSTANCE.setSculk(player);

        source.sendFeedback(Text.of("Set " + player.getEntityName() + " to " + (isSculk ? "human" : "sculk tracker" + ".")), false);
        return 1;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal(COMMAND_NAME)
            .then(CommandManager.literal("gamestate").executes(SculkhuntDebugCommand::getGameState)) // sculkhunt-debug getGameState
            .then(CommandManager.literal("togglesculk").then(CommandManager.argument("player", EntityArgumentType.player())
            .executes((context) -> toggleSculk(context, EntityArgumentType.getPlayer(context, "player"))))) // sculkhunt-debug setSculk <player>
        );
    }
}