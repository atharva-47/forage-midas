package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRecordRepository;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConduit {
    private final UserRecordRepository userRecordRepository;

    public DatabaseConduit(UserRecordRepository userRecordRepository) {
        this.userRecordRepository = userRecordRepository;
    }

    public void save(UserRecord userRecord) {
        userRecordRepository.save(userRecord);
    }
}
