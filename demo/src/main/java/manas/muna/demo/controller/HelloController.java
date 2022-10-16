package manas.muna.demo.controller;

import manas.muna.demo.util.StockUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class HelloController {

    @Value("${info.root-dir}")
    String root;

    @Autowired
    Environment environment;

    @Autowired
    ServletContext context;

    @RequestMapping("/")
    public String hello() {
        return "Hello ......Your app is up and running";
    }
}
