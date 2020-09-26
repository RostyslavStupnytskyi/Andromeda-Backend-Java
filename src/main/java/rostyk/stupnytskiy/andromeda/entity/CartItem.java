package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Advertisement advertisement;

    private Integer count;

    @ManyToOne
    private Cart cart;
}
