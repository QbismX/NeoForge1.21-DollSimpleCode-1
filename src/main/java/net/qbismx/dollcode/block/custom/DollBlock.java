package net.qbismx.dollcode.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DollBlock extends Block {

    // 現在、テクスチャが読み込まれない＆当たり判定がない。壊せない。

    public static final MapCodec<DollBlock> CODEC = simpleCodec(DollBlock::new);
    // 上半身, デフォルトでは南の方を見ている
    protected static final VoxelShape HEAD;
    protected static final VoxelShape UPPER_BODY;
    protected static final VoxelShape UPPER_RIGHT_ARM;
    protected static final VoxelShape UPPER_LEFT_ARM;
    // 下半身
    protected static final VoxelShape LOWER_BODY;
    protected static final VoxelShape LOWER_RIGHT_ARM;
    protected static final VoxelShape LOWER_LEFT_ARM;
    protected static final VoxelShape RIGHT_LEG;
    protected static final VoxelShape LEFT_LEG;

    // 2ブロックの空間の上部分
    protected static final VoxelShape UPPER_SHAPE;
    // 2ブロックの空間の下部分
    protected static final VoxelShape LOWER_SHAPE;

    // 上半分か下半分か
    public static final EnumProperty<DoubleBlockHalf> HALF;
    // 向き
    public static final DirectionProperty FACING;

    @Override
    protected MapCodec<? extends DollBlock> codec() { return CODEC;}

    public DollBlock(Properties properties) {
        super(properties);
        // ?
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.SOUTH).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    // 形の設定

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER
                ? rotateShape(LOWER_SHAPE, facing)
                : rotateShape(UPPER_SHAPE, facing);
    }

    // 回転
    private static VoxelShape rotateShape(VoxelShape shape, Direction facing) {

        if (facing == Direction.SOUTH) {
            return shape; // デフォルト南向き
        }

        VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };

        int times = switch (facing) {
            case WEST -> 1;
            case NORTH -> 2;
            case EAST -> 3;
            default -> 0;
        };

        for (int i = 0; i < times; i++) {
            buffer[1] = Shapes.empty();

            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                buffer[1] = Shapes.or(buffer[1],
                        Block.box(
                                16 - maxZ, minY, minX,
                                16 - minZ, maxY, maxX
                        ));
            });

            buffer[0] = buffer[1];
        }

        return buffer[0];
    }

    // 壊れた場合
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            level.destroyBlock(pos.above(), false);
        } else {
            level.destroyBlock(pos.below(), false);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        // 上に空きがあるかチェック
        if (pos.getY() < level.getMaxBuildHeight() - 1 &&
                level.getBlockState(pos.above()).canBeReplaced(context)) {

            return this.defaultBlockState()
                    .setValue(FACING, context.getHorizontalDirection().getOpposite())
                    .setValue(HALF, DoubleBlockHalf.LOWER);
        }

        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        // 上半分を自動で設置する
        level.setBlock(pos.above(),
                (BlockState) state.setValue(HALF, DoubleBlockHalf.UPPER),
                3);
    }


    // 設置可能条件
    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return level.getBlockState(pos.below()).is(this);
        } else {
            return level.getBlockState(pos.above()).isAir();
        }
    }

    // BlockState(ブロックの状態)に関する性質：上か下か、ブロックの向き
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HALF, FACING});
    }

    static {
        HEAD = Block.box((double)4.4F, (double)5.6F, (double)4.4F, (double)11.6F, (double)12.8F, (double)11.6F); // 計算済
        UPPER_BODY = Block.box((double)4.4F, (double)0.0F, (double)6.2F, (double)11.6F, (double)5.6F, (double)9.8F); // 計算済
        UPPER_RIGHT_ARM = Block.box((double)0.8F, (double)0.0F, (double)6.2F, (double)4.4F, (double)5.6F, (double)9.8F); // 計算済
        UPPER_LEFT_ARM = Block.box((double)11.6F, (double)0.0F, (double)6.2F, (double)15.2F, (double)5.6F, (double)9.8F); // 計算済
        LOWER_BODY = Block.box((double)4.4F, (double)10.8F, (double)6.2F, (double)11.6F, (double)16.0F, (double)9.8F); // 計算済
        LOWER_RIGHT_ARM = Block.box((double)0.8F, (double)10.8F, (double)6.2F, (double)4.4F, (double)16.0F, (double)9.8F); // 計算済
        LOWER_LEFT_ARM = Block.box((double)11.6F, (double)10.8F, (double)6.2F, (double)15.2F, (double)16.0F, (double)9.8F); // 計算済
        RIGHT_LEG = Block.box((double)4.4F, (double)0.0F, (double)6.2F, (double)8.0F, (double)10.8F, (double)9.8F); // 計算済
        LEFT_LEG = Block.box((double)8.0F, (double)0.0F, (double)6.2F, (double)11.6F, (double)10.8F, (double)9.8F); // 計算済
        UPPER_SHAPE = Shapes.or(HEAD, UPPER_BODY, UPPER_RIGHT_ARM, UPPER_LEFT_ARM);
        LOWER_SHAPE = Shapes.or(LOWER_BODY, LOWER_RIGHT_ARM, LOWER_LEFT_ARM, RIGHT_LEG, LEFT_LEG);
        HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
        FACING = HorizontalDirectionalBlock.FACING;
    }
}
