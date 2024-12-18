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
    @Field(type = FieldType.Text, name = "link")
    private String line;

    @Field(type = FieldType.Text, name = "link")
    private Integer provinceCode;

    @Field(type = FieldType.Text, name = "link")
    private String provinceName;

    @Field(type = FieldType.Text, name = "link")
    private Integer districtCode;

    @Field(type = FieldType.Text, name = "link")
    private String districtName;

    @Field(type = FieldType.Text, name = "link")
    private Integer wardCode;

    @Field(type = FieldType.Text, name = "link")
    private String wardName;
}
