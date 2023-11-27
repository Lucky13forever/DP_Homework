package ro.uvt.dp.interfaces;

import ro.uvt.dp.exceptions.AmountException;

public interface Operations extends Transfer{
	public double getTotalAmount();

	public double getInterest();

	public void depose(double amount) throws AmountException;

	public void retrieve(double amount) throws AmountException;
}
