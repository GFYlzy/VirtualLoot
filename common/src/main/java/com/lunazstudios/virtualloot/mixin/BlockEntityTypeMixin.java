package com.lunazstudios.virtualloot.mixin;

import com.lunazstudios.virtualloot.VirtualLoot; // 导入主类
import com.cobblemon.mod.common.CobblemonBlockEntities;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public abstract class BlockEntityTypeMixin {
    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    private void virtualloot$acceptVirtualPasture(BlockState state, CallbackInfoReturnable<Boolean> cir) {
         // 如果 Cobblemon 尚未完全加载，直接跳过，避免触发类初始化
        if (!VirtualLoot.cobblemonReady) {
            return;
        }
        if ((Object) this == CobblemonBlockEntities.PASTURE && VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
            cir.setReturnValue(true);
        }
    }
}
