package bank;
import bank.exceptions.*;

public class Account {
  private int id;
  private String accountType;
  private double accountBalance;


  public Account() {
  }


  public Account(int id, String accountType, double accountBalance) {
    this.id = id;
    this.accountType = accountType;
    this.accountBalance = accountBalance;
  }


  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAccountType() {
    return this.accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public double getAccountBalance() {
    return this.accountBalance;
  }

  public void setAccountBalance(double accountBalance) {
    this.accountBalance = accountBalance;
  }

  public void deposit(double amount) throws AmountException{
    if(amount < 1){
      throw new AmountException("the minimum deposit is 1.00");
    }
    else{
      double newBalance = this.accountBalance + amount;
      setAccountBalance(newBalance);
      DataSource.updateAccountBalance(id, newBalance);
    }
  }

  public void withdraw(double amount) throws AmountException {
    if(amount < 1){
      throw new AmountException("the minimum deposit is 1.00");
    }
    else if (amount > getAccountBalance()){
      throw new AmountException("you don't have enough balance to make this withdrawal");
    }
    else{
      double newBalance = this.accountBalance - amount;
      setAccountBalance(newBalance);
      DataSource.updateAccountBalance(id, newBalance);
    }
    
  }

}
