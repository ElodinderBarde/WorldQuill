package ch.Elodin.RealmQuill.model.ruf;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReputationConflictKey implements Serializable {
    private int sourceClanId;
    private int targetClanId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReputationConflictKey)) return false;
        ReputationConflictKey that = (ReputationConflictKey) o;
        return sourceClanId == that.sourceClanId && targetClanId == that.targetClanId;
    }
    @Override
    public int hashCode() { return Objects.hash(sourceClanId, targetClanId); }
}
