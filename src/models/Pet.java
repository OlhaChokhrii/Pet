package models;

import Enums.PetStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)

public class Pet {
    private long id;
    private String name;
    private List<String> photoUrls;
    private PetStatus status;
    private Category category;
    private List<Tag> tags;

}

