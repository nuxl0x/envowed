package nuxlox.envowed;

import net.fabricmc.api.ModInitializer;

import nuxlox.envowed.item.BindingItems;
import nuxlox.envowed.item.ItemHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Envowed implements ModInitializer {
	public static final String MOD_ID = "envowed";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info(MOD_ID + " is now initializing.");

        // Item & Item Group Initialization
        ItemHelper.initializeItems();
        ItemHelper.initializeItemGroups();
	}
}