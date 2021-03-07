package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GoodsShopMarkupLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GoodsShopMarkup markup;

    @Column(name = "_order")
    private Integer order;

    @OneToMany(mappedBy = "line")
    private List<GoodsShopMarkupElement> elements = new ArrayList<>();

    public GoodsShopMarkupElement updateElementForLine(GoodsShopMarkupElement element) {
        if (element.getEndPosition() > 4) return null;
        element.setLine(this);
        return element;
    }

    public boolean canAddElement(GoodsShopMarkupElement element) {
        for (int i = element.getStartPosition(); i <= element.getEndPosition(); i++) {
            if (getElementInPosition(i) != null) return false;
        }
        return true;
    }

    public GoodsShopMarkupElement getElementInPosition(Integer position) {
        for (GoodsShopMarkupElement element : this.elements) {
            if (element.isAtPosition(position)) return element;
        }
        return null;
    }
}
