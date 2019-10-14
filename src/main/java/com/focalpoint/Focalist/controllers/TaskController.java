package com.focalpoint.Focalist.controllers;

import com.focalpoint.Focalist.models.ApplicationUser;
import com.focalpoint.Focalist.models.ApplicationUserRepository;
import com.focalpoint.Focalist.models.Task;
import com.focalpoint.Focalist.models.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;
import java.time.OffsetDateTime;


@Controller
public class TaskController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    TaskRepository taskRepository;

    // TODO: figure out how to store date and timezone
    @PostMapping("/api/task")
    public RedirectView addTask(String title, String note, String time, String offset, Principal p) {
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        time = time + "+0" + offset + ":00";
        System.out.println(time);
        OffsetDateTime taskTime = OffsetDateTime.parse(time);
        System.out.println(taskTime);
        Date UtcTime = Date.from(taskTime.toInstant());
        System.out.println(UtcTime);
        Task newTask = new Task(title, note, UtcTime, currentUser);
        taskRepository.save(newTask);
        return new RedirectView("/userAccount");
    }
}
