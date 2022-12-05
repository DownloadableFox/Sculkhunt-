package io.github.downloadablefox.sculkhunt;

import java.util.ArrayList;
import java.util.UUID;

import org.quiltmc.qsl.networking.api.PlayerLookup;

import io.github.downloadablefox.sculkhunt.components.SculkComponentRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class GameState {
    public static GameState INSTANCE = new GameState();

    public State state;
    public int preparationTime;
    public ArrayList<UUID> blacklisted = new ArrayList<>(); // players who are black listed from turning into sculks
    public ArrayList<UUID> sculks = new ArrayList<>(); // players that are sculks or will turn into them
    public ArrayList<UUID> escaped = new ArrayList<>(); // players who got to the end and escaped
    
    private GameState() {
        this.state = State.WAITING;
        this.preparationTime = 0;
    }

    public enum State {
        WAITING(0, "waiting"),
        PREPARING(1, "preparing"),
        STARTED(2, "started"),
        ENDED(3, "ended");

        private final int id;
        private final String name;

        State(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public void startGame() {
        // TODO: finish the start game function
        this.state = State.STARTED;
    }

    public void stopGame() {
        // TODO: finish the stop game function
        this.state = State.ENDED;
    }

    public void setPrepTime(int time) {}

    public boolean blacklistPlayer(ServerPlayerEntity player) {
        if (blacklisted.contains(player.getUuid())) {
            return false;
        }

        blacklisted.add(player.getUuid());
        return true;
    }

    public boolean unblacklistPlayer(ServerPlayerEntity player) {
        if (!blacklisted.contains(player.getUuid())) {
            return false;
        }

        blacklisted.remove(player.getUuid());
        return true;
    }

    public boolean isSculk(ServerPlayerEntity player) {
        return sculks.contains(player.getUuid());
    }

    public boolean setSculk(ServerPlayerEntity player) {
        if (sculks.contains(player.getUuid())) {
            return false;
        }

        sculks.add(player.getUuid());
        player.getComponent(SculkComponentRegistry.SCULK).setSculk(true);
        return true;
    }

    public boolean removeSculk(ServerPlayerEntity player) {
        if (!sculks.contains(player.getUuid())) {
            return false;
        }

        sculks.remove(player.getUuid());
        player.getComponent(SculkComponentRegistry.SCULK).setSculk(false);
        return true;
    }

    public boolean hasEscaped(ServerPlayerEntity player) {
        return escaped.contains(player.getUuid());
    }

    public boolean setEscaped(ServerPlayerEntity player) {
        if (escaped.contains(player.getUuid())) {
            return false;
        }

        escaped.add(player.getUuid());
        return true;
    }

    public boolean removeEscaped(ServerPlayerEntity player) {
        if (!escaped.contains(player.getUuid())) {
            return false;
        }

        escaped.remove(player.getUuid());
        return true;
    }

    public float getSculkPercentage(ServerWorld world) {
        int playerCount = PlayerLookup.world(world).size();
        int sculkCount = sculks.size();
        return (float) sculkCount / playerCount;
    }
}