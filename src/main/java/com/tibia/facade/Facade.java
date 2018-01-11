package com.tibia.facade;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.tibia.helper.ConstantsHelper;
import com.tibia.helper.ImageHelper;
import com.tibia.helper.KeyboardHelper;
import com.tibia.helper.MouseHelper;
import com.tibia.helper.UtilHelper;
import com.tibia.helper.XMLHelper;
import com.tibia.model.Item;

import net.sourceforge.tess4j.TesseractException;

public class Facade {
	private ImageHelper image;
	private MouseHelper mouse;
	private KeyboardHelper keyboard;
	private XMLHelper xml;
	private ConstantsHelper constants;
	private UtilHelper util;

	private List<Item> items;

	public Facade() throws AWTException {
		this.image = new ImageHelper();
		this.mouse = new MouseHelper();
		this.keyboard = new KeyboardHelper();
		this.xml = new XMLHelper();
		this.constants = new ConstantsHelper();
		this.util = new UtilHelper();
	}

	public void run() throws InterruptedException, AWTException, IOException, TesseractException {
		showMessage(true, this.constants.PROGRAM_TITLE, "Bem-vindo \n\n 1) Abra a janela do Tibia. \n 2) Abra o Market. \n 3) Aperte OK. \n\n");

		this.items = xml.getItemsList();

		delay(500);

		this.mouse.clickOnAnonymous();

		delay(500);

		this.mouse.clickOnBuyButton();

		delay(500);

		for (int i = 0; i < this.items.size(); i++) {
			startShopping(this.items.get(i));

			delay(2000);
		}

		this.mouse.clickOnCloseMarket();
	}	

