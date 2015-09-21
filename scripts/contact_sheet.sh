#!/bin/bash

# Creates a contact sheet for about 2300 images. No real reason just a nice thumbnail view.
# Grid layout pins both width and height so there's no fixing the gaps of white space between.

montage -verbose -font Arial -pointsize 10 -background "#FFFFFF" -fill "gray" -define png:size=300x300 -geometry "300x300" -tile "40x60" -auto-orient ../target/images/*.png ~/contact.png

