import express from "express";


import {
    createUser,
    getUserById
} from '../controllers/user.controller.js';

import {
    addBook,
    searchBooks,
    getBookById,
    updateBook,
    deleteBook
} from '../controllers/book.controller.js';

import {
    issueBook,
    returnBook,
    getLoansByUser,
    getOverdueLoans,
    extendLoan
} from '../controllers/loan.controller.js';

import {
    getPopularBooks,
    getActiveUsers,
    getStatsOverview
} from '../controllers/stats.controller.js';

const router = express.Router();

router.post('/users', createUser);
router.get('/users/:id', getUserById);


router.post('/books', addBook);
router.get('/books', searchBooks);
router.get('/books/:id', getBookById);
router.put('/books/:id', updateBook);
router.delete('/books/:id', deleteBook);

router.post('/loans', issueBook);
router.post('/returns', returnBook);
router.get('/loans/overdue', getOverdueLoans);
router.get('/loans/:user_id', getLoansByUser);
router.put('/loans/:id/extend', extendLoan);

router.get('/stats/books/popular', getPopularBooks);
router.get('/stats/users/active', getActiveUsers);
router.get('/stats/overview', getStatsOverview);



export default router;