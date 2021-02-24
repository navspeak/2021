package com.ps.conference.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "sessions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/*
Basically getOne is a lazy load operation.
Thus you get only a reference (a proxy) to the entity.
That means no DB access is actually made. Only when you call it's properties then it will query the DB.
findByID does the call 'eagerly'/immediately when you call it, thus you have the actual entity fully populated.

Take a look at this: Link to the difference between getOne & findByID
https://www.javacodemonk.com/difference-between-getone-and-findbyid-in-spring-data-jpa-3a96c3ff
 */
@Getter @Setter @NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long session_id;
    private String session_name;
    private String session_description;
    private Integer session_length;

    @ManyToMany
    @JoinTable(
            name="session_speakers",
            joinColumns = @JoinColumn(name="session_id"),
            inverseJoinColumns = @JoinColumn(name = "speaker_id")
    )
    private List<Speaker> speakers;
}
/*
CREATE TABLE sessions
(
    session_id          SERIAL PRIMARY KEY,
    session_name        varchar(80)   NOT NULL,
    session_description varchar(1024) NOT NULL,
    session_length      integer       NOT NULL
);

CREATE TABLE session_speakers
(
    session_id integer NOT NULL REFERENCES sessions (session_id),
    speaker_id integer NOT NULL REFERENCES speakers (speaker_id)
);

 */