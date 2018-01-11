package com.tibia.helper;

public class UtilHelper {
	public String normalizeId(String id) {
		return id
				.trim()
				.replaceAll(" ", "")
				.replaceAll(",", ", ")
				.replaceAll("l", "1")
				.replaceAll("S", "5")
				.replaceAll("z", ":")
				.replaceAll("O", "0");
	}

	public String normalizePrice(String price) {
		return price
				.trim()
				.replaceAll(" ", "")
				.replaceAll(",", "")
				.replaceAll("l", "1")
				.replaceAll("O", "0");
	}

	public String normalizeFps(String fps) {
		return fps
				.trim()
				.replaceAll(" ", "")
				.replaceAll(",", "")
				.replaceAll("l", "1")
				.replaceAll("O", "0")
				.replaceAll("fps", "")
				.substring(0, fps.lastIndexOf("."));
	}
}
