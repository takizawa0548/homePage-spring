package com.example.takisahp.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm {

    private Integer id;
    //名前
    @NotBlank
    private String name;
    //性別
    private String sex;
    //誕生日
    private String birthdate;
    //電話番号
    private String phonenumber;
    //Eメール
    private String email;
    //国籍
    private String nationality;
}
