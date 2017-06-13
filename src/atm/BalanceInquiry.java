package atm;

public class BalanceInquiry extends Transaction{

	public BalanceInquiry(int accountNumber, Screen screen, BankDatabase bankDatabase) {
		super(accountNumber, screen, bankDatabase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		BankDatabase bankDatabase=getBankDatabase();
		Screen screen=getScreen();
		double availableBalance=bankDatabase.getAvailableBalance(getAccountNumber());
		double totalBalance=bankDatabase.getTotalBalance(getAccountNumber());
		screen.displayMessageLine("\nBalance Information");
		screen.displayMessage("- Availabel balance: ");
		screen.displayDollarAmount(availableBalance);
		screen.displayMessage("\n - Total balance: ");
		screen.displayDollarAmount(totalBalance);
		screen.displayMessageLine(" ");
		
	}
	
}
