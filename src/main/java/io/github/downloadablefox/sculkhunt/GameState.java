package io.github.downloadablefox.sculkhunt;

public class GameState {
    public static final GameState INSTANCE = new GameState();
    public State state;

    private GameState() {
        state = State.WAITING;
    }

    public enum State {
        WAITING(0, "Waiting"),
        PREPARING(1, "Preparing"),
        STARTED(2, "Started"),
        ENDED(3, "Ended");

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
}