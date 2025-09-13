package nuxlox.envowed.item.util;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SignedContractItem extends Item {
    public SignedContractItem(Settings settings) {
        super(settings);
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
