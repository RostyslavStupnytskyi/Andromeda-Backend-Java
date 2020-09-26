package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user")
    private Account account;

    @OneToOne(mappedBy = "user")
    private Cart cart;
}
