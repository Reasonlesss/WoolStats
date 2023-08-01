package codes.reason.wool.embed.text;

import codes.reason.wool.common.TextColor;

public record TextPart(String text, TextColor color) {

    public TextPart append(String text) {
        return new TextPart(text() + text, color);
    }

}
