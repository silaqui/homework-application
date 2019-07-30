package com.example.homework.application.dao.helpers;

import com.example.homework.application.entity.Book;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JsonReader {

    public static List<Book> readJsonFromResource(String resourceName) {
        List<Book> books = new ArrayList<>();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream(resourceName);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser
                    .parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            JSONArray booksJsonArray = (JSONArray) jsonObject.get("items");
            for (Object book : booksJsonArray) {
                books.add(fromJson((JSONObject) book));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return books;
    }

    private static Book fromJson(JSONObject jsonObject) {
        Book book = new Book();

        JSONArray industryIdentifiers = (JSONArray) ((JSONObject) jsonObject.get("volumeInfo"))
                .get("industryIdentifiers");
        Optional<JSONObject> isbn = (Optional<JSONObject>) industryIdentifiers.stream()
                .filter(o -> ((JSONObject) o).get("type").equals("ISBN_13")).findFirst();
        book.setIsbn(isbn.isPresent() ?
                (String) isbn.get().get("identifier") :
                (String) jsonObject.get("id"));

        book.setTitle((String) ((JSONObject) jsonObject.get("volumeInfo")).get("title"));
        book.setSubtitle((String) ((JSONObject) jsonObject.get("volumeInfo")).get("subtitle"));
        book.setPublisher((String) ((JSONObject) jsonObject.get("volumeInfo")).get("publisher"));
        book.setDescription(
                (String) ((JSONObject) jsonObject.get("volumeInfo")).get("description"));
        book.setThumbnailUrl((String) ((JSONObject) ((JSONObject) jsonObject.get("volumeInfo"))
                .get("imageLinks")).get("thumbnail"));
        book.setLanguage((String) ((JSONObject) jsonObject.get("volumeInfo")).get("language"));
        book.setPreviewLink(
                (String) ((JSONObject) jsonObject.get("volumeInfo")).get("previewLink"));

        Long pageCount = (Long) ((JSONObject) jsonObject.get("volumeInfo")).get("pageCount");
        if (pageCount != null) {
            book.setPageCount(pageCount.intValue());
        }
        String publishedDate = (String) ((JSONObject) jsonObject.get("volumeInfo"))
                .get("publishedDate");
        if (publishedDate != null) {
            Timestamp timestamp = Timestamp.valueOf(publishedDate.length() == 4 ?
                    publishedDate + "-01-01 00:00:00" :
                    publishedDate + " 00:00:00");
            book.setPublishedDate(timestamp.getTime());
        }

        book.setAverageRating(
                (Double) ((JSONObject) jsonObject.get("volumeInfo")).get("averageRating"));
        book.setAuthors((List<String>) ((JSONObject) jsonObject.get("volumeInfo")).get("authors"));
        book.setCategories(
                (List<String>) ((JSONObject) jsonObject.get("volumeInfo")).get("categories"));

        return book;
    }


}
