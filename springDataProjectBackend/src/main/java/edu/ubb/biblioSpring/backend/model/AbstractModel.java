package edu.ubb.biblioSpring.backend.model;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractModel {
    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid;

    private void ensureUuidNotNull(){
        if(uuid==null){
            uuid=UUID.randomUUID().toString();
        }
    }

    @PrePersist
    public void prePersist(){
        ensureUuidNotNull();
    }

    public String getUuid() {
        ensureUuidNotNull();
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        AbstractModel that = (AbstractModel) obj;
        return Objects.equals(getUuid(),that.getUuid());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getUuid());
    }
}
