# decay
## Status
**Currently only supports pixel sorting!**

Following functionalities will be added shortly:

* Adapt bound of streak lengths
* Change thickness of streaks
* horizontal sorting
* increasing streak length throughout picture or just part of picture
* enclosure sorting: select specific part of image to be modified and leave the rest untouched
* Support for other data types than jpg


## Getting Started
### Usage
Compile using the following command:

```
javac decay.java
```
An image folder has been added with example images that deliver visually pleasing results. To decay one of the examples, run the following command:  

```
java decay -op (param_1 ... param_n) fileName
```  

and replace -op with the operation descriptor, params with the operation parameters and fileName with the name of the example image. Do not append ".jpg"! You can find the operation descriptors below in the Functionalities section.

Example:  

```
java decay -ps 2 long scream
```  
This command will run the pixelsorter with a thickness of 2 pixels and long streaks, explained in the Pixel Sorting section.

A color and grayscale version of the decayed image will appear in the output folder.

If you'd like to 'decay' your own image, place it into the images folder and run the command of your choice. (Make sure your image is in .jpg format).


# Functionalities
### Pixel Sorting

![t1](https://raw.githubusercontent.com/felixschmetz/decay/master/examples/thickness/t1.jpg)![t2](https://raw.githubusercontent.com/felixschmetz/decay/master/examples/thickness/t2.jpg)![t6](https://raw.githubusercontent.com/felixschmetz/decay/master/examples/thickness/t6.jpg)![t10](https://raw.githubusercontent.com/felixschmetz/decay/master/examples/thickness/t10.jpg)


