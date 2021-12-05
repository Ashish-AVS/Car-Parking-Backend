package com.example.springsocial.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity @Data
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Integer fastTag = 1000;

    @OneToMany(targetEntity = Waitlist.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "waitlist_user", referencedColumnName = "id")
    private List<Waitlist> waitlists = new ArrayList<>();

    @OneToMany(targetEntity = Car.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "customer_car", referencedColumnName = "id")
    private List<Car> cars = new ArrayList<>();

//    @OneToMany(targetEntity = ParkingSlot.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//    @JoinColumn(name = "user_slot", referencedColumnName = "id")
//    private List<ParkingSlot> parkingSlot = new ArrayList<>();

    @OneToMany(targetEntity = Booking.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "customer_booking", referencedColumnName = "id")
    private List<Booking> bookings= new ArrayList<>();
// OTP START
private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes

    @Column(name = "one_time_password")
    private String oneTimePassword;

    @Column(name = "otp_requested_time")
    private Date otpRequestedTime;


    public boolean isOTPRequired() {
        if (this.getOneTimePassword() == null) {
            return false;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();

        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            // OTP expires
            return false;
        }

        return true;
    }
    // OTP END
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
