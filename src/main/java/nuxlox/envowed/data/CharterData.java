package nuxlox.envowed.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import nuxlox.envowed.Envowed;

import java.util.ArrayList;
import java.util.List;

public class CharterData extends PersistentState {
    private static final String DATA_NAME = "envowed_charter_data";

    // Data Fields
    private final List<String> charters = new ArrayList<>();

    public CharterData() {
        super();
    }

    public static CharterData get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(
                CharterData::fromNbt,
                CharterData::new,
                DATA_NAME
        );
    }

    public static CharterData fromNbt(NbtCompound nbt) {
        CharterData data = new CharterData();

        if (nbt.contains("charters", NbtElement.LIST_TYPE)) {
            NbtList contractsNbt = nbt.getList("charters", NbtElement.STRING_TYPE);
            for (int i = 0; i < contractsNbt.size(); i++) {
                data.charters.add(contractsNbt.getString(i));
            }
        }

        return data;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {

        NbtList chartersNbt = new NbtList();
        for (String str : charters) {
            chartersNbt.add(NbtString.of(str));
        }
        nbt.put("charters", chartersNbt);

        return nbt;
    }

    public Boolean checkForExistingEntry(String entryToCheck) {
        return charters.contains(entryToCheck);
    }

    public void createNewEntry(String entryData) {

        if (!charters.contains(entryData)) {
            charters.add(entryData);
            this.markDirty();
        } else {
            Envowed.LOGGER.error("Failed to create new charter entry, as entry already existed.");
        }

    }

    public void removeEntry(String entryData) {

        if (charters.contains(entryData)) {
            charters.remove(entryData);
            this.markDirty();
        } else {
            Envowed.LOGGER.error("Failed to remove existing charter entry, no entry found.");
        }

    }

    public List<String> getCharters() {
        return charters;
    }
}
