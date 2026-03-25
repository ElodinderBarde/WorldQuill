package ch.Elodin.RealmQuill.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ShopDTO {
    private int id;
    private String name;
    private int shopTypeId;
    private String shopTypeName;
    private int locationId;
    private String cityName;
    private String notes;
    // Neu nach DB-Migration
    private Integer campaignId;

    public ShopDTO(int shopId, String name, int shopTypeId, String shopTypeName, int locationId, String cityName, int i, String notes) {
        this.id = shopId;
        this.name = name;
        this.shopTypeId = shopTypeId;
        this.shopTypeName = shopTypeName;
        this.locationId = locationId;
        this.cityName = cityName;
        this.notes = notes;
        this.campaignId = i;
    }
}