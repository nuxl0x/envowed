package nuxlox.envowed.item.util;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import nuxlox.envowed.item.BindingItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ContractItem extends Item {
    public ContractItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (world.isClient) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }

        user.setStackInHand(hand, new ItemStack(BindingItems.SIGNED_CONTRACT));

        return TypedActionResult.success(user.getStackInHand(hand));

    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("itemTooltip.envowed.contract1"));
        tooltip.add(Text.translatable("itemTooltip.envowed.contract2"));
        tooltip.add(Text.translatable("itemTooltip.envowed.contract3"));
    }
}
