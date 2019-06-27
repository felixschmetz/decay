import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;


class ImageProcessor {

  private int imageType;

  // Save BufferedImage as file
  void saveImage(String fileName, BufferedImage image) {

    try {
      File directory = new File("output");
      if (! directory.exists()){
        directory.mkdir();
      }

      ImageIO.write(image, "jpg", new File("output/" + fileName + "_out.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Saving \"" + fileName + "_out.jpg" + "\"");
  }

  // Save Matrix of Colors as file
  void saveImage(String fileName, Color[][] image) {

    BufferedImage output = converToBImage(image);

    try {

      File directory = new File("output");
      if (! directory.exists()){
        directory.mkdir();
      }

      ImageIO.write(output, "jpg", new File("output/" + fileName + "_out.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Saving \"" + fileName + "_out.jpg" + "\"");

  }

  // Loads image in project root directory
  BufferedImage loadImage(String fileName) {

    File file = new File("images/" + fileName + ".jpg");
    BufferedImage image = null;
    try {
      image = ImageIO.read(file);
      imageType = image.getType();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return image;
  }


  // Convert BufferedImage to grayscale
  BufferedImage convertToGrayscale(BufferedImage image) {

    int n = image.getWidth();
    int m = image.getHeight();
    BufferedImage grayImage = new BufferedImage(n, m, image.getType());

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {

        int rgb = image.getRGB(i,j);
        Color imageColor = new Color(rgb);

        int outRGB = (int) (imageColor.getRed() * 0.299)
                   + (int) (imageColor.getGreen() * 0.587)
                   + (int) (imageColor.getBlue() * 0.114);

        Color outColor = new Color(outRGB, outRGB, outRGB);
        grayImage.setRGB(i, j, outColor.getRGB());

      }
    }

    return grayImage;
  }

  // Build grayScale matrix
  // Same function above but this is to minimize runtime so we dont convert to grayscale first and then build the matrix
  Color[][] buildMatrixGray(BufferedImage image) {

    int n = image.getWidth();
    int m = image.getHeight();

    Color[][] output = new Color[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {

        Color imageColor = new Color(image.getRGB(i,j));

        int outRGB = (int) (imageColor.getRed() * 0.299)
                   + (int) (imageColor.getGreen() * 0.587)
                   + (int) (imageColor.getBlue() * 0.114);

        output[i][j] = new Color(outRGB, outRGB, outRGB);

      }
    }

    return output;
  }

  // Take BufferedImage as input and convert to matrix of Color
  Color[][] buildMatrix(BufferedImage image) {

    int n = image.getWidth();
    int m = image.getHeight();

    Color[][] output = new Color[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) output[i][j] = new Color(image.getRGB(i,j));
    }

    return output;
  }

  BufferedImage converToBImage(Color[][] image) {

    int n = image.length;
    int m = image[0].length;
    BufferedImage out = new BufferedImage(n, m, imageType);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {

        Color outColor = image[i][j];
        out.setRGB(i, j, outColor.getRGB());

      }
    }

    return out;

  }

}
