package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @Column(unique = true)
    private String title;

    @OneToMany(mappedBy = "category")
    private List<Subcategory> subcategories;
}
