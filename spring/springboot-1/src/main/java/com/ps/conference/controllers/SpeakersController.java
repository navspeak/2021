package com.ps.conference.controllers;

import com.ps.conference.models.Speaker;
import com.ps.conference.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list(){
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Optional<Speaker> get(@PathVariable Long id){
        //return speakerRepository.getOne(id);
        return speakerRepository.findById(id);
    }

    @PostMapping
    public Speaker create(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        speakerRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker){
        Speaker existingSpeaker = speakerRepository.getOne(id);
        //TODO: validation of payload
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }

}
