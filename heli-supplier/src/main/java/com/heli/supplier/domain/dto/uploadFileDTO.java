package com.heli.supplier.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * ClassName: uploadFileDTO
 * Package: com.heli.supplier.domain.dto
 * Description:
 *
 * @Author
 * @Create 2025/2/26 15:25
 * @Version 1.0
 */
@Data
public class uploadFileDTO {
    MultipartFile excelFile;

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date uploadMonth;
}
