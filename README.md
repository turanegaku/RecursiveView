RecursiveView
====

## Overview
This is Recursive Image Viewer using JavaFX.

## Description
- This application show you **ALL** Image file in the directory.
- You know what images in directories which is in the specified directory.
- input your specified directory in the inputbox "root directory"
- You can see the images up down scroll.
- Click ``Next`` or ``Prev`` button, then viewer show the next Directory

## Requirement
- JavaFX
- Java8

## Example
This is sample Directory.
```
specified_directory/
+-- animal/
	+-- bird/
		+-- bird1.png
		+-- bird2.png
		+-- bird3.png
		+-- bird4.png
		+-- bird5.png
		+-- bird6.png
	+-- cat.png
	+-- dog.png
+-- food/
	+-- egg.png
	+-- cake.png
	+-- rice.png
+-- charactor/
	+-- a.png
	+-- b.png
	+-- c.png
	+-- d.png
	+-- e.png
	+-- f.png
```

You input abusolute path of specified_directory in the inputbox "root directory"  
`Enter Key`  
You can first see bird1~5.png, cat.png, dog.png.  
``Next``  
egg.png, cake.png, rice.png  
``Next``  
a ~ f.png
