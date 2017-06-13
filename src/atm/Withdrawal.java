package atm;

public class Withdrawal extends Transaction {
	private int amount;
	private Keypad keypad;
	private CashDispenser cashDispenser;
	private final static int CANCELED = 6;

	public Withdrawal(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad keypad,
			CashDispenser cashDispenser) {
		super(userAccountNumber, atmScreen, atmBankDatabase);
		this.keypad = keypad;
		this.cashDispenser = cashDispenser;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		boolean cashDispensed=false;
		double availableBalance;
		BankDatabase bankDatabase=getBankDatabase();
		Screen screen=getScreen();
		do{
			amount=displayMenuOfAmounts();
			if(amount!=CANCELED){
				availableBalance=bankDatabase.getAvailableBalance(getAccountNumber());
				if(amount<=availableBalance){
					if(cashDispenser.issufficientCashAvailable(amount)){
						bankDatabase.debit(getAccountNumber(), amount);
						cashDispenser.dispenseCash(amount);
						cashDispensed=true;
						screen.displayMessageLine("\nYour cash has been" + " dispensed. Please take your cash now.");
					}
					else{//cash dispenser does not have enough cash
						screen.displayMessageLine("\nInsuffcient cash available in the ATM." + "\n\nPlease choose a smaller amount.");
					}
				}
				else{//not enough money available in user's account
					screen.displayMessageLine("\n insufficient funds in your account." + "]n\nPlease choose a smaller amount.");
				}
				
			}else{
				screen.displayMessageLine("\nCancelling transaction...");
				return;//return to main menu, because user cancelled
			}
		} while(!cashDispensed);
	}
	private int displayMenuOfAmounts(){
		int userChoice=0;
		Screen screen=getScreen();
		int[] amounts={0,20,40,60,100,200};
		while(userChoice==0){
			screen.displayMessageLine("\nWithdrawal menu:");
			screen.displayMessageLine("1 - $20");
			screen.displayMessageLine("2 - $40");
			screen.displayMessageLine("3 - $60");
			screen.displayMessageLine("4 - $100");
			screen.displayMessageLine("5 - $200");
			screen.displayMessageLine("6 - Cancel transaction");
			screen.displayMessage("\nChoose a withdrawal amount: ");
			int input = keypad.getInput();
			switch(input){
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				userChoice=amounts[input];
				break;
			case CANCELED:
				userChoice=CANCELED;
				break;
			default:
				screen.displayMessageLine("\nInvalid selection. Try again.");
			}
			
		}
		return userChoice;
	}

}
