package net.qbismx.dollcode.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.qbismx.dollcode.DollCode;
import net.qbismx.dollcode.block.custom.DollBlock;
import net.qbismx.dollcode.block.custom.DollLowerBlock;
import net.qbismx.dollcode.block.custom.DollUpperBlock;
import net.qbismx.dollcode.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    // ゲームに認知させたいModのブロック全体用の登録箱
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DollCode.MODID);

    // 追加したいブロックの登録箱
    public static final DeferredBlock<DollBlock> DOLL_BLOCK = registerBlock("doll_block_a",
            ()-> new DollBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f)           // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
            ));

    public static final DeferredBlock<DollUpperBlock> DOLL_UPPER_BLOCK = registerBlock("doll_test_upper",
            ()-> new DollUpperBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f)           // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
            ));

    public static final DeferredBlock<DollLowerBlock> DOLL_LOWER_BLOCK = registerBlock("doll_test_lower",
            ()-> new DollLowerBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f)           // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
            ));

    //　ブロック登録用のメソッド
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // ブロックアイテムの登録用メソッド
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // 登録用メソッド
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
