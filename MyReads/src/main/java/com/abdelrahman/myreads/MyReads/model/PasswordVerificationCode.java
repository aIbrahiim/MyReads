package com.abdelrahman.myreads.MyReads.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordVerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="code_id")
    @Setter(AccessLevel.NONE)
    private long codeId;

    private String code;



    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;


    public PasswordVerificationCode(User user) {
        this.user = user;
        int codeValue = new Random().nextInt(999999);
        code =  String.format("%06d", codeValue);
    }

}
