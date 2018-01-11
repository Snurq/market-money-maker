package com.tibia.facade;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import com.tibia.helper.ConstantsHelper;
import com.tibia.helper.ImageHelper;
import com.tibia.helper.KeyboardHelper;
import com.tibia.helper.MouseHelper;
import com.tibia.helper.XMLHelper;
import com.tibia.model.Item;

import net.sourceforge.tess4j.TesseractException;

public class Facade {
	private ImageHelper imageHelper;
	private MouseHelper mouseHelper;
	private KeyboardHelper keyboardHelper;
	private XMLHelper xmlHelper;
	private ConstantsHelper constantsHelper;

	private List<Item> items;

	public Facade() throws AWTException {
		this.imageHelper = new ImageHelper();
		this.mouseHelper = new MouseHelper();
		this.keyboardHelper = new KeyboardHelper();
		this.xmlHelper = new XMLHelper();
		this.constantsHelper = new ConstantsHelper();
	}

	public void run() throws InterruptedException, AWTException, IOException, TesseractException {
		delay(3000);

		this.items = xmlHelper.getItemsList();

		delay(500);

		this.mouseHelper.clickOnAnonymous();

		delay(500);

		this.mouseHelper.clickOnBuyButton();

		delay(500);

		for (int i = 0; i < this.items.size(); i++) {
			startShopping(this.items.get(i));

			delay(2000);
		}

		this.mouseHelper.clickOnCloseMarket();
	}	

