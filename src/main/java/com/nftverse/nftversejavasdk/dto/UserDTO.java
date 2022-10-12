package com.nftverse.nftversejavasdk.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String avatar;
    private String gender;
    private String bio;
    private String userName;
    private String mobile;
    private String email;
    private String authToken;
}
