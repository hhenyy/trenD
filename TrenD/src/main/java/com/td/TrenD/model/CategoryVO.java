package com.td.TrenD.model;

import lombok.*;
import javax.persistence.*;

@Data
@Entity
@Table(name = "category_code")
public class CategoryVO {

    @Id
    private String cateCd;
    private String cateNm;
    private char cateDelYn;
    private String idx_category;

}
