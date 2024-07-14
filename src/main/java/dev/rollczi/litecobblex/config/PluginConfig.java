package dev.rollczi.litecobblex.config;

import dev.rollczi.litecobblex.cobblex.CobbleXDrop;
import java.io.File;
import java.util.List;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class PluginConfig implements ReloadableConfig {

    @Exclude
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Description("# Wiadomość o niepowodzeniu płatności")
    public String msgPaymentFailed = "&cNie posiadasz tyle itemow!";

    @Description("# Wiadomość o pomyślnej stworzeniu cobbleX")
    public String msgPaymentSucessfull = "&aStworzyles cobbleX";

    @Description("# Wiadomość, gdy przedmioty wypadną na ziemię z powodu braku miejsca")
    public String msgDropOnGround = "&cZabraklo ci miejsca w eq itemy wypadly na ziemie";

    @Description("# Wiadomość o dropie przedmiotu")
    public String msgDrop = "WOW! wydropiles: {dropName}!";

    @Description("# Przedmiot CobbleX")
    public ItemStack cobblexItem = createCobbleXItem();

    private ItemStack createCobbleXItem() {
        ItemStack item = new ItemStack(Material.MOSSY_COBBLESTONE);
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 10);
        item.editMeta(itemMeta -> itemMeta.displayName(MINI_MESSAGE.deserialize("<gradient:#1a266a:#27a76a>CobbleX</gradient>")));

        return item;
    }

    @Description("# Dropy z CobbleX")
    public List<CobbleXDrop> cobblexDrops = List.of(
        new CobbleXDrop(new ItemStack(Material.DIAMOND), 0.1, "Diament")
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }

}
