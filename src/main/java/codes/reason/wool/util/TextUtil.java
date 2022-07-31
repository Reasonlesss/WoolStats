package codes.reason.wool.util;

import codes.reason.wool.text.TextColor;
import codes.reason.wool.text.TextPart;

import java.awt.*;
import java.awt.font.LineMetrics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextUtil {

    public static void drawColoredText(Graphics2D g2d, List<TextPart> partList, int x, int y) {
        FontMetrics metrics = g2d.getFontMetrics();
        for (TextPart part:partList) {
            g2d.setColor(part.color().getColor());
            g2d.drawString(part.text(), x, y);
            x += metrics.stringWidth(part.text());
        }
    }

    public static int getWidth(Graphics2D g2d, List<TextPart> partList) {
        FontMetrics metrics = g2d.getFontMetrics();
        return metrics.stringWidth(getRawString(partList));
    }

    public static String getRawString(List<TextPart> partList) {
        StringBuilder stringBuilder = new StringBuilder();
        partList.forEach(part -> stringBuilder.append(part.text()));
        return stringBuilder.toString();
    }

    private static final Map<Character, TextColor> CODES = new HashMap<>();

    static {
        CODES.put('1', TextColor.DARK_BLUE);
        CODES.put('2', TextColor.DARK_GREEN);
        CODES.put('3', TextColor.DARK_AQUA);
        CODES.put('4', TextColor.DARK_RED);
        CODES.put('5', TextColor.DARK_PURPLE);
        CODES.put('6', TextColor.GOLD);
        CODES.put('7', TextColor.GRAY);
        CODES.put('8', TextColor.DARK_GRAY);
        CODES.put('9', TextColor.BLUE);
        CODES.put('0', TextColor.BLACK);

        CODES.put('a', TextColor.GREEN);
        CODES.put('b', TextColor.AQUA);
        CODES.put('c', TextColor.RED);
        CODES.put('d', TextColor.LIGHT_PURPLE);
        CODES.put('e', TextColor.YELLOW);
        CODES.put('f', TextColor.WHITE);
    }

    public static List<TextPart> parseCodes(char codeChar, String text) {
        List<TextPart> parts = new ArrayList<>();
        StringBuilder currentText = new StringBuilder();
        TextColor currentColor = TextColor.WHITE;

        char[] chars = text.toCharArray();
        boolean checkingCode = false;

        for (char aChar : chars) {
            if (checkingCode) {
                if (CODES.containsKey(aChar)) {
                    parts.add(new TextPart(currentText.toString(), currentColor));
                    currentText = new StringBuilder();
                    currentColor = CODES.get(aChar);
                }else {
                    currentText.append(codeChar);
                    currentText.append(aChar);
                }
                checkingCode = false;
                continue;
            }
            if (aChar == codeChar) {
                checkingCode = true;
                continue;
            }
            currentText.append(aChar);
        }

        parts.add(new TextPart(currentText.toString(), currentColor));
        return parts;
    }



}
