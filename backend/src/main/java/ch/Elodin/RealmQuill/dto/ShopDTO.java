package ch.Elodin.RealmQuill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {
    private int id;
    private String name;
    private int shopTypeId;
    private String shopTypeName;
    private int cityId;
    private String cityName;
    private int locationId;
    private String notes;
}




