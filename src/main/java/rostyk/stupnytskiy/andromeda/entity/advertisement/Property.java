package rostyk.stupnytskiy.andromeda.entity.advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String value;

    @ManyToOne
    private Advertisement advertisement;
}
