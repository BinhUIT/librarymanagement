package com.library.librarymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@Controller
public class UIController {   
    @GetMapping("/{page}") 
    public String uiController(@PathVariable String page) 
    { 
        return page;
    }

    


}
