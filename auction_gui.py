import tkinter as tk
from tkinter import messagebox, simpledialog

# Step 1: Define the Product class to store item details
class Product:
    def __init__(self, name, starting_price):
        self.name = name
        self.starting_price = starting_price
        self.highest_bid = starting_price
        self.highest_bidder = None

# Step 2: Create the Auction System class
class AuctionSystem:
    def __init__(self):
        self.products = []

    def add_product(self, name, starting_price):
        """Add a new product to the auction."""
        product = Product(name, starting_price)
        self.products.append(product)
        return product

    def place_bid(self, product_name, bidder_name, bid_amount):
        """Place a bid on a product."""
        for product in self.products:
            if product.name == product_name:
                if bid_amount > product.highest_bid:
                    product.highest_bid = bid_amount
                    product.highest_bidder = bidder_name
                    return True
                else:
                    return False  # Bid too low
        return None  # Product not found

    def get_results(self):
        """Get the auction results as a string."""
        results = []
        for product in self.products:
            if product.highest_bidder:
                results.append(f"{product.name}: Sold to {product.highest_bidder} for ${product.highest_bid}")
            else:
                results.append(f"{product.name}: No bids. Unsold.")
        return "\n".join(results)

# Step 3: Create the GUI for the auction system
class AuctionGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Online Auction System")
        self.auction = AuctionSystem()

        # GUI Components
        self.label = tk.Label(root, text="Welcome to the Auction System", font=("Arial", 16))
        self.label.pack(pady=10)

        self.add_product_button = tk.Button(root, text="Add Product", command=self.add_product)
        self.add_product_button.pack(pady=5)

        self.place_bid_button = tk.Button(root, text="Place Bid", command=self.place_bid)
        self.place_bid_button.pack(pady=5)

        self.close_auction_button = tk.Button(root, text="Close Auction", command=self.close_auction)
        self.close_auction_button.pack(pady=5)

        self.exit_button = tk.Button(root, text="Exit", command=root.quit)
        self.exit_button.pack(pady=5)

        self.result_text = tk.Text(root, height=10, width=50)
        self.result_text.pack(pady=10)

    def add_product(self):
        """Add a product through a dialog."""
        name = simpledialog.askstring("Product Name", "Enter the product name:")
        if name:
            starting_price = simpledialog.askfloat("Starting Price", "Enter the starting price:")
            if starting_price is not None:
                self.auction.add_product(name, starting_price)
                messagebox.showinfo("Success", f"Added product '{name}' with starting price ${starting_price}.")

    def place_bid(self):
        """Place a bid through a dialog."""
        product_name = simpledialog.askstring("Product Name", "Enter the product name to bid on:")
        if product_name:
            bidder_name = simpledialog.askstring("Bidder Name", "Enter your name:")
            if bidder_name:
                bid_amount = simpledialog.askfloat("Bid Amount", "Enter your bid:")
                if bid_amount is not None:
                    result = self.auction.place_bid(product_name, bidder_name, bid_amount)
                    if result is True:
                        messagebox.showinfo("Success", f"Bid placed by {bidder_name} for ${bid_amount}.")
                    elif result is False:
                        messagebox.showwarning("Failed", "Bid too low. Please place a higher bid.")
                    else:
                        messagebox.showerror("Error", "Product not found.")

    def close_auction(self):
        """Display the auction results."""
        results = self.auction.get_results()
        self.result_text.delete(1.0, tk.END)
        self.result_text.insert(tk.END, results)

# Step 4: Run the GUI application
if __name__ == "__main__":
    root = tk.Tk()
    app = AuctionGUI(root)
    root.mainloop()

