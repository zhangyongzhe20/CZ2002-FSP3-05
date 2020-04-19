package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entity.Promotion;
import entity.Reservation;

public class PromotionMrg {
	public static List<Promotion> promotions = new ArrayList<Promotion>();
	final static String fileName = "promotion_data.txt";
	public static PromotionMrg getInstance() {
		PromotionMrg promotionMrg = new PromotionMrg();
		return promotionMrg;
	}

	public static Promotion createNewPromotion() {
		return new Promotion();
	}

	public void createPromotion(Promotion promotion) {
		promotions.add(promotion);
	}

	public void deletePromotion(Promotion promotion) {
		promotions.remove(promotion);
	}

	public boolean updatePromotion(Promotion promotion) {
		boolean update = false;
		
		for (Promotion p : promotions) {
			if (p.getPromotionCode().equalsIgnoreCase(promotion.getPromotionCode())) {
				p.setDiscount(promotion.getDiscount());
				p.setPromoDescription(promotion.getPromoDescription());
				p.setPromoStartDate(promotion.getPromoStartDate());
				p.setPromoEndDate(promotion.getPromoEndDate());
				update = true;
			}
		}
		return update;
	}

	public Promotion getPromotionByPromotionCode(String promotionCode) {
		for (Promotion p : promotions) {
			if (p.getPromotionCode().equalsIgnoreCase(promotionCode)) {
				return p;
			}
		}
		return null;
	}

	public List<Promotion> getAllPromotion() {
		return promotions;
	}
	
	public void loadPromotionData() throws FileNotFoundException {
		File file = new File(fileName);
		try {
			file.createNewFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Scanner sc = new Scanner(file);
		String data;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		while (sc.hasNextLine()) {
			data = sc.nextLine();
			String[] temp = data.split(",");
			Promotion p = new Promotion();
			p.setPromotionCode(temp[0]);
			p.setPromoDescription(temp[1]);
			p.setDiscount(Double.parseDouble(temp[2]));
			p.setPromoStartDate(LocalDateTime.parse(temp[3], formatter));
			p.setPromoEndDate(LocalDateTime.parse(temp[4], formatter));
			promotions.add(p);
		}
		sc.close();
	}

	public void writePromotionData() throws IOException {
		FileWriter fileWriter = new FileWriter(fileName);
		PrintWriter fileOut = new PrintWriter(fileWriter);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		if (promotions.size() > 0) {
			for (Promotion p : promotions) {
				fileOut.print(p.getPromotionCode() + ",");
				fileOut.print(p.getPromoDescription() + ",");
				fileOut.print(p.getDiscount() + ",");
				fileOut.print(p.getPromoStartDate().format(formatter) + ",");
				fileOut.print(p.getPromoEndDate().format(formatter) + ",");
				fileOut.println();
			}
			System.out.println("finish writing");
			fileOut.close();
		}
	}
}