	private void startShopping(Item item) throws InterruptedException, AWTException, IOException, TesseractException {
		this.mouse.clickOnSearchBox();

		delay(500);

		this.keyboard.selectAllTextAndDelete();

		delay(1000);

		this.keyboard.type(item.getName());

		delay(500);

		this.mouse.clickOnFirstFound();

		delay(2500);

		String id = util.normalizeId(this.image.getTextFromImage(
				this.constants.FIRST_SELLER_END_AT_X_TOP,
				this.constants.FIRST_SELLER_END_AT_Y_TOP,
				this.constants.FIRST_SELLER_END_AT_X_BOTTOM,
				this.constants.FIRST_SELLER_END_AT_Y_BOTTOM));

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

				this.mouse.clickOnPiecePriceBox();

				delay(500);

				this.keyboard.selectAllTextAndDelete();

				delay(1000);

				this.keyboard.type(String.valueOf(firstOffer));

				delay(500);

				for (int i = 1; i < item.getBuy(); i++) {
					this.mouse.clickOnIncreaseItemQuantity();
					delay(150);
				}

				this.mouse.clickOnCreateOffer();

				delay(1500);

				String createdId = util.normalizeId(this.image.getTextFromImage(
						this.constants.FIRST_SELLER_END_AT_X_TOP,
						this.constants.FIRST_SELLER_END_AT_Y_TOP,
						this.constants.FIRST_SELLER_END_AT_X_BOTTOM,
						this.constants.FIRST_SELLER_END_AT_Y_BOTTOM));

				delay(1000);

				this.xml.updateItemId(createdId, item.getName());

				System.out.println("Primeiro ao comprar " + item.getName() + " por: " + firstOffer);
			} else {
				/**
				 * Se já existir alguma oferta:
				 * Verificar se há alguma oferta obsoleta.
				 */
				boolean foundObsoleteOfferId = false;
				boolean foundObsoleteOfferRow = false;
				
				for (int f = 0; f < constants.NUMBER_OF_OFFERS_TO_CHECK; f++) {
					if (!foundObsoleteOfferId) {
						String currentRowId = util.normalizeId(this.image.getTextFromImage(
							this.constants.FIRST_SELLER_END_AT_X_TOP,
							this.constants.FIRST_SELLER_END_AT_Y_TOP,
							this.constants.FIRST_SELLER_END_AT_X_BOTTOM,
							this.constants.FIRST_SELLER_END_AT_Y_BOTTOM,
							f));
						
						if (!currentRowId.equals("")) {
							if (item.getId().equals(currentRowId)) {
								System.out.println("Cancelar a oferta obsoleta que está na linha " + (f + 1) + ".");
								
								foundObsoleteOfferId = true;
								
								mouse.clickOnMyOffers();
								
								delay(2000);
								
								int totalOfBuyOffers = Integer.parseInt(util.normalizeId(this.image.getTextFromImage(
										this.constants.NUMBER_OF_BUY_OFFERS_X_TOP,
										this.constants.NUMBER_OF_BUY_OFFERS_Y_TOP,
										this.constants.NUMBER_OF_BUY_OFFERS_X_BOTTOM,
										this.constants.NUMBER_OF_BUY_OFFERS_Y_BOTTOM)));
								
								int numberOfHiddenBuyOffers = totalOfBuyOffers - constants.NUMBER_OF_VISIBLE_BUY_OFFERS;
								
								mouse.clickOnFirstBuyOffer();
								
								String currentBuyOfferId;
								for (int g = 0; g < constants.NUMBER_OF_VISIBLE_BUY_OFFERS; g++) {
									if (!foundObsoleteOfferRow) {
										currentBuyOfferId = util.normalizeId(this.image.getTextFromImage(
											this.constants.FIRST_BUY_OFFER_END_AT_X_TOP,
											this.constants.FIRST_BUY_OFFER_END_AT_Y_TOP,
											this.constants.FIRST_BUY_OFFER_END_AT_X_BOTTOM,
											this.constants.FIRST_BUY_OFFER_END_AT_Y_BOTTOM,
											g));
										
										if (item.getId().equals(currentBuyOfferId)) {
											delay(1000);
											
											mouse.clickOnCancelOffer();
											
											delay(1000);
											
											foundObsoleteOfferRow = true;
											
											mouse.clickOnBackToMarket();
											
											delay(2000);
											
											System.out.println("Oferta do item " + item.getName() + " com o id: " + currentBuyOfferId + ". Cancelada com sucesso.");
											
											break;
										} else {
											keyboard.type("ˇ");
											
											delay(100);
										}
									}
								}
								
								if (!foundObsoleteOfferRow) {
									for (int h = 0; h < (numberOfHiddenBuyOffers + 1); h++) {
										currentBuyOfferId = util.normalizeId(this.image.getTextFromImage(
											this.constants.LAST_BUY_OFFER_END_AT_X_TOP,
											this.constants.LAST_BUY_OFFER_END_AT_Y_TOP,
											this.constants.LAST_BUY_OFFER_END_AT_X_BOTTOM,
											this.constants.LAST_BUY_OFFER_END_AT_Y_BOTTOM));
										
										if (item.getId().equals(currentBuyOfferId)) {
											delay(1000);
											
											mouse.clickOnCancelOffer();
											
											delay(1000);
											
											foundObsoleteOfferRow = true;
											
											mouse.clickOnBackToMarket();
											
											delay(2000);
											
											System.out.println("Oferta do item " + item.getName() + " com o id: " + currentBuyOfferId + ". Cancelada com sucesso.");
											
											break;
										} else {
											keyboard.type("ˇ");
											
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
				int price = Integer.parseInt(util.normalizePrice(this.image.getTextFromImage(
					this.constants.PIECE_PRICE_X_TOP,
					this.constants.PIECE_PRICE_Y_TOP,
					this.constants.PIECE_PRICE_X_BOTTOM,
					this.constants.PIECE_PRICE_Y_BOTTOM)));

				price = (price + 1);

				/**
				 * Se o maior preço ofertado for menor que o preço do item:
				 */
				if (price < item.getPrice()) {
					this.mouse.clickOnPiecePriceBox();

					delay(500);

					this.keyboard.selectAllTextAndDelete();

					delay(1000);

					keyboard.type(String.valueOf(price));

					delay(500);

					for (int i = 1; i < item.getBuy(); i++) {
						this.mouse.clickOnIncreaseItemQuantity();
						delay(150);
					}

					this.mouse.clickOnCreateOffer();

					delay(1500);

					String createdId = util.normalizeId(this.image.getTextFromImage(
							this.constants.FIRST_SELLER_END_AT_X_TOP,
							this.constants.FIRST_SELLER_END_AT_Y_TOP,
							this.constants.FIRST_SELLER_END_AT_X_BOTTOM,
							this.constants.FIRST_SELLER_END_AT_Y_BOTTOM));

					delay(1000);

					this.xml.updateItemId(createdId, item.getName());

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

	private void showMessage(boolean alwaysOnTop, String title, String message) {
		JOptionPane jOptionPane = new JOptionPane();
		jOptionPane.setMessage(message);
		JDialog dialog = jOptionPane.createDialog(null);
		dialog.setTitle(title);
		dialog.setAlwaysOnTop(alwaysOnTop);  
		dialog.setVisible(true);
	}
}
