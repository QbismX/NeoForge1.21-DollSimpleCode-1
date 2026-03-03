package net.qbismx.dollcode.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.qbismx.dollcode.DollCode;
import net.qbismx.dollcode.block.custom.DollLowerBlock;
import net.qbismx.dollcode.block.custom.DollUpperBlock;
import net.qbismx.dollcode.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    // ゲームに認知させたいModのブロック全体用の登録箱
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DollCode.MODID);

    public static final DeferredBlock<DollUpperBlock> DOLL_UPPER_BLOCK = registerBlock("doll_test_upper",
            ()-> new DollUpperBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f, 3600000.0f)   // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
                    .pushReaction(PushReaction.BLOCK) // ピストンでは押せない
                    .lightLevel(x -> 15)
                    .noOcclusion()
            ));

    public static final DeferredBlock<DollLowerBlock> DOLL_LOWER_BLOCK = registerBlock("doll_test_lower",
            ()-> new DollLowerBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f, 3600000.0f)    // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
                    .pushReaction(PushReaction.BLOCK) // ピストンでは押せない
                    .lightLevel(x -> 15)
                    .noOcclusion()
            ));

    // 怪人キュビスム(黄色信号モード)
    public static final DeferredBlock<DollLowerBlock> DOLL_RISK_BLOCK = registerBlock("doll_qbism_risk",
            ()-> new DollLowerBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f, 3600000.0f)    // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
                    .pushReaction(PushReaction.BLOCK) // ピストンでは押せない
                    .lightLevel(x -> 15)
                    .noOcclusion()
            ));

    // TEST_DOLL
    public static final DeferredBlock<DollLowerBlock> TEST_DOLL = registerBlock("test_skin",
            ()-> new DollLowerBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f, 3600000.0f)    // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
                    .pushReaction(PushReaction.BLOCK) // ピストンでは押せない
                    .lightLevel(x -> 15)
                    .noOcclusion()
            ));

    // 怪人キュビスム(ハロウィンモード)
    public static final DeferredBlock<DollLowerBlock> HALLOWEEN_MODE = registerBlock("qbism_halloween",
            ()-> new DollLowerBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f, 3600000.0f)    // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
                    .pushReaction(PushReaction.BLOCK) // ピストンでは押せない
                    .lightLevel(x -> 15)
                    .noOcclusion()
            ));

    // 怪人キュビスム(クリスマスモード)
    public static final DeferredBlock<DollLowerBlock> CHRISTMAS_MODE = registerBlock("qbism_christmas",
            ()-> new DollLowerBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f, 3600000.0f)    // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
                    .pushReaction(PushReaction.BLOCK) // ピストンでは押せない
                    .lightLevel(x -> 15)
                    .noOcclusion()
            ));

    // 怪人キュビスム(新年モード)
    public static final DeferredBlock<DollLowerBlock> NEW_YEAR_MODE = registerBlock("qbism_new_year",
            ()-> new DollLowerBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f, 3600000.0f)    // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
                    .pushReaction(PushReaction.BLOCK) // ピストンでは押せない
                    .lightLevel(x -> 15)
                    .noOcclusion()
            ));

    // 怪人キュビスム(エラーモード)
    public static final DeferredBlock<DollLowerBlock> ERROR_MODE = registerBlock("qbism_error",
            ()-> new DollLowerBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f, 3600000.0f)    // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
                    .pushReaction(PushReaction.BLOCK) // ピストンでは押せない
                    .lightLevel(x -> 15)
                    .noOcclusion()
            ));

    // 怪人キュビスム(ファイナルモード)
    public static final DeferredBlock<DollLowerBlock> FINAL_MODE = registerBlock("qbism_final_mode",
            ()-> new DollLowerBlock(BlockBehaviour.Properties.of() // ブロックの性質を設定
                    .strength(4f, 3600000.0f)    // ブロックを壊すときの硬さ
                    .sound(SoundType.METAL) // 音の設定：金属
                    .pushReaction(PushReaction.BLOCK) // ピストンでは押せない
                    .lightLevel(x -> 15)
                    .noOcclusion()
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
