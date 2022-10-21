package io.github.downloadablefox.sculkhunt.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.entity.LivingEntity;

public interface SculkProperties extends ComponentV3 {
    public boolean getSculk();
    public void setSculk(boolean sculk);
    public boolean isDetected();
    public void setDetectedTicks(int ticks);
    public int getDetetedTicks();
    public LivingEntity getEntity();
}
