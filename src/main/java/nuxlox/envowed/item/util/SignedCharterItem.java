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

public class SignedCharterItem extends Item {
    public SignedCharterItem(Settings settings) {
        super(settings);
    }



    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getNbt();

        tooltip.add(Text.translatable("itemTooltip.envowed.signedCharter1"));
        tooltip.add(Text.translatable("itemTooltip.envowed.signedCharter2"));

        if (nbt != null && nbt.contains("chartererName") && nbt.contains("charteredName")) {
            String chartererName = nbt.getString("chartererName");
            String charteredName = nbt.getString("charteredName");
            tooltip.add(Text.translatable("itemTooltip.envowed.charterer", chartererName).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable("itemTooltip.envowed.chartered", charteredName).formatted(Formatting.DARK_RED));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
