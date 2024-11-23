package com.hometopia.mediaservice.utils;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface FileUtils {

    static String getFilename(String originalFilename) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_" + originalFilename;
    }

    static MediaType getMediaType(String extension) {
        return switch (extension) {
            // Images
            case "jpg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;

            // Documents
            case "pdf" -> MediaType.APPLICATION_PDF;

            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }

    static String getMineType(@Nullable String extension) {
        if (extension == null) {
            return "application/octet-stream";
        }

        return switch (extension) {
            // Images
            case "avif" -> "image/avif";
            case "bmp" -> "image/bmp";
            case "gif" -> "image/gif";
            case "ico" -> "image/vnd.microsoft.icon";
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "svg" -> "image/svg+xml";
            case "tif", "tiff" -> "image/tiff";
            case "webp" -> "image/webp";

            // Documents
            case "csv" -> "text/csv";
            case "doc" -> "application/msword";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "pdf" -> "application/pdf";
            case "ppt" -> "application/vnd.ms-powerpoint";
            case "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "rtf" -> "application/rtf";
            case "txt" -> "text/plain";
            case "xls" -> "application/vnd.ms-excel";
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

            // Archives
            case "rar" -> "application/vnd.rar";
            case "zip" -> "application/zip";
            case "7z" -> "application/x-7z-compressed";

            default -> "application/octet-stream";
        };
    }

}
