package com.nftverse.nftversejavasdk.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class UserValidDTO {
    private Long userId;
    private String authToken;
    private List<UserRoleDTO> usersRoles;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public List<UserRoleDTO> getUsersRoles() {
        return usersRoles;
    }

    public void setUsersRoles(List<UserRoleDTO> usersRoles) {
        this.usersRoles = usersRoles;
    }
}
