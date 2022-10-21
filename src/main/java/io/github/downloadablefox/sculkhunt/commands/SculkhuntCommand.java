package io.github.downloadablefox.sculkhunt.commands;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;

import io.github.downloadablefox.sculkhunt.GameState;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SculkhuntCommand {
    private static final String COMMAND_NAME = "sculkhunt";
    private static final Style ERROR_STYLE = Style.EMPTY.withColor(Formatting.RED);
    private static final Style SUCCESS_STYLE = Style.EMPTY.withColor(Formatting.GREEN);

    /*
     * Commands:
     * > start - Starts the game
     * > stop - Stops the game
     * > preptime <time> - Sets the time before the game starts
     * > blacklist <add/remove> <playername> - Adds or removes a player from the blacklist
     * > sculklist <add/remove> <playername> - Adds or removes a player from the sculk list
     */

    public static int startGame(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        source.sendFeedback(Text.literal("Starting game...").setStyle(SUCCESS_STYLE), false);
        GameState.INSTANCE.startGame();
        return 1;
    }

    public static int stopGame(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.sendFeedback(Text.literal("Stoping game...").setStyle(ERROR_STYLE), false);
        GameState.INSTANCE.stopGame();
        return 1;
    }

    public static void setPrepTime(CommandContext<ServerCommandSource> context, int time) {
        ServerCommandSource source = context.getSource();
        source.sendFeedback(Text.of("Setting prep time to " + time + " seconds..."), false);
        GameState.INSTANCE.setPrepTime(time);
    }

    public static void blacklistPlayer(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> players) {
        ServerCommandSource source = context.getSource();

         if (players.size() > 1) {
            for (ServerPlayerEntity player : players) {
                if (!GameState.INSTANCE.blacklistPlayer(player)) {
                    source.sendFeedback(Text.literal("Couldn't blacklist " + player.getEntityName()  + " as they're already blacklisted!").setStyle(ERROR_STYLE), false);
                }
            }

            source.sendFeedback(Text.literal("Blacklisted " + players.size() + " players from turning into a sculk.").setStyle(SUCCESS_STYLE), false);
            return;
        }

        ServerPlayerEntity player = players.iterator().next();
        if (!GameState.INSTANCE.blacklistPlayer(player)) {
            source.sendFeedback(Text.literal("Couldn't blacklist " + player.getEntityName()  + " as they're already blacklisted!").setStyle(ERROR_STYLE), false);
        } else {
            source.sendFeedback(Text.literal("Blacklisted " + player.getEntityName() + " from turning into a sculk.").setStyle(SUCCESS_STYLE), false);
        }
    }

    public static void unblacklistPlayer(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> players) {
        ServerCommandSource source = context.getSource();

        if (players.size() > 1) {
            for (ServerPlayerEntity player : players) {
                if (!GameState.INSTANCE.unblacklistPlayer(player)) {
                    source.sendFeedback(Text.literal("Couldn't unblacklist " + player.getEntityName()  + " as they're not blacklisted!").setStyle(ERROR_STYLE), false);
                }
            }

            source.sendFeedback(Text.literal("Unblacklisted " + players.size() + " players from turning into a sculk.").setStyle(SUCCESS_STYLE), false);
            return;
        }

        ServerPlayerEntity player = players.iterator().next();
        if (!GameState.INSTANCE.unblacklistPlayer(player)) {
            source.sendFeedback(Text.literal("Couldn't unblacklist " + player.getEntityName()  + " as they're not blacklisted!").setStyle(ERROR_STYLE), false);
        } else {
            source.sendFeedback(Text.literal("Unblacklisted " + player.getEntityName() + " from turning into a sculk.").setStyle(SUCCESS_STYLE), false);
        }
    }

    public static void sculklistPlayer(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> players) {
        ServerCommandSource source = context.getSource();

        if (players.size() > 1) {
            for (ServerPlayerEntity player : players) {
                if (!GameState.INSTANCE.setSculk(player)) {
                    source.sendFeedback(Text.literal("Couldn't add " + player.getEntityName()  + " to the sculk list as they're already in it!").setStyle(ERROR_STYLE), false);
                }
            }

            source.sendFeedback(Text.literal("Added " + players.size() + " players to the sculk list.").setStyle(SUCCESS_STYLE), false);
            return;
        }

        ServerPlayerEntity player = players.iterator().next();
        if (!GameState.INSTANCE.setSculk(player)) {
            source.sendFeedback(Text.literal("Couldn't add " + player.getEntityName()  + " to the sculk list as they're already in it!").setStyle(ERROR_STYLE), false);
        } else {
            source.sendFeedback(Text.literal("Added " + player.getEntityName() + " players to the sculk list.").setStyle(SUCCESS_STYLE), false);
        }
    }

    public static void unsculklistPlayer(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> players) {
        ServerCommandSource source = context.getSource();

        if (players.size() > 1) {
            for (ServerPlayerEntity player : players) {
                if (!GameState.INSTANCE.removeSculk(player)) {
                    source.sendFeedback(Text.literal("Couldn't remove " + player.getEntityName()  + " from the sculk list they're not in it!").setStyle(ERROR_STYLE), false);
                }
            }

            source.sendFeedback(Text.literal("Removed " + players.size() + " players from the sculk list.").setStyle(SUCCESS_STYLE), false);
            return;
        }

        ServerPlayerEntity player = players.iterator().next();
        if (!GameState.INSTANCE.removeSculk(player)) {
            source.sendFeedback(Text.literal("Couldn't remove " + player.getEntityName()  + " as they're not in it!").setStyle(ERROR_STYLE), false);
        } else {
            source.sendFeedback(Text.literal("Removed " + player.getEntityName() + " from turning from the sculk list.").setStyle(SUCCESS_STYLE), false);
        }
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal(COMMAND_NAME)
            .requires(source -> source.hasPermissionLevel(2))

            .then(CommandManager.literal("start").executes(SculkhuntCommand::startGame)) // sculkhunt start

            .then(CommandManager.literal("stop").executes(SculkhuntCommand::stopGame)) // sculkhunt stop

            .then(CommandManager.literal("preptime").then(CommandManager.argument("time", IntegerArgumentType.integer()).executes(context -> {
                setPrepTime(context, IntegerArgumentType.getInteger(context, "time"));
                return 1;
            }))) // sculkhunt preptime <time>

            .then(CommandManager.literal("blacklist").then(CommandManager.literal("add").then(CommandManager.argument("targets", EntityArgumentType.players()).executes(context -> {
                blacklistPlayer(context, EntityArgumentType.getPlayers(context, "targets"));
                return 1;
            })))) // sculkhunt blacklist add <playername>

            .then(CommandManager.literal("blacklist").then(CommandManager.literal("remove").then(CommandManager.argument("targets", EntityArgumentType.players()).executes(context -> {
                unblacklistPlayer(context, EntityArgumentType.getPlayers(context, "targets"));
                return 1;
            })))) // sculkhunt blacklist remove <playername>

            .then(CommandManager.literal("sculklist").then(CommandManager.literal("add").then(CommandManager.argument("targets", EntityArgumentType.players()).executes(context -> {
                sculklistPlayer(context, EntityArgumentType.getPlayers(context, "targets"));
                return 1;
            })))) // sculkhunt sculklist add <playername>

            .then(CommandManager.literal("sculklist").then(CommandManager.literal("remove").then(CommandManager.argument("targets", EntityArgumentType.players()).executes(context -> {
                unsculklistPlayer(context, EntityArgumentType.getPlayers(context, "targets"));
                return 1;
            })))) // sculkhunt sculklist remove <playername>

        );
    }
}