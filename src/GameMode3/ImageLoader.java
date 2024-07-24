package GameMode3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            // Tạo đường dẫn tệp đầy đủ
            String fullPath = "Data" + path;
            System.out.println("Loading image from path: " + fullPath);

            // Kiểm tra tệp có tồn tại không
            File imgFile = new File(fullPath);
            if (!imgFile.exists()) {
                System.out.println("File not found: " + fullPath);
                System.exit(1);
            }

            // Đọc tệp hình ảnh
            return ImageIO.read(imgFile);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
