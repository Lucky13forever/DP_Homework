package ro.uvt.dp.interfaces;

import ro.uvt.dp.account.Account;
import ro.uvt.dp.exceptions.AmountException;

public interface Transfer {
	public void transfer(Account c, double s) throws AmountException;
}
