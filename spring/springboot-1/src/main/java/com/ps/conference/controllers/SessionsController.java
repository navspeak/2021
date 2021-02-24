package com.ps.conference.controllers;

import com.ps.conference.models.Session;
import com.ps.conference.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/version")
    public String getAppVersion(){
        return appVersion;
    }

    @GetMapping
    public List<Session> list(){
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id){
        return sessionRepository.getOne(id);
    }

    @PostMapping
    public Session create(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }

    @DeleteMapping ("/{id}")
    public void delete(@PathVariable Long id){
        sessionRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Session update(@PathVariable Long id, @RequestBody Session session){
        Session existingSession = sessionRepository.getOne(id);
        //TODO: validation of payload
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }

}
