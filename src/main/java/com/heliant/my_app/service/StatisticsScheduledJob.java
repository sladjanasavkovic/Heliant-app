package com.heliant.my_app.service;

import com.heliant.my_app.model.Statistics;
import com.heliant.my_app.repository.StatisticsRepository;
import com.heliant.my_app.repository.SubmittedDocumentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class StatisticsScheduledJob {

    final SubmittedDocumentRepository submittedDocumentRepository;
    final StatisticsRepository statisticsRepository;

    public StatisticsScheduledJob(SubmittedDocumentRepository submittedDocumentRepository, StatisticsRepository statisticsRepository) {
        this.submittedDocumentRepository = submittedDocumentRepository;
        this.statisticsRepository = statisticsRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void createStatisticsForLastDay(){
        final LocalDateTime startDateTime = LocalDate.now().minusDays(1).atTime(0,0,0);
        final LocalDateTime endDateTime = LocalDate.now().atTime(0,0,0);
        Integer submittedDocumentCount = submittedDocumentRepository.countSubmittedDocumentForLastDay(startDateTime, endDateTime);

        Statistics statistics = new Statistics();
        statistics.setDate(startDateTime.toLocalDate());
        statistics.setSubmittedDocumentCount(submittedDocumentCount);
        statisticsRepository.save(statistics);
    }
}
