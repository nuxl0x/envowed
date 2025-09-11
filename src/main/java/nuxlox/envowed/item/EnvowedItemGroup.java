package nuxlox.envowed.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nuxlox.envowed.Envowed;

public class EnvowedItemGroup {

    public static final RegistryKey<ItemGroup> ENVOWED_ITEM_GROUP_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(), new Identifier(Envowed.MOD_ID, "envowed"));

    public static final ItemGroup ENVOWED_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BindingItems.CONTRACT))
            .displayName(Text.translatable("itemGroup.envowed"))
            .build();

    public static void registerItemGroup() {

        Registry.register(Registries.ITEM_GROUP, ENVOWED_ITEM_GROUP_KEY, ENVOWED_ITEM_GROUP);

    }

    public static void addItemGroupItems() {

        ItemGroupEvents.modifyEntriesEvent(ENVOWED_ITEM_GROUP_KEY).register(itemGroup -> {

            itemGroup.add(BindingItems.CONTRACT);
            itemGroup.add(BindingItems.SIGNED_CONTRACT);
            itemGroup.add(BindingItems.CHARTER);
            itemGroup.add(BindingItems.SIGNED_CHARTER);

        });

    }

    public static void initialize() {
        registerItemGroup();
        addItemGroupItems();
    }

}
