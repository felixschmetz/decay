import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

class Decay {

  public static void main(String[] args) {

    Parser p = new Parser();
    ImageProcessor ip = new ImageProcessor();
    String fileName = p.getFileName(args);
    BufferedImage image = ip.loadImage(fileName);
    Color[][] colorMatrix = ip.buildMatrix(image);

    Operation o = p.getOperator(args);

    o.manipulate(colorMatrix);

    ip.saveImage(fileName, colorMatrix);

  }

}
