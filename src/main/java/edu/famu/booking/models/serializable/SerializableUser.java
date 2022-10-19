package edu.famu.booking.models.serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SerializableUser {
    private @Nullable String id;
    private String username;
    private @Nullable String email;
    private String country;
    private String img;
    private String city;
    private String phone;
    private boolean isAdmin;

    public boolean getIsAdmin() {
        return isAdmin;
    }
}
