package pl.bochunator.doncards.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class English {
    @Id
    private long id;
    private String content;
}
