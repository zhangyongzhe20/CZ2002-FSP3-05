package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Promotion {
	private String promotionCode;
	private String promoDescription;
	private double discount;
	LocalDateTime promoStartDate;
	LocalDateTime promoEndDate;
	

	public String getPromotionCode() {
		return promotionCode;
	}


	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}


	public String getPromoDescription() {
		return promoDescription;
	}


	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}


	public double getDiscount() {
		return discount;
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}


	public LocalDateTime getPromoStartDate() {
		return promoStartDate;
	}


	public void setPromoStartDate(LocalDateTime promoStartDate) {
		this.promoStartDate = promoStartDate;
	}


	public LocalDateTime getPromoEndDate() {
		return promoEndDate;
	}


	public void setPromoEndDate(LocalDateTime promoEndDate) {
		this.promoEndDate = promoEndDate;
	}


	public void printPromotionInfo() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
		
		System.out.println(" -------------------------------------------");
		System.out.println("1.Promotion Code: " + this.getPromotionCode());
		System.out.println("2.Promotion Description: " + this.getPromoDescription());
		System.out.println("3.Promotion Discount: " + this.getDiscount()+"%");
		System.out.println("4.Promotion Start Date: " + formatter.format(this.getPromoStartDate()));
		System.out.println("5.Promotion End Date: " + formatter.format(this.getPromoEndDate()));
	}
	
}
