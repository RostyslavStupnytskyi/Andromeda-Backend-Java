package rostyk.stupnytskiy.andromeda.entity.statistics;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class AccountStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String confirmationCode;

    private Boolean isConfirmed;

    @ManyToOne
    private Country country;

    @OneToOne(mappedBy = "statistics")
    private Account account;
}
