package ru.hogwarts.school.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class GetPortServiceProductionTest implements GetPortService {
    @Override
    public String getPort() {
        return "11111";
    }
}
