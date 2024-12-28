package com.biblio.mapper;

import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.BookUpdateGlobalRequest;
import com.biblio.dto.response.BookMetadataResponse;
import com.biblio.entity.BookMetadata;
import com.biblio.enumeration.EBookMetadataStatus;

import java.time.LocalDateTime;

public class BookMetadataMapper {

    public static BookMetadata toBookMetadata(BookCreateGlobalRequest request) {
        return BookMetadata.builder()
                .createdAt(LocalDateTime.now())
                .openingDate(LocalDateTime.now())
                .importPrice(Double.parseDouble(request.getImportPrice()))
                .status(EBookMetadataStatus.IN_STOCK)
                .build();
    }

    public static BookMetadata toBookMetadata(BookUpdateGlobalRequest request) {
        return BookMetadata.builder()
                .createdAt(LocalDateTime.now())
                .openingDate(LocalDateTime.now())
                .importPrice(Double.parseDouble(request.getImportPrice()))
                .status(EBookMetadataStatus.IN_STOCK)
                .build();
    }

    public static BookMetadataResponse toBookMetadataResponse(BookMetadata bookMetadata) {
        BookMetadataResponse bookMetadataResponse = new BookMetadataResponse();

//        List<MediaFile> mediaFiles = new ArrayList<>(bookMetadata.getMediaFiles());

//        mediaFiles.sort(Comparator.comparing(MediaFile::getFileName));

//        for (MediaFile mediaFile : mediaFiles) {
//            System.out.println("MediaFile: " + mediaFile.getFileName());
//            bookMetadataResponse.getMediaFiles().add(MediaFileMapper.toMediaFileResponse(mediaFile));
//        }

        return bookMetadataResponse;
    }
}
