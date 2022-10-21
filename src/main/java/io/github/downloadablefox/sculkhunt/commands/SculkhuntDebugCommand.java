package io.github.downloadablefox.sculkhunt.commands;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import io.github.downloadablefox.sculkhunt.GameState;
import io.github.downloadablefox.sculkhunt.components.SculkComponent;
import io.github.downloadablefox.sculkhunt.components.SculkComponentRegistry;
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

    public static int setSculk(CommandContext<ServerCommandSource> context, ServerPlayerEntity player) {
        ServerCommandSource source = context.getSource();
        boolean isSculk = SculkComponentRegistry.SCULK.maybeGet(player).map(SculkComponent::getSculk).orElse(false);
        
        player.getComponent(SculkComponentRegistry.SCULK).setSculk(!isSculk);
        source.sendFeedback(Text.of("Set " + player.getEntityName() + " to " + (isSculk ? "human" : "sculk tracker" + ".")), false);
        
        return 1;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal(COMMAND_NAME)
            .then(CommandManager.literal("gamestate").executes(SculkhuntDebugCommand::getGameState)) // sculkhunt-debug getGameState
            .then(CommandManager.literal("sculk").then(CommandManager.argument("player", EntityArgumentType.player())
            .executes((context) -> setSculk(context, EntityArgumentType.getPlayer(context, "player"))))) // sculkhunt-debug setSculk <player>
        );
    }
}