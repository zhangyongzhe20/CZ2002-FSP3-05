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
	private static List<Promotion> promotions;
	private Promotion promotion;
	private final static String FILENAME = "promotion_data.txt";

    /**
     * Applied Singelton Desgin Pattern in Mrg classes
     */
    private static PromotionMrg SINGLE_INSTANCE;
    public static PromotionMrg getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new PromotionMrg();
        }
        return SINGLE_INSTANCE;
    }

    public PromotionMrg() {
        promotions = new ArrayList<Promotion>();
    }

	

	public boolean checkValidPromotionExist(String promotionCode) {
		for (Promotion promotion : promotions) {
			if (promotion.getPromotionCode().equalsIgnoreCase(promotionCode)) {
				if(promotion.getPromoStartDate().isBefore(LocalDateTime.now())&& promotion.getPromoEndDate().isAfter(LocalDateTime.now())) {
				this.promotion = promotion;
					return true;
				}
			}
		}
		return false;
	}
	public static boolean checkPromotionExist(String promotionCode) {
		for (Promotion promotion : promotions) {
			if (promotion.getPromotionCode().equalsIgnoreCase(promotionCode)) {
				return true;
	
			}
		}
		return false;
	}
	public void setPromotionCode(String promotionCode) {
		if(checkPromotionExist(promotionCode)) {
			promotion = getPromotionByPromotionCode(promotionCode);
		}else {
		promotion.setPromotionCode(promotionCode);
		}
	}
	
	public void setPromoDescription(String promoDescription) {
		promotion.setPromoDescription(promoDescription);
	}
	public void setDiscount(double discount) {
		promotion.setDiscount(discount);
	}
	
	public void setPromoStartDate(LocalDateTime promoStartDate) {
		promotion.setPromoStartDate(promoStartDate);
	}
	
	public void setPromoEndDate(LocalDateTime promoEndDate) {
		promotion.setPromoEndDate(promoEndDate);
	}
	
	public double getDiscount() {
		return promotion.getDiscount();
	}
	
	public LocalDateTime getPromoStartDate() {
		return promotion.getPromoStartDate();
	}
	public void createNewPromotion() {
		promotion = new Promotion();
	}
		
	
	public void createPromotion() {
		promotions.add(promotion);
		try {
			writePromotionData();
			System.out.println("Promotion is created successfully!");
			System.out.println("-------------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deletePromotion() {
		promotions.remove(promotion);
		try {
			writePromotionData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void updatePromotion() {
		try {
			writePromotionData();
			System.out.println("Promotion is updated successfully!");
			System.out.println("-------------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void printPromotionInfo() {
	   promotion.printPromotionInfo();
	}
	public void printAllPromotionInfo() {
		for (Promotion p : promotions) {
			p.printPromotionInfo();
		}
	}
	public void loadPromotionData() throws FileNotFoundException {
		File file = new File(FILENAME);
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
			if(!data.isEmpty()){
			String[] temp = data.split(",");
			Promotion p = new Promotion();
			p.setPromotionCode(temp[0]);
			p.setPromoDescription(temp[1]);
			p.setDiscount(Double.parseDouble(temp[2]));
			p.setPromoStartDate(LocalDateTime.parse(temp[3], formatter));
			p.setPromoEndDate(LocalDateTime.parse(temp[4], formatter));
			promotions.add(p);
		}
	}
		sc.close();
	}

	public void writePromotionData() throws IOException {
		FileWriter fileWriter = new FileWriter(FILENAME);
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
			fileOut.close();
		}
	}
}
