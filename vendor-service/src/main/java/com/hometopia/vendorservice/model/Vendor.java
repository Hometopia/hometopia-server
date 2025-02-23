package com.hometopia.vendorservice.model;

import com.hometopia.commons.enumeration.AssetCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "vendor")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "link")
    private String link;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Object, name = "address")
    private Address address;

    @Field(type = FieldType.Text, name = "website")
    private String website;

    @Field(type = FieldType.Text, name = "phone_number")
    private String phoneNumber;

    @Field(type = FieldType.Text, name = "asset_category")
    private AssetCategory assetCategory;

    @GeoPointField
    private GeoPoint location;
}
