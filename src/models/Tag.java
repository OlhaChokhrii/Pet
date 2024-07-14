package models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown =true)
@Data
@Builder
public class Tag {
    private long id;
    private String name;
}
