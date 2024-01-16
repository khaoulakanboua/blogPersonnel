package com.example.demo;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

 class BlogProjectApplicationTest {

    @Test
    void applicationStarts() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BlogProjectApplication.main(new String[]{});

        Assert.assertTrue(outContent.toString().contains("Started BlogProjectApplication"));
    }
}
