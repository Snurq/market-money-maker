package com.tibia.helper;

import java.util.regex.Pattern;

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

	public String normalizePing(String ping) {
		ping = ping
			.trim()
			.replaceAll(Pattern.quote("Low lag "), "")
			.replaceAll(Pattern.quote("Medium lag "), "")
			.replaceAll(Pattern.quote("High lag "), "")
			.replaceAll(Pattern.quote("Measuring lag... "), "")
			.replaceAll(" ", "")
			.replaceAll("l", "1")
			.replaceAll("O", "0")
			.replaceAll("ms", "")
			.replaceAll(Pattern.quote(","), "")
			.replaceAll(Pattern.quote("("), "")
			.replaceAll(Pattern.quote(")"), "");
		
		try {
			Integer.parseInt(ping);
			
			return ping;
		} catch (Exception e) {
			return "0";
		}
	}
}
