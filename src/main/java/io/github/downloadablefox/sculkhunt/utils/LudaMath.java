package io.github.downloadablefox.sculkhunt.utils;

public class LudaMath {
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
