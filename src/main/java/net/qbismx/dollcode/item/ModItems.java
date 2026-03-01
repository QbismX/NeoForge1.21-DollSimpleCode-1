package net.qbismx.dollcode.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.qbismx.dollcode.DollCode;

import java.util.function.Supplier;

public class ModItems {

    // ゲームに認知させたいModのアイテム全体の登録箱
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DollCode.MODID);

    // アイテム登録用メソッド
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
