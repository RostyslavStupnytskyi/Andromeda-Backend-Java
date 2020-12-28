package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class UserDeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserAccount user;

    private String recipient;

    private String phoneNumber;

    private String region;

    private String city;

    private String street;

}
