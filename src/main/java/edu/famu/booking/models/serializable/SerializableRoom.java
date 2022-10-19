package edu.famu.booking.models.serializable;
import edu.famu.booking.models.RoomNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Null;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SerializableRoom {
    private @Nullable String id;
    private String title;
    private double price;
    private int maxPeople;
    private @Nullable ArrayList<RoomNumber> roomNumbers;
}

