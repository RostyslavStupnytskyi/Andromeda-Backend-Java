package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
/*
Entity Account is used to store information about the account of the user, administrator, moderator or seller
Account has a password, login, e-mail, photo username,
and the entity that contains the user (and administrator or moderator) or seller
*/
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String username;

    private String password;

    @Column(nullable = false)
    private UserRole userRole;

    private String email;

    private String avatar;

    private String confirmationCode;

    private Boolean isConfirmed;

    @OneToOne(cascade = CascadeType.ALL)
    private Seller seller;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}

