import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

// TODO: Refractor code, remove anything from stackoverflow and rewrite

/*
 * TODO: Parallelize: Annoying to wait so long. Things to parallelize:
 * - Converting image to grayscale
 * - Converting BufferedImage to Color[][] Matrix
 * - Sorting algorithm itself (unnecessary though because size is never really over 5000 px which is already damn near basecase)
 * - Sorting of the columns, split number of columns
 */

class PixelSorter {


  static int imageType;
  public static void main(String[] args) {

    String fileName = args[0];
    // BufferedImage image = loadImage("images/" + fileName + ".jpg");
    BufferedImage imagePar = loadImage("images/" + fileName + ".jpg");


    // TODO: Progress bar!
    // TODO: Nicer console output

    // Color[][] gimg = buildMatrixGray(image); //grayscale image
    // Color[][] img = buildMatrix(image); //grayscale image
    Color[][] imgPar = buildMatrix(imagePar); //grayscale image

    int thickness = 1;
    int shortest = 40;
    int longest = 60;

    // sort_seq(img, thickness, shortest, longest);
    sort_parallel(imgPar, thickness, shortest, longest);
    // saveImage(fileName + "_out", img);
    // saveImage(fileName + "_out" + "_gray", img);
    saveImage(fileName + "_out" + "_par", imgPar);

    // TODO: add drawing interface to determine enclosing

    // TODO: smoothing of enclosing?

  }

  static void sort_seq(Color[][] img, int thickness, int shortest, int longest) {
    sort(img, thickness, shortest, longest, 0, img.length);
  }

  static void sort_parallel(Color[][] img, int thickness, int shortest, int longest) {

    int maxThreadCount = Runtime.getRuntime().availableProcessors();



    if (img.length < 1000) sort(img, thickness, shortest, longest, 0, img.length);
    else {

      int mid = img.length / 2;
      int mid1 = mid/2;
      int mid2 = (mid + img.length) / 2;

      sort(img, thickness, shortest, longest, 0, mid1);
      sort(img, thickness, shortest, longest, mid1, mid);
      sort(img, thickness, shortest, longest, mid, mid2);
      sort(img, thickness, shortest, longest, mid2, img.length);

    }

    // sort(img, thickness, shortest, longest, 0, img.length);

  }

  static int[] partition(Color[] currentCol, int min, int max) {

    Random rnd = new Random();
    int divisor = rnd.nextInt(max - min + 1) + min;
    int[] lengths = new int[divisor];

    int maxLength, minLength;

    int total = 0;

    // randomly assign a length to each interval
    minLength = 0;
    maxLength = currentCol.length / divisor * 3; // 3 tends to give good results

    for (int k = 0; k < divisor - 1; k++) {

      // maxLength = currentCol.length - total;

      int currLength = rnd.nextInt(maxLength - minLength + 1) + minLength;

      while (total + currLength > currentCol.length) {
        maxLength--;
        currLength = rnd.nextInt(maxLength - minLength + 1) + minLength;
      }

      lengths[k] = currLength;
      total += currLength;
    }
    lengths[lengths.length - 1] = currentCol.length - total;

    return lengths;
  }

  static void sort(Color[][] img, int thickness, int shortest, int longest, int left, int right) {

    if (img == null || thickness < 1 || shortest < 1 || longest < 1) return;
    // TODO: add enclosure

    int n = img.length;
    int[] lengths =  partition(img[0], shortest, longest);


    // this code is bad because runtime error, just test!
    // int templong = img[0].length - 5;
    // int tempshort = templong - 5;
    // if (tempshort < 1) tempshort = 1;

    for (int i = left; i < right; i++) {
      Color[] currentCol = img[i];
      // Generate random division of column and random lengths
      if (i % thickness == 0) lengths = partition(currentCol, shortest, longest);
      // if (i % thickness == 0) {
      //   lengths = partition(currentCol, tempshort, templong);
      //   templong -= 4;
      //   tempshort = templong -4;
      //   if (templong < 40) templong = 40;
      //   if (tempshort < 1) tempshort = 1;
      // }

      for (int j = 0, start = 0; j < lengths.length; start += lengths[j], j++) {
        pixelsort(currentCol, start, start + lengths[j]);
      }

    }
  }

  static void pixelsort(Color[] pixels, int start, int end) {
    Comparator<Color> compare = new Comparator<Color>() {
      @Override
      public int compare(Color o1, Color o2) {
          return o1.getRed() - o2.getRed();
      }
    };

    Arrays.sort(pixels, start, end, compare);
  }

  static BufferedImage converToBImage(Color[][] image) {

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

  // Save BufferedImage as file
  static void saveImage(String fileName, BufferedImage image) {

    try {
      File directory = new File("output");
      if (! directory.exists()){
        directory.mkdir();
      }

      ImageIO.write(image, "jpg", new File("output/" + fileName + ".jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Saving \"" + fileName + ".jpg" + "\"");
  }

  // Save Matrix of Colors as file
  static void saveImage(String fileName, Color[][] image) {

    BufferedImage output = converToBImage(image);

    try {

      File directory = new File("output");
      if (! directory.exists()){
        directory.mkdir();
      }

      ImageIO.write(output, "jpg", new File("output/" + fileName + ".jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Saving \"" + fileName + ".jpg" + "\"");

  }

  // Loads image in project root directory
  static BufferedImage loadImage(String fileName) {

    File file = new File(fileName);
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
  static BufferedImage convertToGrayscale(BufferedImage image) {

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
  static Color[][] buildMatrixGray(BufferedImage image) {

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
  static Color[][] buildMatrix(BufferedImage image) {

    int n = image.getWidth();
    int m = image.getHeight();

    Color[][] output = new Color[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) output[i][j] = new Color(image.getRGB(i,j));
    }

    return output;
  }

}
