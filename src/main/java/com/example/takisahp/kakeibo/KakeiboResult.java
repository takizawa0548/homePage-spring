package com.example.takisahp.kakeibo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="kakeibo_zisseki")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakeiboResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer renban;
    //家計簿ID
    private String kakeibo_id;
    //項目名
    private String kakeibo_koumoku;
    //実績金額
    private Integer kakeibo_zisseki_kingaku;
    //実績日
    private Date kakeibo_date;
}
