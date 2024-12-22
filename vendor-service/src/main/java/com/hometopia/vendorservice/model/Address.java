package com.hometopia.vendorservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Field(type = FieldType.Text, name = "line")
    private String line;

    @Field(type = FieldType.Text, name = "provinceCode")
    private Integer provinceCode;

    @Field(type = FieldType.Text, name = "provinceName")
    private String provinceName;

    @Field(type = FieldType.Text, name = "districtCode")
    private Integer districtCode;

    @Field(type = FieldType.Text, name = "districtName")
    private String districtName;

    @Field(type = FieldType.Text, name = "wardCode")
    private Integer wardCode;

    @Field(type = FieldType.Text, name = "wardCode")
    private String wardName;
}
