package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "subcategory")
    private List<Advertisement> advertisements;
}
