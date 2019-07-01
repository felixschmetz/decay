import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

class PixelSortThread implements Runnable {

  private Color[][] img;
  private int thickness, shortest, longest;

  public PixelSortThread(Color[][] img, int thickness, int shortest, int longest) {
      this.img = img;
      this.thickness = thickness;
      this.shortest = shortest;
      this.longest = longest;
  }

  public void run() {
    sort(this.img);
  }

  void pixelsort(Color[] pixels, int start, int end, boolean direction) {
    Comparator<Color> compare;

    if (direction) {
      compare = new Comparator<Color>() {
        @Override
        public int compare(Color o1, Color o2) {
            return o1.getRed() - o2.getRed();
        }
      };
    } else {
      compare = new Comparator<Color>() {
        @Override
        public int compare(Color o1, Color o2) {
            return o2.getRed() - o1.getRed();
        }
      };
    }

    Arrays.sort(pixels, start, end, compare);
  }

  void sort(Color[][] img) {

    if (img == null || this.thickness < 1 || this.shortest < 1 || this.longest < 1)
        return;
    // TODO: add enclosure

    int[] lengths =  partition(img[0], this.shortest, this.longest);

    for (int i = 0; i < img.length; i++) {
      Color[] currentCol = img[i];
      // Generate random division of column and random lengths
      if (i % this.thickness == 0) lengths = partition(currentCol, this.shortest, this.longest);

      for (int j = 0, start = 0; j < lengths.length; start += lengths[j], j++) {
        pixelsort(currentCol, start, start + lengths[j], true);
      }

    }
  }

  int[] partition(Color[] currentCol, int min, int max) {

    Random rnd = new Random();
    int divisor = rnd.nextInt(max - min + 1) + min;
    int[] lengths = new int[divisor];

    int maxLength, minLength;

    int total = 0;

    // randomly assign a length to each interval
    minLength = 0;
    maxLength = currentCol.length / divisor * 3; // 3 tends to give good results

    for (int k = 0; k < divisor - 1; k++) {

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

}
