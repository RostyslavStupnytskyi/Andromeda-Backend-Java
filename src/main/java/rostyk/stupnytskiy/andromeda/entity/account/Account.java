package rostyk.stupnytskiy.andromeda.entity.account;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.Address;
import rostyk.stupnytskiy.andromeda.entity.statistics.AccountStatistics;

import javax.persistence.*;
import java.util.List;

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

    private String password;

    @Column(nullable = false)
    private UserRole userRole;

    private String avatar;

    @OneToOne(cascade = CascadeType.ALL)
    private Seller seller;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "account")
    private List<Address> addresses;

    @OneToOne(cascade =  CascadeType.ALL)
    private AccountStatistics statistics;
}

