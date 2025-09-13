package nuxlox.envowed.item.util;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import nuxlox.envowed.Envowed;
import nuxlox.envowed.data.CharterData;
import nuxlox.envowed.data.ContractData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DebuggerItem extends Item {

    public DebuggerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.pass(stack);
        }

        ContractData contractData = ContractData.get((ServerWorld) world);
        CharterData charterData = CharterData.get((ServerWorld) world);

        List<String> contracts = contractData.getContracts();
        List<String> charters = charterData.getCharters();

        Envowed.LOGGER.info("Contract Entries:");
        for (String str : contracts) {
            Envowed.LOGGER.info(str);
        }

        Envowed.LOGGER.info("");

        Envowed.LOGGER.info("Charter Entries:");
        for (String str : charters) {
            Envowed.LOGGER.info(str);
        }


        return TypedActionResult.success(stack);
    }



    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext tooltipContext) {

        tooltip.add(Text.literal("The mightiest of all of the items within Envowed."));
        tooltip.add(Text.literal("Nothing comes close to the power of this... weapon."));
        tooltip.add(Text.literal("All bow in glory at the sight of the mighty... DEBUGGER!"));
        tooltip.add(Text.literal("(Envowed Contract & Charter Storage Debugger)"));

    }
}
