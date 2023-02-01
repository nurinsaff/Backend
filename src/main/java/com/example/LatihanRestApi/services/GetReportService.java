package com.example.LatihanRestApi.services;

import com.example.LatihanRestApi.dto.GetReportDTO;
import com.example.LatihanRestApi.dto.GetReportResponseDTO;
import com.example.LatihanRestApi.models.entity.Transaction;
import com.example.LatihanRestApi.models.entity.User;
import com.example.LatihanRestApi.models.repository.TransactionRepo;
import com.example.LatihanRestApi.models.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class GetReportService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    TransactionRepo transactionRepo;

    public List<GetReportDTO> getReport(LocalDate localDate) {

        // 1. Ambil semua data user terdaftar
        Iterable<User> users = userRepo.findAll();

        List<GetReportDTO> report = new ArrayList<>();

        // loop per user
        for (var user : users) {

            // 2. Ambil data transaksi di tanggal input
            List<Transaction> transactions = user.getTransactions().stream().filter(transaction ->
                    localDate.equals(transaction.getDate())).collect(Collectors.toList());
            Long currentBalance = transactions.get(transactions.size() - 1).getBalanceAfter();
            Long oldBalance = transactions.get(0).getBalanceBefore();

            System.out.println(">>>" + user.getUsername());

            String changeInPercentage;
            Double temp;

            DecimalFormat df = new DecimalFormat("###.##");
            if (oldBalance == 0){
                changeInPercentage = "0%";
            } else {
                // 3. Hitung change in percentage
                temp = ((1.0 * (currentBalance - oldBalance) / oldBalance) * 100);
                System.out.println(temp);
                changeInPercentage = df.format(temp) + "%";
            }
            // 4. Membuat list report semua user
            GetReportDTO getReportDTO = new GetReportDTO(user.getUsername(), changeInPercentage, localDate.toString());

            report.add(getReportDTO);
        }

        return report;
    }
}
