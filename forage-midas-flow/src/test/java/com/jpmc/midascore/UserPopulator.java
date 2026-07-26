package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class UserPopulator {

    @Autowired
    private UserRepository userRepository;

    public void populate() {
        // User data file ka exact path:
        populate("/test_data/lkjhgfdsa.hjkl");
    }

    public void populate(String path) {
        String resourcePath = path.startsWith("/") ? path : "/" + path;
        InputStream is = getClass().getResourceAsStream(resourcePath);

        if (is == null) {
            System.err.println(">>> ERROR: File not found at: " + resourcePath);
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                String cleanLine = line.trim();
                if (cleanLine.isEmpty()) continue;

                String[] parts = cleanLine.split(",");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    float balance = Float.parseFloat(parts[1].trim());

                    UserRecord user = new UserRecord(name, balance);
                    userRepository.save(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}