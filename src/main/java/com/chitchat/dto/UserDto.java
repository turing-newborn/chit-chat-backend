package com.chitchat.dto;

import com.chitchat.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String email;
    private String fullName;
    private String gender;
    private Status status;
}
