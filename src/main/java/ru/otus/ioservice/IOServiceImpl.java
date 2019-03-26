package ru.otus.ioservice;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class IOServiceImpl implements IOService{

    @Override
    public String userInput(String message) {
        String input=null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(message);
            input = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    @Override
    public void showText(String text) {
        System.out.print(text);
    }
}
