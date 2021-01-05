package rostyk.stupnytskiy.andromeda.entity.account;

import lombok.*;
import rostyk.stupnytskiy.andromeda.dto.response.account.AccountResponse;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

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

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "account_type")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    private String password;

    private String avatar;

    @Column(nullable = false)
    private UserRole userRole;

    @ManyToOne
    private Country country;

    public AccountResponse mapToResponse(){
        return new AccountResponse(this);
    }
}

