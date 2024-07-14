package models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Category {
    private long id;
    private String name;
    // Геттери та сеттери
   // public long getId() {
      //  return id;
    //}

    //public void setId(long id) {
       // this.id = id;
    //}

   // public String getName() {
       // return name;
   // }

    //public void setName(String name) {
       // this.name = name;
    }


