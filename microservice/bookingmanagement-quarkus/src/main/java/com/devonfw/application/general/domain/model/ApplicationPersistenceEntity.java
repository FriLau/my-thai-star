package com.devonfw.application.general.domain.model;

import javax.persistence.*;

/**
 * Abstract Entity for all Entities with an id and a version field.
 *
 */
@MappedSuperclass
public abstract class ApplicationPersistenceEntity {

    private static final long serialVersionUID = 1L;

    /** @see #getId() */
    private Long id;

    /** @see #getModificationCounter() */
    private int modificationCounter;

    /**
     * The constructor.
     */
    public ApplicationPersistenceEntity() {

        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {

        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    public void setId(Long id) {

        this.id = id;
    }

//    @Version TODO
    public int getModificationCounter() {

        return this.modificationCounter;
    }

    public void setModificationCounter(int version) {

        this.modificationCounter = version;
    }

    public String toString() {

        StringBuilder buffer = new StringBuilder();
        toString(buffer);
        return buffer.toString();
    }

    /**
     * Method to extend {@link #toString()} logic.
     *
     * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(Object) append} the string
     *        representation.
     */
    protected void toString(StringBuilder buffer) {

        buffer.append(getClass().getSimpleName());
        if (this.id != null) {
            buffer.append("[id=");
            buffer.append(this.id);
            buffer.append("]");
        }
    }
}
