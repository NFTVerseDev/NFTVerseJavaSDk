package com.nftverse.nftversejavasdk.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleDTO {
    private Long id;
    private Integer roleId;
    private UserDTO user;
    private RoleMasterDTO role;
}
