package com.td.TrenD.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import lombok.*;
import java.util.Date;

@Data
@Entity
@Table(name = "trend_tbl")
public class TrendVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trNo;
    private String cateCd;
    private String trSubject;
    private String trContent;
    private int trReadCount;
    private Date trDate;
    private Date trUpdate;
    private char trDelYn;
  
    @ManyToOne
    @JoinColumn(name = "cateCd", insertable = false, updatable = false)
    private CategoryVO categoryVO;
  
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserVO userVO;

}