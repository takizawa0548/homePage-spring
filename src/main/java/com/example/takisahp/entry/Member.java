package com.example.takisahp.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    private Integer id;
    //名前
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
