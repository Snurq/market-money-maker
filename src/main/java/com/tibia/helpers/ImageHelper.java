package com.tibia.helpers;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ImageHelper {
	public BufferedImage resize(BufferedImage image, int newWidth, int newHeight) throws IOException {
		return Thumbnails.of(image).forceSize(newWidth, newHeight).asBufferedImage();
	}
}
