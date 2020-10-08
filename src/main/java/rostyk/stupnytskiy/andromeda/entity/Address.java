package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private User user;
}
