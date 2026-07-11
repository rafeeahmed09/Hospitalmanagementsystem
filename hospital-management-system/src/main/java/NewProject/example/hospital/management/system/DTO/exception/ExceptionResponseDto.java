package NewProject.example.hospital.management.system.DTO.exception;

import lombok.*;

import java.time.LocalDateTime;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponseDto {

    private LocalDateTime timestamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;
}
