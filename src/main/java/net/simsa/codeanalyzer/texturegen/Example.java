package net.simsa.codeanalyzer.texturegen;
import magick.ImageInfo;
//import magick.Magick; 
import magick.MagickImage; //This class will convert the image to JPG format

public class Example {

      public static void main(String[] args) throws Exception {
        
       String inputfileName = "screendump.bmp"; //Input BMP file
       ImageInfo info = new ImageInfo(inputfileName); //Get BMP file into ImageInfo object
       MagickImage magick_converter = new MagickImage(info); //Create MagickImage object
       
       String outputfile = "jmagick_converted_image.jpg"; //Output File name
       magick_converter.setFileName(outputfile); //set output file format
       magick_converter.writeImage(info); //do the conversion
       
      }

}
