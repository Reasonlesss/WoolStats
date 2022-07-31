package codes.reason.wool.text;

public record TextPart(String text, TextColor color) {

    public TextPart append(String text) {
        return new TextPart(text() + text, color);
    }

}
