package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.udg.pds.springtodo.serializer.JsonDateDeserializer;
import org.udg.pds.springtodo.serializer.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "usergroup")
public class Group implements Serializable {
    /**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<User> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user")
    private User owner;

    @Column(name = "fk_user", insertable = false, updatable = false)
    private Long userId;

    public Group(){
    }

    public Group(String name, String description){
        this.name = name;
        this.description = description;
    }

    @JsonView(Views.Private.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @JsonView(Views.Complete.class)
    public Collection<User> getMembers() {
        return members;
    }

    @JsonView(Views.Private.class)
    public String getDescription() {
        return description;
    }

    @JsonView(Views.Private.class)
    public String getName() {
        return name;
    }

    @JsonView(Views.Complete.class)
    public long getUserId() {
        return userId;
    }

}
