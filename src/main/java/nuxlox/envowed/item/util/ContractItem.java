package nuxlox.envowed.item.util;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import nuxlox.envowed.item.BindingItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

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

        NbtCompound nbt = stack.getOrCreateNbt();
        String contractorName = nbt.getString("contractorName");
        String contracteeName = nbt.getString("contracteeName");

        if (Objects.equals(contractorName, "")) {
            nbt.putString("contractorName", user.getEntityName());
            stack.setNbt(nbt);
            updatePlayerInventory(user);
            return TypedActionResult.success(stack);
        }

        if (Objects.equals(contracteeName, "")) {
            nbt.putString("contracteeName", user.getEntityName());
        }

        stack.setNbt(nbt);
        updatePlayerInventory(user);

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
            String contracteeName = nbt.getString("contracteeName");
            tooltip.add(Text.literal("Contractor Name: " + contractorName).formatted(Formatting.BOLD, Formatting.YELLOW));
            tooltip.add(Text.literal("Contractee Name: " + contracteeName).formatted(Formatting.RED));
        }

        super.appendTooltip(stack, world, tooltip, context);

    }
}
