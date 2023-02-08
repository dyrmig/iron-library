package com.littlecodewarriors.ironlibrary;

import com.littlecodewarriors.ironlibrary.model.Book;
import com.littlecodewarriors.ironlibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class IronLibraryApplication implements CommandLineRunner {
	//Uncomment line underneath and comment the one above in order to make the tests work
//public class IronlibraryApplication {
	@Autowired
	BookRepository bookRepository;
	public static void main(String[] args) {
		SpringApplication.run(IronLibraryApplication.class, args);
	}

	//Comment all in: public void run(String... args) throws Exception, in order to make the tests work
	@Override
	public void run(String... args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		help();
		System.out.println("Introduce your command:");
		boolean runProgram = true;
		while (runProgram) {
			String command = scanner.nextLine();
			switch (command) {
				case "1":
					break;
				case "2":
					System.out.println("Enter the title of the book:");
					command = scanner.nextLine();
					List<Book> bookList = bookRepository.findByTitleContaining(command);
					for(Book book: bookList){
						System.out.println(book.getIsbn()+" "+book.getTitle()+" "+book.getCategory()+" "+book.getQuantity());
					}
					break;
				case "3":
					break;
				case "4":
					break;
				case "5":
					break;
				case "6":
					break;
				case "7":
					break;
				case "8":
					runProgram = false;
					break;
				default:
					help();
					break;

			}
		}
		scanner.close();
	}
	public static void help(){
		System.out.println(
				"1. Add book\n" +
				"2. Search book by title\n" +
				"3. Search book by category\n" +
				"4. Search book by author\n" +
				"5. Get all books\n" +
				"6. Issue book to student\n" +
				"7. Get all books by student\n" +
				"8. Exit");
	}

}
