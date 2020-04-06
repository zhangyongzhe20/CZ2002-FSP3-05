import java.time.LocalDateTime;

public class Guest {

    private String guestName;
    private String creditCard;
    private String address;
    private String country;
    private String gender;
    private String passport;
    private String nationality;
    private String contact;
    private LocalDateTime checkIn;
    private Room guestRoom;

    public Guest(String guestName, String creditCard, String address, String country, String gender, String passport, String nationality, String contact, LocalDateTime localDateTime, Room guestRoom) {
        this.guestName = guestName;
        this.creditCard = creditCard;
        this.address = address;
        this.country = country;
        this.gender = gender;
        this.passport = passport;
        this.nationality = nationality;
        this.contact = contact;
        this.checkIn = localDateTime;
        this.guestRoom = guestRoom;
    }


    public String getGuestName() {
        return guestName;
    }

    /*
     * @param the guestName to set
     */
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getCreditCard() {
        return creditCard;
    }


    /*
     * @param the creditCard to set
     */
    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getAddress() {
        return address;
    }

    /*
     * @param the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    /*
     * @param the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    /*
     * @param the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassport() {
        return passport;
    }

    /*
     * @param the passport to set
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getNationality() {
        return nationality;
    }

    /*
     * @param the nationality to set
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getContact() {
        return contact;
    }

    /*
     * @param the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    /*
     * @param the checkIn to set
     */
    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public Room getGuestRoom() {
        return guestRoom;
    }

    /*
     * @param the guestRoom to set
     */
    public void setGuestRoom(Room guestRoom) {
        this.guestRoom = guestRoom;
    }
}
