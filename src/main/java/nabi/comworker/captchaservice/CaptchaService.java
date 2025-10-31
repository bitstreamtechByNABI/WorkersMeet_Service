package nabi.comworker.captchaservice;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class CaptchaService {

    private final Map<String, String> captchaStore = new ConcurrentHashMap<>();

    public Map<String, String> generateCaptcha() throws IOException {
 
        String captchaText = generateCaptchaText(5); 
        String captchaKey = UUID.randomUUID().toString();

        captchaStore.put(captchaKey, captchaText);

        BufferedImage image = new BufferedImage(180, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 180, 50);

        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < 180; i += 10) g.drawLine(i, 0, i, 50);
        for (int i = 0; i < 50; i += 10) g.drawLine(0, i, 180, i);

        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));
        g.drawLine(0, 30, 180, 20);

        g.setFont(new Font("Arial", Font.BOLD, 28));
        FontRenderContext frc = g.getFontRenderContext();

        Random rand = new Random();
        int startX = 25;
        for (int i = 0; i < captchaText.length(); i++) {
            char c = captchaText.charAt(i);
            float scale = 0.8f + rand.nextFloat() * 0.6f;
            int offsetY = (int) (Math.sin(i * Math.PI / 4) * 9) + 30;

            GlyphVector gv = g.getFont().createGlyphVector(frc, String.valueOf(c));
            Shape glyph = gv.getOutline();

            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate(startX, offsetY);
            g2.scale(scale, scale);

            g2.setColor(new Color(rand.nextInt(200, 255), rand.nextInt(200, 255), rand.nextInt(200, 255)));
            g2.fill(glyph);
            g2.dispose();

            startX += 30;
        }

        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());

        Map<String, String> response = new ConcurrentHashMap<>();
        response.put("url", "data:image/png;base64," + base64Image);
        response.put("key", captchaKey);

        return response;
    }

    private String generateCaptchaText(int length) {
        String upper = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijkmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "@#$@~%&?";

        String allChars = upper + lower + digits + special;
        Random rand = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(allChars.charAt(rand.nextInt(allChars.length())));
        }
        return sb.toString();
    }


    public boolean validateCaptcha(String key, String userInput) {
        if (key == null || userInput == null) return false;

        String expected = captchaStore.get(key);
        if (expected == null) return false;

        captchaStore.remove(key); 
        return expected.equalsIgnoreCase(userInput.trim());
    }

    public void clearStore() {
        captchaStore.clear();
    }
}
