#include <iostream>
#include <stack>
#include <string>
#include <vector>
#include <sstream>
#include <algorithm> // Include for std::find_if

class Transaction {
public:
    std::string description;
    double amount;

    Transaction(std::string desc, double amt) : description(desc), amount(amt) {}
};

class PersonalFinanceTracker {
private:
    std::stack<Transaction> transactions;
    double balance;
    std::vector<Transaction> expenseList;

public:
    PersonalFinanceTracker() : balance(0) {}

    void addTransaction(std::string description, double amount) {
        transactions.push(Transaction(description, amount));
        balance += amount;
        if (amount < 0) {
            expenseList.push_back(Transaction(description, amount)); // Store expenses separately
        }
        std::cout << "Transaction added: " << description << " of amount " << amount << std::endl;
    }

    void undoTransaction() {
        if (!transactions.empty()) {
            Transaction lastTransaction = transactions.top();
            transactions.pop();
            balance -= lastTransaction.amount;

            // If the last transaction was an expense, remove it from the expense list
            if (lastTransaction.amount < 0) {
                auto it = std::remove_if(expenseList.begin(), expenseList.end(),
                    [&](Transaction& exp) { return exp.description == lastTransaction.description && exp.amount == lastTransaction.amount; });
                if (it != expenseList.end()) {
                    expenseList.erase(it, expenseList.end());
                }
            }

            std::cout << "Transaction undone: " << lastTransaction.description << " of amount " << lastTransaction.amount << std::endl;
        } else {
            std::cout << "No transactions to undo." << std::endl;
        }
    }

    void showBalance() {
        std::cout << "Current balance: " << balance << std::endl;
    }

    void showExpenses() {
        std::cout << "\nList of Expenses:\n";
        for (const auto& exp : expenseList) {
            std::cout << "Description: " << exp.description << ", Amount: " << exp.amount << std::endl;
        }
        std::cout << "Total Expenses: " << getTotalExpenses() << std::endl;
    }

    double getTotalExpenses() {
        double total = 0;
        for (const auto& exp : expenseList) {
            total += exp.amount; // Sum of all expenses (amounts are negative)
        }
        return total;
    }
};

void displayMenu() {
    std::cout << "\nPersonal Finance Tracker\n";
    std::cout << "1. Add Income\n";
    std::cout << "2. Add Expense\n";
    std::cout << "3. Add Multiple Expenses\n";
    std::cout << "4. Undo Last Transaction\n";
    std::cout << "5. Show Current Balance\n";
    std::cout << "6. Show Expenses\n";
    std::cout << "7. Exit\n";
    std::cout << "Choose an option: ";
}

int main() {
    PersonalFinanceTracker tracker;
    int choice;
    std::string description;
    double amount;

    while (true) {
        displayMenu();
        std::cin >> choice;

        switch (choice) {
            case 1:
                std::cout << "Enter income description: ";
                std::cin.ignore(); // Clear input buffer
                std::getline(std::cin, description);
                std::cout << "Enter income amount: ";
                std::cin >> amount;
                tracker.addTransaction(description, amount);
                break;
            case 2:
                std::cout << "Enter expense description: ";
                std::cin.ignore(); // Clear input buffer
                std::getline(std::cin, description);
                std::cout << "Enter expense amount: ";
                std::cin >> amount;
                tracker.addTransaction(description, -amount); // Negate the amount for expenses
                break;
            case 3: {
                std::cout << "Enter expenses in the format 'description amount', each on a new line. Type 'done' to finish.\n";
                std::string line;
                while (true) {
                    std::getline(std::cin, line);
                    if (line == "done") break;

                    std::istringstream iss(line);
                    std::string desc;
                    double amt;
                    if (iss >> desc >> amt) {
                        tracker.addTransaction(desc, -amt); // Negate the amount for expenses
                    } else {
                        std::cout << "Invalid input. Please enter in the format 'description amount'.\n";
                    }
                }
                break;
            }
            case 4:
                tracker.undoTransaction();
                break;
            case 5:
                tracker.showBalance();
                break;
            case 6:
                tracker.showExpenses();
                break;
            case 7:
                std::cout << "Exiting the application.\n";
                return 0;
            default:
                std::cout << "Invalid option. Please try again.\n";
                break;
        }
    }

    return 0;
}
