package com.example.takisahp.kakeibo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakeiboResultForm {

    private Integer renban;
    //家計簿ID
    private String kakeibo_id;
    //項目名
    @NotBlank
    private String kakeibo_koumoku;
    //実績金額
    @NotNull
    private Integer kakeibo_zisseki_kingaku;
    //実績日
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date kakeibo_date;
}
