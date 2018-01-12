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
	private List<Item> items;
	
	private ImageHelper image;
	private MouseHelper mouse;
	private KeyboardHelper keyboard;
	private XMLHelper xml;
	private ConstantsHelper constants;
	private UtilHelper util;

	public Facade() throws AWTException {
		image = new ImageHelper();
		mouse = new MouseHelper();
		keyboard = new KeyboardHelper();
		xml = new XMLHelper();
		constants = new ConstantsHelper();
		util = new UtilHelper();
	}

	public void run() throws InterruptedException, AWTException, IOException, TesseractException {
		showMessage("Bem-vindo \n\n 1) Abra a janela do Tibia. \n 2) Aperte Alt + F8 para verificação de latência. \n 3) Abra a janela do Market. \n 4) Aperte OK. \n\n");
		
		int currentPing = Integer.parseInt(util.normalizePing(image.getTextFromImage(constants.PING_X_TOP, constants.PING_Y_TOP, constants.PING_X_BOTTOM, constants.PING_Y_BOTTOM)));

		items = xml.getItemsList();

		boolean isTheFirstNegotiation = true;
		for (int i = 0; i < items.size(); i++) {
			String isMarketOpened = util.normalizeId(image.getTextFromImage(constants.MARKET_TITLE_X_TOP, constants.MARKET_TITLE_Y_TOP, constants.MARKET_TITLE_X_BOTTOM, constants.MARKET_TITLE_Y_BOTTOM));
			
			if (!isMarketOpened.equals("Market")) {
				isTheFirstNegotiation = true;
				showMessage("A janela do Market está fechada.");
				i--;
				continue;
			}
			
			if (currentPing > constants.MAX_ACCEPTED_LATENCY) {
				isTheFirstNegotiation = true;
				showMessage("Seu ping está muito alto para poder executar o MarketMaker.");
				i--;
				continue;
			}

			if (isTheFirstNegotiation) {
				mouse.clickOnAnonymous();
				mouse.clickOnBuyButton();
				
				isTheFirstNegotiation = false;
			}

			buyItem(items.get(i));
		}

		mouse.clickOnCloseMarket();
		System.exit(0);
	}
	
	private void removeOverpricedItems(Item item) throws InterruptedException, AWTException, IOException, TesseractException {
		mouse.clickOnSearchBox();
		delay(500);
		keyboard.selectAllTextAndDelete();
		delay(1000);
		keyboard.type(item.getName());
		delay(500);
		mouse.clickOnFirstFound();
		delay(1500);

		String price = util.normalizePrice(image.getTextFromImage(
			constants.PIECE_PRICE_X_TOP,
			constants.PIECE_PRICE_Y_TOP,
			constants.PIECE_PRICE_X_BOTTOM,
			constants.PIECE_PRICE_Y_BOTTOM));
		
		if (!price.equals("")) {
			if (Integer.parseInt(price) > item.getPrice()) {
				System.out.println(item.getName() + " deletado.");
				xml.deleteItem(item.getName());
			}
		}
	}
	
	private void removeLowTransactionsItems(Item item) throws InterruptedException, AWTException, IOException, TesseractException {
		mouse.clickOnSearchBox();
		delay(500);
		keyboard.selectAllTextAndDelete();
		delay(1000);
		keyboard.type(item.getName());
		delay(500);
		mouse.clickOnFirstFound();
		delay(1500);
		
		mouse.clickOnDetailsButton();
		
		int numberOfTransactions = Integer.parseInt(
				util.normalizeNumber(image.getTextFromImage(
				constants.NUMBER_TRANSACTIONS_X_TOP,
				constants.NUMBER_TRANSACTIONS_Y_TOP,
				constants.NUMBER_TRANSACTIONS_X_BOTTOM,
				constants.NUMBER_TRANSACTIONS_Y_BOTTOM)));
		
		if (numberOfTransactions < constants.NUMBER_OF_TRANSACTIONS_REQUIRED) {
			xml.deleteItem(item.getName());
		}
	}

	private void buyItem(Item item) throws InterruptedException, AWTException, IOException, TesseractException {
		mouse.clickOnSearchBox();
		delay(500);
		keyboard.selectAllTextAndDelete();
		delay(1000);
		keyboard.type(item.getName());
		delay(500);
		mouse.clickOnFirstFound();
		delay(2500);

		String id = util.normalizeId(image.getTextFromImage(
				constants.FIRST_SELLER_END_AT_X_TOP,
				constants.FIRST_SELLER_END_AT_Y_TOP,
				constants.FIRST_SELLER_END_AT_X_BOTTOM,
				constants.FIRST_SELLER_END_AT_Y_BOTTOM));

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

				mouse.clickOnPiecePriceBox();

				delay(500);

				keyboard.selectAllTextAndDelete();

				delay(1000);

				keyboard.type(String.valueOf(firstOffer));

				delay(500);

				for (int i = 1; i < item.getBuy(); i++) {
					mouse.clickOnIncreaseItemQuantity();
					delay(150);
				}

				mouse.clickOnCreateOffer();

				delay(1500);

				String createdId = util.normalizeId(image.getTextFromImage(
						constants.FIRST_SELLER_END_AT_X_TOP,
						constants.FIRST_SELLER_END_AT_Y_TOP,
						constants.FIRST_SELLER_END_AT_X_BOTTOM,
						constants.FIRST_SELLER_END_AT_Y_BOTTOM));

				delay(1000);

				xml.updateItemId(createdId, item.getName());

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
						String currentRowId = util.normalizeId(image.getTextFromImage(
							constants.FIRST_SELLER_END_AT_X_TOP,
							constants.FIRST_SELLER_END_AT_Y_TOP,
							constants.FIRST_SELLER_END_AT_X_BOTTOM,
							constants.FIRST_SELLER_END_AT_Y_BOTTOM,
							f));
						
						if (!currentRowId.equals("")) {
							if (item.getId().equals(currentRowId)) {
								System.out.println("Cancelar a oferta obsoleta que está na linha " + (f + 1) + ".");
								
								foundObsoleteOfferId = true;
								
								mouse.clickOnMyOffers();
								
								delay(2000);
								
								int totalOfBuyOffers = Integer.parseInt(util.normalizeId(image.getTextFromImage(
										constants.NUMBER_OF_BUY_OFFERS_X_TOP,
										constants.NUMBER_OF_BUY_OFFERS_Y_TOP,
										constants.NUMBER_OF_BUY_OFFERS_X_BOTTOM,
										constants.NUMBER_OF_BUY_OFFERS_Y_BOTTOM)));
								
								int numberOfHiddenBuyOffers = totalOfBuyOffers - constants.NUMBER_OF_VISIBLE_BUY_OFFERS;
								
								mouse.clickOnFirstBuyOffer();
								
								String currentBuyOfferId;
								for (int g = 0; g < constants.NUMBER_OF_VISIBLE_BUY_OFFERS; g++) {
									if (!foundObsoleteOfferRow) {
										currentBuyOfferId = util.normalizeId(image.getTextFromImage(
											constants.FIRST_BUY_OFFER_END_AT_X_TOP,
											constants.FIRST_BUY_OFFER_END_AT_Y_TOP,
											constants.FIRST_BUY_OFFER_END_AT_X_BOTTOM,
											constants.FIRST_BUY_OFFER_END_AT_Y_BOTTOM,
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
										currentBuyOfferId = util.normalizeId(image.getTextFromImage(
											constants.LAST_BUY_OFFER_END_AT_X_TOP,
											constants.LAST_BUY_OFFER_END_AT_Y_TOP,
											constants.LAST_BUY_OFFER_END_AT_X_BOTTOM,
											constants.LAST_BUY_OFFER_END_AT_Y_BOTTOM));
										
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
				int price = Integer.parseInt(util.normalizePrice(image.getTextFromImage(
					constants.PIECE_PRICE_X_TOP,
					constants.PIECE_PRICE_Y_TOP,
					constants.PIECE_PRICE_X_BOTTOM,
					constants.PIECE_PRICE_Y_BOTTOM)));

				price = (price + 1);

				/**
				 * Se o maior preço ofertado for menor que o preço do item:
				 */
				if (price < item.getPrice()) {
					mouse.clickOnPiecePriceBox();

					delay(500);

					keyboard.selectAllTextAndDelete();

					delay(1000);

					keyboard.type(String.valueOf(price));

					delay(500);

					for (int i = 1; i < item.getBuy(); i++) {
						mouse.clickOnIncreaseItemQuantity();
						delay(150);
					}

					mouse.clickOnCreateOffer();

					delay(1500);

					String createdId = util.normalizeId(image.getTextFromImage(
							constants.FIRST_SELLER_END_AT_X_TOP,
							constants.FIRST_SELLER_END_AT_Y_TOP,
							constants.FIRST_SELLER_END_AT_X_BOTTOM,
							constants.FIRST_SELLER_END_AT_Y_BOTTOM));

					delay(1000);

					xml.updateItemId(createdId, item.getName());

					System.out.println("Comprando " + item.getName() + " por: " + price + " gps.");
				} else {
					System.out.println("Não comprou " + item.getName() + ". Caro demais.");
				}
			}
		}
		
		delay(2000);
	}

	private void delay(int milliseconds) throws InterruptedException {
		Thread.sleep(milliseconds);
	}

	private void showMessage(String message) {
		JOptionPane jOptionPane = new JOptionPane();
		jOptionPane.setMessage(message);
		
		JDialog dialog = jOptionPane.createDialog(null);
		dialog.setTitle(constants.PROGRAM_TITLE);
		dialog.setAlwaysOnTop(true);  
		dialog.setVisible(true);
	}
}
