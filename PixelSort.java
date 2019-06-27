import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

class PixelSort extends Operation {

  private int thickness, shortest, longest;
  private String length;
  public PixelSort(int thickness, String length) {
    this.thickness = thickness;
    this.length = length;

    int[] bound = getBound();

    this.shortest = bound[0];
    this.longest = bound[1];

  }

  public void manipulate(Color[][] img) {
    sort(img, thickness, shortest, longest, 0, img.length);
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

  static void sort(Color[][] img, int thickness, int shortest,
                                  int longest, int left, int right) {

    if (img == null || thickness < 1 || shortest < 1 || longest < 1)
      return;
    // TODO: add enclosure

    int n = img.length;
    int[] lengths =  partition(img[0], shortest, longest);

    for (int i = left; i < right; i++) {
      Color[] currentCol = img[i];
      // Generate random division of column and random lengths
      if (i % thickness == 0) lengths = partition(currentCol, shortest, longest);

      for (int j = 0, start = 0; j < lengths.length; start += lengths[j], j++) {
        pixelsort(currentCol, start, start + lengths[j]);
      }

    }
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

  public int[] getBound() {
    if (this.length.toLowerCase().equals("long")) {
      return new int[]{20, 40};
    } else if (this.length.toLowerCase().equals("med")) {
      return new int[]{40, 60};
    } else {
      return new int[]{60, 80};
    }
  }


}
