package entity;


import java.time.LocalDateTime;
import java.util.List;

public class Guest {

    private String guestName;
    private String creditCard;
    private String address;
    private String country;
    private String gender;
    private String identityType;
    private String IC;
    private String nationality;
    private String contact;
    private List<String> roomNumList;


    /**
 * Entity of Guest Object
 * @version 1.0
 */


    /**
     * Constructor of Guest()
     */
public Guest(String guestName, String creditCard, String address, String country, String gender, String identityType,String IC, String nationality, String contact, List<String> roomNumList) {
        this.guestName = guestName;
        this.creditCard = creditCard;
        this.address = address;
        this.country = country;
        this.gender = gender;
        this.identityType = identityType;
        this.IC = IC;
        this.nationality = nationality;
        this.contact = contact;
        this.roomNumList = roomNumList;
 }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

  
    public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIC() {
		return IC;
	}

	public void setIC(String iC) {
		IC = iC;
	}

	public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


	public List<String> getRoomNumList() {
		return roomNumList;
	}

	public void setRoomNumList(List<String> roomNumList) {
		this.roomNumList = roomNumList;
	}


    

}
