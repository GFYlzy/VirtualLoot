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
         // 如果 Cobblemon 尚未就绪，尝试懒加载检测
        if (!VirtualLoot.cobblemonReady) {
            try {
                // 尝试加载 Cobblemon 的方块实体类，成功则说明 Cobblemon 已初始化
                Class.forName("com.cobblemon.mod.common.CobblemonBlockEntities");
                VirtualLoot.cobblemonReady = true;
            } catch (Throwable t) {
                // Cobblemon 尚未初始化，直接返回，避免触发异常
                return;
            }
        }
        if ((Object) this == CobblemonBlockEntities.PASTURE && VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
            cir.setReturnValue(true);
        }
    }
}
