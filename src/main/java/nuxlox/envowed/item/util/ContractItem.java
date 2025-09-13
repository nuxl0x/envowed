package nuxlox.envowed.item.util;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import nuxlox.envowed.Envowed;
import nuxlox.envowed.data.ContractData;
import nuxlox.envowed.item.BindingItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ContractItem extends Item {
    public ContractItem(Settings settings) {
        super(settings);
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.pass(stack);
        }

        ContractData contractData = ContractData.get((ServerWorld) world);
        ItemStack signedContract = BindingItems.SIGNED_CONTRACT.getDefaultStack();

        NbtCompound nbt = stack.getOrCreateNbt();
        String contractorName = nbt.getString("contractorName");
        String contracteeName = nbt.getString("contracteeName");

        if (contractorName.isEmpty()) {
            nbt.putString("contractorName", user.getEntityName());
            stack.setNbt(nbt);
            updatePlayerInventory(user);
            return TypedActionResult.success(stack);
        }

        if (contracteeName.isEmpty()) {
            nbt.putString("contracteeName", user.getEntityName());
            contracteeName = user.getEntityName();
        }
        stack.setNbt(nbt);
        signedContract.setNbt(nbt);
        String contractInfo = contractorName + ":" + contracteeName;

        // Checks that this contractor and contractee do not have a contract in inverse circumstances.
        String inverseContractInfo = contracteeName + ":" + contractorName;
        if (contractData.checkForExistingEntry(inverseContractInfo) == true) {
            Envowed.LOGGER.error("Failed to create new ContractData entry due to the contractor being contracted to the contractee.");
            return TypedActionResult.fail(stack);
        }

        // Checks that the contractor and contractee are not the same person.
        // Comment this code out if you are testing solo.
        // if (Objects.equals(contractorName, contracteeName)) {
        //     Envowed.LOGGER.error("Failed to create new ContractData entry due to the contractor and contractee being the same person.");
        //     return TypedActionResult.fail(stack);
        // }

        contractData.createNewEntry(contractInfo);

        user.setStackInHand(hand, signedContract);

        return TypedActionResult.success(stack);

    }



    public void updatePlayerInventory(PlayerEntity user) {
        if (user instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.getInventory().markDirty();
        }
    }



    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getNbt();

        tooltip.add(Text.translatable("itemTooltip.envowed.contract1"));
        tooltip.add(Text.translatable("itemTooltip.envowed.contract2"));
        tooltip.add(Text.translatable("itemTooltip.envowed.contract3"));

        if (nbt != null && nbt.contains("contractorName")) {
            String contractorName = nbt.getString("contractorName");
            tooltip.add(Text.translatable("itemTooltip.envowed.contractor", contractorName).formatted(Formatting.GOLD));
        }

        super.appendTooltip(stack, world, tooltip, context);

    }
}
