package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.GetPortService;

@RequestMapping("/port")
@RestController
public class InfoController {


    @Autowired
    GetPortService getPortService;

    @GetMapping()
    public String getPort()
    {
        return getPortService.getPort();
    }

}
