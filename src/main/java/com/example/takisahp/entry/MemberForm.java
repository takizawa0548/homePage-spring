package com.example.takisahp.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm {

    private Integer id;
    //名前
    @NotBlank
    @Length(min=0,max=20)
    private String name;
    //性別
    @NotBlank
    @Length(min=0,max=5)
    private String gender;
    //誕生日
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date birthdate;
    //電話番号
    @NotBlank
    @Length(min=0,max=20)
    private String phonenumber;
    //Eメール
    @NotBlank
    @Length(min=0,max=50)
    @Email
    private String email;
    //国籍
    @NotBlank
    @Length(min=0,max=20)
    private String nationality;
}
