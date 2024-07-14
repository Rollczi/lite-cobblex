package dev.rollczi.litecobblex.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.eldoria.jacksonbukkit.JacksonPaper;
import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import net.dzikoysk.cdn.serdes.SimpleSerializer;
import org.bukkit.inventory.ItemStack;
import panda.std.Result;

public class ItemStackComposer implements SimpleDeserializer<ItemStack>, SimpleSerializer<ItemStack>, Composer<ItemStack> {

    private final ObjectMapper JSON = JsonMapper.builder()
        .addModule(JacksonPaper.builder()
            .useLegacyItemStackSerialization()
            .build()
        )
        .build();

    @Override
    public Result<ItemStack, Exception> deserialize(String value) {
        try {
            return Result.ok(JSON.readValue(value, ItemStack.class));
        } catch (JsonProcessingException exception) {
            return Result.error(exception);
        }
    }

    @Override
    public Result<String, Exception> serialize(ItemStack duration) {
        try {
            return Result.ok(JSON.writeValueAsString(duration));
        } catch (JsonProcessingException exception) {
            return Result.error(exception);
        }
    }

}
