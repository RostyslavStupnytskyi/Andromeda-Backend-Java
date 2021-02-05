package rostyk.stupnytskiy.andromeda.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@NoArgsConstructor

@Getter
@Setter
public class PaginationRequest {

    @Positive
    @NotNull
    private Integer size;
    @NotNull
    @PositiveOrZero
    private Integer page;

    private String field;

    private Sort.Direction direction;

    public PaginationRequest(Integer size, Integer page) {
        this.size = size;
        this.page = page;
    }

    public PaginationRequest(Integer size, Integer page, String field, Sort.Direction direction) {
        this.size = size;
        this.page = page;
        this.field = field;
        this.direction = direction;
    }


    public Pageable mapToPageable() {
        if (field != null && direction != null) {
            return PageRequest.of(page, size, direction, field, "id");
        } else if (field != null) {
            return PageRequest.of(page, size, Sort.Direction.ASC, field, "id");
        } else {
            return PageRequest.of(page, size);

        }
    }
}

