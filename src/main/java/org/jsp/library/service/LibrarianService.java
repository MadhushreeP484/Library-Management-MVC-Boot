package org.jsp.library.service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.jsp.library.dao.BookDao;
import org.jsp.library.dao.LibrarianDao;
import org.jsp.library.dto.Book;
import org.jsp.library.dto.BookRecord;
import org.jsp.library.dto.Librarian;
import org.jsp.library.helper.LoginHelper;
import org.jsp.library.helper.SendMailLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Service
public class LibrarianService {

	@Autowired
	private LibrarianDao librarianDao;

	@Autowired
	private SendMailLogic mailLogic;
	
	@Autowired
	private BookDao bookDao;

	public String librarianAccess(LoginHelper helper, ModelMap model) {
		if (helper.getEmail().equals("jesm33@jsp.com") && helper.getPassword().equals("admin")) {
			model.put("pos", "Authorization Success");
			return "LibrarianMain";
		} else {
			model.put("neg", "Unauthorized You can not access");
			return "Home";
		}
	}

	public String createLibrarianAccount(Librarian librarian, ModelMap model) {
		int otp = new Random().nextInt(100000, 999999);
		librarian.setOtp(otp);

		Librarian librarian2 = librarianDao.fetchByEmail(librarian.getEmail());

		if (librarian2 == null) {
			librarianDao.librarianDataSave(librarian);
			mailLogic.librarianSignupMail(librarian);
			model.put("pos", "Otp Sent Success");
			model.put("id", librarian.getId());
			return "VerifyOtp";
		} else {
			if (librarian2.isStatus()) {
				model.put("neg", "Email Should not be Repeated");
				return "LibrarianSignup";
			} else {
				mailLogic.librarianSignupMail(librarian2);
				model.put("pos", "Otp Sent Success");
				model.put("id", librarian2.getId());
				return "VerifyOtp";
			}
		}
	}

	public String createLibrarianAccount(int id, int otp, ModelMap model) {
		Librarian dto = librarianDao.fetchById(id);
		if (dto == null) {
			model.put("neg", "Id doesnt exists");
			return "LibrarianSignup";
		} else {
			if (dto.getOtp() == otp) {
				dto.setStatus(true);

				librarianDao.librarianDataSave(dto);
				model.put("pos", "Account Created Successfully");
				return "LibrarianLogin";
			} else {
				model.put("neg", "Incorrect OTP");
				model.put("id", id);
				return "VerifyOtp";
			}
		}
	}

	public String login(LoginHelper helper, ModelMap model, HttpSession session) {
		Librarian librarianDto = librarianDao.fetchByEmail(helper.getEmail());
		if (librarianDto == null) {
			model.put("neg", "Email doesn't exists");
			return "LibrarianLogin";
		} else {
			if (librarianDto.getPassword().equals(helper.getPassword())) {
				if (librarianDto.isStatus()) {
					session.setMaxInactiveInterval(60);
					session.setAttribute("librarian", librarianDto);
					model.put("pos", "Logged In Successfully");
					return "LibrarianHome";
				} else {
					model.put("neg", "Please Verify OTP");
					model.put("id", librarianDto.getId());
					return "VerifyOtp";
				}
			} else {
				model.put("neg", "Password Missmatch");
				return "LibrarianLogin";
			}
		}
	}

	public String addBook(Book book, MultipartFile pic, ModelMap model, HttpSession session) throws IOException {
		if (session.getAttribute("librarian") == null) {
			model.put("neg", "Invalid Session");
			return "Home";
		} else {
		byte[] picture = new byte[pic.getInputStream().available()];
		pic.getInputStream().read(picture);
		book.setPicture(picture);

		Book book2 = bookDao.findbyNameAndAuthor(book.getName(), book.getAuthor());
		if (book2 == null) {
			book.setStatus(true);
			bookDao.save(book);
			model.put("pos", "Book Added Successfully");
			return "LibrarianHome";
		} else {
			book2.setQuantity(book2.getQuantity() + book.getQuantity());
			bookDao.save(book2);
			model.put("pos", "Book Added Successfully");
			return "LibrarianHome";
		}
		}
	}

	public String fetchAllBooks(ModelMap model, HttpSession session) {
		if (session.getAttribute("librarian") == null) {
			model.put("neg", "Invalid Session");
			return "Home";
		} else {
		List<Book> books = bookDao.findAll();
		if (books.isEmpty()) {
			model.put("neg","No Books Found");
			return "LibrarianHome";
		} else {
			model.put("books", books);
			return "LibrarianBooks";
		}
		}
	}

	public String fetchBooks(String name, ModelMap model, HttpSession session) {
		if (session.getAttribute("librarian") == null) {
			model.put("neg", "Invalid Session");
			return "Home";
		} else {
		List<Book> books = bookDao.findByName(name);
		if(books.isEmpty())
			books = bookDao.findByAuthor(name);
		if(books.isEmpty())
			model.put("neg", "Book Not Found");
		
		return fetchAllBooks(model, session);
		}
	}

	public String delete(int id, ModelMap model, HttpSession session) {
		if (session.getAttribute("librarian") == null) {
			model.put("neg", "Invalid Session");
			return "Home";
		} else {
			Book book = bookDao.findById(id);
			if (book == null) {
				model.put("neg", "Book Not Found");
				return fetchAllBooks(model, session);
			} else {
				bookDao.deleteBook(id);
				model.put("pos", "Book Deleted Successfully");
				return fetchAllBooks(model, session);
			}
		}
	}

	public String edit(int id, ModelMap map, HttpSession session) {
		if (session.getAttribute("librarian") == null) {
			map.put("neg", "Invalid Session");
			return "Home";
		} else {
			Book book = bookDao.findById(id);
			if (book == null) {
				map.put("neg", "Book Not Found");
				return fetchAllBooks(map, session);
			} else {
				map.put("book", book);
				return "EditBook";
			}
		}
	}

	public String updateBook(Book book, ModelMap model, HttpSession session) {
		if (session.getAttribute("librarian") == null) {
			model.put("neg", "Invalid Session");
			return "Home";
		} else {
			Book book2 = bookDao.findById(book.getId());
			book.setPicture(book2.getPicture());
			book.setStatus(book2.isStatus());
			bookDao.save(book);
			model.put("pos", "Book Updated Successfully");
			return "LibrarianHome";
		}
	}

	public String viewBorrowHistory(HttpSession session, ModelMap map) {
		if (session.getAttribute("librarian") == null) {
			map.put("neg", "Invalid Session");
			return "Home";
		} else {
			return "BookId";
		}
	}

	public String viewBorrowHistory(HttpSession session, ModelMap map, int id) {
		if (session.getAttribute("librarian") == null) {
			map.put("neg", "Invalid Session");
			return "Home";
		} else {
			Book book = bookDao.findById(id);
			if (book == null) {
				map.put("neg", "Book Not Found");
				return "LibrarianHome";
			}
			else {
				List<BookRecord> records=book.getRecords();
				if(records.isEmpty())
				{
					map.put("neg", "No History Found");
					return "LibrarianHome";
				}
				else {
					map.put("records", records);
					return "BookHistory";
				}
			}
		}
	}
}
