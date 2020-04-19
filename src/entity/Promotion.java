package entity;

import java.time.LocalDateTime;

import entity.Room.BedType;
import entity.Room.RoomType;

public class Promotion {
	private String promotionCode;
	private String promo_desc;
	private double discount;
	LocalDateTime promo_from;
	LocalDateTime promo_to;
	
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
	public String getPromo_desc() {
		return promo_desc;
	}
	public void setPromo_desc(String promo_desc) {
		this.promo_desc = promo_desc;
	}
	
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public LocalDateTime getPromo_from() {
		return promo_from;
	}
	public void setPromo_from(LocalDateTime promo_from) {
		this.promo_from = promo_from;
	}
	public LocalDateTime getPromo_to() {
		return promo_to;
	}
	public void setPromo_to(LocalDateTime promo_to) {
		this.promo_to = promo_to;
	}

	
}
