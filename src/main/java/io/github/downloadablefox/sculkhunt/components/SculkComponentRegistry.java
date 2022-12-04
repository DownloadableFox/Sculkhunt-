package io.github.downloadablefox.sculkhunt.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import io.github.downloadablefox.sculkhunt.Sculkhunt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class SculkComponentRegistry implements EntityComponentInitializer {
    public static final Identifier SCULK_IDENTIFIER = new Identifier(Sculkhunt.MOD_ID, "sculk");
    public static final ComponentKey<SculkComponent> SCULK = ComponentRegistryV3.INSTANCE.getOrCreate(SCULK_IDENTIFIER, SculkComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, SCULK, SculkComponent::new);
    }
}
