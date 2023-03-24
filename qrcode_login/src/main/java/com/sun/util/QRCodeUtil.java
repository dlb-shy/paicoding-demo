package com.sun.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class QRCodeUtil {

    /**
     * 生成二维码图片
     * @param content 二维码内容
     * @param width 图片宽度
     * @param height 图片高度
     * @param imagePath 图片存放路径
     */
    public static void generateQRCode(String content, int width, int height, String imagePath) throws WriterException, IOException {
        // 设置二维码相关配置
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        hints.put(EncodeHintType.MARGIN, 0);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        // 添加logo图片
        File logoFile = new File("qrcode_login/src/main/resources/images/logo.png");
        if (logoFile.exists()) {
            BufferedImage logoImage = ImageIO.read(logoFile);
            Graphics2D graphics = image.createGraphics();
            int logoWidth = Math.min(logoImage.getWidth(), image.getWidth() * 2 / 10);
            int logoHeight = Math.min(logoImage.getHeight(), image.getHeight() * 2 / 10);
            int x = (image.getWidth() - logoWidth) / 2;
            int y = (image.getHeight() - logoHeight) / 2;
            graphics.drawImage(logoImage, x, y, logoWidth, logoHeight, null);
            graphics.dispose();
        }
        // 将二维码图片写入文件
        ImageIO.write(image, "png", new File(imagePath));
    }

    public static void main(String[] args) throws IOException, WriterException {
        String content = "http://www.baidu.com"; // 要生成二维码的内容
        int width = 300; // 二维码图片宽度
        int height = 300; // 二维码图片高度
        String imagePath = "qrcode.png"; // 二维码图片存放路径

        // 生成二维码图片
        generateQRCode(content, width, height, imagePath);
    }

    /**
     * 解析二维码图片内容
     * @param imagePath 二维码图片路径
     * @return 二维码内容
     */
    public static String parseQRCode(String imagePath) throws Exception {
        BufferedImage image = ImageIO.read(new File(imagePath));
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        HashMap<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }
}
