package io.github.downloadablefox.sculkhunt;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.server.network.ServerPlayerEntity;

public class GameState {
    public static final GameState INSTANCE = new GameState();
    
    public State state;
    public int preparationTime;
    public ArrayList<UUID> blacklisted = new ArrayList<>(); // players who are black listed from turning into sculks
    public ArrayList<UUID> sculks = new ArrayList<>(); // players that are sculks or will turn into them
    public ArrayList<UUID> escaped = new ArrayList<>(); // players who got to the end and escaped

    private GameState() {
        state = State.WAITING;
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

    }

    public void stopGame() {}

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

    public boolean setSculk(ServerPlayerEntity player) {
        if (sculks.contains(player.getUuid())) {
            return false;
        }

        sculks.add(player.getUuid());
        return true;
    }

    public boolean removeSculk(ServerPlayerEntity player) {
        if (!sculks.contains(player.getUuid())) {
            return false;
        }

        sculks.remove(player.getUuid());
        return true;
    }
}