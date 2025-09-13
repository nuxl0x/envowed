package nuxlox.envowed.item.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import nuxlox.envowed.Envowed;
import nuxlox.envowed.data.ContractData;

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

        List<String> contracts = contractData.getContracts();

        for (String str : contracts) {
            Envowed.LOGGER.info(str);
        }

        return TypedActionResult.success(stack);
    }
}
