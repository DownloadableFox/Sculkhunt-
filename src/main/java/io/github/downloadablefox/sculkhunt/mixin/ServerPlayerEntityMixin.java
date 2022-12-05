package io.github.downloadablefox.sculkhunt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.downloadablefox.sculkhunt.GameState;
import io.github.downloadablefox.sculkhunt.components.SculkComponentRegistry;
import io.github.downloadablefox.sculkhunt.utils.LudaMath;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "onSpawn()V")
    public void onSpawn(CallbackInfo info) {   
        GameState gamestate = GameState.INSTANCE;

        // if the game hasn't started don't do anything
        if (gamestate.state != GameState.State.STARTED) return;
        
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        AttributeContainer attributes = player.getAttributes();
        if (gamestate.hasEscaped(player)) return;

        // turn player into sculk
        if (!gamestate.isSculk(player)) gamestate.setSculk(player);
        else player.getComponent(SculkComponentRegistry.SCULK).setSculk(true); // isSculk component is lost when player respawns

        // setting sculk attributes
        ServerWorld world = player.getWorld();
        float sculkPercentage = gamestate.getSculkPercentage(world);

        float health = LudaMath.clamp(-10f * sculkPercentage + 12f, 4f, 12f); // heath function = -10x + 12
        float damage = LudaMath.clamp(-5f * sculkPercentage + 6f, 2f, 5f); // damage functon -5x + 6

        attributes.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(health);
        attributes.getCustomInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        attributes.getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.12f);

        player.setHealth(health);

        /*
            TODO:
            - set sculk model
            - respawn player in catalyst (close or new)
        */

        // giving sculk items
        player.giveItemStack(new ItemStack(Items.SCULK, 1 + player.getRandom().nextInt(5)));
    }
}
