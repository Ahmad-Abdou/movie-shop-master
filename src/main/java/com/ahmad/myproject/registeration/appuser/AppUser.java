package com.ahmad.myproject.registeration.appuser;


import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;



@Data
@NoArgsConstructor
@Entity
public class AppUser  {


    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String userName;
    @Email(message = "please enter a valid email")
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked = false;
    private Boolean enabled = false;
    public AppUser(String userName,
                   String email,
                   String password,
                   AppUserRole appUserRole) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
    }


    public String getUserName() {
        return userName;
    }

    public String getUsername() {
        return email;
    }


}
