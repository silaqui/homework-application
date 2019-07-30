package com.example.homework.application.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.example.homework.application.entity.Author;
import com.example.homework.application.entity.Book;

@Profile("json_data")
@Repository
public class BookDaoImpl implements BookDao {

	private static List<Book> books;

	static {
		books = new ArrayList<>();
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = classloader.getResourceAsStream("books.json");
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
	}

	public static Book fromJson(JSONObject jsonObject) {
		Book book = new Book();

		JSONArray industryIdentifiers = (JSONArray) ((JSONObject) jsonObject.get("volumeInfo"))
				.get("industryIdentifiers");
		Optional<JSONObject> isbn = (Optional<JSONObject>) industryIdentifiers.stream()
				.filter(o -> ((JSONObject) o).get("type").equals("ISBN_13")).findFirst();
		book.setIsbn(isbn.isPresent() ?
				(String) isbn.get().get("identifier") :
				(String) ((JSONObject) jsonObject.get("volumeInfo")).get("id"));

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

	@Override
	public Collection<Book> getAllBooks() {
		return books;
	}

	@Override
	public Optional<Book> getBook(String id) {
		return books.stream().filter(book -> book.getIsbn() != null && book.getIsbn().equals(id))
				.findFirst();
	}

	@Override
	public Collection<Book> getCategory(String categoryName) {
		return books.stream().filter(b -> isPhraseInListOfString(b.getCategories(), categoryName))
				.collect(Collectors.toList());

	}

	@Override
	public Collection<Book> getQuery(String phrase) {
		return books.stream().filter(book -> matchPhrase(book, phrase))
				.collect(Collectors.toList());
	}

	// @formatter:off
	private boolean matchPhrase(Book book, String phrase) {
		return isPhraseInString(book.getIsbn(),phrase)  ||
				isPhraseInString(book.getTitle(),phrase)  ||
				isPhraseInString(book.getSubtitle(),phrase)  ||
				isPhraseInString(book.getPublisher(),phrase)  ||
				isPhraseInString(book.getDescription(),phrase)  ||
				isPhraseInString(book.getThumbnailUrl(),phrase)  ||
				isPhraseInString(book.getLanguage(),phrase)  ||
				isPhraseInString(book.getPreviewLink(),phrase)  ||
				isPhraseInString(book.getLanguage(),phrase)  ||
				isPhraseInListOfString(book.getAuthors(),phrase) ||
				isPhraseInListOfString(book.getCategories(),phrase);
	}
	// @formatter:on

	private boolean isPhraseInString(String string, String phrase) {
		return string != null && string.toLowerCase().contains(phrase.toLowerCase());

	}

	private boolean isPhraseInListOfString(List<String> list, String phrase) {
		return list != null ?
				list.stream().map(s -> s.toLowerCase().contains(phrase.toLowerCase()))
						.reduce((a, b) -> a || b).orElse(false) :
				false;
	}

	@Override
	public Collection<Author> getAuthors() {
		Map<String, List<Double>> authorsRatings = new HashMap<>();
		for (Book book : books) {
			if (book.getAverageRating() != null) {
				for (String author : book.getAuthors()) {
					if (authorsRatings.get(author) != null) {
						authorsRatings.get(author).add(book.getAverageRating());
					} else {
						authorsRatings.put(author, Arrays.asList(book.getAverageRating()));
					}
				}
			}
		}
		List<Author> output = new ArrayList<>();
		authorsRatings.forEach((a, r) -> output
				.add(new Author(a, r.stream().mapToDouble(val -> val).average().orElse(0.0))));
		output.sort(Comparator.comparing(Author::getAverageRating, Comparator.reverseOrder()));
		return output;
	}
}
