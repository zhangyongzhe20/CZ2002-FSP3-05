package controller;

import java.util.ArrayList;
import java.util.List;

import entity.Promotion;

public class PromotionMrg {
	public static List<Promotion> promotions = new ArrayList<Promotion>();

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

	public void updatePromotion(Promotion promotion) {
		for (Promotion p : promotions) {
			if (p.getPromotionCode().equalsIgnoreCase(promotion.getPromotionCode())) {
				p.setDiscount(promotion.getDiscount());
				p.setPromo_desc(promotion.getPromo_desc());
				p.setPromo_from(promotion.getPromo_from());
				p.setPromo_to(promotion.getPromo_to());
			}
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
}
