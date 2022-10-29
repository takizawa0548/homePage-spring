package com.example.takisahp.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberTopForm {

    //名前
    @NotBlank
    @Length(min=0,max=20)
    private String searchName;
}
