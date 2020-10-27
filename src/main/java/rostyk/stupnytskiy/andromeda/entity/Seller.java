package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taxpayerNumber;

    private String shopName;

    @OneToOne(mappedBy = "seller")
    private Account account;

    @OneToMany(mappedBy = "seller")
    private List<Advertisement> advertisements;
}
