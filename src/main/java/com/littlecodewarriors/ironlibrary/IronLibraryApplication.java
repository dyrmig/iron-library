package com.littlecodewarriors.ironlibrary;
import com.littlecodewarriors.ironlibrary.model.Author;
import com.littlecodewarriors.ironlibrary.model.Book;
import com.littlecodewarriors.ironlibrary.model.Issue;
import com.littlecodewarriors.ironlibrary.model.Student;
import com.littlecodewarriors.ironlibrary.repository.AuthorRepository;
import com.littlecodewarriors.ironlibrary.repository.BookRepository;
import com.littlecodewarriors.ironlibrary.repository.IssueRepository;
import com.littlecodewarriors.ironlibrary.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class IronLibraryApplication implements CommandLineRunner {
	//Uncomment line underneath and comment the one above in order to make the tests work
//public class IronlibraryApplication {
	@Autowired
	BookRepository bookRepository;
	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	IssueRepository issueRepository;
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
					System.out.println("Enter the ISBN of the new book:");
					String bookIsbn = scanner.nextLine();
					//check if the ISBN exist in the ddbb
					if(bookRepository.findById(bookIsbn).isPresent()){
						System.out.println("This book is already in the system");
						break;
					}
					System.out.println("Enter the title:");
					String bookTitle = scanner.nextLine();
					System.out.println("Enter the category:");
					String bookCategory = scanner.nextLine();
					System.out.println("Enter Author name:");
					String bookAuthorName = scanner.nextLine();
					System.out.println("Enter Author email:");
					String bookAuthorEmail = scanner.nextLine();
					System.out.println("Enter the quantity:");
					Integer bookQuantity = Integer.valueOf(scanner.nextLine());

					//save the book without author
					Book newBook = new Book(bookIsbn,bookTitle,bookCategory,bookQuantity);
					bookRepository.save(newBook);
					//search if the author exists in the ddbb
					Optional<Author> authorOptional = authorRepository.findByNameAndEmail(bookAuthorName,bookAuthorEmail);
					//if author don't exists we create a new author and assign the book and the book to the new author
					if(!authorOptional.isPresent()){
						Author newAuthor = new Author(bookAuthorName,bookAuthorEmail);
						newAuthor.setAuthorBooks(List.of(newBook));
						authorRepository.save(newAuthor);
						newBook.setAuthor(newAuthor);
						bookRepository.save(newBook);
						System.out.println("New book and new author created.");
					} else { //if author
						authorOptional.get().setAuthorBooks(List.of(newBook));
						authorRepository.save(authorOptional.get());
						newBook.setAuthor(authorOptional.get());
						bookRepository.save(newBook);
						System.out.println("New book and created.");
					}

					break;
				case "2":
					System.out.println("Enter the title of the book:");
					command = scanner.nextLine();
					List<Book> bookList = bookRepository.findByTitleContaining(command);
					if(bookList.size()==0){
						System.out.println("No matching results");
					} else {
						for(Book book: bookList){
							System.out.println("--------------------------------\n"  + "ISBN: " + book.getIsbn()+" \nTitle: " +book.getTitle()+" \nAuthor: " +book.getAuthor().getName()+" \nCategory: "+book.getCategory()+" \nQuantity available: "+book.getQuantity() + "\n--------------------------------");
						}
					}
					break;
				case "3":
					System.out.println("Enter the category of books:");
					command = scanner.nextLine();
					List<Book> bookCategoryList = bookRepository.findByCategoryContaining(command);
					if(bookCategoryList.size()==0){
						System.out.println("No matching results");
					} else {
						for(Book book: bookCategoryList){
							System.out.println("--------------------------------\n"  + "ISBN: " + book.getIsbn()+" \nTitle: " +book.getTitle()+" \nAuthor: " +book.getAuthor().getName()+" \nCategory: "+book.getCategory()+" \nQuantity available: "+book.getQuantity() + "\n--------------------------------");
						}
					}
					break;
				case "4":
					System.out.println("Enter the Author:");
					command = scanner.nextLine();
					List<Book> bookAuthorList = authorRepository.findByNameContaining(command);
					if(bookAuthorList.size()==0){
						System.out.println("No matching results");
					} else {
						for(Book book: bookAuthorList){
							System.out.println("--------------------------------\n"  + "ISBN: " + book.getIsbn()+" \nTitle: " +book.getTitle()+" \nAuthor: " +book.getAuthor().getName()+" \nCategory: "+book.getCategory()+" \nQuantity available: "+book.getQuantity() + "\n--------------------------------");
						}
					}
					break;
				case "5":
					List<Book> allBooks = bookRepository.findAll();
					if(allBooks.size()==0){
						System.out.println("No matching results");
					} else {
						for(Book book: allBooks){
							System.out.println("--------------------------------\n"  + "ISBN: " + book.getIsbn()+" \nTitle: " +book.getTitle()+" \nAuthor: " +book.getAuthor().getName()+" \nCategory: "+book.getCategory()+" \nQuantity available: "+book.getQuantity() + "\n--------------------------------");
						}
					}
					break;
				case "6":
					System.out.println("Enter usn:");
					String issueUsn = scanner.nextLine();
					System.out.println("Enter name:");
					String issueName = scanner.nextLine();
					System.out.println("Enter book ISBN:");
					String issueIsbn = scanner.nextLine();
					if(!bookRepository.findById(issueIsbn).isPresent()){
						System.out.println("Book not found in the system");
						break;
					}
					if(bookRepository.findById(issueIsbn).get().getQuantity()<=0){
						System.out.println("No copies of this Book available");
						break;
					}
					Optional<Book> optionalBook = bookRepository.findById(issueIsbn);
					Optional<Student> optionalStudent = studentRepository.findById(issueUsn);
					if(optionalStudent.isPresent()){
						optionalBook.get().setQuantity(optionalBook.get().getQuantity()-1);
						bookRepository.save(optionalBook.get());
						Issue newIssue = new Issue(optionalStudent.get(), optionalBook.get());
						issueRepository.save(newIssue);
						System.out.println("Book issued to "+issueName);
						System.out.println("Return date: "+newIssue.getReturnDate());
					} else {
						optionalBook.get().setQuantity(optionalBook.get().getQuantity()-1);
						bookRepository.save(optionalBook.get());
						Student newStudent = new Student(issueUsn, issueName);
						studentRepository.save(newStudent);
						Issue newIssue = new Issue(newStudent, optionalBook.get());
						issueRepository.save(newIssue);
						System.out.println("Book issued to new student: "+issueName);
						System.out.println("Return date: "+newIssue.getReturnDate());
					}

					break;
				case "7":
					break;
				case "8":
					SpringApplication.run(IronLibraryApplication.class, args).close();
					System.out.println("Goodbye!");
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
