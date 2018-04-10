package domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Ownership")
@NamedQueries({
        @NamedQuery(name = "Ownership.findByOwnerId", query = "SELECT os FROM Ownership os WHERE os.owner = :id"),
        @NamedQuery(name = "Ownership.findByVehicleId", query = "SELECT os FROM Ownership os WHERE os.vehicleId = :id")
})
public class Ownership implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private Owner owner;

    @Column(name = "vehicle_id")
    private long vehicleId;

    @Column(name = "fromDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fromDate;

    @Transient
    private String readableFromDate;

    @Column(name = "toDate", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate toDate;

    @Transient
    private String readableToDate;

    public Ownership(Owner owner, long vehicleId, LocalDate fromDate, @Nullable LocalDate toDate) {
        this.owner = owner;
        this.vehicleId = vehicleId;
        this.fromDate = fromDate;
        this.toDate = toDate;

        this.readableFromDate = this.fromDate.toString();

        if(toDate != null) {
            this.readableToDate = this.toDate.toString();
        }
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

    public LocalDate getFromDate() {
        return fromDate;
    }

    public String getReadableFromDate() { return this.readableFromDate == null ? this.fromDate.toString() : this.readableFromDate; }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getReadableToDate() {
        if (this.toDate != null) {
            return this.readableToDate == null ? this.toDate.toString() : this.readableToDate;
        } else {
            return null;
        }
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
