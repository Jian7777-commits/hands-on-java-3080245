package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {
  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Welcome to your personal bank");
    
    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);


    Customer customer = menu.authCustomer();

    if(customer != null){
      Account account = DataSource.getAccount(customer.getAccountId());
      System.out.println(account);
      menu.showMenu(customer, account);
    }



    menu.scanner.close();
  }

  private Customer authCustomer(){
    System.out.println("please enter username");
    String username = scanner.next();

    System.out.println("please enter password");
    String password = scanner.next();

    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("There was an error " + e.getMessage());
    }

    return customer;
  }

  private void showMenu(Customer customer, Account account){
    int selection = 0;

    while(selection != 4 && customer.isAuthenticated()){
      System.out.println("==========================================");
      System.out.println("Please select from the menu");
      System.out.println("1: Deposit \n2:Withdraw \n3:Check Balance \n4:Exit");
      System.out.println("==========================================");

      selection = scanner.nextInt();
      double amount;
      switch (selection) {
        case 1:
          System.out.println("Make a Deposit");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount); 
          } catch (AmountException e) {
            System.out.println(e.getMessage());
            System.out.println("input a valid amount");
          }
          break;
        case 2:
          System.out.println("Withdraw");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
            System.out.println("Check balance and try again");
          }
          break;
        case 3:
          System.out.println("Check Balance");
          System.out.println(account.getAccountBalance());
          break;
        case 4:
          System.out.println("Exit");
          Authenticator.logout(customer);
          System.out.println("Please Come Again");
          break;

        default:
          System.out.println("invalid Option, Try again");
          break;
      }

    }
  }
}
