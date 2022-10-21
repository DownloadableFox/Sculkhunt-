package io.github.downloadablefox.sculkhunt.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class SculkComponent implements SculkProperties, AutoSyncedComponent, ServerTickingComponent, ClientTickingComponent {
    private final LivingEntity provider;
    private boolean isSculk = false;
    private int detectedTicks = 0;

    SculkComponent(LivingEntity provider) {
        this.provider = provider;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.isSculk = tag.getBoolean("isSculk");
        this.detectedTicks = tag.getInt("detectedTicks");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("isSculk", this.isSculk);
        tag.putInt("detectedTicks", this.detectedTicks);
    }

    @Override
    public void clientTick() {
        if (this.detectedTicks > 0) {
            this.detectedTicks--;
        }
        
    }

    @Override
    public void serverTick() {
        if (this.detectedTicks > 0) {
            this.detectedTicks--;
        }
        
    }   

    @Override
    public boolean getSculk() {
        return this.isSculk;
    }

    @Override
    public void setSculk(boolean sculk) {
        this.isSculk = sculk;
        SculkComponentRegistry.SCULK.sync(this.provider);
    }

    @Override
    public boolean isDetected() {
        return this.detectedTicks > 0;
    }

    @Override
    public int getDetetedTicks() {
        return this.detectedTicks;
    }

    @Override
    public void setDetectedTicks(int ticks) {
        this.detectedTicks = ticks;
        SculkComponentRegistry.SCULK.sync(this.provider);
    }

    @Override
    public LivingEntity getEntity() {
        return this.provider;
    }
}
