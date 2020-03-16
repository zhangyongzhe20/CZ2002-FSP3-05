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

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
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

    public LocalDateTime getLocalDateTime() {
        return checkIn;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.checkIn = localDateTime;
    }

    public Room getGuestRoom() {
        return guestRoom;
    }

    public void setGuestRoom(Room guestRoom) {
        this.guestRoom = guestRoom;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

}
