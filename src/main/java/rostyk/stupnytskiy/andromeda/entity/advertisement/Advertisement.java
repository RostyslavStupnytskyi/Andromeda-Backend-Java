package rostyk.stupnytskiy.andromeda.entity.advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "advertisement_type")
public class Advertisement implements AdvertisementEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    private String mainImage;

    @Override
    public <T extends AdvertisementResponse> AdvertisementResponse mapToResponse() {
        return new AdvertisementResponse(this);
    }

    @Transient
    public String getDiscriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", mainImage='" + mainImage + '\'' +
                '}';
    }
}
