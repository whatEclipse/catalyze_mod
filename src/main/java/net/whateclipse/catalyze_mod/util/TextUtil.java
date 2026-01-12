package net.whateclipse.catalyze_mod.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;

public class TextUtil {

    /**
     * Creates an animated gradient component from a sequence of colors.
     * 
     * @param text   The text to apply the gradient to
     * @param colors The colors to interpolate between (as 0xRRGGBB)
     * @return A MutableComponent with the gradient applied
     */
    public static MutableComponent createAnimatedGradientComponent(String text, int... colors) {
        if (text == null || text.isEmpty())
            return Component.empty();
        MutableComponent root = Component.empty();
        if (colors == null || colors.length < 2) {
            int defaultColor = (colors != null && colors.length == 1) ? colors[0] : 0xFFFFFF;
            return root.append(Component.literal(text).withStyle(s -> s.withColor(defaultColor)));
        }

        long time = System.currentTimeMillis();
        // Cycle every 4 seconds for a very smooth and calm feel
        double phase = (time % 4000) / 4000.0;

        for (int i = 0; i < text.length(); i++) {
            // Calculate spatial offset
            double offset = (double) i / Math.max(1, text.length() - 1);

            // Use a triangular wave for natural ping-pong (0 -> 1 -> 0)
            double t = (offset + phase * 2) % 2.0;
            double totalProgress = t > 1.0 ? 2.0 - t : t;

            // Map totalProgress to the color segments
            double segmentCount = colors.length - 1;
            double scaledProgress = totalProgress * segmentCount;
            int index = (int) scaledProgress;
            double segmentProgress = scaledProgress - index;

            // Clamp index to prevent out of bounds
            index = Math.max(0, Math.min(index, colors.length - 2));

            int color = interpolate(colors[index], colors[index + 1], segmentProgress);
            String charStr = String.valueOf(text.charAt(i));
            root.append(Component.literal(charStr).withStyle(s -> s.withColor(TextColor.fromRgb(color))));
        }

        return root;
    }

    private static int interpolate(int color1, int color2, double ratio) {
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        int r = (int) (r1 + (r2 - r1) * ratio);
        int g = (int) (g1 + (g2 - g1) * ratio);
        int b = (int) (b1 + (b2 - b1) * ratio);

        return (r << 16) | (g << 8) | b;
    }
}
