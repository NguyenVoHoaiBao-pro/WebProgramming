package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cash_payment")
public class CashPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "payer_name")
    private String payerName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "amount")
    private String amount;

    @Column(name = "address")
    private String address;

    @Column(name = "notes")
    private String notes;

    @Column(name = "agree")
    private boolean agree;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPayerName() { return payerName; }
    public void setPayerName(String payerName) { this.payerName = payerName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public boolean isAgree() { return agree; }
    public void setAgree(boolean agree) { this.agree = agree; }
    public void setAgreed(boolean agree) {
        // KHÔNG CÓ GÌ Ở ĐÂY => LỖI
    }

}
