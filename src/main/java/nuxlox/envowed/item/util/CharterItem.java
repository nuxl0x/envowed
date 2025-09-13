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
import nuxlox.envowed.data.CharterData;
import nuxlox.envowed.item.BindingItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CharterItem extends Item {
    public CharterItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.pass(stack);
        }

        CharterData charterData = CharterData.get((ServerWorld) world);
        ItemStack signedCharter = BindingItems.SIGNED_CHARTER.getDefaultStack();

        NbtCompound nbt = stack.getOrCreateNbt();
        String chartererName = nbt.getString("chartererName");
        String charteredName = nbt.getString("charteredName");

        if (chartererName.isEmpty()) {
            nbt.putString("chartererName", user.getEntityName());
            stack.setNbt(nbt);
            updatePlayerInventory(user);
            return TypedActionResult.success(stack);
        }

        if (charteredName.isEmpty()) {
            nbt.putString("charteredName", user.getEntityName());
            charteredName = user.getEntityName();
        }
        stack.setNbt(nbt);
        signedCharter.setNbt(nbt);
        String charterInfo = chartererName + ":" + charteredName;

        // Checks that this contractor and contractee do not have a contract in inverse circumstances.
        String inverseCharterInfo = charteredName + ":" + chartererName;
        if (charterData.checkForExistingEntry(inverseCharterInfo) == true) {
            Envowed.LOGGER.error("Failed to create new CharterData entry due to the charterer being charted to the chartered.");
            return TypedActionResult.fail(stack);
        }

        // Checks that the contractor and contractee are not the same person.
        // Comment this code out if you are testing solo.
        // if (Objects.equals(chartererName, charteredName)) {
        //     Envowed.LOGGER.error("Failed to create new ContractData entry due to the contractor and contractee being the same person.");
        //     return TypedActionResult.fail(stack);
        // }

        charterData.createNewEntry(charterInfo);

        user.setStackInHand(hand, signedCharter);

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

        tooltip.add(Text.translatable("itemTooltip.envowed.charter1"));
        tooltip.add(Text.translatable("itemTooltip.envowed.charter2"));
        tooltip.add(Text.translatable("itemTooltip.envowed.charter3"));

        if (nbt != null && nbt.contains("chartererName")) {
            String chartererName = nbt.getString("chartererName");
            tooltip.add(Text.translatable("itemTooltip.envowed.charterer", chartererName).formatted(Formatting.GOLD));
        }

        super.appendTooltip(stack, world, tooltip, context);

    }

}
