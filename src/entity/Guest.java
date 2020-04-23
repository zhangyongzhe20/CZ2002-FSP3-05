package entity;

public class Guest {

    private String guestName;
    private String creditCard;
    private String address;
    private String country;
    private String gender;
    private IdentityType identityType;
    private String IC;
    private String nationality;
    private String contact;

    /**
     * Entity of Guest Object
     *
     * @version 1.0
     */

    /**
     * Constructor of Guest()
     */

    public Guest() {
    }

    public enum IdentityType {
        PASSPORT, DRIVING_LICENSE
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

    public IdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType) {
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

    public void printGuestInfo() {
		System.out.println(" -------------------------------------------");
		System.out.println("IC: " + this.getIC());
		System.out.println("1.Identity Type: " + this.getIdentityType());
		System.out.println("2.Name: " + this.getGuestName());
		System.out.println("3.Gender: " + this.getGender());
		System.out.println("4.Contact: " + this.getContact());
		System.out.println("5.Country: " + this.getCountry());
		System.out.println("6.Nationality: " + this.getNationality());
		System.out.println("7.Address: " + this.getAddress());
		System.out.println("8.Credit Card : " + this.getCreditCard());
	}
}
