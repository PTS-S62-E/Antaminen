package dto;

import java.time.LocalDate;

public class TranslocationRequesterDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isForeign = false;
    private long vehicleId = 0;

    public TranslocationRequesterDto(LocalDate startDate, LocalDate endDate, boolean isForeign, long vehicleId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.isForeign = isForeign;
        this.vehicleId = vehicleId;
    }

    public TranslocationRequesterDto() { }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isForeign() {
        return isForeign;
    }

    public void setForeign(boolean foreign) {
        isForeign = foreign;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
