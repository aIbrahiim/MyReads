package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.exception.ApiError;
import com.abdelrahman.myreads.MyReads.exception.AppException;
import com.abdelrahman.myreads.MyReads.exception.ResourceNotFoundException;
import com.abdelrahman.myreads.MyReads.model.Book;
import com.abdelrahman.myreads.MyReads.model.Rating;
import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.model.UserRatedBook;
import com.abdelrahman.myreads.MyReads.repository.BookRepository;
import com.abdelrahman.myreads.MyReads.repository.RatingRepository;
import com.abdelrahman.myreads.MyReads.repository.UserRatedBookRepository;
import com.abdelrahman.myreads.MyReads.repository.UserRepository;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;
import com.abdelrahman.myreads.MyReads.service.UserRatedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.abdelrahman.myreads.MyReads.util.AppConstants.*;

@Service
public class UserRatedBookServiceImpl implements UserRatedBookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRatedBookRepository userRatedBookRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Override
    public void rateBook(UserPrincipal currentUser, Long bookId, Integer rate) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK, ID, bookId));

        checkBookRated(book);

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        String starValue = getStatValue(rate);
        if(starValue == null || starValue.isEmpty()){
            ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE, "Error rating the book");
            throw new AppException(apiError);
        }

        if(userRatedBookRepository.checkRateValue(bookId, user.getId(), rate)){
            return;
        }

        System.out.println(userRatedBookRepository.checkRateNotExists(bookId, user.getId(), rate));
        if(userRatedBookRepository.checkRateNotExists(bookId, user.getId(), rate)){
            rerateTheBook(book, rate, user, starValue );
            return;
        }

        rateTheBook(book, bookId, rate, user, starValue);

    }

    private void rerateTheBook(Book book, Integer rate, User user, String starValue) {
        ratingRepository.updateCountByStar(book.getId(), starValue);
        userRatedBookRepository.deleteRate(user.getId(), book.getId(), rate);
        updateBookRate(book, starValue);
        saveUserRate(user, book, rate);
    }



    private void rateTheBook(Book book, Long bookId, Integer rate, User user, String starValue){
        updateBookRate(book, starValue);
        saveUserRate(user, book, rate);
        //Long cnt = ratingRepository.getRatesOfBookByStarValueAndBook(bookId, starValue);
    }

    private void updateBookRate(Book book, String starValue){
        Long bookId = book.getId();
        ratingRepository.updateCount(bookId, starValue);

        double one = ratingRepository.getRatesOfBookByStarValueAndBook(bookId, "star_one");
        double two = ratingRepository.getRatesOfBookByStarValueAndBook(bookId, "star_two");
        double three = ratingRepository.getRatesOfBookByStarValueAndBook(bookId, "star_three");
        double four = ratingRepository.getRatesOfBookByStarValueAndBook(bookId, "star_four");
        double five = ratingRepository.getRatesOfBookByStarValueAndBook(bookId, "star_five");
        double bookRate = calculateBookRating(one, two, three, four, five);

        book.setRating(bookRate);
        bookRepository.save(book);
    }

    private void checkBookRated(Book book) {
        if(ratingRepository.existsByBook(book))
            return;

        Rating rating1 = new Rating("star_one", (long) 0, book);
        Rating rating2 = new Rating("star_two", (long) 0, book);
        Rating rating3 = new Rating("star_three", (long) 0, book);
        Rating rating4 = new Rating("star_four", (long) 0, book);
        Rating rating5 = new Rating("star_five", (long) 0, book);
        ratingRepository.save(rating1);
        ratingRepository.save(rating2);
        ratingRepository.save(rating3);
        ratingRepository.save(rating4);
        ratingRepository.save(rating5);
    }

    private double calculateBookRating(double v1, double v2, double v3, double v4, double v5){
        double rate = (1*v1 + 2*v2 + 3*v3 + 4*v4 + 5*v5) / (v1+v2+v3+v4+v5);
        return rate;
    }

    private void saveUserRate(User user,Book book,Integer rate){
        UserRatedBook bookRated = new UserRatedBook(user, book, rate, LocalDateTime.now());
        userRatedBookRepository.save(bookRated);

    }

    private String getStatValue(Integer rate){
        switch (rate){
            case 1:
                return "star_one";
            case 2:
                return "star_two";
            case 3:
                return "star_three";
            case 4:
                return "star_four";
            case 5:
                return "star_five";
            default:
                return null;
        }

    }
}
