package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Ownership")
@NamedQueries({
        @NamedQuery(name = "Ownership.findByOwnerId", query = "SELECT os FROM Ownership os WHERE os.owner_id = :id")
})
public class Ownership implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "vehicle_id")
    private long vehicleId;

    @Column(name = "fromDate")
    private LocalDateTime fromDate;

    @Column(name = "toDate")
    private LocalDateTime toDate;

    public Ownership(Owner owner, long vehicleId, LocalDateTime fromDate, LocalDateTime toDate) {
        this.owner = owner;
        this.vehicleId = vehicleId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Ownership() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }
}
