# decay
Mess up pictures in a cool way.

### Status
Currently only supports pixel sorting!

Following functionalities will be added shortly:
- Adapt bound of streak lengths
- Change thickness of streaks
- horizontal sorting
- increasing streak length throughout picture or just part of picture
- enclosure sorting: select specific part of image to be modified and leave the rest untouched
- Support for other data types than jpg

### Getting Started

# Usage
Compile using the following command:
```
javac PixelSorter.java
```
An image folder has been added with example images that deliver visually pleasing results. To decay one of the examples, run the following command:
```
java PixelSorter fileName
```
and replace fileName with the name of the example image. Do not append ".jpg"!

Example usage:
```
java PixelSorter scream
```
A color and grayscale version of the decayed image will appear in the output folder.

If you'd like to 'decay' your own image, place it into the images folder and run the previously mentioned command. (Make sure your image is in .jpg folder)
