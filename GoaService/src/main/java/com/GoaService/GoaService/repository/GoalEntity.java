package com.GoaService.GoaService.repository;

import com.GoaService.GoaService.domain.Category;
import com.GoaService.GoaService.domain.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "goals")
public class GoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "User_id")
    private long userId;

    @NotBlank
    @Length( max = 50)
    @Column(name = "Title")
    private String title;

    @Length( max = 150)
    @Column(name = "Description")
    private String description;

    @Column(name = "TargetDate")
    private LocalDate targetDate;

    @NotNull
    @Column(name = "Progress")
    private long progress;

    @NotBlank
    private Category category;

    @NotBlank
    private Status status;

}
