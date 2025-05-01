import Loan from '../models/Loan.js';
import Book from '../models/Book.js';


export const issueBook = async (req, res) => {
    try {
        const { user_id, book_id, due_date } = req.body;
        const book = await Book.findById(book_id);
        if (!book || book.available_copies <= 0) {
            return res.status(400).json({ message: "Book is not available" });
        }

        const loan = new Loan({
            user_id,
            book_id,
            due_date,
            status: 'ACTIVE'
        });

        book.available_copies -= 1;
        await book.save();
        await loan.save();

        const loanResponse = {
            id: loan._id,  
            user_id: loan.user_id,
            book_id: loan.book_id,
            issue_date: loan.issue_date.toISOString(),  
            due_date: loan.due_date.toISOString(),      
            status: loan.status
        };

        res.status(201).json(loanResponse);
    } catch (error) {
        res.status(500).json({ message: "Error issuing book", error: error.message });
    }
};


export const returnBook = async (req, res) => {
    try {
        const { loan_id } = req.body;
        const loan = await Loan.findById(loan_id);
        if (!loan || loan.status === 'RETURNED') {
            return res.status(404).json({ message: "Loan not found or already returned" });
        }

        const book = await Book.findById(loan.book_id);
        book.available_copies += 1;
        await book.save();

        loan.return_date = new Date();
        loan.status = 'RETURNED';
        await loan.save();

        const returnResponse = {
            id: loan._id,  
            user_id: loan.user_id,
            book_id: loan.book_id,
            issue_date: loan.issue_date.toISOString(),  
            due_date: loan.due_date.toISOString(),
            return_date: loan.return_date.toISOString(),      
            status: loan.status
        };

        res.status(200).json(returnResponse);
    } catch (error) {
        res.status(500).json({ message: "Error returning book", error: error.message });
    }
};


export const getLoansByUser = async (req, res) => {
    try {
        const loans = await Loan.find({ user_id: req.params.user_id }).populate('book_id');

        
        const loanResponse = loans.map(loan => ({
            id: loan._id,  
            book: {
                id: loan.book_id._id,  
                title: loan.book_id.title,
                author: loan.book_id.author
            },
            issue_date: loan.issue_date.toISOString(),
            due_date: loan.due_date.toISOString(),
            return_date: loan.return_date ? loan.return_date.toISOString() : null,  
            status: loan.status
        }));

        res.status(200).json(loanResponse);
    } catch (error) {
        res.status(500).json({ message: "Error fetching loans", error: error.message });
    }
};



export const getOverdueLoans = async (req, res) => {
    try {
        const today = new Date();
        const overdueLoans = await Loan.find({
            due_date: { $lt: today },
            status: 'ACTIVE'
        }).populate('user_id').populate('book_id');

        
        const overdueResponse = overdueLoans.map(loan => ({
            id: loan._id,  
            user: {
                id: loan.user_id._id,  
                name: loan.user_id.name,
                email: loan.user_id.email
            },
            book: {
                id: loan.book_id._id,  
                title: loan.book_id.title,
                author: loan.book_id.author
            },
            issue_date: loan.issue_date.toISOString(),
            due_date: loan.due_date.toISOString(),
            days_overdue: Math.floor((today - loan.due_date) / (1000 * 60 * 60 * 24))  
        }));

        res.status(200).json(overdueResponse);
    } catch (error) {
        res.status(500).json({ message: "Error fetching overdue loans", error: error.message });
    }
};



export const extendLoan = async (req, res) => {
    try {
        const { extension_days } = req.body;
        const loan = await Loan.findById(req.params.id);

        if (!loan || loan.status === 'RETURNED') {
            return res.status(404).json({ message: "Loan not found or already returned" });
        }

        const original_due_date = new Date(loan.due_date);
        
        const extended_due_date = new Date(loan.due_date);
        extended_due_date.setDate(extended_due_date.getDate() + parseInt(extension_days)); 
        loan.due_date = extended_due_date;

        loan.extensions_count += 1;
        await loan.save();

        const extendedLoanResponse = {
            id: loan._id,
            user_id: loan.user_id,
            book_id: loan.book_id,
            issue_date: loan.issue_date.toISOString(),
            original_due_date: original_due_date.toISOString(),
            extended_due_date: loan.due_date.toISOString(),
            status: loan.status,
            extensions_count: loan.extensions_count
        };

        res.status(200).json(extendedLoanResponse);
    } catch (error) {
        res.status(500).json({ message: "Error extending loan", error: error.message });
    }
};
