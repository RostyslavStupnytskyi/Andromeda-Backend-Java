package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
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

    @OneToOne(mappedBy = "account")
    private Seller seller;

    @OneToOne(mappedBy = "account")
    private User user;
}

