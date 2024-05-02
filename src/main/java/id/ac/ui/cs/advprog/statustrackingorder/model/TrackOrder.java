package id.ac.ui.cs.advprog.statustrackingorder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tracking")
public class TrackOrder {
    @Id
    private Long trackingId;
    private Long orderId;
    private String methode;
    private String resiCode;

}
