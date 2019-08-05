# decay

![head](https://i.imgur.com/i4pZjV8.jpg)
## Status
**Currently only supports pixel sorting!**

Following functionalities will be added shortly:

Soon:
* Full parallelization
* Arguments for
  * Light to dark or dark to light
  * Adapt bound of streak lengths (manually?)
* horizontal sorting
* arguments for horizontal or vertical sorting
* Java interface with sliders and such (live operations on reduced image as preview and saving of high qual image)

Near future:
* increasing streak length throughout picture or just part of picture

Far future:
* enclosure sorting: select specific part of image to be modified and leave the rest untouched

Probably around October/November:
* Port everything to iphone
* Support for different editing operations:
  * Simple ones:
    * Blurring
    * Graining
    * common camera artifacts
    * Color degrading
  * think of other ones
* Make new github repository with nice layout

## Getting Started
### Usage
Compile using the following command:

```
javac Decay.java ImageProcessor.java PixelSort.java Parser.java Operation.java
```
An image folder has been added with example images that deliver visually pleasing results. To decay one of the examples, run the following command:  

```
java Decay -op param_1 ... param_n fileName
```  
and replace -op with the operation descriptor, param_1 through param_n with the operation parameters and fileName with the name of the image you want to decay. Do not append ".jpg"! You can find the operation descriptors below in the Functionalities section.

Example usage:  

```
java Decay -ps 2 short scream
```  
This command will run the pixelsorter with a thickness of 2 pixels and short vertical streaks. All this is explained in the Pixel Sorting section. All images have to be in the image folder.

A colored version of the decayed image will appear in the output folder. TODO: Add gray parameter.

If you'd like to 'decay' your own image, place it into the images folder and run the command of your choice. (Make sure your image is in .jpg format)


# Functionalities
### Pixel Sorting

**What does it do?** The pixel sorter will go through each column \[row\], divide each row \[column\] into a random number of subarrays between the lower and upper bound provided by the user and sort them from dark to light \[or light to dark\] depending on the users input. For vertical sorting use keyword vertical, for horizontal sorting use keyword horizontal.

**Operation descriptor:** -ps
**Parameters:** thickness [Integer], length [String]

**Thickness:** The thickness parameter determines the thickness of the vertical streaks in pixels. Pictured below. The first image has a thickness of 1, which means that they are one pixel wide. The second picture has thickness 3 and the last thickness 10. How visually pleasing the result is depends on the resolution of the input image.  
  

![thickness](https://i.imgur.com/Xe3MK6J.png)
  
  
**Length:**
The average length of the vertical streaks. Either write short, med or long.  
  
![length](https://i.imgur.com/VIs7OAo.jpg)
