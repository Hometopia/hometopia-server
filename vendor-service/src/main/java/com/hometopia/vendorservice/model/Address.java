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

    @Field(type = FieldType.Text, name = "province_code")
    private Integer provinceCode;

    @Field(type = FieldType.Text, name = "province_name")
    private String provinceName;

    @Field(type = FieldType.Text, name = "district_code")
    private Integer districtCode;

    @Field(type = FieldType.Text, name = "district_name")
    private String districtName;

    @Field(type = FieldType.Text, name = "ward_code")
    private Integer wardCode;

    @Field(type = FieldType.Text, name = "ward_name")
    private String wardName;
}
