package com.td.TrenD.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "trend_tbl")
public class TrendVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trNo;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserVO userVO;

    @ManyToOne
    @JoinColumn(name = "cateCd", insertable = false, updatable = false)
    private CategoryVO categoryVO;

    private String cateCd;
    private String trSubject;
    private String trContent;
    private int trReadCount;
    private Date trDate;
    private Date trUpdate;
    private char trDelYn;
}