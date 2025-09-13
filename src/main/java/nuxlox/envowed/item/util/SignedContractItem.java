package nuxlox.envowed.item.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nuxlox.envowed.data.ContractData;
import nuxlox.envowed.item.BindingItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class SignedContractItem extends Item {
    public SignedContractItem(Settings settings) {
        super(settings);
    }



    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        BlockPos pos = context.getBlockPos();
        BlockState blockState = world.getBlockState(pos);

        if (world.isClient) {
            return ActionResult.FAIL;
        }

        if (player == null) {
            return ActionResult.FAIL;
        }

        if (!blockState.isOf(Blocks.FIRE)) {
            return ActionResult.FAIL;
        }

        ItemStack stack = player.getStackInHand(hand);
        NbtCompound nbt = stack.getNbt();

        if (nbt == null) {
            return ActionResult.FAIL;
        }

        String contractorName = nbt.getString("contractorName");
        String contracteeName = nbt.getString("contracteeName");
        String contractInfo = contractorName + ":" + contracteeName;
        ContractData contractData = ContractData.get((ServerWorld) world);

        if (!Objects.equals(contractorName, player.getEntityName()) && !Objects.equals(contracteeName, player.getEntityName())) {
            return ActionResult.FAIL;
        }

        contractData.removeEntry(contractInfo);

        player.setStackInHand(hand, new ItemStack(BindingItems.CONTRACT));

        return ActionResult.SUCCESS;
    }



    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getNbt();

        tooltip.add(Text.translatable("itemTooltip.envowed.signedContract1"));
        tooltip.add(Text.translatable("itemTooltip.envowed.signedContract2"));

        if (nbt != null && nbt.contains("contractorName") && nbt.contains("contracteeName")) {
            String contractorName = nbt.getString("contractorName");
            String contracteeName = nbt.getString("contracteeName");
            tooltip.add(Text.translatable("itemTooltip.envowed.contractor", contractorName).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable("itemTooltip.envowed.contractee", contracteeName).formatted(Formatting.DARK_RED));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
