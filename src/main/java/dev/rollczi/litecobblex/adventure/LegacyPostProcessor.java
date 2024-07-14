package dev.rollczi.litecobblex.adventure;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class LegacyPostProcessor implements UnaryOperator<Component> {

    private static final Pattern PATTERN = Pattern.compile(".*");
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
    private static final BiFunction<MatchResult, TextComponent.Builder, ComponentLike> REPLACEMENT = (matchResult, build) -> LEGACY_SERIALIZER.deserialize(matchResult.group());

    private static final TextReplacementConfig LEGACY_REPLACEMENT_CONFIG = TextReplacementConfig.builder()
        .match(PATTERN)
        .replacement(REPLACEMENT)
        .build();

    @Override
    public Component apply(Component component) {
        return component.replaceText(LEGACY_REPLACEMENT_CONFIG);
    }

}