	private void startShopping(Item item) throws InterruptedException, AWTException, IOException, TesseractException {
		this.mouseHelper.clickOnSearchBox();

		delay(500);

		this.keyboardHelper.selectAllTextAndDelete();

		delay(1000);

		this.keyboardHelper.type(item.getName());

		delay(500);

		this.mouseHelper.clickOnFirstFound();

		delay(2500);

		String id = this.imageHelper.getTextFromImage(
				this.constantsHelper.FIRST_SELLER_END_AT_X_TOP,
				this.constantsHelper.FIRST_SELLER_END_AT_Y_TOP,
				this.constantsHelper.FIRST_SELLER_END_AT_X_BOTTOM,
				this.constantsHelper.FIRST_SELLER_END_AT_Y_BOTTOM)
				.trim()
				.replaceAll(" ", "")
				.replaceAll(",", ", ")
				.replaceAll("l", "1")
				.replaceAll("S", "5")
				.replaceAll("z", ":")
				.replaceAll("O", "0");

		if (id.equals(item.getId())) {
			/**
			 * Se já existir uma oferta.
			 */
			System.out.println("Não comprou " + item.getName() + ". Você já possui uma oferta corrente.");
		} else {
			/**
			 * Se não existir nenhuma oferta:
			 */
			if (id.equals("")) {
				int firstOffer = Math.round((item.getPrice() / 2));

				this.mouseHelper.clickOnPiecePriceBox();

				delay(500);

				this.keyboardHelper.selectAllTextAndDelete();

				delay(1000);

				this.keyboardHelper.type(String.valueOf(firstOffer));

				delay(500);

				for (int i = 1; i < item.getBuy(); i++) {
					this.mouseHelper.clickOnIncreaseItemQuantity();
					delay(150);
				}

				this.mouseHelper.clickOnCreateOffer();

				delay(1500);

				String createdId = this.imageHelper.getTextFromImage(
						this.constantsHelper.FIRST_SELLER_END_AT_X_TOP,
						this.constantsHelper.FIRST_SELLER_END_AT_Y_TOP,
						this.constantsHelper.FIRST_SELLER_END_AT_X_BOTTOM,
						this.constantsHelper.FIRST_SELLER_END_AT_Y_BOTTOM)
						.trim()
						.replaceAll(" ", "")
						.replaceAll(",", ", ")
						.replaceAll("l", "1")
						.replaceAll("S", "5")
						.replaceAll("z", ":")
						.replaceAll("O", "0");

				delay(1000);

				this.xmlHelper.updateItemId(createdId, item.getName());

				System.out.println("Primeiro ao comprar " + item.getName() + " por: " + firstOffer);
			} else {
				/**
				 * Se já existir alguma oferta:
				 * Verificar se há alguma oferta obsoleta.
				 */
				boolean foundObsoleteOfferId = false;
				boolean foundObsoleteOfferRow = false;
				
				for (int f = 0; f < constantsHelper.NUMBER_OF_OFFERS_TO_CHECK; f++) {
					if (!foundObsoleteOfferId) {
						String currentRowId = this.imageHelper.getTextFromImage(
							this.constantsHelper.FIRST_SELLER_END_AT_X_TOP,
							this.constantsHelper.FIRST_SELLER_END_AT_Y_TOP,
							this.constantsHelper.FIRST_SELLER_END_AT_X_BOTTOM,
							this.constantsHelper.FIRST_SELLER_END_AT_Y_BOTTOM,
							f)
							.trim()
							.replaceAll(" ", "")
							.replaceAll(",", ", ")
							.replaceAll("l", "1")
							.replaceAll("S", "5")
							.replaceAll("z", ":")
							.replaceAll("O", "0");
						
						if (!currentRowId.equals("")) {
							if (item.getId().equals(currentRowId)) {
								System.out.println("Cancelar a oferta obsoleta que está na linha " + (f + 1) + ".");
								
								foundObsoleteOfferId = true;
								
								mouseHelper.clickOnMyOffers();
								
								delay(2000);
								
								int totalOfBuyOffers = Integer.parseInt(this.imageHelper.getTextFromImage(
										this.constantsHelper.NUMBER_OF_BUY_OFFERS_X_TOP,
										this.constantsHelper.NUMBER_OF_BUY_OFFERS_Y_TOP,
										this.constantsHelper.NUMBER_OF_BUY_OFFERS_X_BOTTOM,
										this.constantsHelper.NUMBER_OF_BUY_OFFERS_Y_BOTTOM)
										.trim()
										.replaceAll(" ", "")
										.replaceAll(",", ", ")
										.replaceAll("l", "1")
										.replaceAll("S", "5")
										.replaceAll("z", ":")
										.replaceAll("O", "0"));
								
								int numberOfHiddenBuyOffers = totalOfBuyOffers - constantsHelper.NUMBER_OF_VISIBLE_BUY_OFFERS;
								
								mouseHelper.clickOnFirstBuyOffer();
								
								String currentBuyOfferId;
								for (int g = 0; g < constantsHelper.NUMBER_OF_VISIBLE_BUY_OFFERS; g++) {
									if (!foundObsoleteOfferRow) {
										currentBuyOfferId = this.imageHelper.getTextFromImage(
											this.constantsHelper.FIRST_BUY_OFFER_END_AT_X_TOP,
											this.constantsHelper.FIRST_BUY_OFFER_END_AT_Y_TOP,
											this.constantsHelper.FIRST_BUY_OFFER_END_AT_X_BOTTOM,
											this.constantsHelper.FIRST_BUY_OFFER_END_AT_Y_BOTTOM,
											g)
											.trim()
											.replaceAll(" ", "")
											.replaceAll(",", ", ")
											.replaceAll("l", "1")
											.replaceAll("S", "5")
											.replaceAll("z", ":")
											.replaceAll("O", "0");
										
										if (item.getId().equals(currentBuyOfferId)) {
											delay(1000);
											
											mouseHelper.clickOnCancelOffer();
											
											delay(1000);
											
											foundObsoleteOfferRow = true;
											
											mouseHelper.clickOnBackToMarket();
											
											delay(2000);
											
											System.out.println("Oferta do item " + item.getName() + " com o id: " + currentBuyOfferId + ". Cancelada com sucesso.");
											
											break;
										} else {
											keyboardHelper.type("ˇ");
											
											delay(100);
										}
									}
								}
								
								if (!foundObsoleteOfferRow) {
									for (int h = 0; h < (numberOfHiddenBuyOffers + 1); h++) {
										currentBuyOfferId = this.imageHelper.getTextFromImage(
											this.constantsHelper.LAST_BUY_OFFER_END_AT_X_TOP,
											this.constantsHelper.LAST_BUY_OFFER_END_AT_Y_TOP,
											this.constantsHelper.LAST_BUY_OFFER_END_AT_X_BOTTOM,
											this.constantsHelper.LAST_BUY_OFFER_END_AT_Y_BOTTOM)
											.trim()
											.replaceAll(" ", "")
											.replaceAll(",", ", ")
											.replaceAll("l", "1")
											.replaceAll("S", "5")
											.replaceAll("z", ":")
											.replaceAll("O", "0");
										
										if (item.getId().equals(currentBuyOfferId)) {
											delay(1000);
											
											mouseHelper.clickOnCancelOffer();
											
											delay(1000);
											
											foundObsoleteOfferRow = true;
											
											mouseHelper.clickOnBackToMarket();
											
											delay(2000);
											
											System.out.println("Oferta do item " + item.getName() + " com o id: " + currentBuyOfferId + ". Cancelada com sucesso.");
											
											break;
										} else {
											keyboardHelper.type("ˇ");
											
											delay(100);
										}
									}
								}
								
							}
						}
					}
				}
				
				/**
				 * Iniciar o processo de compra
				 */
				int price = Integer.parseInt(this.imageHelper.getTextFromImage(
					this.constantsHelper.PIECE_PRICE_X_TOP,
					this.constantsHelper.PIECE_PRICE_Y_TOP,
					this.constantsHelper.PIECE_PRICE_X_BOTTOM,
					this.constantsHelper.PIECE_PRICE_Y_BOTTOM)
					.trim()
					.replaceAll(" ", "")
					.replaceAll(",", "")
					.replaceAll("l", "1")
					.replaceAll("O", "0"));

				price = (price + 1);

				/**
				 * Se o maior preço ofertado for menor que o preço do item:
				 */
				if (price < item.getPrice()) {
					this.mouseHelper.clickOnPiecePriceBox();

					delay(500);

					this.keyboardHelper.selectAllTextAndDelete();

					delay(1000);

					keyboardHelper.type(String.valueOf(price));

					delay(500);

					for (int i = 1; i < item.getBuy(); i++) {
						this.mouseHelper.clickOnIncreaseItemQuantity();
						delay(150);
					}

					this.mouseHelper.clickOnCreateOffer();

					delay(1500);

					String createdId = this.imageHelper.getTextFromImage(
							this.constantsHelper.FIRST_SELLER_END_AT_X_TOP,
							this.constantsHelper.FIRST_SELLER_END_AT_Y_TOP,
							this.constantsHelper.FIRST_SELLER_END_AT_X_BOTTOM,
							this.constantsHelper.FIRST_SELLER_END_AT_Y_BOTTOM)
							.trim()
							.replaceAll(" ", "")
							.replaceAll(",", ", ")
							.replaceAll("l", "1")
							.replaceAll("S", "5")
							.replaceAll("z", ":")
							.replaceAll("O", "0");

					delay(1000);

					this.xmlHelper.updateItemId(createdId, item.getName());

					System.out.println("Comprando " + item.getName() + " por: " + price + " gps.");
				} else {
					System.out.println("Não comprou " + item.getName() + ". Caro demais.");
				}
			}
		}
	}

	private void delay(int milliseconds) throws InterruptedException {
		Thread.sleep(milliseconds);
	}
}
